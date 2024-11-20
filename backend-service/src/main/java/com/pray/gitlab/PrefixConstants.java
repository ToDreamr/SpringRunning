package com.pray.gitlab;

/**
 * PrefixConstants
 *
 * @author Cotton Eye Joe
 * @since 2024/11/19 14:47
 */
public class PrefixConstants {
    protected static final String GITLAB_FILE_CONTENT_API = "https://#{REPO_IP}/api/v3/projects/#{PROJECT_ID}/repository/files?" +
            "private_token=#{PRIVATE_TOKEN}&file_path=#{FILE_PATH}&ref=#{BRANCH_NAME}";

    protected static final String GITLAB_SESSION_API = "https://#{REPO_IP}/api/v3/session?login=#{USER_NAME}&password=#{PASSWORD}";
    protected static final String GITLAB_SINGLE_PROJECT_API = "http://#{REPO_IP}/api/v3/projects/#{PROJECT_PATH}?" +
            "private_token=#{PRIVATE_TOKEN}";
    protected static String GITLAB_FILECONTENT_API = "https://#{REPO_IP}/api/v3/projects/#{PROJECT_ID}/repository/files" +
            "?private_token=#{PRIVATE_TOKEN}&file_path=#{FILE_PATH}&ref=#{BRANCH_NAME}";

    //curl --header "PRIVATE-TOKEN: <your_access_token>" \
    //  --url "https://gitlab.example.com/api/v4/projects/13083/repository/files/app%2Fmodels%2Fkey%2Erb?ref=main"
    protected static String GET_FILE_V4 = "http://#{REPO_IP}/api/v4/projects/#{PROJECT_ID}/repository/files" +
            "?private_token=#{PRIVATE_TOKEN}&file_path=#{FILE_PATH}&ref=#{BRANCH_NAME}";
}
