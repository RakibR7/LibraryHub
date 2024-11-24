package atu.ie.libraryhub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(Long id, Book updatedBook) {
        if (bookRepository.existsById(id)) {
            updatedBook.setId(id);
            return bookRepository.save(updatedBook);
        }
        return null;
    }

    public void deleteBook(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
        }
    }

    public boolean borrowBook(Long bookId) {
        Book book = bookRepository.findById(bookId).orElse(null);

        if (book != null && book.getAvailability()) {
            book.setAvailability(false);
            bookRepository.save(book);
            return true;
        }
        return false;
    }

    public void returnBook(Long bookId) {
        Book book = bookRepository.findById(bookId).orElse(null);

        if (book != null) {
            book.setAvailability(true);
            bookRepository.save(book);
        }
    }
}

