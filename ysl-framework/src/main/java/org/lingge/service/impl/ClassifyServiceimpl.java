package org.lingge.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lingge.constants.SystemConstants;
import org.lingge.domain.ResponseResult;
import org.lingge.domain.entity.Article;
import org.lingge.domain.entity.Classify;
import org.lingge.domain.entity.Role;
import org.lingge.domain.vo.CategoryVo;
import org.lingge.domain.vo.ClassificationVo;
import org.lingge.domain.vo.PageVo;
import org.lingge.mapper.ClassifyMapper;
import org.lingge.service.ArticleService;
import org.lingge.service.ClassifyService;
import org.lingge.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * (Classify)表服务实现类
 *
 * @author makejava
 * @since 2023-01-30 23:26:13
 */
@Service("classifyService")
public class ClassifyServiceimpl extends ServiceImpl<ClassifyMapper, Classify> implements ClassifyService {

    @Lazy
    @Autowired
    private ArticleService articleService;
    @Override
    public ResponseResult queryClassificationList() {
        LambdaQueryWrapper<Article> articlewrapper = new LambdaQueryWrapper<>();
        articlewrapper.eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        //查询文章表必须是已发布的
        List<Article> articles = articleService.list(articlewrapper);
        //使用stream流获取文章分类id，并且去重
        Set<Long> id = articles.stream()
                .map(Article::getCategoryId)
                .collect(Collectors.toSet());
        //根据已发布文章id查询分类表
        List<Classify> classifyList = listByIds(id);
        //必须是正常使用的分类
        List<Classify> list = classifyList.stream()
                .filter(classify ->SystemConstants.NORMAL_USE.equals(classify.getStatus()))
                .collect(Collectors.toList());
        //封装vo
        List<ClassificationVo> classificationVos = BeanCopyUtils.copyList(list, ClassificationVo.class);

        return ResponseResult.okResult(classificationVos);
    }

    @Override
    public List<CategoryVo> listAllCategory() {
        //查询分类为正常使用状态的所有分类
        LambdaQueryWrapper<Classify> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Classify::getStatus,SystemConstants.NORMAL_USE);
        List<Classify> list = list(queryWrapper);
        List<CategoryVo> categoryVos = BeanCopyUtils.copyList(list, CategoryVo.class);
        return categoryVos;
    }

    @Override
    public PageVo selectCategoryPage(Classify classify, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Classify> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(classify.getName()),Classify::getName,classify.getName());
        queryWrapper.eq(Objects.nonNull(classify.getStatus()),Classify::getStatus, classify.getStatus());
        Page<Classify> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page,queryWrapper);
        //转换成vo
        List<Classify> roles = page.getRecords();

        PageVo pageVo = new PageVo();
        pageVo.setTotal(page.getTotal());
        pageVo.setRows(roles);
        return pageVo;

    }
}
