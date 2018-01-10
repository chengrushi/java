

/*���޶�̬����*/
var likeIt = function(obj){
    var dynamicsId = obj.parentNode.id;
    $.ajax({
        url:'clickLike',
        type:'POST',
        data:{dynamicsId:dynamicsId},
        success:function(result){
            if(result['success']==true){
                //����
                $("#"+dynamicsId+"like").css('background-image','url("../resources/src/like2.png")');
                $("#"+dynamicsId+"likeh4").replaceWith("<h4 id='"+dynamicsId+"likeh4'>"+result['data']+"</h4>");
                $("#"+dynamicsId+"likeh4").css('color','#e81c4f');
            }
            else{
                //ȡ������
                $("#"+dynamicsId+"like").css('background-image','url("../resources/src/like.png")');
                $("#"+dynamicsId+"likeh4").replaceWith("<h4 id='"+dynamicsId+"likeh4'>"+result['data']+"</h4>");
                $("#"+dynamicsId+"likeh4").css('color','#56abe4');
            }
        },
        error:function(){
            alert("System inner error!");
        }
    });
};
/**
 * �û������̬����ͼ�꣬���ֶ�̬����ģ̬�򣬰�����̬��Ϣ��������ͷ�����۵ȡ�
 * @param obj
 */
var commentIt = function(obj){

    $('.dynamisDetailOuter,.dynamisDetailContainer').fadeIn(300);
    $('.dynamisDetailContainer').css('transform','scale(1)');
    $('html,body').css('overflow-y','hidden');
    var dynamicsId = obj.parentNode.id;
    $('.dynamisDetailContainer').empty();//���ģ̬����ԭ�е�����
    /*��̬��Ϣ*/
    $.ajax({
        url:"detailDynamicsById",
        type:"POST",
        data:{dynamicsId:dynamicsId},
        success:function(result){

            var dynamics = result['data'];
            $('.dynamisDetailContainer').append('<img src="'+basePath+"user_space/"+dynamics['user']['headImg']+'">');
            $('.dynamisDetailContainer').append('<span><a href="'+dynamics["user"]["userId"]+'/userDetail">'+dynamics['user']['username']+'</a></span>');
            $('.dynamisDetailContainer').append('<span>'+dynamics['user']['email']+'</span>');
            $('.dynamisDetailContainer').append('<span>'+dynamics['dynamicsText']+'</span>');

            if(dynamics['dynamicsFile'].substr(dynamics['dynamicsFile'].lastIndexOf("."))=='.jpg'||
                dynamics['dynamicsFile'].substr(dynamics['dynamicsFile'].lastIndexOf("."))=='.png'){
                $('.dynamisDetailContainer').append("<img src='"+basePath+"user_space/"+dynamics['dynamicsFile']+"'>");
            }else{
                $('.dynamisDetailContainer').append(
                    "<video class='video-js vjs-default-skin vjs-big-play-centered' width='640' height='360' controls> " +
                    "<source src='"+basePath+"user_space/"+dynamics['dynamicsFile']+"' type='video/mp4' />" +
                    "</video>");
            }

            $('.dynamisDetailContainer').append('<span>LIKES <h6>'+dynamics["likeNum"]+'</h6> </span>');

            //��̬�����û�
            $.ajax({
                url:"dynamicsOfLikeUser",
                type:"POST",
                data:{dynamicsId:dynamicsId},
                success:function(result){
                    var items = result['data'];
                    $('.dynamisDetailContainer').append('<div id="usersFade"></div>');
                    for(var i=0;i<items.length-1;i++){
                        $("#usersFade").append('<a href="'+items[i]['userId']+'/userDetail">' +
                            '<img src="'+basePath+"user_space/"+items[i]['headImg']+'">' +
                            '</a>');
                    }
                    $('.dynamisDetailContainer').append('<div class="sendCommentDiv"></div>');
                    $('.sendCommentDiv').append('<img src="'+basePath+"user_space/"+items[i]['headImg']+'">');
                    $('.sendCommentDiv').append('<textarea class="commentText" placeholder="Comment what you want to say!"></textarea>');
                    $('.sendCommentDiv').append('<input type="button" value="Comment" onclick="sendComment('+dynamicsId+')" />');

                    /**
                     * ��ʾ��ǰ��̬ȫ������
                     */
                    $.ajax({
                        url:"showCommentOfDynamics",
                        type:"POST",
                        data:{dynamicsId:dynamicsId},
                        success:function(result){
                            var commentItems = result['data'];
                            for(var i = 0;i<commentItems.length;i++){
                                $('.dynamisDetailContainer').append('<div class="commentLittleDiv" id="comment'+commentItems[i]['commentId']+'"></div>');
                                $('#comment'+commentItems[i]['commentId']).append('<img src="'+basePath+"user_space/"+commentItems[i]['sendUser']['headImg']+'">');
                                $('#comment'+commentItems[i]['commentId']).append('<span>'+commentItems[i]['sendUser']['username']+'</span>');
                                $('#comment'+commentItems[i]['commentId']).append('<span>' +
                                    '<span>'+"@"+commentItems[i]['receiveUsername']+'</span>' +
                                    ' <span>'+commentItems[i]['commentText']+'</span>'+
                                    '</span>');
                                $('#comment'+commentItems[i]['commentId']).append('<img src="../resources/src/like.png" title="����">');
                                $('#comment'+commentItems[i]['commentId']).append('<span>'+commentItems[i]['likeNum']+'</span>');
                                $('#comment'+commentItems[i]['commentId']).append('<img src="../resources/src/reply.png" title="�ظ�" onclick="replyComment('+commentItems[i]['commentId']+')">');
                                $('#comment'+commentItems[i]['commentId']).append('<span>'+commentItems[i]['commentNum']+'</span>');
                                $('#comment'+commentItems[i]['commentId']).append('<span>'+(i+1)+"#"+'</span>');
                                var now = new Date(commentItems[i]['commentTime']);
                                $('#comment'+commentItems[i]['commentId']).append('<span>'+now.getFullYear()+"-"+now.getMonth()+"-"+now.getDay()+" "+now.getHours()+":"+now.getMinutes()+":"+now.getSeconds()+'</span>');

                                /**
                                 * ��ʾ���۵����лظ�
                                 */
                                var helper = commentItems[i]['commentId'];
                                $.ajax({
                                    url:"showReplyOfComment",
                                    type:"POST",
                                    async:false,
                                    data:{commentId:commentItems[i]['commentId']},
                                    success:function(result){
                                        var replyList = result['data'];
                                        for(var j = 0;j<replyList.length;j++){
                                            $('.replyCommentDiv').css('display','none');
                                            $('#comment'+helper).append('<div class="replyCommentDivTrue" id="reply'+replyList[j]["replyId"]+'" style="display:none;"></div>');
                                            $('#reply'+replyList[j]["replyId"]).append('<img src="'+basePath+"user_space/"+replyList[j]['sendUser']['headImg']+'">');
                                            $('#reply'+replyList[j]["replyId"]).append('<span>'+replyList[j]['sendUser']['username']+'</span>');
                                            $('#reply'+replyList[j]["replyId"]).append('<span>'+
                                                '<span>'+"@"+replyList[j]['receiveUsername']+'</span>'+
                                                '<span>'+replyList[j]['replyText']+'</span>'+
                                                '</span>');
                                            $('#reply'+replyList[j]["replyId"]).append('<img src="../resources/src/like.png" title="����">');
                                            $('#reply'+replyList[j]["replyId"]).append('<span>'+replyList[j]['likeNum']+'</span>');
                                            $('#reply'+replyList[j]["replyId"]).append('<img src="../resources/src/reply.png" title="�ظ�" onclick="replyCommentReply('+replyList[j]['replyId']+' '+","+' '+helper+')">');
                                            $('#reply'+replyList[j]["replyId"]).append('<span>0</span>');
                                            $('#reply'+replyList[j]["replyId"]).append('<span></span>');
                                            $('#reply'+replyList[j]["replyId"]).append('<span>'+replyList[j]['replyTime']+'</span>');
                                            $('#reply'+replyList[j]["replyId"]).fadeIn(400);
                                        }
                                    },
                                    error:function(){

                                    }
                                });
                            }
                        },
                        error:function(){
                            alert("System inner error!");
                        }
                    });
                },
                error:function(){
                    alert("System inner error");
                }
            });
        },
        error:function(){
            alert("System inner error");
        }
    });
};

