package net.hackathlon.hcluser.user.login;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public void storeUserCredentials(User user) {
        userRepository.save(user);
    }

    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Map<String, String> generateRandomCredentials() {
        Faker fake = new Faker(new Random(ThreadLocalRandom.current().nextInt(1, 100 + 1)));
        return new HashMap<>() {{
            put("username", (fake.pokemon().name() + "." + fake.cat().name().substring(0, 3)).toLowerCase());
            put("password", fake.code().asin());
        }};
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        user.orElseThrow(() -> new UsernameNotFoundException("Username: '" + username + "' does not exist!"));
        return user.map(InternalUserDetails::new).get();
    }
}
