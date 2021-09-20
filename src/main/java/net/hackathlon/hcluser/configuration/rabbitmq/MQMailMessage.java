package net.hackathlon.hcluser.configuration.rabbitmq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MQMailMessage {

    private String messageId;
    private String userEmailId;
    private String username;
    private String password;

}
