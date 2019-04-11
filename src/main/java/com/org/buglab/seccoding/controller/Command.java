package com.org.buglab.seccoding.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.lang.ProcessBuilder;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/command")
public class Command {

    @RequestMapping(value = "/start/string", method = RequestMethod.GET)
    public void start(HttpServletRequest request) {
        String cmd = "mkdir buglabxxr";
        String output = "";
        //接受参数
        java.lang.ProcessBuilder builder;
        // if(checkCommand(command))
        if (File.separator.startsWith("\\")) {
            builder = new ProcessBuilder("cmd", "/c", cmd);
        } else {
            builder = new ProcessBuilder("/bin/bash", "-c", cmd);
        }
        try {
            Process process = builder.start();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String s = bufferedReader.readLine();
            while (s != null) {
                output = output + s + "\n";
                s = bufferedReader.readLine();
            }
            bufferedReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @RequestMapping(value = "/start/list", method = RequestMethod.GET)
    public void start_list(HttpServletRequest request) {
        String cmd = "mkdir buglabxxr";
        String output = "";
        //接受参数
        java.lang.ProcessBuilder builder;
        List<String> list = new ArrayList<String>();
        list.add("notepad.exe");
        // if(checkCommand(command))
        if (File.separator.startsWith("\\")) {
            builder = new ProcessBuilder(list);
        } else {
            builder = new ProcessBuilder("/bin/bash", "-c", cmd);
        }
        try {
            Process process = builder.start();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String s = bufferedReader.readLine();
            while (s != null) {
                output = output + s + "\n";
                s = bufferedReader.readLine();
            }
            bufferedReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @RequestMapping(value = "/exec", method = RequestMethod.GET)
    public void exec(HttpServletRequest request) {
        Process process;
        String cmd = "cmd /c ipconfig/all";
        try {
            //执行命令
            process = Runtime.getRuntime().exec(cmd);
            //取得命令结果的输出流
            InputStream inputStream = process.getInputStream();
            //用一个读输出流类去读
            InputStreamReader isr = new InputStreamReader(inputStream);
            //用缓冲器读行
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            //直到读完为止
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
