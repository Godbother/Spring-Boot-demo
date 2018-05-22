$("#logout").click(function () {
    var url = "http://localhost:8080/logout";
    $.post(url,{},function(data){
        alert(data);
        window.location.href = "index.html";
    });
})