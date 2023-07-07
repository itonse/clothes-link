package com.itonse.clotheslink.admin.service;

import com.itonse.clotheslink.common.UserVo;
import com.itonse.clotheslink.customer.domain.Customer;
import com.itonse.clotheslink.seller.domain.Seller;

public interface TokenService {

    UserVo validateToken(String token);

    Seller findSellerByToken(String token);

    Customer findCustomerByToken(String token);
}
