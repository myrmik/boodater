package boodater.service;

import boodater.dao.BookDao;
import boodater.model.Book;
import boodater.model.BookStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookProcessServiceImpl extends BaseService implements BookProcessService {
    @Autowired
    BookDao bookDao;

    @Autowired
    BookCrawlerService bookCrawlerService;

    @Autowired
    BookCheckerService bookCheckerService;

    @Override
    public Book processBook(String bookUrl) {
        if (bookUrl == null)
            throw new IllegalArgumentException();

        Book crawledBook = bookCrawlerService.crawlBook(bookUrl);
        if (crawledBook == null) {
            log.warn("Book was not crawled, url: " + bookUrl);
            return null;
        }

        Book checkedBook = bookCheckerService.checkNewBook(crawledBook);
        return bookDao.insertBook(checkedBook);
    }

    @Override
    public void readBook(Book book) {
        book.setStatus(BookStatus.OLD);
        bookDao.insertBook(book);
    }
}
