package org.lingge.service;

import org.lingge.domain.ResponseResult;
import org.lingge.domain.entity.User;

public interface BlogLoginService {
    ResponseResult login(User user);

    ResponseResult lonout();
}
