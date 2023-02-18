package org.lingge.Controller;

import org.lingge.annotation.SystemLog;
import org.lingge.domain.ResponseResult;
import org.lingge.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/article")
@RestController
public class ArticleController {
    @Autowired
    protected ArticleService articleService;
    /**
     * 查询热门文章，封装成ResponseResult格式返回
     * @return hotArticleList
     */
    @SystemLog(businessName = "查询热门文章接口")
    @GetMapping("/hotarticleList")
    public ResponseResult hotArticleList(){
        //使用ResponseResult封装hotArticleList方法查询到的数据
        return articleService.hotArticleList();

    }
    @SystemLog(businessName = "查询文章首页简介接口")
    @GetMapping("/articleList")
    public ResponseResult articleList(Integer pageNum, Integer pageSize,Long categoryId){
        return articleService.articleList(pageNum,pageSize,categoryId);
    }
    @SystemLog(businessName = "查询文章详情接口")
    @GetMapping("/{id}")
    public ResponseResult getArticleDetail(@PathVariable("id") Long id){
        return articleService.getArticleDetail(id);
    }


    //
    @PutMapping("/updateViewCount/{id}")
    public ResponseResult updateViewCount(@PathVariable("id") Long id){
        return articleService.updateViewCount(id);
    }
}
