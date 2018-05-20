$(document).ready(function(){
    var html = "";
    var url = "http://localhost:8080/10/1/searchall";
    var result = $("#resulttable");
    var input = $("#in").val();
    $.post(url,{},function(data){
        data.warcList.forEach(warcforeachprint);
        result.html(html);
        setpage(data);
    });
    function warcforeachprint(data) {
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
            "</form></th>" +
            "                        </tr>";
    }
    function setpage(data) {
        document.getElementById("fenye").innerHTML += "<li><a href='#'>&laquo;</a></li>";
        for (var i = 1;i<=data.pageacount;i++){
            if (i == 1){
                document.getElementById("fenye").innerHTML += "<li class='active'><a onclick=''>" + i + "</a></li>" ;
            }else {
                document.getElementById("fenye").innerHTML += "<li><a onclick=''>" + i + "</a></li>";
            }
        }
        document.getElementById("fenye").innerHTML += "<li><a href='#'>&raquo;</a></li>";
    }

})