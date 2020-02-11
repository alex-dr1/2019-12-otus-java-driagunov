package ru.otus;

public class TestClass {

    @Before
    void before1(){
        System.out.println("Run before1");
    }

    @Test
    void test1(){
        System.out.println("Run test1");
        throw new RuntimeException("Failure");
    }

    @Test
    void test2(){
        System.out.println("Run test2");
    }

    @Test
    void test3(){
        System.out.println("Run test3");
    }

    @After
    void after1(){
        System.out.println("Run after1");
    }

    @After
    void after2(){
        System.out.println("Run after2");
    }

    @Before
    void before2(){
        System.out.println("Run before2");
    }

}
