package com.example.examboard.service;

import com.example.examboard.config.PrincipalDetails;
import com.example.examboard.constant.UserRole;
import com.example.examboard.entity.UserAccount;
import com.example.examboard.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PrincipalDetailService implements UserDetailsService {
    @Autowired
    UserAccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserAccount> _account = accountRepository.findById(username);

        if(_account.isEmpty()){
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }

        UserAccount account = _account.get();

        return new PrincipalDetails(account);
    }
}
