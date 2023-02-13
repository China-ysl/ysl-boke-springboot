package org.lingge.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.lingge.domain.entity.Article;

/**
 * (Classify)表数据库访问层
 *
 * @author makejava
 * @since 2023-01-30 23:29:02
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
}
