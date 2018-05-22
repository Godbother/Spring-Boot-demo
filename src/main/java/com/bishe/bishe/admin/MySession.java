package com.bishe.bishe.admin;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MySession {
    private static Map<String,Object> attrfields;

    private static void init(){
        if (attrfields==null) {
            attrfields = new HashMap<>();
        }
    }

    public void setAttr(String key,Object value){
        init();
        attrfields.put(key, value);
    }

    public void removeAttr(String key){
        init();
        attrfields.remove(key);
    }

    public Object getAttr(String key){
        return attrfields.get(key);
    }

    public void destroy(){
        attrfields = null;
    }
}
