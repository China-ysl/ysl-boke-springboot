package org.lingge.Controller;

import org.lingge.annotation.SystemLog;
import org.lingge.constants.SystemConstants;
import org.lingge.domain.ResponseResult;
import org.lingge.domain.entity.Comment;
import org.lingge.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;
    //文章评论查询
    @SystemLog(businessName = "//文章评论查询接口")
    @GetMapping("/commentlist")
    public ResponseResult commentlist(Long articleId,String type,  Integer pageNum ,Integer pageSize){
        return commentService.commentlist(articleId, SystemConstants.NORMAL_USE,pageNum,pageSize);
    }

    @SystemLog(businessName = "发表评论接口")
    @PostMapping
    public ResponseResult addcomment(@RequestBody Comment comment){
        return commentService.addcomment(comment);
    }

    //友链评论查询
    @SystemLog(businessName = "友链评论查询接口")
    @GetMapping("/linkCommentList")
    public ResponseResult linkCommentList(Long articleId ,String type,Integer pageNum ,Integer pageSize){
        return commentService.commentlist(articleId,SystemConstants.UNUSED,pageNum,pageSize);
    }
}
