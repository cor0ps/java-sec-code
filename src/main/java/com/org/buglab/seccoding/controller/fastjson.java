package com.org.buglab.seccoding.controller;

@Controller
public class fastjson {

    @RequestMapping(value = "/fastjson",method = RequestMethod.POST)
    public  JSONObject fastjson(@RequestBody String data){
        JSONObject object=JSONObject.parseObject(data,Feature.SupportNonPublicField);
        JSONObject ret=new JSONObject();
        ret.put("code",1001);
        ret.put("data","Hello "+object.get("name"));
        return ret;

    }
}
