package com.example.examboard.service;

import com.example.examboard.constant.UserRole;
import com.example.examboard.entity.UserAccount;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.apache.catalina.valves.rewrite.InternalRewriteMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    EntityManager em;

    @Transactional
    public void createUser(String userId, String userPassword, String email, String nickname){
        UserAccount account = new UserAccount();
        account.setUserId(userId);
        account.setUserPassword(passwordEncoder.encode(userPassword));
        account.setEmail(email);
        account.setNickname(nickname);
        if("ADMIN".equals(userId.toUpperCase())){
            account.setUserRole(UserRole.ADMIN);
        } else {
            account.setUserRole(UserRole.USER);
        }

        em.persist(account);
    }
}
