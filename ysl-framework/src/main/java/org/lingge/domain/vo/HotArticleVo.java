package org.lingge.domain.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * vo 优化实体类
 * 把实体类中的敏感字段从实体类中剔除在呈现给用户
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HotArticleVo {
    //文章id
    @TableId
    private Long id;
    //标题
    private String title;

    //访问量
    private Long viewCount;
}
