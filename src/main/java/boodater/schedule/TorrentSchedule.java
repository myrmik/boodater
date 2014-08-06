package boodater.schedule;

import boodater.dao.CurrentEpisodeDao;
import boodater.model.CurrentEpisode;
import boodater.model.TorrentProperties;
import boodater.service.NyaaCrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@EnableScheduling
@Component
public class TorrentSchedule extends BaseSchedule {

    private final static int DOWNLOAD_DELAY = 1800000; // 30 min
    private final static int DOWNLOAD_ONE_DELAY = 5000; // 5 sec

    @Autowired
    TorrentProperties torrentProperties;

    @Autowired
    NyaaCrawlerService nyaaCrawlerService;

    @Autowired
    CurrentEpisodeDao currentEpisodeDao;

    @Scheduled(fixedDelay=DOWNLOAD_DELAY)
    public void searchTorrents() {
        List<CurrentEpisode> currentEpisodes = currentEpisodeDao.selectAll();
        if (currentEpisodes == null) {
            log.warn("There is no torrents to download");
            return;
        }

        log.debug("Started search for torrents, size: " + currentEpisodes.size());
        int downloadedNbr = 0;
        for (CurrentEpisode episode : currentEpisodes) {
            episode.setEpisode(episode.getEpisode() + 1); // increase # of episode
            String filePath = torrentProperties.getSavePath() + episode + ".torrent";
            boolean downloaded = nyaaCrawlerService.downloadTorrent(episode, filePath);
            if (downloaded) {
                log.debug("Episode was downloaded: " + episode);
                episode.setDate(new Date());
                currentEpisodeDao.insertTorrent(episode);
                ++downloadedNbr;
            } else {
                log.debug("There is no such episode yet: " + episode);
            }

            sleepForTorrent();
        }
        log.debug("Finished search for torrents, size: " + currentEpisodes.size() + ", downloaded: " + downloadedNbr);
    }

    private void sleepForTorrent() {
        try {
            Thread.sleep(DOWNLOAD_ONE_DELAY);
        } catch (InterruptedException e) {
            log.warn("Download sleep was interrupted", e);
        }
    }
}
