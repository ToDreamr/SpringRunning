package com.pray.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.pray.annotation.BackendBaseLog;
import com.pray.common.Result;
import com.pray.file.FileDownLoadService;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * FileController
 *
 * @author 春江花朝秋月夜
 * @since 2023/11/2 19:13
 */
@RestController
public class FileController {
    @Value("${server.port}")
    private String port;

    @Value("${file.ip}")
    private String ip;

    @Resource
    private FileDownLoadService fileDownLoadService;

    @Resource
    private RestTemplate httpRestTemplate;

    @PostMapping("/upload")
    public Result<?> upload(MultipartFile file) throws IOException{
        //获取源文件名称
        String filename = file.getOriginalFilename();
        String flag = IdUtil.fastSimpleUUID();
        String rootFilePath = System.getProperty("user.dir") + "/files/" + flag + "_" + filename;

        File rootFile = new File(rootFilePath);

        if (rootFile.getParentFile().exists()){
            rootFile.getParentFile().mkdir();
        }
        FileUtil.writeBytes(file.getBytes(), rootFilePath);
        return Result.ok("http://" + ip + ":" + port + "/files/" + flag);
    }

    @GetMapping("/loginCheck")
    @BackendBaseLog(title = "登录校验",serviceType = "校验服务")
    public Result<String> checkAnnotationLogin(){
        return Result.ok("You are authorized");
    }

    @GetMapping("/fileDownLoad")
    public void fileDownLoad(HttpServletRequest request,@RequestParam String fileName,HttpServletResponse response) throws IOException {
        fileDownLoadService.download(request,fileName,response);
    }
    @GetMapping("/streamDownLoad")
    public void streamDownLoad(HttpServletRequest servletRequest,@RequestParam String fileName,HttpServletResponse response) throws IOException {
        Long startTime = System.currentTimeMillis();

        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("UTF-8"), "ISO8859-1"));
        httpRestTemplate.execute(
                "http://localhost:9201/api/fileDownLoad?" +
                        "fileName=" + fileName,
                HttpMethod.GET,
                new RequestCallback() {
                    @Override
                    public void doWithRequest(ClientHttpRequest request) throws IOException {
                        request.getHeaders().set("Accept", MediaType.APPLICATION_OCTET_STREAM_VALUE);
                    }
                },
                new ResponseExtractor<Void>() {
                    /**
                     * @param clientHttpResponse
                     * @return
                     * @throws IOException
                     */
                    @Override
                    public Void extractData(ClientHttpResponse clientHttpResponse) throws IOException {
                        InputStream inputStream = clientHttpResponse.getBody();
                        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                        ServletOutputStream servletOutputStream = response.getOutputStream();

                        byte[] bytes = new byte[8972];
                        int length;
                        while ((length = bufferedInputStream.read(bytes)) != -1) {
                            servletOutputStream.write(bytes, 0, length);
                        }
                        bufferedInputStream.close();
                        inputStream.close();
                        servletOutputStream.flush();
                        servletOutputStream.close();
                        return null;
                    }
                }
        );
        Long endTime = System.currentTimeMillis();
        System.out.println(String.format(">>>>>> send file: {%s}>>>>>>>",endTime-startTime));
    }
}
