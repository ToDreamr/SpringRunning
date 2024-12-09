package com.pray.mybatis;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * IOStreamResolve
 *
 * @author 九歌天上有
 * @since 2024/12/4 下午7:40
 */
public class IOStreamResolve {
    public static void main(String[] args) throws IOException {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileWriter fw = null;
        FileReader fr = null;
        BufferedReader br = null;
        BufferedWriter bw = null;
        PrintWriter pw = new PrintWriter(
                new OutputStreamWriter(System.out, StandardCharsets.UTF_8)
        );
        final String sourceDir = "D:\\Work\\SpringRunning\\backend-mybatis-core\\src\\main\\resources\\md\\如何读取resource中的文件.md";
        try {
            fis = new FileInputStream(sourceDir);//将文件读取到内存中的操作
            File file = new File(sourceDir);
            assert (file.exists());
            String outputName = file.getName().substring(0, file.getName().indexOf("."));
            String fileType = file.getName().substring(file.getName().indexOf("."));
            final String targetDir = "D:\\Work\\SpringRunning\\backend-mybatis-core\\src\\main\\resources\\md\\"+outputName+"-副本"+fileType+"\\";

            fos = new FileOutputStream(targetDir);

            int fileLength;
            //读取一个字节,字节流
//            while ((fileLength = fis.read()) != -1) {
//                pw.print((char) fileLength);
//            }
//            pw.flush();
            //转换流
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            OutputStreamWriter osw = new OutputStreamWriter(fos,StandardCharsets.UTF_8);

            //缓冲流
            br = new BufferedReader(isr);
            bw = new BufferedWriter(osw);

            //打印流

            //缓冲区，一个一个字节流
            char[] buffer = new char[1024];
            int length;
            while ((length = br.read(buffer)) != -1) {
                bw.write(buffer, 0, length);
            }
            bw.flush();

            //缓冲流,一行一行，字符流
            String line;
            while ((line=br.readLine())!=null) {
                bw.write(line);
                bw.newLine();
            }
            bw.flush();

            //打印流
            //定义格式和占位符：%s
            System.out.printf("%s健康%s", "吃营养食物过", "生活");
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (fis != null) {
                fis.close();
            }
            if (fos != null) {
                fos.close();
            }
        }

    }
}
