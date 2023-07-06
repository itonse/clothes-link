package com.itonse.clotheslink.admin.service;

import com.itonse.clotheslink.admin.domain.Mail;
import com.itonse.clotheslink.admin.dto.UserInfo;
import com.itonse.clotheslink.common.UserType;
import com.itonse.clotheslink.common.UserVo;

public interface MailAuthService {

    boolean verifyUserAuth(UserType userType, String token);

    UserVo verifyEmailSending(String token);

    Mail manageMail(UserVo vo);

    UserInfo sendMail(UserVo vo, Mail mail);

    UserInfo processSendAuthMail(String token);
}
