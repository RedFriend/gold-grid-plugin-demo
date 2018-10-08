//加载完dom后执行
var val;
$(function () {
  $('#butn_5').click(function(){
    val = $('#sousuo').val();
    shouan();
  })
    shouan();
    //获取对应的id并调取接口
    $('.right_left').on('click', 'li', function () {
        var tempId = $(this).data('id'); //获取文书模板模板id
        debugger
        if($('.anhaoname')[0]){
            caseNum = $('.anhaoname').val();
        }
        var outPath = "";   //文书生成路径
        if (!caseNum) {
            alert('请输入案号');
            return;
        }
        var data = {
            tempId: tempId,
            outPath: outPath,
            caseNum: caseNum
        }
        $.ajax({
            url: contextPath + '/document/createDoc',
            data: data,
            success: function (result) {
                //shouan();  //重新刷新页面数据
                debugger
                if (!result.msg == "success") {
                    alert(result.errorMsg);
                }else{
                    debugger
                    //通知父页面更新目录树
                    parent.postMessage("refreshTree","*");
                }
                var filePath;
                try {
                    filePath = result.data[result.data.length - 1].docUrl;
                } catch (e) {
                    console.log
                }
                if($("#docView")[0])
                $("#docView").attr("src", "officeEdit?filePath=" + filePath);
            },
            error: function (returndata) {
                alert('接口数据错误!')
            }
        });
    });
});


function shouan() {
    var data = {
      fydm:'2577',
      caseType:'0301',
      name:val
    }
    $.ajax({
        url: contextPath + '/getTemplates',
        data:data,
        success: function (returndata) {
            closeLoading();
            var list = returndata;
            var liststr = "";
            var listd = "";
            for (var i = 0; i < list.length; i++) {
                if (list[i].templateList.length != 0) {
                    var list1 = list[i].templateList;
                    for (var j = 0; j < list1.length; j++) {
                        if (list1[j].commonFlag == 1) {
                            liststr += '<li data-id=' + list1[j].id + '><i></i><span>' + list1[j].templateName + '</span></li>'
                        }
                    }
                    $('.cyong').html(liststr);
                    listd += '<p class="chay chay_two">' + list[i].type + '</p>';
                    listd += '<ul class="xialaList nocy">';
                    for (var j = 0; j < list1.length; j++) {
                        listd += '<li data-id=' + list1[j].id + '><i></i><span>' + list1[j].templateName + '</span></li>'
                    }
                    listd += '</ul>';
                } else {
                    listd += '<p class="chay chay_two">' + list[i].type + '</p>';
                }

            }
            $('.right_left').append(listd);
            $('.chay_two').next('.xialaList').hide();
            $('.chay').click(function(){
                if ($(this).hasClass('chay_two')) {
                    $(this).removeClass('chay_two');
                    $(this).addClass('chay_one');
                    $(this).next('.xialaList').slideDown(200);
                }else{
                    $(this).removeClass('chay_one');
                    $(this).addClass('chay_two');
                    $(this).next('.xialaList').slideUp(200);
                }
            })
        },
        error: function (returndata) {
            alert('列表数据加载错误');
        }
    });
}

function closeLoading() {
    $("#page_load").remove();
}
