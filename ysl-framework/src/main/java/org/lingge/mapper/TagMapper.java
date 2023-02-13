package org.lingge.mapper;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.lingge.domain.entity.Tag;


/**
 * 标签(Tag)表数据库访问层
 *
 * @author makejava
 * @since 2023-02-11 17:14:06
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {

}
