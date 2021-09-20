package net.hackathlon.hcluser.user.registration;

import lombok.extern.slf4j.Slf4j;
import net.hackathlon.hcluser.configuration.rabbitmq.MQConfig;
import net.hackathlon.hcluser.configuration.rabbitmq.MQRegistrationMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Slf4j
@RestController
public class UserRegistrationController {

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    RabbitTemplate template;

    @PostMapping("/api/user/registration")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserInfoRegistrationRequest userInfoRegistrationRequest) {
        log.info("Registration form received ! " + userInfoRegistrationRequest);

        UserInfo userInfo = new UserInfo(userInfoRegistrationRequest);
        Long userInfoId = userInfoService.registerUserInfo(userInfo);
        template.convertAndSend(
                MQConfig.EXCHANGE,
                MQConfig.REGISTRATION_ROUTING,
                new MQRegistrationMessage(UUID.randomUUID().toString(), userInfoId, userInfo.getEmail_id()));

        return new ResponseEntity<>("Registration form submitted successfully! \n Please change the password before using the platform!", HttpStatus.CREATED);
    }

}
