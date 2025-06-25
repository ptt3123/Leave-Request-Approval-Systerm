package com.example.gmail_service.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "notify-exchange";
    public static final String EMAIL_QUEUE_GMAIL = "email-queue.gmail";
    public static final String EMAIL_ROUTING_KEY = "notify.email";

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Queue gmailEmailQueue() {
        return new Queue(EMAIL_QUEUE_GMAIL, true);
    }

    @Bean
    public Binding emailBinding() {
        return BindingBuilder
                .bind(gmailEmailQueue())
                .to(exchange())
                .with(EMAIL_ROUTING_KEY);
    }

    // Khai báo converter mặc định cho toàn app
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
