## fastjson拓展

## @JSONString 注解

- 在字段上标注
- 在反序列化时会识别字符串到对象的转换
- `Serializers.BEAN_TO_STRING` 会在序列化时把对象先变成json字符串

## example

```java

public class Data  {
    private String name;
    private String value;
    @JSONString
    private Person child;
    @JSONString
    private List<Person> list;
    public Data(String name, String value) {
        this.name = name;
        this.value = value;
    }
    public Data() {

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Person getChild() {
        return child;
    }

    public void setChild(Person child) {
        this.child = child;
    }

    public List<Person> getList() {
        return list;
    }

    public void setList(List<Person> list) {
        this.list = list;
    }


}

```

```java
public class Person {
    private String name;

    public Person(String name) {
        this.name = name;
    }
    public Person() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

```

## test case

```java
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
        System.out.println(JSON.toJSONString(data, Serializers.BEAN_TO_STRING));
    }

}
```

## out put
> 不使用Serializers.BEAN_TO_STRING
```json 
{"child":{"name":"lucy"},"list":[{"name":"lili"}],"name":"1","value":"2"}
```
> 使用Serializers.BEAN_TO_STRING
```json
{"child":"{\"name\":\"lucy\"}","list":"[{\"name\":\"lili\"}]","name":"1","value":"2"}
```