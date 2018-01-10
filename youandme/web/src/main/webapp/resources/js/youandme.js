$(function(){

    /*��ס��*/
    var stringToLoginFromCookie = $.cookie("stringToLogin");
    var passwordToLoginFromCookie = $.cookie("passwordToLogin");
    if(stringToLoginFromCookie!=null && passwordToLoginFromCookie!=null && stringToLoginFromCookie!="null"){
        //�����û���¼��cookie��Ϣ
        $("#stringToLogin").val($.cookie("stringToLogin"));
        $("#passwordToLogin").val($.cookie("passwordToLogin"));
    }else{
        //�������û���¼��cookie��Ϣ
        $("#stringToLogin").val("");
        $("#passwordToLogin").val("");
    }


    /*ע��*/
    $("#registerButton").on('click',function(){

        var username = $("#username").val();
        var password = $("#password").val();
        var email = $("#email").val();
        $.post("registerUser",{username:username,password:password,email:email},function(result){

            alert(result['info']);
            var user = result['data'];
            alert(user['username']);
            $("#username").val("");
            $("#email").val("");
            $("#password").val("");
            $("#stringToLogin").val(user['username']);
            $("#passwordToLogin").val("");
        });
    });

    /*��¼*/
    $("#loginButton").on('click',function(){

        var stringToLogin = $("#stringToLogin").val();
        var password = $("#passwordToLogin").val();
        $.post("userLogin",{stringToLogin:stringToLogin,password:password},function(result){

            var isSuccess = result['success'];
            if(isSuccess){//��¼�ɹ�

                if($("#checkBox").is(":checked")==true){//checkBox��ѡ��
                    //�û���Ϣд��cookie
                    /*$.cookie("stringToLogin",stringToLogin,{expires:7,path:"/"});  Ӳ��cookie
                    $.cookie("passwordToLogin",password,{expires:7,path:"/"});*/
                    $.cookie("stringToLogin",stringToLogin);//�Ựcookie
                    $.cookie("passwordToLogin",password);

                }else{//������ܴ��ڵ�cookie
                    $.cookie("stringToLogin",null) ;
                    $.cookie("passwordToLogin",null);
                }
                window.location.href= "index";//�ͻ����ض�������ҳ
            }
            else{//��¼ʧ��
                var loginInfo = result['info'];
                alert(loginInfo);//��ʾʧ����Ϣ
            }
        });
    });

});