/**
 * �û��Զ�̬��������
 * @param obj
 */
var sendComment  =function(obj){

    var commentContent = $(".commentText").val();
    $.ajax({
        url:"sendComment",
        type:"POST",
        data:{dynamicsId:obj,commentContent:commentContent},
        success:function(result){

            var commentInfo = result['data'];
            $('.dynamisDetailContainer').append('<div class="commentLittleDiv" id="comment'+commentInfo['commentId']+'"></div>');
            $('#comment'+commentInfo['commentId']).append('<img src="'+basePath+"user_space/"+commentInfo['sendUser']['headImg']+'">');
            $('#comment'+commentInfo['commentId']).append('<span>'+commentInfo['sendUser']['username']+'</span>');
            $('#comment'+commentInfo['commentId']).append('<span>' +
                '<span>'+"@"+commentInfo['receiveUsername']+'</span>' +
                ' <span>'+commentInfo['commentText']+'</span>'+
                '</span>');
            $('#comment'+commentInfo['commentId']).append('<img src="../resources/src/like.png" title="����">');
            $('#comment'+commentInfo['commentId']).append('<span>'+commentInfo['likeNum']+'</span>');
            $('#comment'+commentInfo['commentId']).append('<img src="../resources/src/reply.png" title="�ظ�" onclick="replyComment('+commentInfo['commentId']+')">');
            $('#comment'+commentInfo['commentId']).append('<span>'+commentInfo['commentNum']+'</span>');
            $('#comment'+commentInfo['commentId']).append('<span>'+"?#"+'</span>');
            var now = new Date(commentInfo['commentTime']);
            $('#comment'+commentInfo['commentId']).append('<span>'+now.getFullYear()+"-"+now.getMonth()+"-"+now.getDay()+" "+now.getHours()+":"+now.getMinutes()+":"+now.getSeconds()+'</span>');
        },
        error:function(){
            alert("System inner error!");
        }
    });
};

