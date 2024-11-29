package com.pray.stream;

import java.io.*;
import java.text.MessageFormat;
import java.util.*;

/**
 * ListStreamFunc
 *
 * @author 九歌天上有
 * @since 2024/11/27 下午9:13
 */
public class ListStreamFunc {

    public static final List<String> MED_HEADERS = List.of(
            "病案号", "就诊号", "电子状态", "姓名", "身份证号", "病区",
            "科室", "主管医生", "入院日期", "出院日期", "住院天数",
            "临床主诊断", "首页主诊断", "费别", "送交人员", "签收人员",
            "编码人员", "质控人员", "归档人员"
    );
    // 提供一个静态方法来获取这个表头列表

    public static final Map<String, String> BASE_HEADERS;
    static {
        Map<String, String> baseHeaders = new LinkedHashMap<>();
        baseHeaders.put("statusName", "审核状态");
        baseHeaders.put("applyNo", "申请序号");
        baseHeaders.put("requestName", "申请人员");
        baseHeaders.put("requestReason", "申请理由");
        baseHeaders.put("mrNo", "病案号");
        baseHeaders.put("visitNo", "就诊号");
        baseHeaders.put("patName", "姓名");
        baseHeaders.put("idCard", "身份证号");
        baseHeaders.put("dept", "科室");
        baseHeaders.put("wardName", "病区");
        baseHeaders.put("cisDiagNamePri", "临床主诊断");
        baseHeaders.put("inDiagName", "首页主诊断");
        baseHeaders.put("verifyEmpName", "审核人员");
        baseHeaders.put("failureReason", "拒绝理由");
        BASE_HEADERS = Collections.unmodifiableMap(baseHeaders);
    }
    public static List<String> getAdditionalHeaders() {
        return MED_HEADERS;
    }
    private static final Map<String,String> finalMap = BASE_HEADERS;
    private static final List<String> finalList = List.of(
            "小明","王大奶","Johnny","蜜雪冰城","345","霸王茶姬","Star Bucks","王大奶","霸王茶姬","321"
    );

    /**
     *  如何创建出stream？<br/>
     *  实现Collection的接口，例如List，Map，Set<br/>
     *  使用Arrays.stream()方法实现<br/>
     *  使用Stream接口构造<br/>
     *
     *  如何收集好完成流操作的数据<br/>
     *  ().collect(Collectors.toList())<br/>
     *  ().collect(Collectors.toSet())<br/>
     */
    public static void main(String[] args) {

        System.out.println("==================================Java中的流Stream操作=====================================");
        //筛选：filter()
        //筛选出合适条件的列表元素->filter
        List<String> list = MED_HEADERS.stream().filter(
                s -> s.length() <= 3
        ).toList();

        //操作：forEach()
        //对列表里面的每一个元素进行一些操作,此时可以省略掉stream的声明
        List<String> result = new ArrayList<>();//使用新的列表来进行结果集的存储
        finalList.stream().forEach(o -> {
            result.add(o.toUpperCase());
        });
        System.out.print("对列表里面的每一个元素进行一些操作forEach(): ");
        result.forEach(s -> {
            System.out.print(s);
            System.out.print(" ");
        });
        System.out.println();

        //映射:map()
        //按照设定好的函数逻辑将元素从A映射到B
        //先筛选元素，再将筛选好的元素进行映射
        List<char[]> mapList = MED_HEADERS.stream().filter(
                s -> s.length() <= 3
        ).map(String::toCharArray).toList();
//        for (char[] chars : mapList) {
//            for (char c : chars) {
//                System.out.printf(c+" ");
//            }
//        }

        //合并流：flatMap()
        //当遇见两个流及以上的时候，需要将流进行合并的需求场景即可使用这个方法
        //元素内部是List
        List<List<String>> doubleList = List.of(MED_HEADERS, finalList);
        List<String> flapList = doubleList.stream().flatMap(Collection::stream).filter(
                s -> s.length() <= 3
        ).toList();
        System.out.println("合并流：flatMap(): " + flapList);

        //去重:distinct()

        System.out.println("去重:distinct(): " + finalList.stream().distinct().toList());

        //排序：sorted(),指定排序比较器
        System.out.println("排序: " + finalList.stream().sorted(
                (o1, o2) -> o1.length() - o2.length()
        ).toList());

        //限制流的大小或者跳过一些元素：limited(),skip()
        System.out.println("限制流的大小或者跳过一些元素: " + finalList.stream().limit(4).skip(2).toList());

        //终结操作，指的是将流终结或者收集起来组装成数据
        //forEach(fun)

        //组装成Set、List
        System.out.println("组装成Set、List: " + new HashSet<>(finalList).stream().toList());
        // System.out.println(finalList.stream().collect(Collectors.toSet()).stream().toList());

        //组装成数组toArray()
        String[] array = finalList.toArray(String[]::new);
        System.out.print("组装成数组toArray(): ");
        for (String string : array) {
            System.out.print(string + " ");
        }
        System.out.println();

        //聚合操作：reduce()
        String reduce = finalList.stream().reduce("", String::concat);//初始值
        System.out.println("聚合操作：reduce() " + reduce);

        //检查流中符合条件的值
        boolean isMatched = finalList.stream().anyMatch(
                s -> s.contains("霸王")
        );
        System.out.println("流中含有霸王词的元素：" + isMatched);

        //统计元素：count()
        System.out.println("流元素的数量：" + finalList.stream().count());

        //并行的流：parallelStream(),使用对应转换映射操作：mapToInt()
        int[] values = finalList.parallelStream().filter(
                s -> s.matches("-?\\d+(\\.\\d+)?")//收集数据元素
        ).mapToInt(Integer::valueOf).toArray();
        for (int value : values) {
            System.out.print(value + " ");
        }
        System.out.println();

        String message = finalList.stream().reduce("", String::concat);
        System.out.println(MessageFormat.format("{0}", message));

        InputStream stream = Thread.currentThread().getClass().getResourceAsStream("classpath:com/pray/CustomPluginMaven.class");
        assert stream != null;
        BufferedInputStream bufferedInputStream = new BufferedInputStream(stream);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream));
        bufferedReader.lines().forEach(System.out::println);
    }
}
