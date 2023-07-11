package com.itonse.clotheslink.mail.domain;

import com.itonse.clotheslink.common.UserType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(indexes = {
        @Index(name = "idx_email", columnList = "email"),
        @Index(name = "idx_userType", columnList = "userType")
})
public class Mail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    @Enumerated(EnumType.STRING)
    private UserType userType;
    private String authCode;
    private boolean verified;
    private LocalDateTime expiredAt;
}
