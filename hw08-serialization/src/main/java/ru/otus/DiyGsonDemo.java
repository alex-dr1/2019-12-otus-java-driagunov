package ru.otus;

import com.google.gson.Gson;

import java.util.List;
import java.util.Set;


public class DiyGsonDemo {
    public static void main(String[] args) {
//        {"boolean":true,"value2":"t","value1":22,"value2":"test","stringList":["one","two","three"],"longSet":[3000,10,200]}
//        null
//        ""
//        "12345"
//        12345

        Gson gson = new Gson();
        DiyGson diyGson = new DiyGson();

        BagOfValues obj = new BagOfValues(
                true,
                't',
                (short) 344,
                88888,
                10_000_000L,
                12.34F,
                56.89,
                "text123",
                List.of("one", "two", "three"),
                Set.of(10L, 200L, 3000L)
        );
//        System.out.println(obj);

        Object obj2 = obj;
//        Object obj2 = 123.567;

        String json = gson.toJson(obj2);

        String diyJson = diyGson.toJson(obj2);
        System.out.print("JSON=");
        System.out.println(json);

        System.out.print("DiyJSON=");
        System.out.println(diyJson);

//        BagOfValues obj2 = gson.fromJson(json, BagOfValues.class);
//        System.out.println(obj.equals(obj2));
//        System.out.println(obj2);
    }
}
