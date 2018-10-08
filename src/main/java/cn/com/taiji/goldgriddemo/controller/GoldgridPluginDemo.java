package cn.com.taiji.goldgriddemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import com.alibaba.fastjson.JSON;

@Controller
public class GoldgridPluginDemo {

    @RequestMapping(value = "/officeEdit")
    public ModelAndView officeEdit(@RequestParam String filePath){
        //测试地址
        //http://146.12.14.248:8080/officeEdit?filePath=C:code.docx
        ModelAndView mv = new ModelAndView();
        mv.setViewName("DocumentEdit");
        mv.addObject("pd", "");
        mv.addObject("QX", "aaa");
        mv.addObject("mFileName", filePath);
        mv.addObject("mNodeName", "aaa");
        mv.addObject("mNodePid", "");
        mv.addObject("mDataId", "");
        mv.addObject("treeNodeId", "");
        mv.addObject("treeNodeColor", "");
        mv.addObject("userId", "ws");
        mv.addObject("userName", "wsadmin");
        return mv;
    }

    @RequestMapping(value = "/officeServer")
    public void officeServer(HttpServletRequest request, HttpServletResponse response) throws Exception {
        iMsgServer2015 MsgObj = new iMsgServer2015();
        if (request.getMethod().equalsIgnoreCase("POST")) {
            MsgObj.setSendType("JSON");
            MsgObj.Load(request);
            String formData = request.getParameter("FormData");
            Map<String, String> req = JSON.parseObject(formData, Map.class);

            if ("LOADFILE".equals(req.get("OPTION"))) {
                String mFileName = req.get("FILENAME");
                MsgObj.MsgTextClear();
                if (MsgObj.MsgFileLoad( mFileName)) {
//                    logger.info(dataCollectionUrlPath + mFileName + "文件打开成功");
                } else {
//                    logger.info(dataCollectionUrlPath + mFileName + "文件打开失败");
                }
            }
        }
        MsgObj.Send(response);
    }

}
