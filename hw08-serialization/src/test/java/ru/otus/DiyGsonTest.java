package ru.otus;

import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тест.Самодельный GSON.")
class DiyGsonTest {
  @DisplayName("Преобразование в json")
  @Test
  void toJson() {
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
    String diyJson = diyGson.toJson(obj);
    BagOfValues obj2 = gson.fromJson(diyJson, BagOfValues.class);
    assertEquals(obj2,obj);
  }
}