//测试freemarker的使用
<br>
//取pojo中的数据
id: ${person1.id}
name: ${person1.name}
<br>
id: ${person2.id?c}
name: ${person2.name}
<br>
id: ${person3.id}
name: ${person3.name}

<br>//-------------------
<br>
//取list集合中的数据
<#list list as persion>
<#--设置下标-->
    ${persion_index+1}
    <#if persion_index%2==0>
        //奇数行
        <br>
        <font style="color: red">
            ${persion.id?c}
            ${persion.name}
        </font>
        <br>
    <#else>
        //偶数行
        <br>
        <font style="color: blue">
            ${persion.id?c}
            ${persion.name}
        </font>
        <br>
    </#if>
</#list>

//遍历map集合中的数据--第一种
<br>
<#list map?keys as key>
    ${map[key].id}
    ${map[key].name}
    <br>
</#list>
//遍历map集合中的数据--第二种
<br>
${map.p1.id}
${map.p1.name}
<br>
${map.p2.id}
${map.p2.name}
<br>
${map.p3.id}
${map.p3.name}
<br>
${map.p4.id}
${map.p4.name}
<br>
//取日期
<br>
${date?date}
<br>
//取时间
<br>
${date?time}
<br>
//取时间和日期
<br>
${date?datetime}
<br>
//自定义日期时间格式
<br>
${date?string("yyyy/MM/dd HH/mm/ss")}
<br>
//null值的处理
${keynull!}
<br>
${keynull!"这里出现了空值!"}
<br>
//判断是否为空
<br>
<#--这里的第二个问号相当于exists-->
<#if keynull??>
    不为空值
<#else>
    这里出现了空值
</#if>
<br>
//另一种判断方式
<br>
<#if keynull?exists>
    不为空值
<#else>
    这里出现了空值
</#if>
<br>
//include标签的使用
<br>
<#include "html.htm">