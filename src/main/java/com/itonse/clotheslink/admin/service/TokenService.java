package com.itonse.clotheslink.admin.service;

import com.itonse.clotheslink.admin.dto.UserInfo;
import com.itonse.clotheslink.customer.domain.Customer;
import com.itonse.clotheslink.seller.domain.Seller;

public interface TokenService {

    UserInfo validateToken(String token);

    Seller findSellerByToken(String token);

    Customer findCustomerByToken(String token);
}
