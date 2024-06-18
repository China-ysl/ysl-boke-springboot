package org.lingge.domain.entity;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 角色和菜单关联表(RoleMenu)表实体类
 *
 * @author makejava
 * @since 2023-02-19 21:00:11
 */
@SuppressWarnings("serial")
@Data
@TableName("sys_role_menu")
@AllArgsConstructor
@NoArgsConstructor
public class RoleMenu  {
    //角色ID@TableId
    private Long roleId;
    //菜单ID@TableId
    private Long menuId;




}

