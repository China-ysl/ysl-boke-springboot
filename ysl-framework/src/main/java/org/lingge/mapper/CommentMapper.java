package org.lingge.mapper;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.lingge.domain.entity.Comment;


/**
 * 评论表(Comment)表数据库访问层
 *
 * @author makejava
 * @since 2023-02-03 01:33:22
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

}
