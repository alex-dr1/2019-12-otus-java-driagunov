package ru.otus;

import java.util.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        String[] arrayString = new String[100];
        for (int i = 0; i < arrayString.length; i++){
            arrayString[i] = "A" + Integer.toString(100 + i);
        }
        System.out.println("Исходный массив arrayString");
        System.out.println(Arrays.toString(arrayString));

//        ===========================================================================
//                              Collections.addAll()
//        ===========================================================================

//        List<String> list = new ArrayList<>();
        List<String> list = new DIYArrayList<>();

        Collections.addAll(list, arrayString);

        System.out.println();
        System.out.println("Collections.addAll(list, arrayString)");
        printList(list);

//        ===========================================================================
//                              Collections.copy()
//        ===========================================================================

//        List<String> list2 = new ArrayList<>();
        List<String> list2 = new DIYArrayList<>();
        list2.add("aaaa");
        list2.add("bbbb");
        list2.add("cccc");
        list2.add("dddd");
        list2.add("eeee");
        Collections.copy(list, list2);

        System.out.println();
        System.out.println("list2");
        printList(list2);
        System.out.println();
        System.out.println("Collections.copy(list, list2)");
        printList(list);


//        ===========================================================================
//                              Collections.copy()
//        ===========================================================================


//        List<String> list3 = new ArrayList<>();
        List<String> list3 = new DIYArrayList<>();
        for (int i = 1; i<=100; i++){
            Random random = new Random();
            list3.add(Integer.toString(random.nextInt(100)+100));
        }
        System.out.println();
        System.out.println("Random list3");
        printList(list3);

        Comparator<String> comp = new Comparator<String>() {
            @Override
            public int compare(String s, String t1) {
                return Integer.parseInt(s) - Integer.parseInt(t1);
            }
        };
        Collections.sort(list3, comp);

        System.out.println();
        System.out.println("Collections.sort(list3, comp)");
        System.out.println("Sort list3");
        printList(list3);
    }



    static void printList(List<?> list){
        for (int i = 0; i < list.size(); i++){
            System.out.print(list.get(i) + " ");
            if((i+1)%10 == 0){
                System.out.println();
            }
        }
        System.out.println();
    }
}
