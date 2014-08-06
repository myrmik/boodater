package boodater.dao;

import boodater.model.CurrentEpisode;

import java.util.List;

public interface CurrentEpisodeDao extends BaseDao<Integer, CurrentEpisode> {
    CurrentEpisode insertTorrent(CurrentEpisode episode);
    CurrentEpisode selectTorrent(int id);
    List<CurrentEpisode> selectAllTorrents();
    void deleteTorrent(int id);
}
