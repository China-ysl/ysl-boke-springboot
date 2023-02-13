package org.lingge.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lingge.domain.entity.Tag;
import org.lingge.mapper.TagMapper;
import org.lingge.service.TagService;
import org.springframework.stereotype.Service;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2023-02-11 17:14:06
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

}