/**
 * �ظ���̬������,��ʾ��ǰ�ظ��û�ͷ������򣬱��ظ��û���
 * @param commentId
 */
var replyComment = function(commentId){

    $.ajax({
        url:"replyCommentHelp",
        type:"POST",
        async:false,
        data:{commentId:commentId},
        success:function(result){
            var list = result['data'];
            $("#comment"+commentId).append('<div class="replyCommentDiv"></div>');
            $('.replyCommentDiv').append('<img src="'+basePath+"user_space/"+list[0]["headImg"]+'">');
            $('.replyCommentDiv').append('<textarea class="replyCommentContent" placeholder="@'+list[1]['username']+'"></textarea>');
            $('.replyCommentDiv').append('<input type="button" value="Reply" onclick="appendComment('+commentId+')">')
        },
        error:function(){
            alert("System inner error!");
        }
    });
};

/**
 * �û��ظ���̬������
 * @param commentId
 */
var appendComment = function(commentId){

    var replyCommentContent = $(".replyCommentContent").val();
    $.ajax({
        url:"sendReply",
        type:"POST",
        data:{commentId:commentId,replyCommentContent:replyCommentContent},
        success:function(result){
            var replyInfo = result['data'];
            $('.replyCommentDiv').css('display','none');
            $('#comment'+commentId).append('<div class="replyCommentDivTrue" id="reply'+replyInfo["replyId"]+'" style="display:none;"></div>');
            $('#reply'+replyInfo["replyId"]).append('<img src="'+basePath+"user_space/"+replyInfo['sendUser']['headImg']+'">');
            $('#reply'+replyInfo["replyId"]).append('<span>'+replyInfo['sendUser']['username']+'</span>');
            $('#reply'+replyInfo["replyId"]).append('<span>'+
                '<span>'+"@"+replyInfo['receiveUsername']+'</span>'+
                '<span>'+replyInfo['replyText']+'</span>'+
                '</span>');
            $('#reply'+replyInfo["replyId"]).append('<img src="../resources/src/like.png" title="����">');
            $('#reply'+replyInfo["replyId"]).append('<span>'+replyInfo['likeNum']+'</span>');
            $('#reply'+replyInfo["replyId"]).append('<img src="../resources/src/reply.png" title="�ظ�" onclick="replyCommentReply('+replyInfo['replyId']+' '+","+' '+commentId+')">');
            $('#reply'+replyInfo["replyId"]).append('<span>0</span>');
            $('#reply'+replyInfo["replyId"]).append('<span></span>');
            $('#reply'+replyInfo["replyId"]).append('<span>'+replyInfo['replyTime']+'</span>');
            $('#reply'+replyInfo["replyId"]).fadeIn(400);
        },
        error:function(){
            alert("System inner error!");
        }
    });
};

/**
 * �û��ظ���̬�����еĻظ���������ʾ��ǰ�û�ͷ�񣬱��ظ��û�������
 * @param replyId
 */
