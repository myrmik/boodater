package boodater.service;

import boodater.model.CurrentEpisode;

public interface NyaaCrawlerService {
    boolean downloadTorrent(CurrentEpisode episode, String filePath);
}
