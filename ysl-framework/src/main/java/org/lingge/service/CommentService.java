package org.lingge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.lingge.domain.ResponseResult;
import org.lingge.domain.entity.Comment;


/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2023-02-03 01:33:22
 */
public interface CommentService extends IService<Comment> {
    ResponseResult commentlist(Long articleId , String type, Integer pageNum, Integer pageSize);

    ResponseResult addcomment(Comment comment);
}
