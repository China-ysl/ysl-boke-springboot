package org.lingge.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lingge.constants.SystemConstants;
import org.lingge.domain.ResponseResult;
import org.lingge.domain.dto.AddArticleDto;
import org.lingge.domain.entity.Article;
import org.lingge.domain.entity.ArticleTag;
import org.lingge.domain.entity.Classify;
import org.lingge.domain.vo.ArticleDetailVo;
import org.lingge.domain.vo.ArticleListVo;
import org.lingge.domain.vo.HotArticleVo;
import org.lingge.domain.vo.PageVo;
import org.lingge.mapper.ArticleMapper;
import org.lingge.service.ArticleService;
import org.lingge.service.ArticleTagService;
import org.lingge.service.ClassifyService;
import org.lingge.utils.BeanCopyUtils;
import org.lingge.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ArticleServiceimpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    private ClassifyService classifyService ;

    @Autowired
    private ArticleTagService articleTagService;

    @Autowired
    private RedisCache redisCache;
    @Override
    public ResponseResult hotArticleList() {
        //查询热门文章，封装成ResponseResult格式返回
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //必须是已发布的文章
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //浏览量进行排序
        queryWrapper.orderByDesc(Article::getViewCount);
        //最多只查询十条
        Page<Article> page = new Page<>(1, 10);
        page(page, queryWrapper);
        //使用page的getRecords方法拿到数据
        List<Article> articles = page.getRecords();
        //工具类bean拷贝方法
        List<HotArticleVo> vos = BeanCopyUtils.copyList(articles, HotArticleVo.class);
        //将拿到的数据封装到统一的ResponseResult格式中并返回
        return ResponseResult.okResult(vos);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        //查询条件
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //如果有categoryId 查询时就要和传入的相同
        queryWrapper.eq(Objects.nonNull(categoryId) && categoryId > 0, Article::getCategoryId, categoryId);
        // 状态是已经发布的
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //对is_top进行降序
        queryWrapper.orderByDesc(Article::getIsTop);
        //分页查询
        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page,queryWrapper);
        //查询categoryName
        List<Article> articles = page.getRecords();
        //查询分类id 查询分类信息 ，获取分类名
        articles.stream()
                .map(article-> article.setCategoryName(classifyService.getById(article.getCategoryId()).getName()))
                .collect(Collectors.toList());
        //封装vo
        List<ArticleListVo> vos = BeanCopyUtils.copyList(page.getRecords(), ArticleListVo.class);
        PageVo pageVo = new PageVo(vos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        //根据id查询数据
        Article byId = getById(id);
        //转换成vo
        ArticleDetailVo detailVo = BeanCopyUtils.copyBean(byId, ArticleDetailVo.class);
        //根据分类id查询分类名
        Long categoryId = detailVo.getCategoryId();
        Classify classifyServiceById = classifyService.getById(categoryId);
        if (classifyServiceById!=null){
            detailVo.setCategoryName(classifyServiceById.getName());
        }
        //封装成统一响应格式
        return ResponseResult.okResult(detailVo);
    }

    @Override
    public ResponseResult<Article> add(AddArticleDto articleDto) {
        //添加 博客
        Article article = BeanCopyUtils.copyBean(articleDto, Article.class);
        save(article);
        List<ArticleTag> articleTags = articleDto.getTags().stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .collect(Collectors.toList());

        //添加 博客和标签的关联
        articleTagService.saveBatch(articleTags);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        //更新redis中对应 id的浏览量
        redisCache.incrementCacheMapValue(SystemConstants.ARTICLE_VIEWCOUNT,id.toString(),1);
        return ResponseResult.okResult();
    }

}
