package com.example.demo.controller.abs;

import com.example.demo.bean.response.Result;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface AbsImgController {
    @ApiOperation(value = "上传图片", notes = "上传图片功能")
    @RequestMapping(value = "/uploadimg", method = RequestMethod.POST)
    Result uploadImg(MultipartFile multipartFile);


    @ApiOperation(value = "下载文件", notes = "下载文件功能")
    @RequestMapping(value = "/download/{fileName}", method = RequestMethod.GET)
    void downloadFile(@PathVariable("fileName") String fileName, HttpServletResponse response);

    @ApiOperation(value = "查看文件", notes = "查看文件功能")
    @RequestMapping(value = "/view", method = RequestMethod.GET)
    ResponseEntity<InputStreamResource> view(@RequestParam String fileName);


    @ApiOperation(value = "查看文字", notes = "查看文字功能")
    @RequestMapping(value = "/book", method = RequestMethod.GET)
    ResponseEntity<InputStreamResource> book(@RequestParam String id);

}
