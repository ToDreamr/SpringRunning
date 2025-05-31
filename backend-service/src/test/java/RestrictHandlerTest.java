import com.pray.delay.RestrictHandler;

import java.util.concurrent.*;


/**
 * <p>
 * RestrictHandlerTest
 * <p>
 *
 * @author 花行 (Rain)
 * @since 2025/5/31 15:38
 */

public class RestrictHandlerTest {
    public static void main(String[] args) throws InterruptedException {
        // 1. 初始化限流器（限制5次/2秒）
        RestrictHandler restrictHandler = new RestrictHandler(5, 2);

        // 2. 测试基本计数功能
        System.out.println("=== 测试基本计数 ===");
        String key = "user1";
        for (int i = 1; i <= 6; i++) {
            boolean isLimited = restrictHandler.checkAndPut(key, 5);
            System.out.printf("第%d次请求，是否被限流：%b%n", i, isLimited);
        }

        // 3. 测试自动清理过期键
        System.out.println("\n=== 测试自动清理 ===");
        String expireKey = "user2";
        restrictHandler.checkAndPut(expireKey, 5);
        System.out.println("添加键后，map中存在该键: " + restrictHandler.getMap().containsKey(expireKey));
        Thread.sleep(2100); // 等待2.1秒（超过时间窗口）
        System.out.println("等待2.1秒后，map中存在该键: " + restrictHandler.getMap().containsKey(expireKey));

        // 4. 测试并发安全
        System.out.println("\n=== 测试并发安全 ===");
        String concurrentKey = "user3";
        int threadCount = 10;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> restrictHandler.checkAndPut(concurrentKey, 5));
        }
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.SECONDS);
        System.out.println("并发请求后，计数结果: " +
                restrictHandler.getMap().get(concurrentKey).value.get()); // 应输出10

        // 5. 测试手动移除键
        System.out.println("\n=== 测试手动移除 ===");
        String removeKey = "user4";
        restrictHandler.checkAndPut(removeKey, 5);
        System.out.println("添加键后，map中存在该键: " + restrictHandler.map.containsKey(removeKey));
        restrictHandler.removeDelayKey(removeKey);
        System.out.println("手动移除后，map中存在该键: " + restrictHandler.map.containsKey(removeKey));

    }
}
