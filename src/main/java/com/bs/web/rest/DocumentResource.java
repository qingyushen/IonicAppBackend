package com.bs.web.rest;

import com.bs.JWT.JWTProvider;
import com.bs.doc.generation.CreateDocument;
import com.bs.model.Group;
import com.bs.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@Transactional
@RequestMapping("/FileTransfer")
public class DocumentResource {
    @Autowired
    private CreateDocument creation;
    @Autowired
    private JWTProvider jwtProvider;

    @RequestMapping(value="/download/{name}")
    @ResponseBody
    public void downFile(HttpServletRequest request, @PathVariable(value = "name") String name,
                         HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        User curUser = jwtProvider.getUserDetails(request);

        try {
            creation.generate(curUser.getTeam());
            // 设置响应类型（应用程序强制下载）
            response.setContentType("application/force-download");
            // 读取文件
            String path = "iPlat4C-Weekly.docx";
            InputStream in = new FileInputStream(path);
            // 设置响应头，对文件进行url编码
            name = URLEncoder.encode(name, "UTF-8");


            Path path1= Paths.get(path);
            String mimeType= Files.probeContentType(path1);
            InputStream is = new BufferedInputStream(new FileInputStream(path));
            response.setHeader("Content-Disposition", "attachment;filename=" + name);
            response.setContentLength(in.available());
            response.setContentType(mimeType);
            response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode("iPlat4C-Weekly.docx","UTF-8") + "\"");
            response.addHeader("Content-Length", "" + is.available());
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("If-Modified-Since", "0");

            // 文件copy
            OutputStream out = response.getOutputStream();
            byte[] b = new byte[1024];
            int len = 0;
            while ((len = in.read(b)) != -1) {
                out.write(b, 0, len);
            }
            out.flush();
            out.close();
            in.close();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
}
