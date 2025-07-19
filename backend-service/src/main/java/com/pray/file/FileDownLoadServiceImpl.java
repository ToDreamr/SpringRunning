package com.pray.file;


import com.fasterxml.jackson.core.io.UTF8Writer;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * <p>
 * FileDownLoadServiceImpl
 * <p>
 *
 * @author 花行 (Rain)
 * @since 2025/6/28 13:11
 */
@Service
public class FileDownLoadServiceImpl implements FileDownLoadService {

    /**
     * @param request
     * @param fileName
     * @param response
     */
    @Override
    public void download(HttpServletRequest request, String fileName, HttpServletResponse response) throws IOException {
        Long startTime = System.currentTimeMillis();
        //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
//        response.setContentType("multipart/form-data");
        ServletOutputStream outputStream = response.getOutputStream();
        // 输出到客户端的文件名要使用setHeader来设置
        response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes(StandardCharsets.UTF_8), "ISO8859-1"));
        response.setCharacterEncoding("UTF-8");
        String filePath = "C:\\Users\\Rain\\Desktop\\" + fileName;

        response.setContentLength((int) new File(filePath).length());
        FileInputStream fileInputStream = new FileInputStream(filePath);
        byte[] buffer = new byte[4096];
        int length;
        while ((length = fileInputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
        outputStream.flush();
        Long endTime = System.currentTimeMillis();
        System.out.println(String.format(">>>>>> send file: {%s}>>>>>>>",endTime-startTime));
    }
}
