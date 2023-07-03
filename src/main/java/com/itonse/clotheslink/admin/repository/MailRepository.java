package com.itonse.clotheslink.admin.repository;

import com.itonse.clotheslink.admin.domain.Mail;
import com.itonse.clotheslink.common.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MailRepository extends JpaRepository<Mail, Long> {

    Optional<Mail> findByEmailAndUserType(String email, UserType userType);
}
