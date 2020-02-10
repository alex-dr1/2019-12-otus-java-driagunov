package otus;

public class TestClass {

    @Before
    void test1(){
        System.out.println("Run test1");
    }

    @Test
    void test3(){
        System.out.println("Run test3");
        throw new RuntimeException("Failure3");
    }

    @Test
    void test4(){
        System.out.println("Run test4");
    }

    @After
    void test5(){
        System.out.println("Run test5");
    }

    @After
    void test6(){
        System.out.println("Run test6");
        throw new RuntimeException("Failure6");
    }

    @Before
    void test2(){
        System.out.println("Run test2");
        throw new RuntimeException("Failure2");
    }

}
