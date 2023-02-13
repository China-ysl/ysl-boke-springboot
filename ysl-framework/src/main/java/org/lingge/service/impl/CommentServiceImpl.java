package org.lingge.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lingge.constants.SystemConstants;
import org.lingge.domain.ResponseResult;
import org.lingge.domain.entity.Comment;
import org.lingge.domain.vo.CommentVo;
import org.lingge.domain.vo.PageVo;
import org.lingge.mapper.CommentMapper;
import org.lingge.service.CommentService;
import org.lingge.service.UserService;
import org.lingge.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2023-02-03 01:33:22
 */
@Service("commentService")
public class    CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private UserService userService;
    @Override
    public ResponseResult commentlist(Long articleId, String type, Integer pageNum, Integer pageSize) {
        //查询对应文章下的根评论
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        //根据文章id articleId查询
        queryWrapper.eq(SystemConstants.NORMAL_USE.equals(articleId),Comment::getArticleId,articleId);
        //判断是否为根评论 rootid是否为-1
        queryWrapper.eq(Comment::getRootId, -1);
        //评论类型
        queryWrapper.eq(Comment::getType, type);
        //文章分类
        queryWrapper.eq(Comment::getArticleId,articleId);
        //分页查询
        Page<Comment> page=new Page(pageNum,pageSize);
        page(page,queryWrapper);
        List<CommentVo> copyBean = toCommentVoList(page.getRecords());
        //在查询对应根评论下的子评论
        copyBean.stream().map(commentVo -> {
            //根据id查子评论
            //赋值
            commentVo.setChildren(getchildren(commentVo.getId()));
            return commentVo;
        }).collect(Collectors.toList());
        //封装成ResponseResult返回
        return ResponseResult.okResult(new PageVo(copyBean,page.getTotal()));
    }

    @Override
    public ResponseResult addcomment(Comment comment) {
        //调save方法添加记录
        save(comment);
        return ResponseResult .okResult();
    }


    /**
     * 内部方法
     * @param id
     * @return
     */
    //    获取子评论方法
    private List<CommentVo> getchildren(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getRootId,id);
        List<Comment> comments =list(queryWrapper);

        queryWrapper.orderByDesc(Comment::getCreateTime);

        List<CommentVo> commentVos= toCommentVoList(comments);
        return commentVos;
    }

    private List<CommentVo> toCommentVoList(List<Comment> list){
        List<CommentVo> commentVos = BeanCopyUtils.copyList(list, CommentVo.class);
        //遍历Vo集合
        commentVos.stream().map(commentVo -> {//通过createBy查询评论创建者的昵称并进行赋值getNickName
            commentVo.setUsername(userService.getById(commentVo.getCreateBy()).getNickName());
            if (commentVo.getToCommentUserId()!=-1){
                //通过toCommentUserId查询到所回复用户的昵称并进行赋值getNickName
                commentVo.setToCommentUserName(userService.getById(commentVo.getToCommentUserId()).getNickName());
            }
            return commentVo;
        }).collect(Collectors.toList());

        return commentVos;
    }
}
