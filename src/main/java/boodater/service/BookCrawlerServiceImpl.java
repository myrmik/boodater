package boodater.service;

import boodater.model.Book;
import boodater.model.BookStatus;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BookCrawlerServiceImpl extends BaseService implements BookCrawlerService {
    @Autowired
    DownloadService downloadService;

    private static final String AUTHOR_KEY = "Copyright";

    @Override
    public Book crawlBook(String url) {
        String html = downloadService.download(url);
        if (html == null)
            return null;

        Document doc = Jsoup.parse(html);
        String author = doc.select("body center table tbody li:containsOwn(" + AUTHOR_KEY + ") a").text();
        String name = doc.select("body center h2").text();

        Elements textItems = doc.select("body > dd");
        StringBuilder text = new StringBuilder();
        for (Element textItem : textItems) {
            String ownText = textItem.ownText().trim();
            if (ownText.isEmpty()) {
                ownText = textItem.tagName("font").text().trim();
            }

            text.append(ownText);
            text.append("\n");
        }

        Book book = new Book();
        book.setName(name);
        book.setAuthor(author);
        book.setText(text.toString());
        book.setDate(new Date());
        book.setStatus(BookStatus.NEW);
        book.setUrl(url);

        return book;
    }
}
