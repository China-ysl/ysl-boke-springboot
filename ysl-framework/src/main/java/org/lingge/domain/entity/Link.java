package org.lingge.domain.entity;

import java.util.Date;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (Link)表实体类
 *
 * @author makejava
 * @since 2023-01-31 20:21:53
 */
@SuppressWarnings("serial")
@Data
@TableName("ysl_link")
@AllArgsConstructor
@NoArgsConstructor
public class Link  {
    //id主键@TableId
    private Long id;

    //网站名称
    private String name;
    //网站logo
    private String logo;
    //网站描述
    private String description;
    //网站地址
    private String address;
    //审核状态 (0代表审核通过，1代表审核未通过，2代表未审核)
    private String status;
    
    private Long createBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    
    private Long updateBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    //删除标志（0代表未删除，1代表已删除）
    private Integer delFlag;



}

