package org.lingge.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lingge.domain.entity.ArticleTag;
import org.lingge.mapper.ArticleTagMapper;
import org.lingge.service.ArticleTagService;
import org.springframework.stereotype.Service;

/**
 * 文章标签关联表(ArticleTag)表服务实现类
 *
 * @author makejava
 * @since 2023-02-17 22:26:14
 */
@Service("articleTagService")
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {

}
