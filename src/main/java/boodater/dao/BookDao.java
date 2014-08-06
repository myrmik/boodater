package boodater.dao;

import boodater.model.Book;

public interface BookDao extends BaseDao<Integer, Book> {
    Book insertBook(Book book);
    Book selectBook(int id);
    Book selectBookByUrl(String url);
    void deleteBook(int id);
}
