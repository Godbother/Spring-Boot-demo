$("#logout").click(function () {
    var url = "http://localhost:8080/userlogout";
    $.post(url,{},function(data){
        alert(data);
        window.location.href = "index.html";
    });
})