package org.lingge.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lingge.domain.ResponseResult;
import org.lingge.domain.dto.TagListDto;
import org.lingge.domain.entity.Tag;
import org.lingge.domain.vo.PageVo;
import org.lingge.domain.vo.TagVo;
import org.lingge.mapper.TagMapper;
import org.lingge.service.TagService;
import org.lingge.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2023-02-11 17:14:06
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Lazy
    @Autowired
    private TagService tagService;

    /**
     * 分页查询标签
     * @return
     */
    @Override
    public ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        //分页查询
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(tagListDto.getName()),Tag::getName,tagListDto.getName());
        queryWrapper.eq(StringUtils.hasText(tagListDto.getRemark()),Tag::getRemark,tagListDto.getRemark());
        Page<Tag> page=new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page,queryWrapper);
        //封装数据返回
        PageVo pageVo =new PageVo(page.getRecords(),page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    /**
     * 添加标签
     * @param tagListDto
     * @return
     */
    @Override
    public ResponseResult<Tag> addTag(TagListDto tagListDto) {
        Tag tag = BeanCopyUtils.copyBean(tagListDto, Tag.class);
        tagService.save(tag);
        return ResponseResult.okResult();
    }

    /**
     * 根据id查询标签
     * @param id
     * @return
     */
    @Override
    public ResponseResult<Tag> getupdateTag(Long id) {
        Tag tag = getById(id);
        return ResponseResult.okResult(tag);
    }

    /**
     * 查询所有状态为正常使用的标签
     * 删除标志（0代表未删除，1代表已删除）
     * @return
     */
    @Override
    public List<TagVo> listAllTag() {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Tag::getId,Tag::getName);
        List<Tag> list = list(queryWrapper);
        List<TagVo> tagVos = BeanCopyUtils.copyList(list, TagVo.class);
        return tagVos;
    }

}
