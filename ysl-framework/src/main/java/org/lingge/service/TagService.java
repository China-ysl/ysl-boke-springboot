package org.lingge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.lingge.domain.ResponseResult;
import org.lingge.domain.dto.PutTagDto;
import org.lingge.domain.dto.TagListDto;
import org.lingge.domain.entity.Tag;
import org.lingge.domain.vo.PageVo;
import org.lingge.domain.vo.TagVo;

import java.util.List;


/**
 * 标签(Tag)表服务接口
 *
 * @author makejava
 * @since 2023-02-11 17:14:06
 */
public interface TagService extends IService<Tag> {

    ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    ResponseResult<Tag> addTag(TagListDto tagListDto);

    ResponseResult<Tag> getupdateTag(Long id);

    List<TagVo> listAllTag();
}
