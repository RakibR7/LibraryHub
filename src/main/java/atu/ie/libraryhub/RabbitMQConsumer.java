package atu.ie.libraryhub;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQConsumer {

    @Autowired
    private BookService bookService;

    @RabbitListener(queues = "borrow.book.queue")
    public void handleBorrowBookMessage(String message) {
        String[] data = message.split(",");
        Long bookId = Long.parseLong(data[1]);
        bookService.borrowBook(bookId);
    }

    @RabbitListener(queues = "return.book.queue")
    public void handleReturnBookMessage(String message) {
        String[] data = message.split(",");
        Long bookId = Long.parseLong(data[1]);
        bookService.returnBook(bookId);
    }
}

