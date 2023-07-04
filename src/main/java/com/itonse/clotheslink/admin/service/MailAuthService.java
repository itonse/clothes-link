package com.itonse.clotheslink.admin.service;

import com.itonse.clotheslink.admin.dto.UserInfo;

public interface MailAuthService {

    boolean verifyUserAuth(String token);

    UserInfo sendMail(String token);
}
