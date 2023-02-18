package org.lingge.Controller;

import org.lingge.domain.ResponseResult;
import org.lingge.domain.dto.AddArticleDto;
import org.lingge.domain.entity.Article;
import org.lingge.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 博文相关接口
 */
@RestController
@RequestMapping("/content/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping
    public ResponseResult<Article> add(@RequestBody AddArticleDto articleDto){
        return articleService.add(articleDto);
    }
}
