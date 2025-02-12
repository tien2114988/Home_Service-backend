package com.threeChickens.homeService.middleware;

import com.threeChickens.homeService.entity.User;
import com.threeChickens.homeService.enums.UserStatus;
import com.threeChickens.homeService.exception.AppException;
import com.threeChickens.homeService.exception.StatusCode;
import org.springframework.stereotype.Component;

@Component
public class UserMiddleware {
    public void checkUserStatus(UserStatus userStatus){
        if(userStatus == UserStatus.PROHIBITIVE){
            throw new AppException(StatusCode.USER_PROHIBITIVE);
        }
    }

    public void checkUserBalance(long balance, long amount){
        if(balance < amount){
            throw new AppException(StatusCode.BALANCE_NOT_ENOUGH);
        }
    }
}
