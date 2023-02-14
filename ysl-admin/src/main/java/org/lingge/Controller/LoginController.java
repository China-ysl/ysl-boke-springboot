package org.lingge.Controller;

import org.lingge.annotation.SystemLog;
import org.lingge.domain.ResponseResult;
import org.lingge.domain.entity.LoginUser;
import org.lingge.domain.entity.User;
import org.lingge.domain.vo.AdminUserInofVo;
import org.lingge.domain.vo.UserinfoVo;
import org.lingge.enums.AppHttpCodeEnum;
import org.lingge.exception.SystemException;
import org.lingge.service.LoginService;
import org.lingge.service.MenuService;
import org.lingge.service.RoleService;
import org.lingge.utils.BeanCopyUtils;
import org.lingge.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LoginController {

    @Autowired
    private MenuService menuService;
    @Autowired
    private LoginService loginService;

    @Autowired
    private RoleService roleService;

    @PostMapping("/login")
    @SystemLog(businessName = "用户登陆接口")
    public ResponseResult login(@RequestBody User user) {
        if (!StringUtils.hasText(user.getUserName())) {
            //提示 必须要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return loginService.login(user);
    }

    @RequestMapping("/logout")
    @SystemLog(businessName = "退出登陆接口")
    public ResponseResult logout() {
        return loginService.lonout();
    }

    @GetMapping("getInfo")
    public ResponseResult<AdminUserInofVo> getInfo() {
        //查询当前登陆用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //根据用户id查询权限信息
        List<String> perms = menuService.selectPermsByUserId(loginUser.getUser().getId());
        //根据用户id查询用户角色信息
        List<String> role = roleService.selectUserRole(loginUser.getUser().getId());
        //获取用户信息
        User user=loginUser.getUser();
        //bean拷贝将user拷贝到userinfo
        UserinfoVo userinfoVo = BeanCopyUtils.copyBean(user, UserinfoVo.class);
        //封装数据返回
        AdminUserInofVo adminUserInofVo = new AdminUserInofVo(perms, role,userinfoVo);
        return ResponseResult.okResult(adminUserInofVo);
    }
}
