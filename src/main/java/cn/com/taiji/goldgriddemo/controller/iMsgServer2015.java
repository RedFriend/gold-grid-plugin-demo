package cn.com.taiji.goldgriddemo.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.fileupload.DefaultFileItemFactory;
import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

/**
 * @author 陈益特
 */
public class iMsgServer2015 {

    private Hashtable<String, String> saveFormParam = new Hashtable<String, String>();  //保存form表单数据
    private Hashtable<String, String> sendFormParam = new Hashtable<String, String>();  //保存form表单数据
    private InputStream fileContentStream;
    private String fileName = "";
    private byte[] mFileBody = null;
    private boolean isLoadFile = false;
    private String sendType = "";

    private static final String MsgError = "404"; //设置常量404，说明没有找到对应的文档


    public String getSendType() {
        return sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType;
    }

    /**
     * @throws FileUploadException
     * @throws IOException
     * @deprecated:后台类解析接口
     * @time:2015-01-09
     */
    public void Load(HttpServletRequest request) throws FileUploadException, IOException {
        request.setCharacterEncoding("gb2312");
        DefaultFileItemFactory diskFileItemFactory = new DefaultFileItemFactory();
        DiskFileUpload fileUpload = new DiskFileUpload(diskFileItemFactory);
        List fileList = fileUpload.parseRequest(request);
        System.out.println("word临时文件：" + fileList);
        if (fileList != null && fileList.size() > 0) {
            for (int i = 0; i < fileList.size(); i++) {
                FileItem item = (FileItem) fileList.get(i);
                if (item.isFormField()) {
                    processFormField(item);
                } else {
                    processUploadedFile(item);
                }
            }
        }
    }

    /**
     * @param item:表单数据
     * @throws UnsupportedEncodingException
     * @deprecated：解析表达数据
     * @time:2015-01-09
     */
    public void processFormField(FileItem item) throws UnsupportedEncodingException {
        String fieldName = item.getFieldName();
        String fieldValue = "";
        fieldValue = item.getString("utf-8");
        if (this.sendType.equalsIgnoreCase("JSON")) {
            JSONObject json = JSONObject.parseObject(fieldValue);
            Iterator iter = json.keySet().iterator();
            while (iter.hasNext()) {
                fieldName = (String) iter.next();
                fieldValue = json.getString(fieldName);
                saveFormParam.put(fieldName, fieldValue);
            }
            return;
        }
        saveFormParam.put(fieldName, fieldValue);
    }


    /**
     * @param item:文档数据
     * @throws IOException
     * @throws UnsupportedEncodingException
     * @deprecated：解析文档数据
     * @time:2015-01-09
     */
    public void processUploadedFile(FileItem item) throws IOException {
        fileName = item.getName();
        if (fileName.indexOf("/") >= 0) {
            fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
        } else if (fileName.indexOf("\\") >= 0) {
            fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
        }
        fileContentStream = item.getInputStream();

    }

    /**
     * @param fieldName:参数名称
     * @deprecated：解析文档数据
     * @return：参数对于的值
     * @time:2015-01-09
     */
    public String GetMsgByName(String fieldName) {
        return saveFormParam.get(fieldName);
    }

    /**
     * 清除所有SetMsgByName所有内容
     *
     * @time:2015-01-09
     */
    public void MsgTextClear() {
        saveFormParam.clear();
    }


    public byte[] MsgFileBody() {
        mFileBody = null;
        isLoadFile = false;
        byte[] buffer = new byte[4096];
        int n = 0;
        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            while (-1 != (n = fileContentStream.read(buffer))) {
                output.write(buffer, 0, n);
            }
            mFileBody = output.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mFileBody;
    }


    /**
     * 把字节数组保存为一个文件
     *
     * @param b
     * @param outputFile
     * @return
     */
    public boolean MsgFileSave(String outputFile) {
        try {
            File f = new File(outputFile);
            f.setWritable(true, false);
            f.setExecutable(true, false);
            f.setReadable(true, false);
            f.setWritable(true);
            f.setExecutable(true);
            f.setReadable(true);
            FileOutputStream fos = null;
            BufferedInputStream bis = null;
            int BUFFER_SIZE = 1024;
            byte[] buf = new byte[BUFFER_SIZE];
            int size = 0;
            bis = new BufferedInputStream(fileContentStream);
            fos = new FileOutputStream(f);
            while ((size = bis.read(buf)) != -1)
                fos.write(buf, 0, size);
            fos.close();
            bis.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean MsgFileLoad(String fileName) throws IOException {
        System.out.println("获取word文件路径：" + fileName);
        if (fileName != null && fileName.toLowerCase().startsWith("http")) {
            URL url = new URL(fileName);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if (conn != null) {
                fileContentStream = conn.getInputStream();
                MsgFileBody();
            } else {
                mFileBody = new byte[0];
            }
        } else {
            File file = new File(fileName);
            if (file.exists()) {
                fileContentStream = new FileInputStream(new File(fileName));
                MsgFileBody();
            } else {
                mFileBody = new byte[0];
            }
        }
        isLoadFile = true;
        return true;
    }


    /**
     * @param response
     * @throws IOException
     * @deprecated:将文件的二进制数据设置到信息包中
     */
    public void Send(HttpServletResponse response) throws IOException {
        try {
            if (isLoadFile) {
                if (mFileBody.length != 0) {
                    response.setCharacterEncoding("utf-8");
                    response.setContentType("application/x-msdownload;charset=utf-8");
                    response.setContentLength(mFileBody.length);
                    response.setHeader("Content-Disposition", "attachment;filename=");
                    response.getOutputStream().write(mFileBody, 0, mFileBody.length);
                } else {
                    response.setHeader("MsgError", iMsgServer2015.MsgError);
                }
            }
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (Exception e) {

        }
    }
}
