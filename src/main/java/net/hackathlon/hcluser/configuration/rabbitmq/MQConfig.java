package net.hackathlon.hcluser.configuration.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {

    public static final String EXCHANGE = "message_exchange";

    public static final String REGISTRATION_QUEUE = "registration_message_queue";
    public static final String REGISTRATION_ROUTING = "registration_message_routing";

    public static final String EMAIL_QUEUE = "email_message_queue";
    public static final String EMAIL_ROUTING = "email_message_routing";

    @Bean
    public Queue regQueue() {
        return new Queue(REGISTRATION_QUEUE);
    }

    @Bean
    public Queue mailQueue() {
        return new Queue(EMAIL_QUEUE);
    }

    @Bean
    public TopicExchange mainTopicExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Binding regBinding(Queue regQueue, TopicExchange mainTopicExchange) {
        return BindingBuilder
                .bind(regQueue)
                .to(mainTopicExchange)
                .with(REGISTRATION_ROUTING);
    }

    @Bean
    public Binding mailBinding(Queue mailQueue, TopicExchange mainTopicExchange) {
        return BindingBuilder
                .bind(mailQueue)
                .to(mainTopicExchange)
                .with(EMAIL_ROUTING);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

}
