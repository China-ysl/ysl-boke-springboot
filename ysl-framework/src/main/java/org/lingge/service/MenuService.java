package org.lingge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.lingge.domain.entity.Menu;

import java.util.List;


/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author makejava
 * @since 2023-02-13 22:30:35
 */
public interface MenuService extends IService<Menu> {

    List<String> selectPermsByUserId(Long id);
}
