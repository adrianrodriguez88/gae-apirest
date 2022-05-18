package com.company.mx.framework.test;

import static java.util.stream.Collectors.joining;

public class TestRange {

    public static void main(String[] args) {
        TestRange t = new TestRange();

        int n = 5;

        t.print(n);


    }



    public void print(int n){

        if(n> 0 && n < 200000){
            int[] range= new int[n+1];

            for(int i=1; i<range.length; i++){
                System.out.println(i);
            }




        }
    }

}
