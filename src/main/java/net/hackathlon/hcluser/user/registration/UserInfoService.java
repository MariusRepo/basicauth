package net.hackathlon.hcluser.user.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    public Long registerUserInfo(UserInfo userInfo) {
        return userInfoRepository.save(userInfo).getId();
    }

    public Optional<UserInfo> findUserInfoById(Long id) {
        return userInfoRepository.findById(id);
    }

}
