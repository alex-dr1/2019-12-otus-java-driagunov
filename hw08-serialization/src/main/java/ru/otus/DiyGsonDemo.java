package ru.otus;

import com.google.gson.Gson;

import java.util.ArrayList;
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

        Person person1 = new Person("Alex", 38);

        BagOfValues obj = new BagOfValues(
                true,
                't',
                (short) 344,
                88888,
                10_000_000L,
                12.34F,
                56.89,
                "text123",
                person1,
                List.of("one", "two", "three"),
                Set.of(10L, 200L, 3000L)
        );
        System.out.println(obj);

        String json = gson.toJson(obj);
        String diyJson = diyGson.toJson(obj);

        System.out.print("   JSON = ");
        System.out.println(json);

        System.out.print("DiyJSON = ");
        System.out.println(diyJson);

        BagOfValues obj2 = gson.fromJson(diyJson, BagOfValues.class);
        System.out.println(obj2.equals(obj));

    }
}
