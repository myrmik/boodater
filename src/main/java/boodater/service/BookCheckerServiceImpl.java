package boodater.service;

import boodater.dao.BookDao;
import boodater.model.Book;
import boodater.model.BookStatus;
import boodater.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BookCheckerServiceImpl extends BaseService implements BookCheckerService {
    public static final int DIFF_TEXT_FRAME_SIZE = 20;

    @Autowired
    BookDao bookDao;

    @Override
    public Book checkNewBook(Book book) {
        if (book == null)
            throw new IllegalArgumentException();

        Book storedBook;
        if (book.getId() != null) {
            storedBook = bookDao.selectBook(book.getId());
        } else if (book.getUrl() != null) {
            storedBook = bookDao.selectBookByUrl(book.getUrl());
        } else {
            throw new IllegalArgumentException();
        }

        if (storedBook != null) {
            String bookTextDiff = fetchBookTextDiff(book, storedBook);
            if (bookTextDiff != null) {
                storedBook.setText(book.getText());
                storedBook.setNewText(bookTextDiff);
                storedBook.setStatus(BookStatus.NEW);
                storedBook.setDate(new Date());
                if (storedBook.getAuthor() == null) {
                    storedBook.setAuthor(book.getAuthor());
                }
                if (storedBook.getName() == null) {
                    storedBook.setName(book.getName());
                }
            }
            return storedBook;
        }

        book.setNewText(book.getText());
        return book;
    }

    private String fetchBookTextDiff(Book newBook, Book oldBook) {
        if (newBook == null || oldBook == null)
            return null;

        String newText = newBook.getText();
        String oldText = oldBook.getText();

        if (newText == null)
            return null;

        if (oldText == null)
            return newText;

        if (newText.length() == oldText.length())
            return null;

        if (oldText.length() < DIFF_TEXT_FRAME_SIZE) {
            log.error("Old text is too short: " + oldText.length());
            return null;
        }

        String oldTextTail = oldText.substring(oldText.length() - DIFF_TEXT_FRAME_SIZE, oldText.length());
        if (!newText.contains(oldTextTail)) {
            log.error("New book text is different, but it does not contain old book text");
            return null;
        }

        return StringUtil.removeAllBefore(newText, oldTextTail);
    }
}
