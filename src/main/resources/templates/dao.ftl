package ${config.packageName}.dao;

import java.util.List;
import ${config.packageName}.entity.${table.entityName};
import org.springframework.stereotype.Repository;
<#assign primarykeys = table.primarykeys>
<#assign columns = table.columns>
/**
 *
 *
 */
@Repository
public class ${table.entityName}Dao extends BaseDao{

    public ${table.entityName} selectByPrimaryKey(long id){
        return sqlSession.selectOne("select${table.entityName}ByPrimaryKey",id);
    }
    public int deleteByPrimaryKey(long id){
        return sqlSession.update("delete${table.entityName}ByPrimaryKey",id);
    }

    public int insertSelective(${table.entityName} record){
        return sqlSession.insert("insert${table.entityName}Selective",record);
    }
    public int updateByPrimaryKeySelective(${table.entityName} record){
        return sqlSession.update("update${table.entityName}ByPrimaryKeySelective",record);
    }
    public List<${table.entityName}> selectByCondition(${table.entityName} record){
        return sqlSession.selectList("select${table.entityName}ByCondition",record);
    }

}