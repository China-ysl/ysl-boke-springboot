package org.lingge.service;
import com.baomidou.mybatisplus.extension.service.IService;
import org.lingge.domain.ResponseResult;
import org.lingge.domain.entity.Classify;

/**
 * (Classify)表服务接口
 *
 * @author makejava
 * @since 2023-01-30 23:24:20
 */
public interface ClassifyService extends IService<Classify> {
    ResponseResult queryClassificationList();
}