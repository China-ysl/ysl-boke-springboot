package org.lingge.mapper;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.lingge.domain.entity.UserRole;


/**
 * 用户和角色关联表(UserRole)表数据库访问层
 *
 * @author makejava
 * @since 2023-02-19 23:14:22
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

}
