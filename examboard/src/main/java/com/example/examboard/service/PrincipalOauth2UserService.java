package com.example.examboard.service;

import com.example.examboard.config.PrincipalDetails;
import com.example.examboard.constant.UserRole;
import com.example.examboard.entity.UserAccount;
import com.example.examboard.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
    @Autowired
    private UserAccountRepository userRepository;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws
            OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        // OAuth2 의 회원 프로필 조회

        String email =
                (String) ((Map)oAuth2User.getAttribute("kakao_account")).get("email");
        String nickname =
                (String) ((Map)oAuth2User.getAttribute("properties")).get("nickname");
        String provider = userRequest.getClientRegistration().getClientId();
        String username = provider + "_" + email;
        String password = UUID.randomUUID().toString();

        Optional<UserAccount> _user = userRepository.findByUserId(username);

        if (_user.isEmpty()) {
            // user의 패스워드가 null이기 때문에 OAuth 유저는 일반적인
            //로그인을 할 수 없음.
            UserAccount user = new UserAccount();
            user.setUserId(username);
            user.setEmail(email);
            user.setNickname(nickname);
            user.setUserRole(UserRole.USER);
            user.setUserPassword(password);
            userRepository.save(user);
            return new PrincipalDetails(user, oAuth2User.getAttributes());
        } else {
            UserAccount user = _user.get();
            return new PrincipalDetails(user, oAuth2User.getAttributes());
        }
    }
}
