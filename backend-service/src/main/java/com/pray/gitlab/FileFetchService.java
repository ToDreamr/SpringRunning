package com.pray.gitlab;

import cn.hutool.core.codec.Base64;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.pray.gitlab.PrefixConstants.*;

/**
 * FileFetchService
 *
 * @author Cotton Eye Joe
 * @since 2024/11/19 14:49
 */
@Service
public class FileFetchService {
    private static final Logger sysLogger = LoggerFactory.getLogger(FileFetchService.class);
    /**
     * 根据用户名称和密码获取gitlab的private token，为Post请求
     *
     * @param ip    gitlab仓库的ip
     * @param userName  登陆gitlab的用户名
     * @param password  登陆gitlab的密码
     * @return  返回该用户的private token
     */
    public static String getPrivateTokenByPassword(String ip, String userName, String password) {

        /** 1.参数替换，生成获取指定用户private Token地址 */
        //  校验参数
        Objects.requireNonNull(ip, "参数ip不能为空！");
        Objects.requireNonNull(userName, "参数userName不能为空！");
        Objects.requireNonNull(password, "参数password不能为空！");
        //  参数准备，存入map
        Map<String, String> params = new HashMap<String, String>(4);
        params.put("REPO_IP", ip);
        params.put("USER_NAME", userName);
        params.put("PASSWORD", password);
        // 调用工具类替换,得到具体的调用地址
        String reqUserTokenUrl = PlaceholderUtil.anotherReplace(GITLAB_SESSION_API, params);
        sysLogger.debug(String.format("获取用户：%s的private token地址为：%s", userName,reqUserTokenUrl));

        /** 2.访问url，获取指定用户的信息 */
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(reqUserTokenUrl, null,String.class);
        sysLogger.debug(String.format("响应头为：%s，响应体为：%s", response.getHeaders(), response.getBody()));


        /** 3.解析结果 */
        String body = response.getBody();
        JSONObject jsonBody = (JSONObject) JSONUtil.parse(body);
        String privateToken =jsonBody.getString("private_token");
        sysLogger.debug(String.format("获取到用户：%s的privateToken为：%s", userName, privateToken));

        /** 4.返回privateToken */
        return privateToken;

    }

