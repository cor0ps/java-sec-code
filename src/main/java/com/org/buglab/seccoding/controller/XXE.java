package com.org.buglab.seccoding.controller;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static com.org.buglab.seccoding.wxpay.sdk.WXPayUtil.xmlToMap;
/*
*
* <?xml version ="1.0" encoding="UTF-8"?>
<!DOCTYPE foo [
<!ENTITY xxe SYSTEM "file:///etc/flag">
]>
<foo>&xxe;</foo>
-javaagent:"D:\Agent.jar" -Dlog4j.configuration=file:C:\Users\t00381761\IdeaProjects\java-sec-code-master\src\main\resources\log4j.properties
 */


/*
@RequestMapping
@RequestMapping 是一个用来处理请求地址映射的注解，可用于类或方法上
        用于类上，表示类中的所有响应请求的方法都是以该地址作为父路径
        用于方法上，表示在类的父路径下追加方法上注解中的地址将会访问到该方法
*/

@Controller
@RequestMapping("/xxe")
public class XXE {

    private String data;
    private HttpServletRequest request;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(ModelMap map, HttpServletRequest request) {
        map.put("SAXReader", "SAXReader");
        map.put("DocumentBuilderFactory", "DocumentBuilderFactory");
        return "index";
    }

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
        } catch (IOException | DocumentException | SAXException e) {
            e.printStackTrace();
        }
        return "Fix";
    }

    @RequestMapping(value = "/DocumentBuilderFactory", method = RequestMethod.POST)
    public String DocumentBuilderFactory(ModelMap map, HttpServletRequest request) {
        try {
            InputStream in = request.getInputStream();
            // DOM 解析器的工厂实例
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            //DOM 工厂获得 DOM 解析器
            DocumentBuilder dbr = dbf.newDocumentBuilder();
            //解析Document
            org.w3c.dom.Document doc = dbr.parse(in);
            // 得到xml根元素       
            org.w3c.dom.Element root = doc.getDocumentElement();
            praseElement(root);
            map.put("SAXReader", "SAXReader");
            in.close();

        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        return "Not Fix";
    }

    @RequestMapping(value = "/DocumentBuilder", method = RequestMethod.POST)
    public String DocumentBuilder(ModelMap map, HttpServletRequest request) {

        Map<String, String> wxpay = null;
        try {
            String xml = getBody(request);
            System.out.println(xml);
            wxpay = xmlToMap(xml);
            System.out.println(wxpay);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!wxpay.isEmpty())
            map.put("weixin", wxpay.toString());
        return "DocumentBuilder";

    }

    // 获取body数据
    private String getBody(HttpServletRequest request) throws IOException {
        InputStream in = request.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        StringBuffer sb = new StringBuffer("");
        String temp;
        while ((temp = br.readLine()) != null) {
            sb.append(temp);
        }
        if (in != null) {
            in.close();
        }
        if (br != null) {
            br.close();
        }
        return sb.toString();
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


    @RequestMapping(value = "/DocumentBuilderFactory/fix", method = RequestMethod.GET)
    public String Fix_DocumentBuilderFactory(HttpServletRequest request) {
        try {
            InputStream in = request.getInputStream();
            // DOM 解析器的工厂实例
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            dbf.setFeature("http://xml.org/sax/features/external-general-entities", false);
            dbf.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            //DOM 工厂获得 DOM 解析器
            DocumentBuilder dbr = dbf.newDocumentBuilder();
            //解析Document
            org.w3c.dom.Document doc = dbr.parse(in);
            // 得到xml根元素       
            org.w3c.dom.Element root = doc.getDocumentElement();
            org.w3c.dom.NodeList elementList = root.getChildNodes();
            // 遍历所有子节点
            praseElement(root);
            // 释放资源       
            in.close();

        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        return "Fix";
    }

    @RequestMapping(value = "/api", method = RequestMethod.POST)
    public String XmlRe(ModelMap map,@RequestBody String data, HttpServletRequest request) throws Exception {
        Map<String,String> wxpay;
        if (Pattern.matches("(?i)(.|\\s)*(file|ftp|gopher|CDATA|read_secret|logs|log|conf|etc|session|proc|root|history|\\.\\.|data|class|bash|viminfo)(.|\\s)*", data)) {
               map.put("Error","Hacker! Hacker! Hacker! ");
            return "DocumentBuilder";
        } else {
            wxpay = xmlToMap(data);
            if (!wxpay.isEmpty())
                map.put("weixin","{\"status\":\"OK\",\"message\":\""+ wxpay.toString() +"\"}");
            return "DocumentBuilder";
        }
    }
}
