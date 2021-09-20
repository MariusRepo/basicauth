package net.hackathlon.hcluser.user.login;

import lombok.extern.slf4j.Slf4j;
import net.hackathlon.hcluser.configuration.rabbitmq.MQConfig;
import net.hackathlon.hcluser.configuration.rabbitmq.MQMailMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserEmailRegistrationListener {

    @RabbitListener(queues = MQConfig.EMAIL_QUEUE)
    public void registrationListener(MQMailMessage message) {
        log.info("Email has been sent to: " + message.getUserEmailId() + " | username: " + message.getUsername() + " | password: " + message.getPassword());
    }

}