var replyCommentReply = function(replyId,commentId){

    $.ajax({
        url:"replyReplyHelp",
        type:"POST",
        data:{replyId:replyId},
        success:function(result){
            var list = result['data'];
            $("#comment"+commentId).append('<div class="replyCommentDiv"></div>');
            $('.replyCommentDiv').append('<img src="'+basePath+"user_space/"+list[0]["headImg"]+'">');
            $('.replyCommentDiv').append('<textarea class="replyCommentContent" placeholder="@'+list[1]['username']+'"></textarea>');
            $('.replyCommentDiv').append('<input type="button" value="Reply" onclick="appendReplyOfReply('+replyId+')">')
        },
        error:function(){
            alert("System inner error!");
        }
    });
};

/**
 * �û��������еĻظ�����ظ�
 * @param replyId
 */
var appendReplyOfReply = function(replyId){

    var replyText = $(".replyCommentContent").val();
    $.ajax({
        url:"sendReplyOfReply",
        type:"POST",
        data:{replyId:replyId,replyText:replyText},
        success:function(result){
            console.log(result);
            var replyInfo = result['data'];
            $('.replyCommentDiv').css('display','none');
            $('#comment'+replyInfo['commentId']).append('<div class="replyCommentDivTrue" id="reply'+replyInfo["replyId"]+'" style="display:none;"></div>');
            $('#reply'+replyInfo["replyId"]).append('<img src="'+basePath+"user_space/"+replyInfo['sendUser']['headImg']+'">');
            $('#reply'+replyInfo["replyId"]).append('<span>'+replyInfo['sendUser']['username']+'</span>');
            $('#reply'+replyInfo["replyId"]).append('<span>'+
                '<span>'+"@"+replyInfo['receiveUsername']+'</span>'+
                '<span>'+replyInfo['replyText']+'</span>'+
                '</span>');
            $('#reply'+replyInfo["replyId"]).append('<img src="../resources/src/like.png" title="����">');
            $('#reply'+replyInfo["replyId"]).append('<span>'+replyInfo['likeNum']+'</span>');
            $('#reply'+replyInfo["replyId"]).append('<img src="../resources/src/reply.png" title="�ظ�" onclick="replyCommentReply('+replyInfo['replyId']+' '+","+' '+replyInfo['commentId']+')">');
            $('#reply'+replyInfo["replyId"]).append('<span>0</span>');
            $('#reply'+replyInfo["replyId"]).append('<span></span>');
            $('#reply'+replyInfo["replyId"]).append('<span>'+replyInfo['replyTime']+'</span>');
            $('#reply'+replyInfo["replyId"]).fadeIn(400);
        },
        error:function(){
            alert("System inner error!");
        }
    });
};

