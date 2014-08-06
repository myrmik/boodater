package boodater.service;

import boodater.model.Book;

public interface BookProcessService {
    Book processBook(String bookUrl);
    void readBook(Book book);
}
