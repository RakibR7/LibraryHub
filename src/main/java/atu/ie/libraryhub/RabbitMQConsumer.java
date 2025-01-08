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
    public void receiveBorrowBookMessage(String message) throws InterruptedException {
        System.out.println("Received message: " + message);
        Thread.sleep(5000);
    }
    @RabbitListener(queues = RabbitMQConfig.RETURN_BOOK_QUEUE)
    public void handleReturnBookMessage(String message) throws InterruptedException {
        System.out.println("Received message: " + message);
        Thread.sleep(5000);
    }
}
