package com.example.demo.util;

import com.example.demo.bean.response.Result;
import com.example.demo.constants.ResultCode;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

/**
 * Author: wangchao
 * Time: 2018-10-23
 * Description: This is
 */
public class FileUtil {
    /**
     * 获取文件的后缀名
     *
     * @param fileName
     * @return
     */
    public static String getExtensionName(String fileName) {
        if (fileName == null || fileName.trim().equals("")) {
            return "";
        }
        String prefix = fileName.substring(fileName.lastIndexOf('.') + 1);

        if (prefix.equals(fileName)) {
            return "";
        }
        return "." + prefix;
    }

    public static void main(String ar[]) {
        OutputUtil.d("后缀名：" + getExtensionName("111.ddd"));
        OutputUtil.d("后缀名：" + getExtensionName("11.1.ddd"));
        OutputUtil.d("后缀名：" + getExtensionName("11..1.ddd"));
        OutputUtil.d("后缀名：" + getExtensionName("111ddd"));
        OutputUtil.d("后缀名：" + getExtensionName(""));
        OutputUtil.d("后缀名：" + getExtensionName(null));

    }


    /**
     * 保存文件，直接以multipartFile形式
     *
     * @param multipartFile 输入
     * @param path          文件保存绝对路径
     * @return 返回文件名
     * @throws IOException
     */
    public static String saveFile(MultipartFile multipartFile, String path) throws IOException {
        if (multipartFile == null || multipartFile.isEmpty() || StringUtils.isBlank(multipartFile.getOriginalFilename())) {
            throw new IOException();
        }
        String root_fileName = multipartFile.getOriginalFilename();
        return saveFile(multipartFile, getExtensionName(root_fileName), path);
    }

    /**
     * 保存文件，直接以multipartFile形式
     *
     * @param multipartFile 输入
     * @param extensionName 后缀名
     * @param path          文件保存绝对路径
     * @return 返回文件名
     * @throws IOException
     */
    public static String saveFile(MultipartFile multipartFile, String extensionName, String path) throws IOException {
        return saveFile(multipartFile.getInputStream(), extensionName, path);
    }

    /**
     * 保存文件，直接以multipartFile形式
     *
     * @param inputStream   输入流
     * @param extensionName 后缀名
     * @param path          文件保存绝对路径
     * @return 返回文件名
     * @throws IOException
     */
    public static String saveFile(InputStream inputStream, String extensionName, String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        FileInputStream fileInputStream = (FileInputStream) inputStream;
        String fileName = UUID.randomUUID() + extensionName;
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path + File.separator + fileName));
        byte[] bs = new byte[1024];
        int len;
        while ((len = fileInputStream.read(bs)) != -1) {
            bos.write(bs, 0, len);
        }
        bos.flush();
        bos.close();
        return fileName;
    }
}
