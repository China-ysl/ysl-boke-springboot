package org.lingge.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lingge.constants.SystemConstants;
import org.lingge.domain.entity.Menu;
import org.lingge.mapper.MenuMapper;
import org.lingge.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author makejava
 * @since 2023-02-13 22:31:56
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<String> selectPermsByUserId(Long id) {
        //根据用户id查询用户权限
        //如果是管理员返回所有的权限
        if (id==1L){
            LambdaUpdateWrapper<Menu> wrapper = new LambdaUpdateWrapper<>();
            wrapper.in(Menu::getMenuType,SystemConstants.MENU_TYPE,SystemConstants.BUTTON);
            wrapper.eq(Menu::getStatus,SystemConstants.NORMAL_USE);
            List<Menu> list = list(wrapper);
            List<String> collect = list.stream()
                    .map(Menu::getPerms)
                    .collect(Collectors.toList());
            return collect;
        }
        //否则返回用户所对应的权限信息

        return menuMapper.selectPermsByUserId(id);

    }
}
