package boodater.service;

import boodater.dao.CurrentEpisodeDao;
import boodater.model.CurrentEpisode;
import boodater.util.StringUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NyaaCrawlerServiceImpl extends BaseService implements NyaaCrawlerService {
    private static final String TORRENT_OWNER = "HorribleSubs";
    private static final String TORRENT_QUALITY = "720p";

    @Autowired
    DownloadService downloadService;

    @Autowired
    CurrentEpisodeDao currentEpisodeDao;

    @Override
    public boolean downloadTorrent(CurrentEpisode episode, String filePath) {
        String query = TORRENT_OWNER + " " + episode.getName() + " " + episode.getEpisodeString() + " " + TORRENT_QUALITY;
        String url = "http://www.nyaa.se/?term=" + StringUtil.encodeUrl(query);
        String html = downloadService.download(url);
        if (html == null)
            return false;

        Document doc = Jsoup.parse(html);
        Elements rowItems = doc.select("tr.tlistrow");
        for (Element rowItem : rowItems) {
            Elements nameItem = rowItem.select("td.tlistname > a");
            String name = nameItem.text();
            if (isMatch(episode, name)) {
                Elements urlItem = rowItem.select("td.tlistdownload > a");
                if (urlItem != null) {
                    String href = urlItem.attr("href");
                    downloadService.downloadToFile(href, filePath);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isMatch(CurrentEpisode episode, String name) {
        return !(episode == null || StringUtil.isEmpty(name))
                && name.contains(TORRENT_OWNER) && name.contains(episode.getName()) && name.contains(" " + episode.getEpisodeString() + " ");
    }
}
