package org.lingge.Controller;

import org.lingge.annotation.SystemLog;
import org.lingge.domain.ResponseResult;
import org.lingge.service.ClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/classify")
@RestController
public class ClassifyController {
    @Autowired
    ClassifyService classifyService;
    //文章分类接口
    @SystemLog(businessName = "文章分类接口")
    @RequestMapping("/queryclassificationList")
    public ResponseResult queryClassificationList(){
        return classifyService.queryClassificationList();
    }
}