$(function(){
    Date.prototype.Format = function (fmt) { //author: meizz
        var o = {
            "M+": this.getMonth() + 1, //�·�
            "d+": this.getDate(), //��
            "h+": this.getHours(), //Сʱ
            "m+": this.getMinutes(), //��
            "s+": this.getSeconds(), //��
            "q+": Math.floor((this.getMonth() + 3) / 3), //����
            "S": this.getMilliseconds() //����
        };
        if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    };
    for(var i =0;i<10;i++){//�ս�����ҳʱ��Ĭ��û����Ϣ
        newsArrayObj[i] = 0;
    }
    /*ajaxFormData*/
    $("#dynamicsButton").on('click',function(){
        var formData = new FormData($( "#postDynamicsForm" )[0]);
        $.ajax({
            url: 'postDynamics',
            type: 'POST',
            data: formData,
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            success: function (result) {
                //�첽�������¶�̬���ݣ��ֲ�ˢ��
                var items = result['data'];
                for(var i=items.length-1;i>=0;i--){
                    //����ҳˢ�²�ͬ������ajax�ֲ�ˢ��Ҫ�����������Ϊ�����ݿ�����ή���������˷�ʱ�䣬��Ӧ�Ĳ������û������DESC
                    if(items[i]['dynamicsFile'].substr(items[i]['dynamicsFile'].lastIndexOf("."))=='.jpg'||
                        items[i]['dynamicsFile'].substr(items[i]['dynamicsFile'].lastIndexOf("."))=='.png'){
                        var listItem = '<div class="DynamicsDiv" id='+items[i]["dynamicsId"]+'></div>';
                        $("#dynamicsContainerFade").append(listItem);
                        $("#"+items[i]["dynamicsId"]).append('<img src="'+basePath+'user_space/'+items[i]['user']['headImg']+'">');
                        $("#"+items[i]["dynamicsId"]).append('<span>'+items[i]["user"]["username"]+'</span>');
                        $("#"+items[i]["dynamicsId"]).append('<span>'+items[i]["createTime"]+'</span>');
                        $("#"+items[i]["dynamicsId"]).append('<span></span>');
                        $("#"+items[i]["dynamicsId"]).append('<span>���(0)</span>');
                        $("#"+items[i]["dynamicsId"]).append('<span>'+items[i]['dynamicsText']+'</span>');
                        $("#"+items[i]["dynamicsId"]).append("<img src='"+basePath+"user_space/"+items[i]['dynamicsFile']+"'>");
                        $("#"+items[i]["dynamicsId"]).append('<span><h4>0</h4></span>');
                        $("#"+items[i]["dynamicsId"]).append("<span id='"+items[i]['dynamicsId']+'like'+"' onclick='likeIt(this)'><h4 id='"+items[i]['dynamicsId']+'likeh4'+"'>"+items[i]['likeNum']+"</h4></span>");
                        $("#"+items[i]["dynamicsId"]).append('<span onclick="commentIt(this)"><h4>0</h4></span>');
                    }
                    else{
                        var listItem = '<div class="DynamicsDiv" id='+items[i]["dynamicsId"]+'></div>';
                        $("#dynamicsContainerFade").append(listItem);
                        $("#"+items[i]["dynamicsId"]).append('<img src="'+basePath+"user_space/"+items[i]['user']['headImg']+'">');
                        $("#"+items[i]["dynamicsId"]).append('<span>'+items[i]["user"]["username"]+'</span>');
                        $("#"+items[i]["dynamicsId"]).append('<span>'+items[i]["createTime"]+'</span>');
                        $("#"+items[i]["dynamicsId"]).append('<span></span>');
                        $("#"+items[i]["dynamicsId"]).append('<span>���(0)</span>');
                        $("#"+items[i]["dynamicsId"]).append('<span>'+items[i]['dynamicsText']+'</span>');
                        $("#"+items[i]["dynamicsId"]).append(
                            "<video class='video-js vjs-default-skin vjs-big-play-centered' width='640' height='360' controls> " +
                            "<source src='"+basePath+"user_space/"+items[i]['dynamicsFile']+"' type='video/mp4' />" +
                            "</video>");
                        console.log(items[i]['dynamicsFile']);
                        $("#"+items[i]["dynamicsId"]).append('<span><h4>0</h4></span>');
                        $("#"+items[i]["dynamicsId"]).append("<span id='"+items[i]['dynamicsId']+'like'+"' onclick='likeIt(this)'><h4 id='"+items[i]['dynamicsId']+'likeh4'+"'>"+items[i]['likeNum']+"</h4></span>");
                        $("#"+items[i]["dynamicsId"]).append('<span onclick="commentIt(this)"><h4>0</h4></span>');
                    }
                }
            },
            error: function () {
                alert("System error!");
            }
        });
    });

    /**
     * ������ҳʱ���Ѿ����޵Ķ�̬���� �����ޡ� ��ʾ
     */
    $.ajax({
        url:'showLikeHelp',
        type:'GET',
        success:function(result){
            var likeList = result['data'];
            for(var i=0;i<likeList.length;i++){

                $("#"+likeList[i]+"like").css('background-image','url("../resources/src/like2.png")');
                $("#"+likeList[i]+"likeh4").css('color','#e81c4f');
            }
        },
        error:function(){
            alert("System inner error!");
        }
    });

    /**
     * �û����ģ̬����İ���ɫ���ǲ㣬ģ̬���븲�ǲ���ʧ
     */
    $('.dynamisDetailOuter').on('click',function(){
        $('.dynamisDetailOuter,.dynamisDetailContainer').fadeOut(300,function(){
            $('html').css('overflow-y','auto');
            $(this).removeAttr('style');
        })
    });

    $("body").keyup(function(e){
        //�û���������
        if($(".navSearch").val()!=""){
            $(".navSearchResult").slideDown(100);
            $.ajax({
                url:"luceneSearchUser",
                type:"POST",
                data:{inputString:$(".navSearch").val()},
                success:function(result){
                    $(".navSearchResult").empty();
                    var userList = result['data'];
                    for(var i=0;i<userList.length;i++){
                        $(".navSearchResult").append('<a href="'+userList[i]['userId']+'/userDetail" class="userOfSearch" id="searchUserResult'+userList[i]['userId']+'"></a>');
                        $("#searchUserResult"+userList[i]['userId']).append('<img src="'+basePath+"user_space/"+userList[i]['headImg']+'">');
                        $("#searchUserResult"+userList[i]['userId']).append('<h1>'+userList[i]['username']+'</h1>');
                        $("#searchUserResult"+userList[i]['userId']).append('<img src="../resources/src/location.png">');
                        $("#searchUserResult"+userList[i]['userId']).append('<h1>'+userList[i]['address']+'</h1>');
                    }
                    $(".navSearchResult").css('height','auto');
                    if($(".navSearchResult").height()>350){
                        $(".navSearchResult").css('height','350px');
                        $(".navSearchResult").css('overflow-y','auto');
                    }
                },
                error:function(){
                    alert("System inner error!");
                }
            });
        }
        //�û�������룬���������Ϊ��
        if($(".navSearch").val()==""){
            $(".navSearchResult").css('display','none');
        }

        //�û�contactǰ����friends
        if($(".searchUserToContact_search").val()!=""){
            $.ajax({
                url:"luceneSearchUser",
                type:"POST",
                data:{inputString:$(".searchUserToContact_search").val()},
                success:function(result){
                    $(".searchUserToContact_body").empty();
                    var userList = result['data'];
                    for(var i=0;i<userList.length;i++){
                        $(".searchUserToContact_body").append('<div class="userToContact" id="userToContact'+userList[i]['userId']+'" onclick="selectWhichToContact('+userList[i]['userId']+')"></div>');
                        $("#userToContact"+userList[i]['userId']).append('<img src="'+basePath+"user_space/"+userList[i]['headImg']+'">');
                        $("#userToContact"+userList[i]['userId']).append('<span>'+userList[i]['username']+'</span>');
                    }
                    $(".searchUserToContact_body").css('height','auto');
                    if($('.searchUserToContact_body').height()>300){
                        $(".searchUserToContact_body").css('height','300px');
                        $(".searchUserToContact_body").css('overflow-y','auto');
                    }
                },
                error:function(){
                    alert("System inner error!");
                }
            });
        }
        //�û��������friends contact�������
        if($(".searchUserToContact_search").val()==""){
            $(".searchUserToContact_body").css('height','auto');
            $(".searchUserToContact_body").empty();
            $(".searchUserToContact_body").append('<div class="searchUserToContact_body_tips">'+
               '<span>Now type in and search friends to contact</span>'+
            '</div>');
        }

        var e = e||event;//����chrome��firefox���ѹ������
        if(e.keyCode==13&&$(".contactDivTrue_right_input").val()!=""){//�û����ü���ʱ���˻س�
            $(".contactDivTrue_right_content").append('<div class="contactContentRight">'+
                                                        '<span>'+new Date().Format("yyyy-MM-dd hh:mm:ss")+'</span>'+
                                                        '<span>'+$(".contactDivTrue_right_input").val()+'</span>'+
                                                       '</div>');
            var contactDiv = document.getElementById('forScroll');
            contactDiv.scrollTop = contactDiv.scrollHeight;

            var data = {};//�½�data���󣬲��涨����������Ӧ��ֵ
            data['fromId'] = sendUid;
            data['fromName'] = sendName;
            data['toId'] = to;
            data['messageText'] = $(".contactDivTrue_right_input").val();
            webSocket.send(JSON.stringify(data));//�������װ��JSON������������

            $(".contactDivTrue_right_input").val("");
        }
    });

    if($(".navSearchResult").height()>350){
        $(".navSearchResult").css('height','350px');
        $(".navSearchResult").css('overflow-y','auto');
    }
    $('.searchToContact').on('click',function(){
        $('.searchToContact').css('display','none');
        $('.searchUserToContact').css('opacity','1');
        $('.searchUserToContact').css('transform','scale(1)');
        $('.contactDivFade').css('right','300px');
        $('.contactDivTrue').css('right','300px');

        if($('.searchUserToContact_body').height()>300){
            $(".searchUserToContact_body").css('height','300px');
            $(".searchUserToContact_body").css('overflow-y','auto');
        }
    });
    $('.searchUserToContact_head').on('click',function(){
        $('.searchUserToContact').css('opacity','0');
        $('.searchUserToContact').css('transform','scale(0.01)');
        $('.searchToContact').fadeIn(10);
        $('.contactDivFade').css('right','220px');
        $('.contactDivTrue').css('right','220px');
    });
    $(".contactDivFade").on('click',function(){//�������
        $(".contactDivFade").css('display','none');
        $(".contactDivTrue").fadeIn(10);
        hasOpenedTab=1;
        seeOne(to);
        removeHasChatSpot(to);
        var contactDiv = document.getElementById('forScroll');
        contactDiv.scrollTop = contactDiv.scrollHeight;
    });
    $(".useToClose").on('click',function(){//�ص������
        hasOpenedTab=0;
        if(isSeeAllNews()==false){
            window.clearInterval(myInterval);
            intervalFlag = 0;
        }
        $(".contactDivTrue").css('display','none');
        $(".contactDivFade").fadeIn(10);
    });


    //�û��ѽ�����ҳ�ͻὨ��Socket����ʼͨ��
    var webSocket = new WebSocket("ws://"+socketPath+"/ws");
    webSocket.onopen = function(event){
        console.log("���ӳɹ�");
        console.log(event);
    };
    webSocket.onerror = function(event){
        console.log("����ʧ��");
        console.log(event);
    };
    webSocket.onclose = function(event){
        console.log("Socket���ӶϿ�");
        console.log(event);
    };
    webSocket.onmessage = function(event){
        //��Ϣ��ʾ��
        $("#newsTips").html('<audio autoplay="autoplay">'+
            '<source src="http://xunlei.sc.chinaz.com/Files/DownLoad/sound1/201607/7499.wav" type="audio/wav" >'+
        '</audio>');
        hasNewsTips();//�ṩ��˸Ч��
        console.log("�յ�һ����Ϣ");
        console.log(event);
        var message = JSON.parse(event.data);//�����ݽ�����JSON��ʽ
        newsArrayAddHelp(message['fromId']);
        addHasChatSpot(message['fromId']);

        if(isContain(message['fromId'])==false){//֮ǰ��û�Ĺ���ģ���Ҫ����contactDivTrue��
            selectWhichToContact(message['fromId']);
        }
        if(isContain(message['fromId'])==true&&to==message['fromId']){
            $(".contactDivTrue_right_content").append('<div class="contactContentLeft">'+
                '<span>'+message['messageDate']+'</span>'+
                '<span>'+message['messageText']+'</span>'+
                '</div>');
            var contactDiv = document.getElementById('forScroll');
            contactDiv.scrollTop = contactDiv.scrollHeight;
        }
        if(hasOpenedTab==1&&isContain(message['fromId'])==true&&to==message['fromId']){
            seeOne(message['fromId']);
            removeHasChatSpot(message['fromId']);
        }
        if(isSeeAllNews()==false){
            window.clearInterval(myInterval);
            intervalFlag = 0;
        }
    }
});

