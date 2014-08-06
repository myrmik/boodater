package boodater.dao;

import boodater.BaseTest;
import boodater.model.CurrentEpisode;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class CurrentEpisodeDaoTest extends BaseTest {

    @Autowired
    private CurrentEpisodeDao currentEpisodeDao;

    @Test
    public void selectAllTorrents_hp() throws Exception {
        // do
        List<CurrentEpisode> currentEpisodes = currentEpisodeDao.selectAllTorrents();

        // verify
        assertNotNull(currentEpisodes);
        assertEquals(3, currentEpisodes.size());
    }
}