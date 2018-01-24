package com.zero;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.ASMClassLoader;
import com.zero.entity.Data;
import com.zero.entity.Person;
import com.zero.scvzerng.fastjson.deserializer.CustomerASMDeserializerFactory;
import com.zero.scvzerng.fastjson.serializer.Serializers;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonTest {
    static {
        ParserConfig.global = new ParserConfig(new CustomerASMDeserializerFactory(new ASMClassLoader()));
    }
    public static void main(String[] args) throws IOException {
        byte[] buff = new byte[1024];
        new Socket("192.168.50.100",6379).getInputStream().read(buff);
        System.out.println(new String(buff));
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
        System.out.println(JSON.toJSONString(data, Serializers.BEAN_TO_STRING));
    }

}
