package com.org.buglab.seccoding.controller;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
/*
*
* <?xml version ="1.0" encoding="UTF-8"?>
<!DOCTYPE foo [
<!ENTITY xxe SYSTEM "file:///etc/flag">
]>
<foo>&xxe;</foo>
 */

@RestController
@RequestMapping("/xxe")
public class XXE {

   @RequestMapping(value = "/SAXReader", method = RequestMethod.POST)
    public String SAXReader(HttpServletRequest request) {
        try {
            InputStream in = request.getInputStream();
            SAXReader saxReader = new SAXReader();
            //生成document文件
            Document document = saxReader.read(in);
            // 得到xml根元素       
            Element root = document.getRootElement();
            // 得到根元素的所有子节点
            List<Element> elementList = root.elements();
            // 遍历所有子节点
            for (Element e : elementList) {
                //map.put(e.getName(), e.getText());
                System.out.println(e.getName() + ":" + e.getText());
            }
            // 释放资源       
            in.close();

        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
        return "Not Fix";
    }
@RequestMapping(value = "/SAXReader-fix", method = RequestMethod.POST)
    public String Fix_SAXReader(HttpServletRequest request) {
        try {
            InputStream in = request.getInputStream();
            SAXReader saxReader = new SAXReader();
            saxReader.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            saxReader.setFeature("http://xml.org/sax/features/external-general-entities", false);
            saxReader.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            Document document = saxReader.read(in);
            Element root = document.getRootElement();
            List<Element> elementList = root.elements();
            for (Element e : elementList) {
                System.out.println(e.getName() + ":" + e.getText());
            }
            in.close();
            in = null;
        } catch (IOException | DocumentException  | SAXException e) {
            e.printStackTrace();
        }
        return "Fix";
    }
 @RequestMapping(value = "/DocumentBuilderFactory", method = RequestMethod.POST)
    public String DocumentBuilderFactory(HttpServletRequest request)
    {
        try {
            InputStream in=request.getInputStream();
            // DOM 解析器的工厂实例
            DocumentBuilderFactory dbf= DocumentBuilderFactory.newInstance();
            //DOM 工厂获得 DOM 解析器
            DocumentBuilder dbr=dbf.newDocumentBuilder();
            //解析Document
            org.w3c.dom.Document doc= dbr.parse(in);
            // 得到xml根元素       
            org.w3c.dom.Element root = doc.getDocumentElement();
            org.w3c.dom.NodeList elementList = root.getChildNodes();
            // 遍历所有子节点

            // 释放资源       
            in.close();

        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        return "Not Fix";
    }

    @RequestMapping(value = "/DocumentBuilderFactory-fix", method = RequestMethod.POST)
    public String Fix_DocumentBuilderFactory(HttpServletRequest request)
    {
        try {
            InputStream in=request.getInputStream();
            // DOM 解析器的工厂实例
            DocumentBuilderFactory dbf= DocumentBuilderFactory.newInstance();
            dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl",true);
            dbf.setFeature("http://xml.org/sax/features/external-general-entities", false);
            dbf.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            //DOM 工厂获得 DOM 解析器
            DocumentBuilder dbr=dbf.newDocumentBuilder();
            //解析Document
            org.w3c.dom.Document doc= dbr.parse(in);
            // 得到xml根元素       
            org.w3c.dom.Element root = doc.getDocumentElement();
            org.w3c.dom.NodeList elementList = root.getChildNodes();
            // 遍历所有子节点

            // 释放资源       
            in.close();

        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        return "Fix";
    }

}
