package boodater.service;

import boodater.model.CurrentEpisode;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;

import static boodater.util.StringUtil.encodeUrl;
import static boodater.util.StringUtil.isEmpty;

@Service
public class NyaaRssCrawlerService extends BaseService implements NyaaCrawlerService {
    private static final String TORRENT_OWNER = "HorribleSubs";
    private static final String TORRENT_QUALITY = "720p";

    @Autowired
    DownloadService downloadService;

    @Override
    public boolean downloadTorrent(CurrentEpisode episode, String filePath) {
        String query = TORRENT_OWNER + " " + episode.getName() + " " + episode.getEpisodeString() + " " + TORRENT_QUALITY;
        String url = "http://www.nyaa.se/?page=rss&term=" + encodeUrl(query);
        String rss = downloadService.download(url);
        if (rss == null)
            return false;

        String episodeUrl = parseEpisodeUrl(episode, rss);

        if (!isEmpty(episodeUrl)) {
            downloadService.downloadToFile(episodeUrl, filePath);
            return true;
        }

        return false;
    }

    private String parseEpisodeUrl(CurrentEpisode episode, String rss) {
        Document doc = Jsoup.parse(rss);
        Elements items = doc.select("rss > channel > item");

        return items.stream()
                .filter(elementsBy(episode))
                .map(Element::ownText) // link
                .findFirst().orElse(null);
    }

    private Predicate<Element> elementsBy(CurrentEpisode episode) {
        return e -> {
            String name = e.select("title").first().text();
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
