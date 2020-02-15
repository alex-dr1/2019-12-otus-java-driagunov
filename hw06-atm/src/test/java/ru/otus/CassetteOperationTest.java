package ru.otus;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CassetteOperationTest {

    @Test
    void put() {
        assertEquals(30L, new CassetteOperation().put(10, 20));
        assertThrows(RuntimeException.class, ()->new CassetteOperation().put(10, -20));
        assertThrows(RuntimeException.class, ()->new CassetteOperation().put(Long.MAX_VALUE - 10, 20));
    }

    @Test
    void get() {
        assertEquals(980L, new CassetteOperation().get(1000, 20));
        assertThrows(RuntimeException.class, ()->new CassetteOperation().get(10, -20));
        assertThrows(RuntimeException.class, ()->new CassetteOperation().get(1000, 2000));
    }
}