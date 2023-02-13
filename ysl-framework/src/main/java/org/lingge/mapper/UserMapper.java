package org.lingge.mapper;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.lingge.domain.entity.User;


/**
 * 用户表(User)表数据库访问层
 *
 * @author makejava
 * @since 2023-02-05 02:06:21
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
