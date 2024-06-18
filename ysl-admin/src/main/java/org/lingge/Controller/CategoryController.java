package org.lingge.Controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import org.lingge.annotation.SystemLog;
import org.lingge.domain.ResponseResult;
import org.lingge.domain.entity.Classify;
import org.lingge.domain.vo.CategoryVo;
import org.lingge.domain.vo.ExcelCategoryVo;
import org.lingge.domain.vo.PageVo;
import org.lingge.enums.AppHttpCodeEnum;
import org.lingge.service.ClassifyService;
import org.lingge.utils.BeanCopyUtils;
import org.lingge.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@RestController
@RequestMapping("/content/category")
public class CategoryController {
    @Autowired
    private ClassifyService classifyService;
    @SystemLog(businessName = "System-写博客查询分类接口")
    @GetMapping("/listAllCategory")
    public ResponseResult listAllCategory(){
        List<CategoryVo> list = classifyService.listAllCategory();
        return ResponseResult.okResult(list);
    }
    @GetMapping("/list")
    @SystemLog(businessName = "System-分类管理查询所有分类接口")
    public ResponseResult list(Classify Classify, Integer pageNum, Integer pageSize) {
        PageVo pageVo = classifyService.selectCategoryPage(Classify, pageNum, pageSize);
        return ResponseResult.okResult(pageVo);
    }
    @SystemLog(businessName = "System-编辑更新分类接口")
    @PutMapping
    public ResponseResult edit(@RequestBody Classify classify){
        classifyService.updateById(classify);
        return ResponseResult.okResult();
    }
    @SystemLog(businessName = "System-根据id删除分类接口")
    @DeleteMapping(value = "/{id}")
    public ResponseResult remove(@PathVariable(value = "id")Long id){
        classifyService.removeById(id);
        return ResponseResult.okResult();
    }
    @SystemLog(businessName = "System-添根据id查询接口")
    @GetMapping(value = "/{id}")
    public ResponseResult getInfo(@PathVariable(value = "id")Long id){
        Classify classify = classifyService.getById(id);
        return ResponseResult.okResult(classify);
    }
    @SystemLog(businessName = "System-添加分类接口")
    @PostMapping
    public ResponseResult add(@RequestBody Classify classify){
        classifyService.save(classify);
        return ResponseResult.okResult();
    }
//    @SystemLog(businessName = "System-导出接口")
    @PreAuthorize("@ps.hasPermission('content:category:export')")
    @GetMapping("/export")
    public void export(HttpServletResponse response) {
        try {
            //设置下载文件的请求头
            WebUtils.setDownLoadHeader("分类.xlsx", response);
            //获取需要导出的数据
            List<Classify> categoryVos = classifyService.list();

            List<ExcelCategoryVo> excelCategoryVos = BeanCopyUtils.copyList(categoryVos, ExcelCategoryVo.class);
            //把数据写入到Excel中
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class).autoCloseStream(Boolean.FALSE).sheet("分类导出")
                    .doWrite(excelCategoryVos);

        } catch (Exception e) {
            //如果出现异常也要响应json
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(result));
        }
    }
}
