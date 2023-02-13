package org.lingge.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ArticleDetailVo {
    //文章id
    @TableId
    private Long id;
    //文章标题
    private String title;
    //文章内容
    private String content;
    //所属分类id
    private Long categoryId;
    //分类名---ysl_article中没有相应到底字段
    @TableField(exist = false)
    private String categoryName;
    //缩略图
    private String thumbnail;
    //是否置顶（1 是 0 否）
    private String isTop;
    //访问量
    private Long viewCount;
    //是否允许评论 1是 0 否
    private String isComment;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}
