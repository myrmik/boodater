package boodater.model;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="book")
public class Book {
    @Id
    @GeneratedValue
    private Integer id;

    @Column
    private String author;

    @Column
    private String name;

    @Lob
    @Column(length = Integer.MAX_VALUE)
    private String text;

    @Lob
    @Column(name="new_text", length = Integer.MAX_VALUE)
    private String newText;

    @Column
    @Enumerated(EnumType.STRING)
    private BookStatus status;

    @Column
    private Date date;

    @Column
    private String url;

    public Book() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        return !(id != null ? !id.equals(book.id) : book.id != null);

    }

    public int getNewTextPercent() {
        if (text == null || newText == null)
            return 0;
        return (newText.length() - text.length()) / newText.length();
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", date=" + date +
                ", url='" + url + '\'' +
                ", textSize=" + (text == null ? 0 : text.length()) +
                ", newTextSize=" + (newText == null ? 0 : newText.length()) +
                '}';
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getNewText() {
        return newText;
    }

    public void setNewText(String newText) {
        this.newText = newText;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
