function login() {
    var username = $("#username");
    // var username = document.getElementById("username");
    var password = $("#password");
    // var password = document.getElementById("password");
    $.ajax({
        url:"user/login1",
        type:"post",
        data:
            {
                username:username,
                password:password
            },
        dataType:"json",
        success:function(result){
            if(result.flag == "true"){
                // location.href = "http://localhost:8080/";
                alert("ok");
            }
        }
    });
};