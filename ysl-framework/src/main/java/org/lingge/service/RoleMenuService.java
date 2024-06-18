package org.lingge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.lingge.domain.entity.RoleMenu;


/**
 * 角色和菜单关联表(RoleMenu)表服务接口
 *
 * @author makejava
 * @since 2023-02-19 21:00:13
 */
public interface RoleMenuService extends IService<RoleMenu> {

    void deleteRoleMenuByRoleId(Long id);
}
