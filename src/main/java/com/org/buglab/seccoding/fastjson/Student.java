package com.org.buglab.seccoding.fastjson;

public class Student {
    public Student() {
        System.out.println("Constructor");
    }

    public String getName() {
        System.out.println("getName()");
        return name;
    }

    public void setName(String name) {
        System.out.println("setName()");
        this.name = name;
    }

    public int getAge() {
        System.out.println("getAge()");
        return age;
    }

    public void setAge(int age) {
        System.out.println("setAge()");
        this.age = age;
    }

    private String name;
    private int age;

}