package com.example.demo.controller;

import com.example.demo.bean.response.Result;
import com.example.demo.config.LocalFile;
import com.example.demo.constants.Constants;
import com.example.demo.exception.MyException;
import com.example.demo.mapper.BooksReadMapper;
import com.example.demo.mapper.FileInfoMapper;
import com.example.demo.model.*;
import com.example.demo.service.FileInfoService;
import com.example.demo.util.DateUtil;
import com.example.demo.util.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.List;

/**
 * Author: wangchao
 * Time: 2018-10-23
 * Description: This is
 */
@RestController
@RequestMapping(value = Constants.FileControllerPath)
@Api(tags = "文件管理中心", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class FileController extends BaseController {
    @Autowired
    LocalFile mLocalFile;

    @Autowired
    private FileInfoService fileInfoService;

    @Autowired
    private BooksReadMapper booksReadMapper;

    @Autowired
    FileInfoMapper mFileInfoMapper;

    @ApiOperation(value = "上传图片", notes = "上传图片功能")
    @RequestMapping(value = "/uploadimg", method = RequestMethod.POST)
    public Result uploadImg(@RequestParam MultipartFile multipartFile) {
        Result result = null;
        try {
            result = fileInfoService.upload(multipartFile);
        } catch (MyException e) {
            e.printStackTrace();
        }
        return result;
    }

    @ApiOperation(value = "下载文件", notes = "下载文件功能")
    @RequestMapping(value = "/download/{fileName}", method = RequestMethod.GET)
    public void downloadFile(@PathVariable String fileName, HttpServletResponse response) {
        try {
            logger.info("fileName:" + fileName);
            fileInfoService.downloadFile(fileName, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ApiOperation(value = "查看文件", notes = "查看文件功能")
    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> view(String fileName) {
        HttpHeaders header = new HttpHeaders();
        header.set("status", "error");

        FileInfo fileInfo = null;

        try {
            FileInfoExample e = new FileInfoExample();
            e.createCriteria().andFileNameEqualTo(fileName);
            List<FileInfo> fileInfos = mFileInfoMapper.selectByExample(e);
            fileInfo = fileInfos.get(0);
        } catch (Exception ee) {
            ee.printStackTrace();
        }

        if (fileInfo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (FileUtil.match(fileInfo.getFileName(), "jpg", "png", "gif", "bmp", "tif")) {
            header.setContentType(MediaType.IMAGE_JPEG);
        } else if (FileUtil.match(fileInfo.getFileName(), "html", "htm")) {
            header.setContentType(MediaType.TEXT_HTML);
        } else if (FileUtil.match(fileInfo.getFileName(), "pdf")) {
            header.setContentType(MediaType.APPLICATION_PDF);
        } else if (FileUtil.match(fileInfo.getFileName(), "txt")) {
            header.setContentType(MediaType.TEXT_PLAIN);
        } else if (FileUtil.match(fileInfo.getFileName(), "json")) {
            header.setContentType(MediaType.APPLICATION_JSON);
        } else if (FileUtil.match(fileInfo.getFileName(), "xml")) {
            header.setContentType(MediaType.APPLICATION_XML);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        header.add("X-Filename", fileInfo.getFileName());
        header.add("X-MD5", fileInfo.getMd5());

        String path = mLocalFile.getBasePath() + fileInfo.getFilePath() + File.separator + fileInfo.getFileName();
        File f = new File(path);
        try {
            FileInputStream in = new FileInputStream(f);
            header.set("status", "ok");
            return new ResponseEntity<>(new InputStreamResource(in), header, HttpStatus.OK);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "查看文字", notes = "查看文字功能")
    @RequestMapping(value = "/book", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> book(String id) {
        List<BooksReadWithBLOBs> booksReads = null;
        try {
            BooksReadExample e = new BooksReadExample();
            e.createCriteria().andIdEqualTo(Integer.valueOf(id));
            booksReads = booksReadMapper.selectByExampleWithBLOBs(e);
        } catch (Exception ee) {
            ee.printStackTrace();
        }
        if (booksReads == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        BooksReadWithBLOBs booksRead = booksReads.get(0);
        HttpHeaders header = new HttpHeaders();

        header.add("Content-Type", "text/plain; charset=UTF-8");
        try {
            InputStream in = new ByteArrayInputStream(booksRead.getContent().getBytes("utf-8"));
            return new ResponseEntity<>(new InputStreamResource(in), header, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "查看随机文字", notes = "查看随机文字功能")
    @RequestMapping(value = "/randombook", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> randombook() {
        ResponseEntity<InputStreamResource> result;
        while (true) {
            List<BooksRead> booksReads = booksReadMapper.getrRandomBook(1);
            if (booksReads.size() > 0) {
                result = book(booksReads.get(0).getId() + "");
                break;
            }
        }

        return result;
    }

    @ApiOperation(value = "随机获取数据", notes = "随机获取数据功能")
    @RequestMapping(value = "/randombooks", method = RequestMethod.GET)
    public Result randomBooks() {
        HashMap<Integer, BooksRead> hashMap = new HashMap<>();

        long start = DateUtil.getNowTime();
        while (true) {
            List<BooksRead> booksReads = booksReadMapper.getrRandomBook(1);
            if (booksReads.size() == 0) {
                continue;
            }
            if (hashMap.size() >= 10) {
                break;
            } else {
                hashMap.put(booksReads.get(0).getId(), booksReads.get(0));
            }
        }
        long end = DateUtil.getNowTime();

        logger.info("time:" + (end - start));

        return Result.success(hashMap.values());
    }

}
