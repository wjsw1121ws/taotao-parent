<%--
  Created by IntelliJ IDEA.
  User: changchun_wu
  Date: 2019/1/24
  Time: 21:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<a href="javascript:void(0)" onclick="importAll()">一键导入</a>

<script type="text/javascript">

    function importAll() {
        $.ajax({
            url:"/importAll",
            type:"GET",
            success:function (data) {
                if (data.status==200){
                    alert("导入成功!");
                }else {
                    alert("导入失败!")
                }
            }

        })
    }
</script>