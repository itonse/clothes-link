package com.itonse.clotheslink.admin.service;

import com.itonse.clotheslink.admin.domain.Mail;
import com.itonse.clotheslink.common.UserType;
import com.itonse.clotheslink.common.UserVo;

public interface MailAuthService {

    boolean verifyUserAuth(UserType userType, String token);

    UserVo verifyEmailSending(String token);

    Mail manageMail(UserVo vo);

    UserVo sendMail(UserVo vo, Mail mail);

    UserVo processSendAuthMail(String token);
}
