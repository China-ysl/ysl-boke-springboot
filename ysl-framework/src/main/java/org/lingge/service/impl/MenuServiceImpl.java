package org.lingge.service.impl;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lingge.constants.SystemConstants;
import org.lingge.domain.entity.Menu;
import org.lingge.domain.vo.MenuVo;
import org.lingge.mapper.MenuMapper;
import org.lingge.service.MenuService;
import org.lingge.utils.BeanCopyUtils;
import org.lingge.utils.SecurityUtils;
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
        if (SecurityUtils.isAdmin()) {
            //如果是管理员返回所有的权限
            LambdaUpdateWrapper<Menu> wrapper = new LambdaUpdateWrapper<>();
            wrapper.in(Menu::getMenuType, SystemConstants.MENU_TYPE, SystemConstants.BUTTON);
            wrapper.eq(Menu::getStatus, SystemConstants.NORMAL_USE);
            List<Menu> list = list(wrapper);
            List<String> collect = list.stream()
                    .map(Menu::getPerms)
                    .collect(Collectors.toList());
            return collect;
        }
        //否则返回用户所对应的权限信息

        return menuMapper.selectPermsByUserId(id);

    }

    @Override
    public List<MenuVo> selectRoutersMenuTreeByUserId(Long userId) {
        List<Menu> menus = null;
        //判断是否是管理员
        if (SecurityUtils.isAdmin()) {
            menus = menuMapper.selectRoutersMenu();
        } else {
            //否则查询当前用户所具有的menu
            menus = menuMapper.selectRoutersMenuTreeByUserId(userId);
        }
        List<MenuVo> menuVos = BeanCopyUtils.copyList(menus, MenuVo.class);

        //构建tree子父菜单关系
        //构建父菜单然后在查询子菜单并设置到children
        List<MenuVo> menuTree = buiderMenuTerr(menuVos,0L);


        return menuTree;
    }

    private List<MenuVo> buiderMenuTerr(List<MenuVo> menus, long parentid) {
        List<MenuVo> menuTree = menus.stream()
                .filter(menu -> menu.getParentId().equals(parentid))
                .map(menu -> menu.setChildren(getChildren(menu, menus)))
                .collect(Collectors.toList());
        return menuTree;
    }

    /**
     * 从menus中获取传入参数的子menu
     *
     * @param menu
     * @param menus
     * @return
     */
    private List<MenuVo> getChildren(MenuVo menu, List<MenuVo> menus) {
        List<MenuVo> chidrenList = menus.stream()
                .filter(menuVo -> menuVo.getParentId().equals(menu.getId()))
                .map(m->m.setChildren(getChildren(m,menus)))
                .collect(Collectors.toList());
        return chidrenList;

    }
}
