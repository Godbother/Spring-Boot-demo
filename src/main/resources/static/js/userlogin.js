$("#login").click(function(){
    var username = document.getElementById("inputEmail").value;
    var password = document.getElementById("inputPassword").value;
    var url = "http://localhost:8080/" + username + "/" + password + "/userlogin";
    $.post(url,{},
        function(data){
            if (data=="登录成功"){
                alert(data);
                window.location.href="indexsucc.html";
            }else{
                alert(data);
            }
        });

});