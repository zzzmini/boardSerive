package com.example.examboard.entity;

import com.example.examboard.constant.UserRole;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString(callSuper = true)
@Table(indexes = {
        @Index(columnList = "email", unique = true),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity
@Data
public class UserAccount extends AuditingFields {
    @Id
    @Column(length = 50)
    private String userId;

    @Column(nullable = false)
    private String userPassword;

    @Column(length = 100)
    private String email;

    @Column(length = 100)
    private String nickname;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @OneToMany(mappedBy = "userAccount")
    private List<Article> articles = new ArrayList<>();
}