/**
 * �û�ѡ����Ҫ���������
 * һ�����contactDivTrue������contactDivFade��
 * @param obj
 */
var selectWhichToContact = function(obj){

    to = obj;//�趨������Ϣ���ĸ�friends��id��,ԭ����-1
    seeOne(obj);
    hasOpenedTab=1;
    $.ajax({
        url:"prepareUserToContact",
        type:"POST",
        data:{userId:obj},
        success:function(result){
            var user = result['data'];

            $(".contactDivTrue").fadeIn(5);
            $(".contactDivFade").css('display','none');

            $(".contactDivTrue_left_img_outer").css('background-color','#1da1f2');
            $(".contactDivTrue_right_head").empty();
            $(".contactDivTrue_right_head").append('<span class="contactDivTrue_right_head_span">'+user['username']+'</span>');
            if(isContain(obj)==false){
                $(".contactDivTrue_left").append('<div class="contactDivTrue_left_img_outer" style="background-color:#8dd2eb;" id="changeUserToContact'+user['userId']+'" onclick="changeUserToContact('+user['userId']+')">'+
                    '<img src="'+basePath+"user_space/"+user['headImg']+'" class="contactDivTrue_left_img">'+
                    '<span class="hasChat" id="hasChat'+user["userId"]+'"></span>'+
                    '</div>');

                arrayObj[arrayCur++] = obj;
            }
            else{
                $("#changeUserToContact"+user['userId']).css('background-color','#8dd2eb');
            }
            removeHasChatSpot(obj);
            $(".contactDivTrue_right_content").empty();
            showContentInThePast(obj);
        },
        error:function(){
            alert("System inner error!");
        }
    });
};

