package com.org.buglab.seccoding.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;

public class FastjsonUnserial {
    public static void main(String[] args) {
        String text = "{\"@type\":\"Student\",\"age\":29,\"name\":\"buglab\"}";
        //FastJson默认是不支持私有属性的
        Student student = JSON.parseObject(text,Student.class,Feature.SupportNonPublicField);
        System.out.println(student);
        System.out.println("Name:" + student.getName() + " Age:" + student.getAge());
        Object student1 = JSON.parseObject(text, Object.class);
        System.out.println(student1);
        System.out.println("object name: " + student1.getClass().getName());
    }
}
