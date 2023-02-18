package org.lingge.domain.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 文章标签关联表(ArticleTag)表实体类
 *
 * @author makejava
 * @since 2023-02-17 22:26:09
 */
@Data
@TableName("ysl_article_tag")
@AllArgsConstructor
@NoArgsConstructor
public class ArticleTag  {
    //文章id@TableId
    private Long articleId;
    //标签id@TableId
    private Long tagId;




}