var changeUserToContact = function(obj){

    to = obj;//�û��ı���Ҫ������û������ڣ�
    seeOne(obj);
    $.ajax({
        url:"prepareUserToContact",
        type:"POST",
        data:{userId:obj},
        success:function(result){
            var user = result['data'];
            $(".contactDivTrue_left_img_outer").css('background-color','#1da1f2');
            $("#changeUserToContact"+user['userId']).css('background-color','#8dd2eb');
            $(".contactDivTrue_right_head").empty();
            $(".contactDivTrue_right_head").append('<span class="contactDivTrue_right_head_span">'+user['username']+'</span>');

            removeHasChatSpot(obj);
            $(".contactDivTrue_right_content").empty();
            showContentInThePast(obj);
        },
        error:function(){
            alert("System inner error!");
        }
    });
};
/**
 * �ж��Ƿ�����û�����������
 * �Ѿ�������˷���true����û����ķ���false
 * @param e
 * @returns {boolean}
 */
var isContain = function(e){
    for(var i =0;i<10;i++){
        if(arrayObj[i] == e){
            return true;
        }
    }
    return false;
};
/**
 * ���������û���ȥ�������¼
 * @param obj
 */
var showContentInThePast = function(obj){
    $.ajax({
        url:"showContentInThePast",
        type:"POST",
        data:{toId:obj},
        success:function(result){
            var contentList = result['data'];
            for(var i=0;i<contentList.length;i++){
                if(sendUid==contentList[i]['fromId']){
                    $(".contactDivTrue_right_content").append('<div class="contactContentRight">'+
                        '<span>'+new Date(contentList[i]['messageDate']).Format("yyyy-MM-dd hh:mm:ss")+'</span>'+
                        '<span>'+contentList[i]['messageText']+'</span>'+
                        '</div>');
                }else{
                    $(".contactDivTrue_right_content").append('<div class="contactContentLeft">'+
                        '<span>'+new Date(contentList[i]['messageDate']).Format("yyyy-MM-dd hh:mm:ss")+'</span>'+
                        '<span>'+contentList[i]['messageText']+'</span>'+
                        '</div>');
                }
            }
            var contactDiv = document.getElementById('forScroll');
            contactDiv.scrollTop = contactDiv.scrollHeight;
        },
        error:function(){
            alert("System inner error!");
        }
    });
};

