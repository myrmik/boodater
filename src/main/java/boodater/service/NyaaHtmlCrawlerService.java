package boodater.service;

import boodater.model.CurrentEpisode;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;
import java.util.function.Predicate;

import static boodater.util.StringUtil.encodeUrl;
import static boodater.util.StringUtil.isEmpty;

public class NyaaHtmlCrawlerService extends BaseService implements NyaaCrawlerService {
    private static final String TORRENT_OWNER = "HorribleSubs";
    private static final String TORRENT_QUALITY = "720p";

    @Autowired
    DownloadService downloadService;

    @Override
    public boolean downloadTorrent(CurrentEpisode episode, String filePath) {
        String query = TORRENT_OWNER + " " + episode.getName() + " " + episode.getEpisodeString() + " " + TORRENT_QUALITY;
        String url = "http://www.nyaa.se/?term=" + encodeUrl(query);
        String html = downloadService.download(url);
        if (html == null)
            return false;

        String episodeUrl = parseEpisodeUrl(episode, html);

        if (episodeUrl != null) {
            downloadService.downloadToFile(episodeUrl, filePath);
            return true;
        }

        return false;
    }

    private String parseEpisodeUrl(CurrentEpisode episode, String html) {
        Document doc = Jsoup.parse(html);
        Elements rowItems = doc.select("tr.tlistrow");

        return rowItems.stream()
                .filter(elementsBy(episode))
                .map(e -> e.select("td.tlistdownload > a"))
                .filter(Objects::nonNull)
                .map(e -> e.attr("href"))
                .findFirst().orElse(null);
    }

    private Predicate<Element> elementsBy(CurrentEpisode episode) {
        return e -> {
            String name = e.select("td.tlistname > a").text();
            return isMatch(episode, name);
        };
    }

    private boolean isMatch(CurrentEpisode episode, String name) {
        return !(episode == null || isEmpty(name))
                && name.contains(TORRENT_OWNER)
                && name.contains(episode.getName())
                && name.contains(" " + episode.getEpisodeString() + " ");
    }
}
