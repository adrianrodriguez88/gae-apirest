package com.company.mx.framework.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TestPromo {

    public static void main(String[] args) {
        TestPromo t = new TestPromo();

        List<String> codeList = new ArrayList<>();
        List<String> shoppingCart = new ArrayList<>();

        String code1 = "apple apple";
        String code2 = "banana anything banana";
        codeList.add(code1);
        codeList.add(code2);

        String cart = "orange apple apple banana orange banana";
        shoppingCart.add(cart);

        int i = t.print(codeList, shoppingCart);
        System.out.println(i);
    }



    public int print(List<String> codeList, List<String> shoppingCart){
        if(!codeList.isEmpty()){
            int result;

            int cartSize = shoppingCart.size();// cartSize=1
            int codeSize = codeList.size();//codeSize=2
            boolean matchesAllGroups = true;
            boolean[] flags = new boolean[codeSize-1];//[1]

            Iterator it = shoppingCart.iterator();

            String[] cartArray = new String[cartSize];
            int x=0;
            while(it.hasNext()){
                cartArray[x] = (String)it.next(); //orange apple apple banana orange banana
                //System.out.println(cartArray[x]);
                x++;
            }

            int combinations =  - codeSize + 1;//6-2+1; = 5 ; 6-3+1 = 4

            for(int i=0; i<codeSize; i++){
                String currentCode = codeList.get(i); //apple apple

                /*for(int j=0; j<cartArray.length-1; j++){
                    if (cartArray[j].concat(" " +cartArray[j+1]).equals(currentCode)) {
                        flags[i] = true;
                    }
                    else {
                        flags[i] = false;
                    }
                }*/

                for(int j=0; j<combinations; j++){
                    System.out.println(cartArray[j]);
                    System.out.println(cartArray[j+1]);
                    System.out.println("currentCode=> "+currentCode);
                    if (cartArray[j].concat(" "+cartArray[j+1]).equals(currentCode)){
                        flags[i] = true;
                    }
                    else {
                        flags[i] = false;
                    }
                }
            }

            for(int i=0; i< flags.length; i++){
                System.out.println(flags[i]);

                if (flags[i] == false)
                    matchesAllGroups = false;
            }

            return matchesAllGroups ? 1 : 0;
        }
        else {
            return 1;
        }


    }

}
