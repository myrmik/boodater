package boodater.dao;

import boodater.model.CurrentEpisode;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CurrentEpisodeDaoImpl extends BaseDaoImpl<Integer, CurrentEpisode> implements CurrentEpisodeDao {
    @Override
    public CurrentEpisode insertTorrent(CurrentEpisode episode) {
        if (episode == null)
            throw new IllegalArgumentException("Book is null");
        insert(episode);
        return episode;
    }

    @Override
    public CurrentEpisode selectTorrent(int id) {
        return select(id);
    }

    @Override
    public List<CurrentEpisode> selectAllTorrents() {
        return selectAll();
    }

    @Override
    public void deleteTorrent(int id) {
        delete(id);
    }
}
