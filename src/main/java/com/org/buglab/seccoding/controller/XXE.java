package com.org.buglab.seccoding.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class XXE {
    @RequestMapping("/xxe")
    public String xxe(){
        String xxx="ceshi";
        return xxx;
    }


}
