package org.lingge.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LinkVo {
    @TableId
    private Long id;
    // 网站名称
    private String name;
    //网站描述
    private String description;
    //网站网址
    private String address;
    //网站logo
    public String logo;

}
