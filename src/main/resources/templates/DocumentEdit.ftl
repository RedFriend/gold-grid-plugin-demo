<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <meta http-equiv="X-UA-Compatible" content="IE=9"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <script src="js/jquery-1.11.1.min.js"></script>
    <script src="js/office/WebOffice.js"></script>
    <link rel='stylesheet' type='text/css' href='css/iWebProduct.css'/>
    <script>var contextPath = "${request.contextPath}"</script>

    <!-- 以下为2015主要方法 -->
    <script type="text/javascript">
        debugger
        var mRecordID = '${mFileName}';
        var mNodeName = '${mNodeName}';
        var mNodePid = '${mNodePid}';
        var mDataId = '${mDataId}';
        var treeNodeId = '${treeNodeId}';
        var treeNodeColor = '${treeNodeColor}';
        var treeNodeSource = '';
        var userId = '${userId}';
        var WebOffice = new WebOffice2015(); //创建WebOffice对象
    </script>
    <script type="text/javascript">
        function Load() {
            try {
                WebOffice.WebUrl = "http://" + (window.location.host + "/" + contextPath + "/officeServer").replace("//", "/");             //WebUrl:系统服务器路径，与服务器文件交互操作，如保存、打开文档，重要文件
                debugger

                WebOffice.FileName = mRecordID;            //FileName:文档名称
                WebOffice.FileType = ".doc";            //FileType:文档类型  .doc  .xls
                WebOffice.UserName = '${userName}';    //UserName:操作用户名，痕迹保留需要
                WebOffice.Skin('blue');                        //设置皮肤
                WebOffice.HookEnabled();
                WebOffice.SetCaption();
                WebOffice.SetUser("system");
                if (WebOffice.WebOpen()) {                             //打开该文档    交互OfficeServer  调出文档OPTION="LOADFILE"
                    WebOffice.setEditType("1");         //EditType:编辑类型  方式一   WebOpen之后
                    WebOffice.VBASetUserName(WebOffice.UserName);    //设置用户名
                    getEditVersion();//判断是否是永中office
                    WebOffice.ShowTitleBar(false);//隐藏标题栏
                    WebOffice.ShowMenuBar(false);//隐藏菜单栏
                    WebOffice.ShowToolBars(false);//隐藏工具栏
                    WebOffice.ShowStatusBar(false);//隐藏状态栏
                    WebOffice.WebObject.ActiveWindow.ActivePane.View.Zoom.Percentage = 70;//默认显示比例
                    WebOffice.WebObject.Application.ActiveWindow.ActivePane.View.Zoom.Percentage = 50;
                }
            } catch (e) {
                //alert(e.description);
            }
        }

        function getEditVersion() {
            var getVersion = WebOffice.getEditVersion(); //获取当前编辑器软件版本
            if (getVersion == "YozoWP.exe" || getVersion == "YozoSS.exe")  //如果是永中office,隐藏手写功能等
            {
                document.getElementById("handWriting1").style.display = 'none';
                document.getElementById("handWriting2").style.display = 'none';
                document.getElementById("expendFunction").style.display = 'none';
                document.getElementById("enableCopy1").style.display = 'none';
                document.getElementById("enableCopy2").style.display = 'none';
                document.getElementById("OpenBookMarks").style.display = 'none';
                document.getElementById("areaProtect").style.display = 'none';
                document.getElementById("areaUnprotect").style.display = 'none';

            } else if (getVersion == "WINWORD.EXE" || getVersion == "wps.exe") {
                WebOffice.ShowWritingUser(true);
            }

        }

        //手写签批
        function HandWriting() {
            var penColor = document.getElementById("PenColor").value;
            var penWidth = document.getElementById("PenWidth").value;
            WebOffice.HandWriting(penColor, penWidth);
        }

        //作用：保存文档
        function SaveDocument(type) {
            stopqp();
            WebOffice.ShowTitleBar(false);//隐藏标题栏
            WebOffice.ShowMenuBar(false);//隐藏菜单栏
            WebOffice.ShowToolBars(false);//隐藏工具栏
            WebOffice.ShowStatusBar(false);//隐藏状态栏
            if (WebOffice.WebSave(type)) {
                return true;
            } else {
                StatusMsg(WebOffice.Status);
                return false;
            }
        }

        function stopqp() {
            WebOffice.StopHandWriting();
        }
    </script>
    <script language="javascript" for="WebOffice2015" event="OnReady()">
        WebOffice.setObj(document.getElementById('WebOffice2015'));//给2015对象赋值
        Load();//避免页面加载完，控件还没有加载情况
    </script>
    <!--以下是多浏览器的事件方法 -->
    <script>
        function OnReady() {
            WebOffice.setObj(document.getElementById('WebOffice2015'));//给2015对象赋值
            //Load();//避免页面加载完，控件还没有加载情况
            window.onload = function () {
                Load();
            } //IE和谷歌可以直接调用Load方法，火狐要在页面加载完后去调用
        }
    </script>
</head>
<body onresize="init()" style="overflow-y:hidden;overflow-x:hidden" onUnload="WebOffice.WebClose()">
<table id="functionBox" cellspacing='0' cellpadding='0'>
    <tr>
        <td id="activeBox">
            <table id="activeTable" cellspacing='0' cellpadding='0' border="0">
                <tr id="handWriting1">
                    <td class="title" style="width:90px;">颜色:</td>
                    <td class="title" style="width:10%">
                        <select id="PenColor">
                            <option value="255" selected="selected">红色</option>
                            <option value="16711680">蓝色</option>
                            <option value="65535">黄色</option>
                            <option value="0">黑色</option>
                            <option value="32768">绿色</option>
                        </select>
                    </td>
                    <td class="title" style="width:10%">笔宽:</td>
                    <td class="title" style="width:10%">
                        <select id="PenWidth">
                            <option value="1">一线</option>
                            <option value="2">二线</option>
                            <option value="3">三线</option>
                            <option value="4" selected="selected">四线</option>
                            <option value="5">五线</option>
                            <option value="6">六线</option>
                            <option value="7">七线</option>
                            <option value="8">八线</option>
                        </select>
                    </td>
                    <td class="title" style="width:10%">
                        <input type="button" value="签批" class="hand2" style="cursor:pointer" onclick="HandWriting();"/>
                    </td>
                    <td class="title" style="width:10%">
                        <input type="button" value="撤销" class="hand2" style="cursor:pointer"
                               onclick="WebOffice.RemoveLastWriting();"/>
                    </td>
                    <td class="title" style="width:40%" id="bcan">
				   <span>
				      <button type="button" class="btn btn-sm btn-primary" onclick="SaveDocument('1')"
                              style="cursor:pointer">保存批注</button>
				   </span>
                    </td>
                    <!-- <td class="title" style="width:20%">
                    <span id = "signature"><a href="#" onclick="SaveDocument('2')">保存签章</a></span>
                    </td> -->
                </tr>
                <tr>
                    <td colspan="12" id="activeTd">&nbsp;<script src="js/office/iWebOffice2015.js"></script>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<div id="dialog-confirm"></div>
</body>
</html>
 <script language="javascript">
     init();

     function init() {
         document.getElementById('WebOffice2015').height = document.documentElement.clientHeight - 20 + "px";
     }

     //获取id的高度
     function getHeight(id) {
         return 800;//document.getElementById(id).offsetHeight;
     }
 </script>