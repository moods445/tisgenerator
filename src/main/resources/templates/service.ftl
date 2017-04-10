package ${config.packageName}.service;

import java.util.List;
import ${config.packageName}.dao.${table.entityName+"Dao"};
import ${config.packageName}.entity.${table.entityName};

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ${table.entityName}Service {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

<#assign daoName = table.entityName?uncap_first+"Dao">
    @Autowired
    private ${table.entityName+'Dao'} ${daoName};

    public ${table.entityName} selectByPrimaryKey(long id){
           return ${daoName}.selectByPrimaryKey(id);
    }
    public boolean deleteByPrimaryKey(long id){
           return ${daoName}.deleteByPrimaryKey(id)==1;
    }

    public boolean insertSelective(${table.entityName} record){
         record.setCreateAt(new Date());
         return ${daoName}.insertSelective(record)==1;
    }
    public boolean updateByPrimaryKeySelective(${table.entityName} record){
        record.setUpdateAt(new Date());
        return ${daoName}.updateByPrimaryKeySelective(record)==1;
    }
    public List<${table.entityName}> selectByCondition(${table.entityName} record){
        return ${daoName}.selectByCondition(record);
    }
}
