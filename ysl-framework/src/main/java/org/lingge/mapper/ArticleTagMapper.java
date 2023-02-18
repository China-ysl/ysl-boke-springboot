package org.lingge.mapper;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.lingge.domain.entity.ArticleTag;


/**
 * 文章标签关联表(ArticleTag)表数据库访问层
 *
 * @author makejava
 * @since 2023-02-17 22:26:15
 */
@Mapper
public interface ArticleTagMapper extends BaseMapper<ArticleTag> {

}
