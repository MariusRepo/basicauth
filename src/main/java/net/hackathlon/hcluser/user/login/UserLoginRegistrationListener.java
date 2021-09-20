package net.hackathlon.hcluser.user.login;

import lombok.extern.slf4j.Slf4j;
import net.hackathlon.hcluser.configuration.rabbitmq.MQConfig;
import net.hackathlon.hcluser.configuration.rabbitmq.MQRegistrationMessage;
import net.hackathlon.hcluser.user.registration.UserInfo;
import net.hackathlon.hcluser.user.registration.UserInfoService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class UserLoginRegistrationListener {

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RabbitListener(queues = MQConfig.QUEUE)
    public void registrationListener(MQRegistrationMessage message) {
        log.info("Message: " + message + " received from queue: " + MQConfig.QUEUE);
        UserInfo userInfo = userInfoService
                .findUserInfoById(message.getUserInfoId())
                .orElseThrow();

        Map<String, String> credentials = userService.generateRandomCredentials();

        User user = new User(credentials.get("username"), credentials.get("password"), userInfo);
        user.setPassword(passwordEncoder.encode(credentials.get("password")));

        userService.storeUserCredentials(user);

        sendEmail(message.getUserEmailId(),
                credentials.get("username"),
                credentials.get("password"));
    }

    private void sendEmail(String email, String username, String password) {
        log.info("Email has been sent to: " + email + " | username: " + username + " | password: " + password);
    }

}
