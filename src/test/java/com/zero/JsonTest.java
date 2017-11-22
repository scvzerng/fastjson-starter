package com.zero;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.BeanContext;
import com.alibaba.fastjson.serializer.ContextValueFilter;
import com.alibaba.fastjson.util.ASMClassLoader;
import com.yazuo.intelligent.fastjson.annotation.JSONString;
import com.yazuo.intelligent.fastjson.parser.deserializer.CustomerASMDeserializerFactory;
import com.zero.entity.Data;
import com.zero.entity.Person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonTest {
    static {
        ParserConfig.global = new ParserConfig(new CustomerASMDeserializerFactory(new ASMClassLoader()));
    }
    public static void main(String[] args) {
        List<Person> personList = new ArrayList<Person>();
        personList.add(new Person("lili"));
        Map jsonMap = new HashMap();
        jsonMap.put("child", JSON.toJSONString(new Person("lucy")));
        jsonMap.put("list", JSON.toJSONString(personList));
        jsonMap.put("name",1);
        jsonMap.put("value",2);
        String json = JSON.toJSONString(jsonMap);
        Data data = JSON.parseObject(json,Data.class);
        assert data.getName().equals("1");
        System.out.println(JSON.toJSONString(data));
        System.out.println(JSON.toJSONString(data, new ContextValueFilter() {

            public Object process(BeanContext context, Object object, String name, Object value) {
                return context.getField().getAnnotation(JSONString.class)!=null?JSON.toJSONString(value):value;
            }
        }));
    }

}
