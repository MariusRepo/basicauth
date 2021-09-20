package net.hackathlon.hcluser.user.login;

import lombok.*;
import net.hackathlon.hcluser.user.registration.UserInfo;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import static net.hackathlon.hcluser.user.login.InternalUserDetails.GUEST_ROLE;

@Table(name = "users")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @OneToOne(targetEntity = UserInfo.class, cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private UserInfo userInfo;

    private String authority;

    private boolean isNonExpired;

    private boolean isNonLocked;

    private boolean isCredentialsNonExpired;

    private boolean isEnabled;

    public User(String username, String password, UserInfo userInfo) {
        this.userInfo = userInfo;
        this.password = password;
        this.username = username;
        this.isEnabled = true;
        this.isCredentialsNonExpired = true;
        this.isNonLocked = true;
        this.isNonExpired = true;
        this.authority = GUEST_ROLE;
    }

}