/**
 * ����ϢʱcontactDivFade���ṩ��˸Ч����ʾ����Ϣ
 */
var hasNewsTips = function(){
    hasNewsTipsHelp();
    if(intervalFlag==0){
        myInterval = window.setInterval("hasNewsTipsHelp()",3000);
        intervalFlag = 1;
    }
};
var hasNewsTipsHelp = function(){
    $(".contactDivFade").css("background","-webkit-linear-gradient(top,#b0fffe,#1ededc)");
    $(".contactDivFade").css("background","-moz-linear-gradient(top,#b0fffe,#1ededc)");
    window.setTimeout("$('.contactDivFade').css('background','#f6f7f9');",600);
};

/**
 *�鿴��ǰ�û��Ƿ�����ȫ����Ϣ
 * �����������Ϣδ�鿴������true
 * ����Ѿ��鿴ȫ����Ϣ/û������Ϣ  ����false
 * @returns {boolean}
 */
var isSeeAllNews = function(){
    for(var i=0;i<10;i++){
        if(newsArrayObj[i]>0){
            return true;
        }
    }
    return false;
};
/**
 * �û��Ѿ��鿴fromIdΪobj����Ϣ
 * @param obj
 */
var seeOne = function(obj){
    for(var i=0;i<10;i++){
        if(newsArrayObj[i]==obj){
            newsArrayObj[i] = 0;
        }
    }
};
/**
 * ���鴢������Ϣ��fromId�û�
 * @param obj
 */
var newsArrayAddHelp = function(obj){
    var fadeFlag = 0;
    for(var i=0;i<10;i++){
        if(newsArrayObj[i]==obj){
            fadeFlag = 1;
        }
    }
    if(fadeFlag==0){
        newsArrayObj[newsArrayCur++] = obj;
    }
};
/**
 * ����hasChat������
 * @param obj
 */
var removeHasChatSpot = function(obj){
    $("#hasChat"+obj).css('display','none');
};
var addHasChatSpot = function(obj){
    $("#hasChat"+obj).fadeIn(1);
};