package com.example.demo.controller.abs;

import com.example.demo.bean.response.Result;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public interface AbsImgController {
    @ApiOperation(value = "上传图片", notes = "上传图片功能")
    @RequestMapping(value = "/uploadimg", method = RequestMethod.POST)
    Result uploadImg(MultipartFile multipartFile);

}
