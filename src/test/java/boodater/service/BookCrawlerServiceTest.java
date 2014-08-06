package boodater.service;

import boodater.MockTest;
import boodater.model.Book;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class BookCrawlerServiceTest extends MockTest {

    @Mock
    DownloadService downloadService;

    @Autowired
    @InjectMocks
    private BookCrawlerService bookCrawlerService;

    @Value("html/book.html")
    private Resource testBookResource;

    private String testBookHtml;

    @Before
    public void readFile() throws IOException {
        if (testBookHtml == null)
            testBookHtml = IOUtils.toString(testBookResource.getInputStream());
    }

    @Test(expected=IllegalArgumentException.class)
    public void crawlBook_argument_null() {
        // given
        doThrow(new IllegalArgumentException()).when(downloadService).download(null);

        // do
        bookCrawlerService.crawlBook(null);
    }

    @Test
    public void crawlBook_hp() {
        // given
        String expectedBookAuthor = "Метельский Николай Александрович";
        String expectedBookName = "Юнлинг";
        int expectedBookContentSize = 1017141;

        when(downloadService.download(anyString())).thenReturn(testBookHtml);

        // do
        Book book = bookCrawlerService.crawlBook("URL");

        // verify
        assertNotNull(book);
        assertNotNull(book.getAuthor());
        assertNotNull(book.getName());
        assertNotNull(book.getText());
        assertEquals(expectedBookAuthor, book.getAuthor());
        assertEquals(expectedBookName, book.getName());
        assertEquals("Book content is wrong", expectedBookContentSize, book.getText().length());
    }
}