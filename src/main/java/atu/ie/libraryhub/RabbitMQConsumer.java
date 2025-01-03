package atu.ie.libraryhub;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQConsumer {

    private final BookService bookService;

    public RabbitMQConsumer(BookService bookService) {
        this.bookService = bookService;
    }

    @RabbitListener(queues = "borrow.book.queue")
    public void handleBorrowBookMessage(String message) {
        // parse "userId,bookId"
        String[] data = message.split(",");
        Long userId = Long.parseLong(data[0]);
        Long bookId = Long.parseLong(data[1]);

        // Call BookService to mark the book as borrowed
        bookService.borrowBook(bookId);
        // Possibly log or do something with userId
    }

    @RabbitListener(queues = "return.book.queue")
    public void handleReturnBookMessage(String message) {
        String[] data = message.split(",");
        Long userId = Long.parseLong(data[0]);
        Long bookId = Long.parseLong(data[1]);

        bookService.returnBook(bookId);
        // Possibly do something with userId
    }

    // If needed, for recommendations:
    @RabbitListener(queues = "recommendation.queue")
    public void handleRecommendationMessage(String message) {
        String[] data = message.split(",");
        Long userId = Long.parseLong(data[0]);
        Long bookId = Long.parseLong(data[1]);
        String action = data[2]; // "borrow" or "return"

        // Possibly generate or update recommended books
    }
}


