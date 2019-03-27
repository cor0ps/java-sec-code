package com.org.buglab.seccoding.controller;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

@Controller
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
   
 private void praseElement(org.w3c.dom.Element element) {
        String tagName = element.getNodeName();
        NodeList children = element.getChildNodes();
        System.out.print("<" + tagName);
        //element 元素的所有属性所构成的 NamedNodeMap 对象，需要对其进行判断
        NamedNodeMap map = element.getAttributes();
        //如果该元素存在属性
        if (null != map) {
            for (int i = 0; i < map.getLength(); i++) {
                //获得该元素的每一个属性
                Attr attr = (Attr) map.item(i);
                String attrName = attr.getName();
                String attrValue = attr.getValue();
                System.out.print(" " + attrName + "=\"" + attrValue + "\"");
            }
        }
        System.out.print(">");
        for (int i = 0; i < children.getLength(); i++) {
            Node node = children.item(i);
            //获得结点的类型
            short nodeType = node.getNodeType();
            if (nodeType == Node.ELEMENT_NODE) {
                //是元素，继续递归
                praseElement((org.w3c.dom.Element) node);
            } else if (nodeType == Node.TEXT_NODE) {
                //递归出口
                System.out.print(node.getNodeValue());
            } else if (nodeType == Node.COMMENT_NODE) {
                System.out.print("<!--");
                Comment comment = (Comment) node;
                //注释内容
                String data = comment.getData();
                System.out.print(data);
                System.out.print("-->");
            }
        }
        System.out.print("</" + tagName + ">");
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

    @RequestMapping(value = "/test" , method = RequestMethod.GET)
    public String world(HttpServletRequest request) {

        //request.setAttribute("name", "xxxxxxxxx");

        return "world";

    }

}
