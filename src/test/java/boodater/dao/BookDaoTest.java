package boodater.dao;

import boodater.BaseTest;
import boodater.model.Book;
import boodater.model.BookStatus;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BookDaoTest extends BaseTest {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private BookDao bookDao;

    @Test(expected = IllegalArgumentException.class)
    public void insertBook_argument_null() throws Exception {
        bookDao.insertBook(null);
    }

    @Test
    @Transactional
    public void insertBook_hp() throws Exception {
        // given
        Book book = new Book();
        book.setAuthor("bookAuthor");
        book.setName("bookName");
        book.setText("bookText");
        book.setNewText("bookNewText");
        book.setStatus(BookStatus.NEW);
        book.setDate(new Date());
        book.setUrl("url");

        // do
        Book returnedBook = bookDao.insertBook(book);

        // verify
        assertBook(book, returnedBook);
    }

    @Test
    public void selectBook_hp() throws Exception {
        // given
        Book expectedBook = new Book();
        expectedBook.setId(1);
        expectedBook.setAuthor("author1");
        expectedBook.setName("name1");
        expectedBook.setText("text1");
        expectedBook.setNewText("newText1");
        expectedBook.setStatus(BookStatus.NEW);
        expectedBook.setDate(dateFormat.parse("2014-05-21"));
        expectedBook.setUrl("http://samlib.ru/m/metelxskij_n_a/junling.shtml");

        // do
        Book actualBook = bookDao.selectBook(1);

        // verify
        assertBook(expectedBook, actualBook);
    }

    @Test
    @Transactional
    public void deleteBook_hp() throws Exception {
        // given
        int id = 1;

        // do
        bookDao.deleteBook(id);

        // verify
        assertEquals(null, bookDao.selectBook(1));
    }

    private void assertBook(Book expectedBook, Book actualBook) {
        assertNotNull(actualBook);
        assertNotNull(actualBook.getId());
        assertNotNull(actualBook.getAuthor());
        assertNotNull(actualBook.getName());
        assertNotNull(actualBook.getText());
        assertNotNull(actualBook.getNewText());
        assertNotNull(actualBook.getStatus());
        assertNotNull(actualBook.getDate());
        assertNotNull(actualBook.getUrl());
        assertEquals(expectedBook, actualBook);
        assertEquals(expectedBook.getAuthor(), actualBook.getAuthor());
        assertEquals(expectedBook.getName(), actualBook.getName());
        assertEquals(expectedBook.getText(), actualBook.getText());
        assertEquals(expectedBook.getNewText(), actualBook.getNewText());
        assertEquals(expectedBook.getStatus(), actualBook.getStatus());
        assertEquals(expectedBook.getDate(), actualBook.getDate());
        assertEquals(expectedBook.getUrl(), actualBook.getUrl());
    }
}
