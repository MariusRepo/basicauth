package net.hackathlon.hcluser.user.registration;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoRegistrationRequest {

    @NotBlank(message = "'First Name' is mandatory")
    private String first_name;

    @NotBlank(message = "'Last Name' is mandatory")
    private String last_name;

    @NotBlank(message = "'Email' is mandatory")
    @Email(message = "'Email' has to be valid")
    private String email_id;

    @NotNull(message = "'Primary Contact' is mandatory")
    @Size(message = "'Primary Contact' has to be between 2 and 14 characters", min = 2, max = 14)
    private String primary_contact;

    @NotNull(message = "'Secondary Contact' is mandatory")
    @Size(message = "'Secondary Contact' has to be between 2 and 14 characters", min = 2, max = 14)
    private String secondary_contact;

    @NotBlank(message = "'National document id name' is mandatory")
    private String nid_name;

    @NotBlank(message = "'National document id number' is mandatory")
    private String nid_doc_id;

    private Date last_login;
}