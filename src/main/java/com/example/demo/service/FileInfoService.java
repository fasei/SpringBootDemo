package com.example.demo.service;


import com.example.demo.bean.response.Result;
import com.example.demo.config.LocalFile;
import com.example.demo.constants.ResultCode;
import com.example.demo.exception.MyException;
import com.example.demo.mapper.FileInfoMapper;
import com.example.demo.model.FileInfo;
import com.example.demo.model.FileInfoExample;
import com.example.demo.util.DateUtil;
import com.example.demo.util.FileUtil;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileInfoService {

    @Autowired
    LocalFile mLocalFile;

    @Autowired
    FileInfoMapper mFileInfoMapper;

    /**
     * 上传文件
     *
     * @param multipartFile
     * @return
     * @throws MyException
     */
    @Transactional
    public Result<?> upload(MultipartFile multipartFile) throws MyException {
        if (multipartFile == null /*|| multipartFile.isEmpty() */ || StringUtils.isBlank(multipartFile.getOriginalFilename())) {
            return Result.failure(ResultCode.PARAM_IS_BLANK);
        }
        String contentType = multipartFile.getContentType();
        if (!contentType.contains("")) {
            return Result.failure(ResultCode.PARAM_IS_BLANK);
        }

        //基础路径  E:/springboot-upload/image/
        String basePath = mLocalFile.getBasePath();
        //获取文件保存路径 \20180608\113339\
        String folder = FileUtil.getFolder();
        // 获取前缀为"FL_" 长度为20 的文件名  FL_eUljOejPseMeDg86h.png
        String fileName = FileUtil.getFileName() + FileUtil.getFileNameSub(multipartFile.getOriginalFilename());

        try {
            // E:\springboot-upload\image\20180608\113339
            Path filePath = Files.createDirectories(Paths.get(basePath, folder));
//            log.info("path01-->{}", filePath);

            //写入文件  E:\springboot-upload\image\20180608\113339\FL_eUljOejPseMeDg86h.png
            Path fullPath = Paths.get(basePath, folder, fileName);
//            log.info("fullPath-->{}", fullPath);
            // E:\springboot-upload\image\20180608\113339\FL_eUljOejPseMeDg86h.png
            Files.write(fullPath, multipartFile.getBytes(), StandardOpenOption.CREATE);

            //保存文件信息
            FileInfo fileInfo = new FileInfo();
            fileInfo.setFileOriginName(multipartFile.getOriginalFilename());
            fileInfo.setFileType(multipartFile.getContentType());
            fileInfo.setSize(multipartFile.getSize());
            fileInfo.setValid((byte) 0x01);
            fileInfo.setIsDelete((byte) 0x00);
            fileInfo.setMd5(FileUtil.md5File(folder));
            fileInfo.setFileName(fileName);
            fileInfo.setFilePath(folder);
            fileInfo.setUploadTime(DateUtil.getCurrentTimeStamp());

            mFileInfoMapper.insert(fileInfo);
        } catch (Exception e) {
            Path path = Paths.get(basePath, folder);
            e.printStackTrace();
//            log.error("写入文件异常,删除文件。。。。", e);
            try {
                Files.deleteIfExists(path);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return Result.failure(ResultCode.DATA_IS_WRONG);
        }
        return Result.success("上传成功");
    }


    /**
     * 文件下载
     *
     * @param fileName
     * @param res
     * @throws MyException
     * @throws UnsupportedEncodingException
     */
    public void downloadFile(String fileName, HttpServletResponse res) throws MyException, UnsupportedEncodingException {
        res.setHeader("downStatus", "error");
        if (fileName == null) {
            throw new MyException(1001, "文件名不能为空");
        }

        // 通过文件名查找文件信息
        FileInfoExample e = new FileInfoExample();
        e.createCriteria().andFileNameEqualTo(fileName);
        List<FileInfo> fileInfos = mFileInfoMapper.selectByExample(e);
//        log.info("fileInfo-->{}", fileInfo);
        if (fileInfos.size() == 0) {
            throw new MyException(2001, "文件名不存在");
        }
        String basePath = mLocalFile.getBasePath();

        FileInfo fileInfo = fileInfos.get(0);

        //设置响应头
        res.setContentType("application/force-download");// 设置强制下载不打开
        res.addHeader("Content-Disposition", "attachment;fileName=" +
                new String(fileInfo.getFileName().getBytes("gbk"), "iso8859-1"));// 设置文件名
        res.setHeader("Context-Type", "application/xmsdownload");

        //判断文件是否存在
        File file = new File(Paths.get(basePath, fileInfo.getFilePath(), fileName).toString());
        if (file.exists()) {
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = res.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
                res.setHeader("downStatus", "ok");
//                log.info("下载成功");
            } catch (Exception ee) {
                ee.printStackTrace();
                throw new MyException(9999, ee.getMessage());
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException ee) {
                        ee.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException ee) {
                        ee.printStackTrace();
                    }
                }
            }
        } else {
            // 文件路径不存在


        }
    }



}
