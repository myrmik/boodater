package boodater.controller;

import boodater.dao.BookDao;
import boodater.model.Book;
import boodater.service.BookProcessService;
import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeUtility;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
public class BookController extends BaseController {


    @Autowired
    private BookProcessService bookProcessService;

    @Autowired
    private BookDao bookDao;

    @RequestMapping(value = {"/", "showBooks"})
    public String showBooks(Model model) {

        List<Book> books = bookDao.selectAll();
        model.addAttribute("books", books);

        return "book";
    }

    @RequestMapping(value = "/addBook")
    public String addBook(@RequestParam(value = "bookUrl", required = true) String bookUrl) {

        bookProcessService.processBook(bookUrl);

        return "redirect:showBooks";
    }

    @RequestMapping(value = "/delBook")
    public String delBook(@RequestParam(value = "bookId", required = true) int bookId) {

        bookDao.deleteBook(bookId);

        return "redirect:showBooks";
    }

    @RequestMapping(value = "/getBook/{bookId}")
    public  void getBook(@PathVariable int bookId, HttpServletResponse response) throws IOException {

        Book book = bookDao.selectBook(bookId);
        if (book == null || book.getText() == null)
            return;

        responseBook(response, book, book.getText());
    }

    @RequestMapping(value = "/getNewBook/{bookId}")
    public  void getNewBook(@PathVariable int bookId, HttpServletResponse response) throws IOException {

        Book book = bookDao.selectBook(bookId);
        if (book == null || book.getNewText() == null)
            return;

        bookProcessService.readBook(book);

        responseBook(response, book, book.getNewText());
    }

    private void responseBook(HttpServletResponse response, Book book, String bookText) throws IOException {
        String fileName = book.getAuthor()   + "." + book.getName();
        fileName = MimeUtility.encodeText(fileName.replace(" ", "_") + ".txt", "UTF8", "Q");

        response.setContentType("text/plain");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        InputStream is = IOUtils.toInputStream(bookText);
        IOUtils.copy(is, response.getOutputStream());
        response.flushBuffer();
    }

}
