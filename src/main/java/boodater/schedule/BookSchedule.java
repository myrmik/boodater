package boodater.schedule;

import boodater.dao.BookDao;
import boodater.model.Book;
import boodater.service.BookProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@EnableScheduling
@Component
public class BookSchedule extends BaseSchedule {

    @Autowired
    BookDao bookDao;

    @Autowired
    BookProcessService bookProcessService;

    //@Scheduled(fixedDelay=3600000) // 1 hour
    public void updateBooks() {
        List<Book> books = bookDao.selectAll();
        for (Book book : books) {
            processBook(book);
        }
    }

    private void processBook(Book book) {
        try {
            Book processedBook = bookProcessService.processBook(book.getUrl());
            log.debug("Book was processed, result: " + processedBook);
        } catch (Exception e) {
            log.error("Book was not processed, book: " + book, e);
        }
    }
}
