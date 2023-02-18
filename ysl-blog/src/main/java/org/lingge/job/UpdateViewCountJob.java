package org.lingge.job;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.lingge.constants.SystemConstants;
import org.lingge.domain.entity.Article;
import org.lingge.service.ArticleService;
import org.lingge.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UpdateViewCountJob {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleService articleService;

    @Scheduled(cron = "0 0/10 * * * ? ")
    public void updateViewCount() {
        System.out.println("============================正在更新浏览量=====================================");
        //获取redis中浏览量数据
        Map<String, Integer> viewCountMap = redisCache.getCacheMap(SystemConstants.ARTICLE_VIEWCOUNT);

        List<Article> articles = viewCountMap.entrySet()
                .stream()
                .map(entry ->
                        new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
                .collect(Collectors.toList());

        for (Article article : articles) {
            LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Article::getId, article.getId());
            updateWrapper.set(Article::getViewCount, article.getViewCount());
            articleService.update(updateWrapper);
            System.out.println("============================浏览量更新成功=====================================");
        }
    }
}