    /**
     * 使用gitLab api获取指定项目的projectId，为Get请求
     *
     * @param ip  项目仓库的ip地址
     * @param projectPath   项目的path，如：http://192.168.59.185/acountting/dispatcher-cloud.git，
     *                      则projectPath为：acountting/dispatcher-cloud
     * @param privateToken  用户个人访问gitlab库时的privateToken，可以通过{@link GitLabAPIUtils#getPrivateTokenByPassword}获取
     * @return  返回目的projectId
     */
    public static String getProjectId(String ip, String projectPath, String privateToken) {
        /** 1.参数替换，生成访问获取project信息的uri地址 */
        //  校验参数
        Objects.requireNonNull(ip, "参数ip不能为空！");
        Objects.requireNonNull(projectPath, "参数projectPath不能为空！");
        Objects.requireNonNull(privateToken, "参数privateToken不能为空！");
        //  参数准备，存入map
        Map<String, String> params = new HashMap<String, String>(4);
        params.put("REPO_IP", ip);
        params.put("PRIVATE_TOKEN", privateToken);
        // gitlab api要求项目的path需要安装uri编码格式进行编码，比如"/"编码为"%2F"
        try {
            params.put("PROJECT_PATH", URLEncoder.encode(projectPath, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(String.format("对%s进行URI编码出错！", projectPath));
        }
        // 调用工具类替换,得到具体的调用地址
        String getSingleProjectUrl = PlaceholderUtil.anotherReplace(GITLAB_SINGLE_PROJECT_API, params);
        sysLogger.debug(String.format("获取projectId的url：%s", getSingleProjectUrl));

        //  创建URI对象
        URI url = null;
        try {
            url = new URI(getSingleProjectUrl);
        } catch (URISyntaxException e) {
            throw new RuntimeException(String.format("使用%s创建URI出错！", getSingleProjectUrl));
        }

        /** 2.访问url，获取制定project的信息 */
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> reslut = restTemplate.getForEntity(url, String.class);
        sysLogger.debug(String.format("响应头为：%s，响应体为：%s", reslut.getHeaders(), reslut.getBody()));

        /** 3.解析结果 */
        if (reslut.getStatusCode() != HttpStatus.OK ) {
            throw new RuntimeException(String.format("请求%s出错！错误码为：%s", url, reslut.getStatusCode()));
        }
        //  如果响应码是200，说明正常拿到响应结果，解析出projectId返回即可
        JSONObject responBody = (JSONObject) JSONUtil.parse(reslut.getBody());
        String projectRepo = responBody.getString("http_url_to_repo");
        String projectId = responBody.getString("id");
        sysLogger.info(String.format("获取到项目：%s的projectId为：%s", projectRepo, projectId));

        /** 4.返回projectId */
        return projectId;
    }

    /**
     *
     * @param ip
     * @param projectPath
     * @param fileFullPath
     * @param branchName
     * @return
     * @throws Exception
     */

    public static String getFileContentFromRepository(String ip,String projectPath,
                                                      String fileFullPath, String branchName) throws Exception {
        //  校验参数
        Objects.requireNonNull(ip, "参数ip不能为空！");
        Objects.requireNonNull(projectPath, "参数projectPath不能为空！");
        Objects.requireNonNull(fileFullPath, "参数fileFullPath不能为空！");
        Objects.requireNonNull(branchName, "参数branchName不能为空！");

        /** 1.依据用户名、密码获取到用户的privateToken */
        String privateToken = "glpat-NPF1WUHuBY2Bvj7uHc5H";

        /** 2.使用privateToken获取项目的projectId */
        String projectId = "64639458";

        /** 3.使用参数替换形成请求git库中文件内容的uri */
        //  参数准备，存入map
        Map<String, String> params = new HashMap<String, String>(4);
        params.put("REPO_IP", ip);
        params.put("PROJECT_ID", projectId);
        params.put("PRIVATE_TOKEN", privateToken);
        params.put("FILE_PATH", fileFullPath);
        params.put("BRANCH_NAME", branchName);
        //  使用工具类替换参数
        String reqFileCotnetUri = PlaceholderUtil.anotherReplace(GET_FILE_V4, params);
        sysLogger.debug(String.format("获取文件：%s的uri：%s", fileFullPath, reqFileCotnetUri));

        /** 4.请求gitlab获取文件内容 */
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(reqFileCotnetUri, String.class);
        sysLogger.debug(String.format("响应头为：%s，响应体为：%s", response.getHeaders(), response.getBody()));

        /** 5.解析响应结果内容 */
        String body = response.getBody();
        JSONObject jsonBody = (JSONObject) JSONUtil.parse(body);
        String fileName = jsonBody.getString("file_name");
        String filePath = jsonBody.getString("file_path");
        String encoding = jsonBody.getString("encoding");
        String content = jsonBody.getString("content");
        String commitId = jsonBody.getString("commit_id");
        String lastCommitId = jsonBody.getString("last_commit_id");

        //  内容已经base64编码，如果需要获取原始文件内容可以参看api：
        //  https://docs.gitlab.com/ee/api/repository_files.html#get-raw-file-from-repository
        content = new String(Base64.decode(content), "UTF-8");

        sysLogger.debug(String.format(
                "获取http://%s 上%s项目 %s分支的%s文件,响应信息是:fileName :%s ,filePath:%s , 编码：%s ,内容:%s , commitId:%s ,lastCommitId :%s",
                ip,projectPath, branchName, fileFullPath, fileName, filePath, encoding, content, commitId,
                lastCommitId));

        /** 6.返回指定文件的内容 */
        sysLogger.debug(String.format("解析得到文件内容为：%s", content));
        return content;
    }
    //https://raw.githubusercontent.com/ToDreamr/mvn-repo/refs/heads/master/kingpower/kingpower-frame-common/1.0.0-241023/kingpower-frame-common-1.0.0-241023.jar

}
