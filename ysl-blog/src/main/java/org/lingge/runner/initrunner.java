package org.lingge.runner;

import org.lingge.constants.SystemConstants;
import org.lingge.domain.entity.Article;
import org.lingge.mapper.ArticleMapper;
import org.lingge.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class initrunner implements CommandLineRunner {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private RedisCache redisCache;
    @Override
    public void run(String... args) throws Exception {
        //注入articleMapper查询article所有的值
        List<Article> articles = articleMapper.selectList(null);
        //通过stream流的形式过滤获取到key id  value viewCount
        Map<String, Integer> viewCount = articles.stream().collect(Collectors.toMap(article -> article.getId().toString(), article -> {
            return article.getViewCount().intValue();
        }));
        redisCache.setCacheMap(SystemConstants.ARTICLE_VIEWCOUNT,viewCount);
        System.out.println("博客后端初始化完成");



    }
}
