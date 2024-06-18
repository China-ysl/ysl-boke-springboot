package org.lingge.domain.entity;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (Classify)表实体类
 *
 * @author makejava
 * @since 2023-01-31 03:01:23
 */
@SuppressWarnings("serial")
@Data
@TableName("ysl_classify")
@AllArgsConstructor
@NoArgsConstructor
public class Classify  {
    //分类id@TableId
    private Long id;

    //分类名
    private String name;
    //如果没有父分类为0
    private String pid;
    //描述
    private String description;
    //状态0:正常,1禁用
    private String status;
    //文章创建人id
    private Long createBy;
    //文章创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    //文章更新人id
    @TableField(fill = FieldFill.UPDATE)
    private Long updateBy;
    //文章更新时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    //删除标志 0未删除 1删除
    private Integer delFlag;



}

