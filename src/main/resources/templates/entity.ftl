package ${config.packageName}.entity;

import java.util.Date;
<#assign primarykeys = table.primarykeys>
<#assign columns = table.columns>
/**
 *
 *
 */
public class ${table.entityName} {

<#assign keys = primarykeys?keys>
<#list keys as key>
    private ${primarykeys[key].propertyType}  ${primarykeys[key].propertyName};
</#list>
<#assign keys = columns?keys>
<#list keys as key>
    private ${columns[key].propertyType}  ${columns[key].propertyName};
</#list>

}