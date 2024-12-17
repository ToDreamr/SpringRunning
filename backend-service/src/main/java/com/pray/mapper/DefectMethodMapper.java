package com.pray.mapper;

import com.pray.entity.DefectMethod;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author Rainy-Heights
* @description 针对表【tb_defect_method(方法测试表)】的数据库操作Mapper
* @createDate 2024-11-08 14:41:47
* @Entity com.pray.entity.DefectMethod
*/
@Mapper
public interface DefectMethodMapper extends BaseMapper<DefectMethod> {
    List<DefectMethod> getQcMethodItems();
    int batchInsertItems(@Param("methodList") List<DefectMethod> defectMethodList);
    List<DefectMethod> selectForEachConditionally(@Param("dm_id")Integer dmId,@Param("runScript") String runScript);
}




