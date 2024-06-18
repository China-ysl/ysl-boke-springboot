package org.lingge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.lingge.domain.ResponseResult;
import org.lingge.domain.dto.AddArticleDto;
import org.lingge.domain.dto.ArticleDto;
import org.lingge.domain.entity.Article;
import org.lingge.domain.vo.ArticleVo;
import org.lingge.domain.vo.PageVo;

public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNun, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);

    ResponseResult<Article> add(AddArticleDto articleDto);

    ResponseResult updateViewCount(Long id);

    PageVo selectArticlePage(Article article, Integer pageNum, Integer pageSize);

    ArticleVo getInfo(Long id);

    void edit(ArticleDto article);
}
