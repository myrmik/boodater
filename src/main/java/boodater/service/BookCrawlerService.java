package boodater.service;

import boodater.model.Book;

public interface BookCrawlerService {
    Book crawlBook(String url);
}
