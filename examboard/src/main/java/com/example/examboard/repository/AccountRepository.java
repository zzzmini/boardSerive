package com.example.examboard.repository;

import com.example.examboard.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<UserAccount, String> {
}
