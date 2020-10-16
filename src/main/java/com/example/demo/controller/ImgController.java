package com.example.demo.controller;

import com.example.demo.bean.Base;
import com.example.demo.bean.response.Result;
import com.example.demo.config.ConfigData;
import com.example.demo.config.LocalFile;
import com.example.demo.constants.ResultCode;
import com.example.demo.controller.abs.AbsImgController;
import com.example.demo.exception.MyException;
import com.example.demo.mapper.FileInfoMapper;
import com.example.demo.model.FileInfo;
import com.example.demo.model.FileInfoExample;
import com.example.demo.service.FileInfoService;
import com.example.demo.util.FileUtil;
import com.example.demo.util.OutputUtil;
import io.swagger.annotations.Api;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import sun.security.pkcs11.wrapper.Constants;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

/**
 * Author: wangchao
 * Time: 2018-10-23
 * Description: This is
 */
@RestController
@RequestMapping(value = "/controller/img")
@Api(value = "图片处理", tags = "img", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class ImgController extends BaseController implements AbsImgController {
    @Autowired
    LocalFile mLocalFile;

    @Autowired
    private FileInfoService fileInfoService;


    @Autowired
    FileInfoMapper mFileInfoMapper;

    @Override
    public Result uploadImg(@RequestParam MultipartFile multipartFile) {


        Result result = null;
        try {
            result = fileInfoService.upload(multipartFile);
        } catch (MyException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void downloadFile(@PathVariable String fileName, HttpServletResponse response) {
        try {
            logger.info("fileName:" + fileName);
            fileInfoService.downloadFile(fileName, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ResponseEntity<InputStreamResource> view(String fileName) {
        List<FileInfo> fileInfos = null;
        try {
            FileInfoExample e = new FileInfoExample();
            e.createCriteria().andFileNameEqualTo(fileName);
            fileInfos = mFileInfoMapper.selectByExample(e);
        } catch (Exception ee) {
            ee.printStackTrace();
        }
        if (fileInfos == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        FileInfo fileInfo = fileInfos.get(0);
        HttpHeaders header = new HttpHeaders();
        if (FileUtil.match(fileInfo.getFileName(), "jpg", "png", "gif", "bmp", "tif")) {
            header.setContentType(MediaType.IMAGE_JPEG);
        } else if (FileUtil.match(fileInfo.getFileName(), "html", "htm")) {
            header.setContentType(MediaType.TEXT_HTML);
        } else if (FileUtil.match(fileInfo.getFileName(), "pdf")) {
            header.setContentType(MediaType.APPLICATION_PDF);
        } else if (FileUtil.match(fileInfo.getFileName(), "txt")) {
            header.setContentType(MediaType.TEXT_PLAIN);
        }  else if (FileUtil.match(fileInfo.getFileName(), "json")) {
            header.setContentType(MediaType.APPLICATION_JSON);
        }  else if (FileUtil.match(fileInfo.getFileName(), "doc")) {
            header.setContentType(MediaType.valueOf("application/msword  "));
        } else if (FileUtil.match(fileInfo.getFileName(), "xml")) {
            header.setContentType(MediaType.APPLICATION_XML);
        }else {
            header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        }
        header.add("X-Filename", fileInfo.getFileName());
        header.add("X-MD5", fileInfo.getMd5());

        String path = mLocalFile.getBasePath() + fileInfo.getFilePath() + File.separator + fileInfo.getFileName();
        File f = new File(path);
        try {
            FileInputStream in = new FileInputStream(f);
            return new ResponseEntity<>(new InputStreamResource(in), header, HttpStatus.OK);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<InputStreamResource> book(String id) {
        List<FileInfo> fileInfos = null;
        try {
            FileInfoExample e = new FileInfoExample();
            e.createCriteria().andFileNameEqualTo(id);
            fileInfos = mFileInfoMapper.selectByExample(e);
        } catch (Exception ee) {
            ee.printStackTrace();
        }
        if (fileInfos == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        FileInfo fileInfo = fileInfos.get(0);
        HttpHeaders header = new HttpHeaders();
        if (FileUtil.match(fileInfo.getFileName(), "jpg", "png", "gif", "bmp", "tif")) {
            header.setContentType(MediaType.IMAGE_JPEG);
        } else if (FileUtil.match(fileInfo.getFileName(), "html", "htm")) {
            header.setContentType(MediaType.TEXT_HTML);
        } else if (FileUtil.match(fileInfo.getFileName(), "pdf")) {
            header.setContentType(MediaType.APPLICATION_PDF);
        } else if (FileUtil.match(fileInfo.getFileName(), "txt")) {
            header.setContentType(MediaType.TEXT_PLAIN);
        }  else if (FileUtil.match(fileInfo.getFileName(), "json")) {
            header.setContentType(MediaType.APPLICATION_JSON);
        }  else if (FileUtil.match(fileInfo.getFileName(), "doc")) {
            header.setContentType(MediaType.valueOf("application/msword  "));
        } else if (FileUtil.match(fileInfo.getFileName(), "xml")) {
            header.setContentType(MediaType.APPLICATION_XML);
        }else {
            header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        }
        header.add("X-Filename", fileInfo.getFileName());
        header.add("X-MD5", fileInfo.getMd5());

        String path = mLocalFile.getBasePath() + fileInfo.getFilePath() + File.separator + fileInfo.getFileName();
        File f = new File(path);
        try {
            FileInputStream in = new FileInputStream(f);
            return new ResponseEntity<>(new InputStreamResource(in), header, HttpStatus.OK);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
