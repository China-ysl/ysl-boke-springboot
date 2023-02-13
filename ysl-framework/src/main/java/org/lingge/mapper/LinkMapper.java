package org.lingge.mapper;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.lingge.domain.entity.Link;


/**
 * (Link)表数据库访问层
 *
 * @author makejava
 * @since 2023-01-31 20:23:57
 */
@Mapper
public interface LinkMapper extends BaseMapper<Link> {

}
