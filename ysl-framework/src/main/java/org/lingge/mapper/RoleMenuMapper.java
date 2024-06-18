package org.lingge.mapper;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.lingge.domain.entity.RoleMenu;


/**
 * 角色和菜单关联表(RoleMenu)表数据库访问层
 *
 * @author makejava
 * @since 2023-02-19 21:00:14
 */
@Mapper
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

}
