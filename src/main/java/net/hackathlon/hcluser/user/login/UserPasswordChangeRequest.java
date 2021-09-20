package net.hackathlon.hcluser.user.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPasswordChangeRequest {
    @NotBlank(message = "'password' is mandatory!")
    @Size(message = "'password' must be between 8 and 16 characters!", min = 8, max = 16)
    private String password;
}
