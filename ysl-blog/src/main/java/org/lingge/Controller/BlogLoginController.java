package org.lingge.Controller;
import org.lingge.annotation.SystemLog;
import org.lingge.domain.ResponseResult;
import org.lingge.domain.entity.User;
import org.lingge.enums.AppHttpCodeEnum;
import org.lingge.exception.SystemException;
import org.lingge.service.BlogLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class BlogLoginController {

    @Autowired
    private BlogLoginService blogLoginService;
    @PostMapping("/login")
    @SystemLog(businessName = "用户登陆接口")
    public ResponseResult login(@RequestBody User user){
        if (!StringUtils.hasText(user.getUserName())){
            //提示 必须要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return  blogLoginService.login(user);

    }
    @RequestMapping("/logout")
    @SystemLog(businessName = "退出登陆接口")
    public ResponseResult logout(){
        return blogLoginService.lonout();
    }
}
