package ru.otus.l011;


import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;

/**
 * To start the application:
 * $ java -jar ./L01-maven/target/L01-maven-jar-with-dependencies.jar
 */
public class HelloOtus {

    public static void main(String[] args) {
        List<Integer> example = new ArrayList<>();
        int min = 0;
        int max = 99;
        for (int i = min; i < max + 1; i++) {
            example.add(i);
        }

        printList(example, min, max);

        printList(Lists.reverse(example), min, max);
    }

    private static void printList(List<Integer> example, int min, int max) {
        for (int i = min; i < max + 1; i++) {
            int value = example.get(i);
            System.out.print(value + " ");
            if((value+1)%10 == 0){
                System.out.println();
            }
        }
        System.out.println();
    }
}
