package com.org.buglab.seccoding.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class FastjsonSerial {

    public static void main(String[] args) {
        Student student=new Student();
        student.setName("buglab");
        student.setAge(29);
        String json_writeclass= JSON.toJSONString(student, SerializerFeature.WriteClassName);
        System.out.println(json_writeclass);
        String json_notwrite= JSON.toJSONString(student);
        System.out.println(json_notwrite);
    }
}
