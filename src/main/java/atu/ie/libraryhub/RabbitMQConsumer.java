package atu.ie.libraryhub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQConsumer {
    private static final Logger logger = LoggerFactory.getLogger(RabbitMQConsumer.class);

    private final BookService bookService;

    public RabbitMQConsumer(BookService bookService) {
        this.bookService = bookService;
    }

    @RabbitListener(queues = RabbitMQConfig.BORROW_BOOK_QUEUE)
    public void handleBorrowBookMessage(String message) {
        logger.info("Received from borrow.book.queue: '{}'", message);
        // Example parse logic:
        // "User 2 borrowed Book 10" => user #2, book #10
        // you can parse the string carefully if needed:
        // or keep it simple if you just need to do some logic

        // Suppose you want to parse out bookId
        // Actually you might have put "2,10" as a CSV, but let's assume you store more text
        // For real code, you'd define a clearer format or use JSON.

        // Example: just log or update DB
        // bookService.borrowBook(bookId)...
    }

    @RabbitListener(queues = RabbitMQConfig.RETURN_BOOK_QUEUE)
    public void handleReturnBookMessage(String message) {
        logger.info("Received from return.book.queue: '{}'", message);
        // parse and do your logic
    }

    @RabbitListener(queues = RabbitMQConfig.RECOMMENDATION_QUEUE)
    public void handleRecommendationMessage(String message) {
        logger.info("Received from recommendation.queue: '{}'", message);
        // parse, update recommended books, etc.
    }
}

