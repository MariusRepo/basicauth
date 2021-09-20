package net.hackathlon.hcluser.user.login;

import lombok.extern.slf4j.Slf4j;
import net.hackathlon.hcluser.configuration.rabbitmq.MQConfig;
import net.hackathlon.hcluser.configuration.rabbitmq.MQMailMessage;
import net.hackathlon.hcluser.configuration.rabbitmq.MQRegistrationMessage;
import net.hackathlon.hcluser.user.registration.UserInfo;
import net.hackathlon.hcluser.user.registration.UserInfoService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
@Slf4j
public class UserLoginRegistrationListener {

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    RabbitTemplate template;

    @RabbitListener(queues = MQConfig.REGISTRATION_QUEUE)
    public void registrationListener(MQRegistrationMessage message) {
        log.info("Message: " + message + " received from queue: " + MQConfig.REGISTRATION_QUEUE);
        UserInfo userInfo = userInfoService
                .findUserInfoById(message.getUserInfoId())
                .orElseThrow();

        Map<String, String> credentials = userService.generateRandomCredentials();

        User user = new User(credentials.get("username"), credentials.get("password"), userInfo);
        user.setPassword(passwordEncoder.encode(credentials.get("password")));

        userService.storeUserCredentials(user);

        sendEmailNotification(message.getUserEmailId(),
                credentials.get("username"),
                credentials.get("password"));
    }

    private void sendEmailNotification(String email, String username, String password) {
        log.info("EMail notification has been sent!");
        template.convertAndSend(
                MQConfig.EXCHANGE,
                MQConfig.EMAIL_ROUTING,
                new MQMailMessage(UUID.randomUUID().toString(), email, username, password));
    }

}
