package boodater.controller;

import boodater.dao.CurrentEpisodeDao;
import boodater.model.CurrentEpisode;
import boodater.model.DataTableResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class TorrentController extends BaseController {

    @Autowired
    private CurrentEpisodeDao currentEpisodeDao;

    @RequestMapping(value = {"/", "showTorrents"})
    public String showTorrents() {
        return "torrent";
    }

    @RequestMapping(value = {"/torrents"})
    public
    @ResponseBody
    DataTableResponse<CurrentEpisode> getTorrents() {
        List<CurrentEpisode> episodes = currentEpisodeDao.selectAllTorrents();
        return new DataTableResponse<>(episodes);
    }
}
