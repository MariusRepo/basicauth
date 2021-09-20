package net.hackathlon.hcluser.configuration.rabbitmq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MQRegistrationMessage {

    private String messageId;
    private Long userInfoId;
    private String userEmailId;

}
