import com.pray.SpringRunning;
import com.pray.gitlab.FileFetchService;
import jakarta.annotation.Resource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

/**
 * GitLabTest
 *
 * @author Cotton Eye Joe
 * @since 2024/11/19 15:12
 */
@SpringBootTest(classes = SpringRunning.class)
@ComponentScan(basePackages = "com.pray.*")
public class GitLabTest {

    String repoIp;
    String privateToken;
    String projectPath1;
    String projectPath2;


    String userName;
    String password;

    String fileFullPath;
    String branchName;

    @Resource
    FileFetchService GitLabAPIUtils;

    @Before
    public void setUp() throws Exception {
        repoIp = "gitlab仓库ip";
        projectPath1 = "acountting/accounting-config-repo";
        projectPath2 = "acountting/csv-filefront-cloud";

        userName = "用户名";
        password="密码";

        fileFullPath = "/apps/cmup-clearing/0055-account.config";
        branchName = "develop";
    }

    @After
    public void tearDown() throws Exception {
        repoIp = null;
        projectPath1  = null;
        projectPath2  = null;
        userName = null;
        password = null;
        fileFullPath = null;
        branchName = null;
    }

    @Test
    public void testGetProjectId() {
        String privateToken = GitLabAPIUtils.getPrivateTokenByPassword(repoIp, userName, password);
        String projectId = GitLabAPIUtils.getProjectId(repoIp, projectPath1, privateToken);
        System.out.println("projectId = " + projectId);
    }

    @Test
    public void testGetPrivateToken() {
        String privateToken = GitLabAPIUtils.getPrivateTokenByPassword(repoIp, userName, password);
        System.out.println("projectId = " + privateToken);
    }
    @Test
    public void testFetchFileContent() throws Exception {
        GitLabAPIUtils.getFileContentFromRepository("gitlab.com/ToDreamr","","","master");
    }

}
