package boodater.service;

import boodater.MockTest;
import boodater.dao.BookDao;
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
import static org.mockito.Mockito.when;

public class BookCheckerServiceTest extends MockTest {

    @Mock
    BookDao bookDao;

    @Autowired
    @InjectMocks
    private BookCheckerService bookCheckerService;

    @Value("html/bookCrawled.txt")
    private Resource testBookResource;
    @Value("html/newBookCrawled.txt")
    private Resource testNewBookResource;
    @Value("html/addedBookText.txt")
    private Resource testAddedBookTextResource;

    private String testBookText;
    private String testNewBookText;
    private String testAddedBookText;

    @Before
    public void readFile() throws IOException {
        if (testBookText == null)
            testBookText = IOUtils.toString(testBookResource.getInputStream());
        if (testNewBookText == null)
            testNewBookText = IOUtils.toString(testNewBookResource.getInputStream());
        if (testAddedBookText == null)
            testAddedBookText = IOUtils.toString(testAddedBookTextResource.getInputStream());
    }

    @Test(expected=IllegalArgumentException.class)
    public void checkNewBook_argument_null() throws Exception {
        // do
        bookCheckerService.checkNewBook(null);
    }

    @Test
    public void checkNewBook_newBook() throws Exception {
        // given
        Book newBook = new Book();
        newBook.setUrl("url");
        newBook.setText("text");

        // do
        Book checkedBook = bookCheckerService.checkNewBook(newBook);

        // verify
        assertNotNull(checkedBook);
        assertNotNull(checkedBook.getText());
        assertNotNull(checkedBook.getNewText());
        assertTrue(newBook == checkedBook);
        assertEquals(checkedBook.getText(), checkedBook.getNewText());
    }

    @Test
    public void checkNewBook_sameBook() throws Exception {
        // given
        Book oldBook = new Book();
        oldBook.setId(1);
        oldBook.setUrl("url");
        oldBook.setText("text");
        oldBook.setNewText("text");

        Book newBook = new Book();
        newBook.setUrl("url");
        newBook.setText("text");

        when(bookDao.selectBookByUrl(newBook.getUrl())).thenReturn(oldBook);

        // do
        Book checkedBook = bookCheckerService.checkNewBook(newBook);

        // verify
        assertNotNull(checkedBook);
        assertNotNull(checkedBook.getText());
        assertNotNull(checkedBook.getNewText());
        assertTrue(checkedBook == oldBook);
        assertEquals(checkedBook.getText(), checkedBook.getNewText());
    }

    @Test
    public void checkNewBook_updatedBookWithSimpleText() throws Exception {
        // given
        Book oldBook = new Book();
        oldBook.setId(1);
        oldBook.setUrl("url");
        oldBook.setText("text text text text text");
        oldBook.setNewText("text text text text text");

        Book newBook = new Book();
        newBook.setUrl("url");
        newBook.setText(oldBook.getText() + ", new text");

        String expectedText = newBook.getText();
        String expectedNewText = ", new text";

        when(bookDao.selectBookByUrl(newBook.getUrl())).thenReturn(oldBook);

        // do
        Book checkedBook = bookCheckerService.checkNewBook(newBook);

        // verify
        assertNotNull(checkedBook);
        assertNotNull(checkedBook.getText());
        assertNotNull(checkedBook.getNewText());
        assertTrue(checkedBook == oldBook);
        assertEquals(expectedText, checkedBook.getText());
        assertEquals(expectedNewText, checkedBook.getNewText());
    }

    @Test
    public void checkNewBook_updatedBookWithRealText() throws Exception {
        // given
        Book oldBook = new Book();
        oldBook.setId(1);
        oldBook.setUrl("url");
        oldBook.setText(testBookText);
        oldBook.setNewText(testBookText);

        Book newBook = new Book();
        newBook.setUrl("url");
        newBook.setText(testNewBookText);

        String expectedText = newBook.getText();
        String expectedNewText = testAddedBookText;

        when(bookDao.selectBookByUrl(newBook.getUrl())).thenReturn(oldBook);

        // do
        Book checkedBook = bookCheckerService.checkNewBook(newBook);

        // verify
        assertNotNull(checkedBook);
        assertNotNull(checkedBook.getText());
        assertNotNull(checkedBook.getNewText());
        assertTrue(checkedBook == oldBook);
        assertEquals(expectedText, checkedBook.getText());
        assertEquals(expectedNewText, checkedBook.getNewText());
    }
}