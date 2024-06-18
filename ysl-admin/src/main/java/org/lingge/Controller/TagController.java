package org.lingge.Controller;
import org.lingge.annotation.SystemLog;
import org.lingge.domain.ResponseResult;
import org.lingge.domain.dto.PutTagDto;
import org.lingge.domain.dto.TagListDto;
import org.lingge.domain.entity.Tag;
import org.lingge.domain.vo.PageVo;
import org.lingge.domain.vo.TagVo;
import org.lingge.service.TagService;
import org.lingge.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/content/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @SystemLog(businessName = "查询标签接口")
    @GetMapping("/list")
    public ResponseResult<PageVo> list(Integer pageNum , Integer pageSize , TagListDto tagListDto){
        return tagService.pageTagList(pageNum,pageSize,tagListDto);
    }
    @SystemLog(businessName = "写博文查询标签接口")
    @GetMapping("/listAllTag")
    public ResponseResult<PageVo> listAllTag(){
        List<TagVo> list = tagService.listAllTag();
        return ResponseResult.okResult(list);
    }
    @PostMapping
    @SystemLog(businessName = "添加标签接口")
    public ResponseResult<Tag> addTag(@RequestBody TagListDto tagListDto){
        return tagService.addTag(tagListDto);
    }

    @GetMapping(value = "/{id}")
    @SystemLog(businessName = "根据id查询标签接口")
    public ResponseResult<Tag> getInfo(@PathVariable(value = "id") Long id){
        return tagService.getupdateTag(id);
    }
    @PutMapping
    @SystemLog(businessName = "修改标签接口")
    public ResponseResult<Tag> updateTag(@RequestBody PutTagDto putTagDto){
        Tag tag = BeanCopyUtils.copyBean(putTagDto,Tag.class);
        tagService.updateById(tag);
        return ResponseResult.okResult();
    }
    @DeleteMapping(value = "/{id}")
    @SystemLog(businessName = "删除标签接口")
    public ResponseResult<Tag> deleteTag(@PathVariable(value = "id") Long id){
        tagService.removeById(id);
        return ResponseResult.okResult();
    }

}
