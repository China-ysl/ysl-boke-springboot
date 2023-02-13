package org.lingge.domain.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * (Article)表实体类
 *
 * @author makejava
 * @since 2023-01-29 20:39:09
 */
@Data
@TableName("ysl_article")
@SuppressWarnings("serial")//去除安全提示
@NoArgsConstructor//空参构造
@AllArgsConstructor//有参构造
@ToString
@Accessors(chain = true)
public class Article{
    //文章id
    @TableId
    private Long id;
    //文章标题
    private String title;
    //文章内容
    private String content;
    //文章类型:1文章 2草稿
    private String type;
    //文章摘要
    private String summary;
    //所属分类id
    private Long categoryId;
    //分类名---ysl_article中没有相应到底字段
    @TableField(exist = false)
    private String categoryName;
    //缩略图
    private String thumbnail;
    //是否置顶（1 是 0 否）
    private String isTop;
    //状态（0 发布 1 草稿）
    private String status;
    //评论数
    private Integer commentCount;
    //访问量
    private Long viewCount;
    //是否允许评论 1是 0 否
    private String isComment;
    
    private Long createBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    
    private Long updateBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    //删除标志 0未删除 1删除
    private Integer delFlag;


    }

