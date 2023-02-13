package org.lingge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.lingge.domain.ResponseResult;
import org.lingge.domain.entity.Link;


/**
 * (Link)表服务接口
 *
 * @author makejava
 * @since 2023-01-31 20:23:34
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();
}
