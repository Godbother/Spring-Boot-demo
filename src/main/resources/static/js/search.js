$(document).ready(function(){
    var html = "";
    var total = document.getElementById("total");
    $("#a0").click(function(){
        var a0 = $("#a0").text();
        $("#searchtype").text(a0);
    });
    $("#a1").click(function(){
        var a1 = $("#a1").text();
        $("#searchtype").text(a1);
    });
    $("#a2").click(function(){
        var a2 = $("#a2").text();
        $("#searchtype").text(a2);
        alert("输入week查询一周内，month查询一月内，year查询一年内");
    });
    $("#a3").click(function(){
        var a3 = $("#a3").text();
        $("#searchtype").text(a3);
        alert("输入week查询一周内，month查询一月内，year查询一年内");
    });
    $("#a4").click(function(){
        var a4 = $("#a4").text();
        $("#searchtype").text(a4);
    });
    $("#a5").click(function(){
        var a5 = $("#a5").text();
        $("#searchtype").text(a5);
    });
    $("#a6").click(function(){
        var a6 = $("#a6").text();
        $("#searchtype").text(a6);
    });
    $("#a7").click(function(){
        var a7 = $("#a7").text();
        $("#searchtype").text(a7);
    });
    $("#a8").click(function(){
        var a8 = $("#a8").text();
        $("#searchtype").text(a8);
    });

    $("#go").click(function(){
        html = "";
        var searchtype = $("#searchtype").text();
        var timestamp = Date.parse(new Date());
        var input = $("#in").val();
        var url = "";
        var result = $("#resulttable");
        if (input!=""){
            if (searchtype=="关键词搜索"){
                url = "http://localhost:8080/10/1/" + input + "/kwsearch";
            }else if (searchtype=="创建时间"){
                url = "http://localhost:8080/10/1/" + input + "/" + timestamp + "/create_at/timesearch";
            }else if (searchtype=="更新时间"){
                url = "http://localhost:8080/10/1/" + input + "/" + timestamp + "/update_at/timesearch";
            }else if(searchtype=="生成的URL"){
                url = "http://localhost:8080/10/1/warcUrl/" + input + "/fieldsearch";
            }else if(searchtype=="提及的URL"){
                url = "http://localhost:8080/10/1/responseUrl/" + input + "/fieldsearch";
            }else{
                url = "http://localhost:8080/10/1/" + searchtype + "/" + input + "/fieldsearch";
            }
            $.post(url,{},function(data){
                data.warcList.forEach(warcforeachprint);
                total.innerText = "共" + data.total + "条结果";
                result.html(html);
            });
        }

    });
    function warcforeachprint(data) {
        var input = $("#in").val();
        if (input==""||input=="undefine"){
            input = "yahoo";
        }
        html = html + "<tr>" +
            "                            <th>" + data.id + "</th>" +
            "                            <th>" + data.warcUrl + "</th>" +
            "                            <th>" + data.responseUrl + "</th>" +
            "                            <th>" + data.headerFields + "</th>" +
            "                            <th>" + data.addTime + "</th>" +
            "                            <th>" + data.updateTime + "</th>" +
            "                            <th><form action='http://localhost:8080/" + data.id + "/" + input + "/idsearch' method='post' target='_blank'><button type='submit' class='btn btn-primary'>详细</button></form>" +
            "<form action='http://localhost:8080/delwarcbyid' method='post'><input name='id' type='hidden' value='" + data.id +"'><button  class='btn btn-danger' type='submit'>删除</button></form>" +
            "</th>" +
            "                        </tr>";
    }
});