package com.example.demo.util;

import com.example.demo.bean.response.Result;
import com.example.demo.constants.ResultCode;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
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


    public static final String BASE_PATH = "";

    /**
     * 获取文件系统分隔符
     * @return
     */
    public static String getFileSeparator(){
        return  System.getProperty("file.separator");
    }

    /**
     * 获取文件保存的文件夹
     * 1.
     * @return
     */
    public static String getFolder(){
        String dateStr = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String timeStr = new SimpleDateFormat("HHmmss").format(new Date());
        return   getFileSeparator() + dateStr + getFileSeparator() + timeStr+getFileSeparator();
    }

    /**
     * 获取随机文件名
     * @return
     */
    public static String getFileName(){
        return UUIDUtil.getUid("Wanc_" , 17) ;
    }

    /**
     * 返回文件名后缀
     * @param fileName
     * @return
     */
    public static String getFileNameSub(String fileName){
        return  fileName.substring(fileName.lastIndexOf("."),fileName.length());
    }

    /**
     * 文件上传
     *
     * @param file
     * @param filePath
     * @param fileName
     * @throws Exception
     */
    //TODO 这样上传,如果文件过大,会导致内存溢出
    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath+fileName);
        out.write(file);
        out.flush();
        out.close();
    }

    /**
     * 匹配文件的后缀
     * @param fileName
     * @param exts
     * @return
     */
    public static boolean match(String fileName, String... exts) {
        if (StringUtils.isBlank(fileName)) {
            return false;
        }
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        for (String ext1 : exts) {
            if (ext.contains(ext1.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 文件路径取md5
     * @param path
     * @return
     */
    public static String md5File(String path) {
        String value = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(path);
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[4096];
            int length;
            while ((length = fis.read(buffer, 0, 4096)) != -1) {
                md.update(buffer, 0, length);
            }

            StringBuilder sb = new StringBuilder(md.digest().length);
            for (byte x : md.digest()) {
                if ((x & 0xff) >> 4 == 0) {
                    sb.append("0").append(Integer.toHexString(x & 0xff));
                } else {
                    sb.append(Integer.toHexString(x & 0xff));
                }
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
//            log.error("calc md5 failed", e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
//                    log.error("close resource failed");
                    e.printStackTrace();
                }

            }
        }

        return value;
    }


}
