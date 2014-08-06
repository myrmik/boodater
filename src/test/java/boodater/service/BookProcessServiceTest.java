package boodater.service;

import boodater.MockTest;
import boodater.dao.BookDao;
import boodater.model.Book;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BookProcessServiceTest extends MockTest {

    @Mock
    BookDao bookDao;

    @Mock
    BookCrawlerService bookCrawlerService;

    @Mock
    BookCheckerService bookCheckerService;

    @Autowired
    @InjectMocks
    private BookProcessService bookProcessService;

    @Test(expected=IllegalArgumentException.class)
    public void processBook_argument_null() {

        // do
        bookProcessService.processBook(null);
    }

    @Test
    public void processBook_hp() {
        // given
        Book book = new Book();

        when(bookCrawlerService.crawlBook(anyString())).thenReturn(book);
        when(bookCheckerService.checkNewBook(book)).thenReturn(book);

        // do
        bookProcessService.processBook("url");

        // verify
        verify(bookDao).insertBook(book);
    }
}