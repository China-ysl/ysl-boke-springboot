package org.lingge.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.lingge.domain.entity.Menu;

import java.util.List;


/**
 * 菜单权限表(Menu)表数据库访问层
 *
 * @author makejava
 * @since 2023-02-13 22:31:27
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    List<String> selectPermsByUserId(Long id);

    List<Menu> selectRoutersMenu();

    List<Menu> selectRoutersMenuTreeByUserId(Long userId);

    List<Long> selectMenuListByRoleId(Long roleId);
}
