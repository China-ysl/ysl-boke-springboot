package org.lingge.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lingge.constants.SystemConstants;
import org.lingge.domain.ResponseResult;
import org.lingge.domain.entity.Article;
import org.lingge.domain.entity.Link;
import org.lingge.domain.vo.LinkVo;
import org.lingge.domain.vo.PageVo;
import org.lingge.mapper.LinkMapper;
import org.lingge.service.LinkService;
import org.lingge.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * (Link)表服务实现类
 *
 * @author makejava
 * @since 2023-01-31 20:40:08
 */
@Service("linkService")
public class LinkServiceimpl extends ServiceImpl<LinkMapper, Link> implements LinkService {
    @Override
    public ResponseResult getAllLink() {
        //查询所有审核通过的
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus, SystemConstants.NORMAL_USE);
        List<Link> links = list(queryWrapper);
        //转换成vo
        //封装返回
        return ResponseResult.okResult(BeanCopyUtils.copyList(links,LinkVo.class));
    }

    @Override
    public PageVo selectLinkPage(Link link, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(link.getName()),Link::getName, link.getName());
        queryWrapper.eq(Objects.nonNull(link.getStatus()),Link::getStatus, link.getStatus());
        //分页查询
        Page<Link> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page,queryWrapper);
        //转换成vo
        List<Link> roles = page.getRecords();
        PageVo pageVo = new PageVo();
        pageVo.setTotal(page.getTotal());
        pageVo.setRows(roles);
        return pageVo;
    }
}
