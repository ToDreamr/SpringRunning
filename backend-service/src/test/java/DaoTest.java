import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pray.SpringRunning;
import com.pray.entity.DefectMethod;
import com.pray.mapper.DefectMethodMapper;
import jakarta.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * DaoTest
 *
 * @author 九歌天上有
 * @since 2024/12/17 下午1:05
 */
@SpringBootTest(classes = SpringRunning.class)
@RunWith(SpringRunner.class)
public class DaoTest {
    @Resource
    DefectMethodMapper defectMethodMapper;
    @Test
    public void batchTest(){

        List<DefectMethod> defectMethodList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            DefectMethod defectMethod = new DefectMethod();
            defectMethod.setDmId(32+i);
            defectMethod.setDefectName("A"+i);
            defectMethod.setMethodName("test_c8a35cb3d98f"+i);
            defectMethod.setDefectName("test_aec1b2621c4d"+i);
            defectMethod.setStdClassifiedId(77+i);
            defectMethod.setRunArgs("test_400ed76f3f05"+i);
            defectMethod.setRunScript("test_5cd4a6b144a"+i);
            defectMethodList.add(defectMethod);
        }


        defectMethodMapper.batchInsertItems(defectMethodList);
    }
    @Test
    public void conditionalTest(){
        List<DefectMethod> defectMethodList = defectMethodMapper.selectForEachConditionally(12, "javascript");
        defectMethodList.forEach(System.out::println);
    }
    @Test
    public void queryWrapper(){
        QueryWrapper<DefectMethod> wrapper = new QueryWrapper<>();//用于构造查询的装饰器，自定义的插入语句（通过 Wrapper 等方式添加条件），可以用来添加条件
        wrapper.geSql("dm_id",String.valueOf(12));
        defectMethodMapper.selectList(wrapper).forEach(System.out::println);
    }
}
