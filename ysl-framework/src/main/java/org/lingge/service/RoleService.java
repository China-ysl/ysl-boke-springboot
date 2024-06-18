package org.lingge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.lingge.domain.ResponseResult;
import org.lingge.domain.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表服务接口
 *
 * @author makejava
 * @since 2023-02-13 23:04:49
 */
public interface RoleService extends IService<Role> {

    List<String> selectUserRole(Long id);

    ResponseResult selectRolePage(Role role, Integer pageNum, Integer pageSize);

    void updateRole(Role role);

    void insertRole(Role role);

    List<Role> selectRoleAll();

    List<Long> selectRoleIdByUserId(Long userId);
}
