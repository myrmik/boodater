package boodater.controller;

import boodater.dao.CurrentEpisodeDao;
import boodater.model.Book;
import boodater.model.CurrentEpisode;
import boodater.model.DataTableResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class TorrentController extends BaseController {

    @Autowired
    private CurrentEpisodeDao currentEpisodeDao;

    @RequestMapping(value = {"showTorrents"})
    public String showTorrents(Model model) {
        return "torrent";
    }

    @RequestMapping(value = {"/torrents"})
    public
    @ResponseBody
    DataTableResponse<CurrentEpisode> showBooks() {
        List<CurrentEpisode> episodes = currentEpisodeDao.selectAllTorrents();
        return new DataTableResponse<CurrentEpisode>(episodes);
    }
}
