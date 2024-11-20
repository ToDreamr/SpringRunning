## SpringRunner

> 专用于框架、中间件、数据库的测试代码仓库

在backend-service中的pom.xml中添加了打包的插件：

```xml
<!--打包的插件-->
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <version>3.1.2</version>
                <configuration>
                    <!-- 你的主类名Main , 如果你的主类名在 com.cnblogs.i 下，那么这里就是 com.cnblogs.i.Main -->
                    <mainClass>com.pray.SpringRunning</mainClass>
                </configuration>
            </plugin>
        </plugins>
    </build>
```
