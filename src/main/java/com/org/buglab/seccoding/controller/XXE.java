package com.org.buglab.seccoding.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
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
            in = null;
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
        return "OK";
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


}
