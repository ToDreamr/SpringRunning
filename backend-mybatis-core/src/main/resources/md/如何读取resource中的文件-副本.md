# 如何从Resource中读取文件

#### 使用`getResource`和`getPath`方法，直接通过`getResource(fileName)`方法获取文件路径，注意如果是`路径中带有中文一定要使用URLDecoder.decode解码`。

```java
    /**
     * 直接通过文件名getPath来获取路径
     *
     * @param fileName
     * @throws IOException
     */
    public void function2(String fileName) throws IOException {
        String path = this.getClass().getClassLoader().getResource(fileName).getPath();//注意getResource("")里面是空字符串
        System.out.println(path);
        String filePath = URLDecoder.decode(path, "UTF-8");//如果路径中带有中文会被URLEncoder,因此这里需要解码
        System.out.println(filePath);
        getFileContent(filePath);
    }

```

#### 直接使用`getResourceAsStream`方法获取流，上面的几种方式都需要获取文件路径，但是在SpringBoot中所有文件都在jar包中，没有一个实际的路径，因此可以使用以下方式。

```java
    /**
     * 直接使用getResourceAsStream方法获取流
     * springboot项目中需要使用此种方法，因为jar包中没有一个实际的路径存放文件
     *
     * @param fileName
     * @throws IOException
     */
    public void function4(String fileName) throws IOException {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(fileName);
        getFileContent(in);
    }

```

#### 类似的办法，同样使用上面提供的函数

```java
    /**
     * 直接使用getResourceAsStream方法获取流
     * 如果不使用getClassLoader，可以使用getResourceAsStream("/配置测试.txt")直接从resources根路径下获取
     *
     * @param fileName
     * @throws IOException
     */
    public void function5(String fileName) throws IOException {
        InputStream in = this.getClass().getResourceAsStream("/" + fileName);
        getFileContent(in);
    }

```

#### 通过`ClassPathResource`类获取文件流

```java
    /**
     * 通过ClassPathResource类获取，建议SpringBoot中使用
     * springboot项目中需要使用此种方法，因为jar包中没有一个实际的路径存放文件
     *
     * @param fileName
     * @throws IOException
     */
    public void function6(String fileName) throws IOException {
        ClassPathResource classPathResource = new ClassPathResource(fileName);
        InputStream inputStream = classPathResource.getInputStream();
        getFileContent(inputStream);
    }

```

#### 绝对路径获取项目中文件的位置，只是本地绝对路径，不能用于服务器获取

```java
    /**
     * 通过绝对路径获取项目中文件的位置（不能用于服务器）
     * @param fileName
     * @throws IOException
     */
    public void function7(String fileName) throws IOException {
        String rootPath = System.getProperty("user.dir");//E:\WorkSpace\Git\spring-framework-learning-example
        String filePath = rootPath + "\\chapter-2-springmvc-quickstart\\src\\main\\resources\\"+fileName;
        getFileContent(filePath);
    }

```

#### 通过`new File("")`获取当前的绝对路径，只是本地绝对路径，不能用于服务器获取。

```java
    /**
     * 通过绝对路径获取项目中文件的位置（不能用于服务器）
     * @param fileName
     * @throws IOException
     */
    public void function8(String fileName) throws IOException {
        //参数为空
        File directory = new File("");
        //规范路径：getCanonicalPath() 方法返回绝对路径，会把 ..\ 、.\ 这样的符号解析掉
        String rootCanonicalPath = directory.getCanonicalPath();
        //绝对路径：getAbsolutePath() 方法返回文件的绝对路径，如果构造的时候是全路径就直接返回全路径，如果构造时是相对路径，就返回当前目录的路径 + 构造 File 对象时的路径
        String rootAbsolutePath =directory.getAbsolutePath();
        System.out.println(rootCanonicalPath);
        System.out.println(rootAbsolutePath);
        String filePath = rootCanonicalPath + "\\chapter-2-springmvc-quickstart\\src\\main\\resources\\"+fileName;
        getFileContent(filePath);
    }


```
