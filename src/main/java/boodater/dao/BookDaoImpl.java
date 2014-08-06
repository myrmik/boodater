package boodater.dao;

import boodater.model.Book;
import org.springframework.stereotype.Repository;

@Repository
public class BookDaoImpl extends BaseDaoImpl<Integer, Book> implements BookDao {
    @Override
    public Book insertBook(Book book) {
        if (book == null)
            throw new IllegalArgumentException("Book is null");
        insert(book);
        return book;
    }

    @Override
    public Book selectBook(int id) {
        return select(id);
    }

    @Override
    public Book selectBookByUrl(String url) {
        return selectByCriteria("url", url);
    }

    @Override
    public void deleteBook(int id) {
        delete(id);
    }
}
