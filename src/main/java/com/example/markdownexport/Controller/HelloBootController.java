package com.example.markdownexport.Controller;

import com.example.markdownexport.bean.Contents;
import com.example.markdownexport.dao.ContentsDao;
import com.google.common.io.Files;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;
import java.util.regex.Pattern;

@RestController
public class HelloBootController {

    @RequestMapping("/helloBoot")
    public String helloBoot(){
        return "Hello Boot-JPA";
    }

    @Autowired
    private ContentsDao contentsDao;

    @RequestMapping("/handler")
    public String handler(){
        List<Contents> all = contentsDao.findAll();

        all.forEach(contents -> {
            //写入磁盘
            String title = contents.getTitle();
            final File newFile = new File("C:\\Users\\user\\Desktop\\blog2\\"+ filenameFilter(title) +".md");
            try {
                Files.write(contents.getContent()
                        .replace("sanii.cn/upload/","app.sanii.cn/upload/old/").getBytes(), newFile);
                Thread.sleep(200);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(title);
        });
        return all.size()+"";
    }
    private static Pattern FilePattern = Pattern.compile("[\\\\/:*?\"<>|]");
    public static String filenameFilter(String str) {
        return str==null?null:FilePattern.matcher(str).replaceAll("");
    }
}