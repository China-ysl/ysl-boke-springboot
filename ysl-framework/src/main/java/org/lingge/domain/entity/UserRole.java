package org.lingge.domain.entity;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 用户和角色关联表(UserRole)表实体类
 *
 * @author makejava
 * @since 2023-02-19 23:14:22
 */
@SuppressWarnings("serial")
@Data
@TableName("sys_user_role")
@AllArgsConstructor
@NoArgsConstructor
public class UserRole  {
    //用户ID@TableId
    private Long userId;
    //角色ID@TableId
    private Long roleId;




}

