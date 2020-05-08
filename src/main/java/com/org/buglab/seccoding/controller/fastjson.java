package com.org.buglab.seccoding.controller;


@RestController
@RequestMapping("/fastjson")
public class fastjson {
    @RequestMapping(value = "/1.2.24",method = RequestMethod.GET)
    public String fastjson()
    {
        return "fastjson 1.2.24";
    }


    @RequestMapping(value = "/1.2.24",method = RequestMethod.POST)
    public JSONObject fastjson(@RequestBody String data){
        //JSONObject object=JSONObject.parseObject(data, Student.class,Feature.SupportNonPublicField);
        Student student=JSONObject.parseObject(data, Student.class,Feature.SupportNonPublicField);
        JSONObject ret=new JSONObject();
        ret.put("code",1001);
        ret.put("data","Hello "+student.getName());
        return ret;

    }
}
