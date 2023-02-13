package org.lingge.Controller;
import org.lingge.annotation.SystemLog;
import org.lingge.domain.ResponseResult;
import org.lingge.domain.entity.User;
import org.lingge.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @SystemLog(businessName = "查询个人信息接口")
    @GetMapping("/userInfo")
    public ResponseResult userinfo() {
        return  userService.userinfo();
    }

    @SystemLog(businessName = "更新用户信息接口")
    @PutMapping("/userInfo")
    public ResponseResult updtaeUserinfo(@RequestBody User user){
        return userService.updtaeUserinfo(user);
    }


    @SystemLog(businessName = "注册用户信息接口")
    @RequestMapping("/register")
    public ResponseResult register(@RequestBody User user){

        return userService.register(user);
    }
}
