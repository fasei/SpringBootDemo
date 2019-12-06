package com.example.demo.controller;

import com.example.demo.bean.Base;
import com.example.demo.bean.response.Result;
import com.example.demo.config.ConfigData;
import com.example.demo.config.LocalFile;
import com.example.demo.constants.ResultCode;
import com.example.demo.controller.abs.AbsImgController;
import com.example.demo.util.FileUtil;
import com.example.demo.util.OutputUtil;
import io.swagger.annotations.Api;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sun.security.pkcs11.wrapper.Constants;

import java.io.*;
import java.util.UUID;

/**
 * Author: wangchao
 * Time: 2018-10-23
 * Description: This is
 */
@RestController
@RequestMapping(value = "/controller/img")
@Api(value = "图片处理", description = "图片处理", tags = "img", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class ImgController extends BaseController implements AbsImgController {

    @Autowired
    LocalFile mLocalFile;

    @Override
    public Result uploadImg(@RequestParam MultipartFile multipartFile, @RequestParam(required = true) String dataType) {
        if (multipartFile == null || multipartFile.isEmpty() || StringUtils.isBlank(multipartFile.getOriginalFilename())) {
            return Result.failure(ResultCode.PARAM_IS_BLANK);
        }
        String contentType = multipartFile.getContentType();
        if (!contentType.contains("")) {
            return Result.failure(ResultCode.PARAM_IS_BLANK);
        }
        String root_fileName = multipartFile.getOriginalFilename();
        logger.info("上传图片:name={},type={}", root_fileName, contentType);
        String saveFileName = null;
        try {
            saveFileName = FileUtil.saveFile(multipartFile, mLocalFile.getPath());
            OutputUtil.d("上传文件名："+saveFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (saveFileName == null) {
            return Result.failure(ResultCode.DATA_SAVE_WRONG);
        } else {
            return Result.success(saveFileName);
        }
    }


}
