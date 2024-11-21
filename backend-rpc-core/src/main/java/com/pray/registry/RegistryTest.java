package com.pray.registry;
import com.pray.config.RegistryConfig;
import com.pray.request.ServiceMetaInfo;
import org.junit.Before;
import org.junit.Test;
/**
 * RegistryTest
 *
 * @author Cotton Eye Joe
 * @since 2024/11/21 16:29
 */
public class RegistryTest {
    private static final Registry registry = new ZookeeperRegistry();
    @Before
    public void init(){
        RegistryConfig config = new RegistryConfig();
        registry.init(config);
    }
    @Test
    public void register() throws Exception {
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName("myService");
        serviceMetaInfo.setServiceVersion("1.0");
        serviceMetaInfo.setServiceHost("localhost");
        serviceMetaInfo.setServicePort(8082);
        registry.register(serviceMetaInfo);
    }

    public static void main(String[] args) throws Exception {
        RegistryConfig config = new RegistryConfig();
        registry.init(config);
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName("myService");
        serviceMetaInfo.setServiceVersion("1.0");
        serviceMetaInfo.setServiceHost("localhost");
        serviceMetaInfo.setServicePort(1234);
        registry.register(serviceMetaInfo);
    }
}
