package com.itonse.clotheslink.mail.repository;

import com.itonse.clotheslink.mail.domain.Mail;
import com.itonse.clotheslink.common.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MailRepository extends JpaRepository<Mail, Long> {

    Optional<Mail> findByEmailAndUserType(String email, UserType userType);
}
