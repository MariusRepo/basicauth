package net.hackathlon.hcluser.user.login;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.util.Date;

import static net.hackathlon.hcluser.user.login.InternalUserDetails.USER_ROLE;

@Slf4j
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/api/user/password/change")
    public ResponseEntity<?> passwordChangeUser(@Valid @RequestBody UserPasswordChangeRequest changeRequest) {
        log.info("Password change request received! " + changeRequest);
        InternalUserDetails principal = (InternalUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findUserByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Username not found!"));

        if (user.getUserInfo().getLast_login() == null) {
            user.setAuthority(USER_ROLE);
        }
        user.setPassword(passwordEncoder.encode(changeRequest.getPassword()));
        user.getUserInfo().setLast_login(new Date());

        userService.storeUserCredentials(user);
        return new ResponseEntity<>("Password change completed successfully!", HttpStatus.ACCEPTED);
    }

    @PreAuthorize(value = "hasAuthority(\"" + USER_ROLE + "\")")
    @GetMapping("/api/user/info")
    public ResponseEntity<?> getUserDetails() {
        log.info("User info request received!");
        InternalUserDetails principal = (InternalUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findUserByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Username not found!"));
        return ResponseEntity.ok(new UserDetailsResponse(user));
    }

}
