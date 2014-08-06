package boodater.schedule;

import boodater.dao.CurrentEpisodeDao;
import boodater.model.CurrentEpisode;
import boodater.model.TorrentProperties;
import boodater.service.NyaaCrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@EnableScheduling
@Component
public class TorrentSchedule extends BaseSchedule {

    @Autowired
    TorrentProperties torrentProperties;

    @Autowired
    NyaaCrawlerService nyaaCrawlerService;

    @Autowired
    CurrentEpisodeDao currentEpisodeDao;

    @Scheduled(fixedDelay=1800000) // 30min
    public void searchTorrents() {
        List<CurrentEpisode> currentEpisodes = currentEpisodeDao.selectAll();
        if (currentEpisodes == null) {
            log.warn("There is no torrents to download");
            return;
        }

        for (CurrentEpisode episode : currentEpisodes) {

            episode.setEpisode(episode.getEpisode() + 1);

            String filePath = torrentProperties.getSavePath() + episode + ".torrent";
            boolean downloaded = nyaaCrawlerService.downloadTorrent(episode, filePath);
            if (downloaded) {
                log.debug("Episode was downloaded: " + episode);
                currentEpisodeDao.insertTorrent(episode);
            }
        }
    }
}
