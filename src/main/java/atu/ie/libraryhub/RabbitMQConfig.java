package atu.ie.libraryhub;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue borrowBookQueue() {
        return new Queue("borrow.book.queue", true);
    }

    @Bean
    public Queue returnBookQueue() {
        return new Queue("return.book.queue", true);
    }
}
