package com.company.mx.framework.test;

import java.util.ArrayList;
import java.util.List;

public class Examen {

    public static void main(String[] args) {
        Examen t = new Examen();

        List<Integer> packageWeights = new ArrayList<>();
        packageWeights.add(2);
        packageWeights.add(9);
        packageWeights.add(10);
        packageWeights.add(3);
        packageWeights.add(7);

       long n = t.print(packageWeights);


    }



    public long print(List<Integer> packageWeights){
        long heaviestPackage = 0L;
        int n = packageWeights.size();
        List<Integer> newPackages = new ArrayList<>();

        if (n >= 1 && n <= 200000){
            int groups = 1;
            boolean modified = false;
            int maxSize = packageWeights.size()-1;
            for(int i=0; i<packageWeights.size(); i++){
                if (i<maxSize) {
                    if (packageWeights.get(i) < packageWeights.get(i + 1)) {
                        if(modified) {
                            modified=false;
                        }
                    } else {
                        groups++;
                        modified = true;
                    }
                }
            }

            List<List<Integer>> listota = new ArrayList<>();
            for(int i=0; i<groups; i++){
                List<Integer> groupN = new ArrayList<>();
                boolean modified2 = false;
                int maxSize2 = packageWeights.size()-1;
                for(int j=0; j<packageWeights.size(); j++){
                    if (j<maxSize2) {
                        if (packageWeights.get(j) < packageWeights.get(j + 1)) {
                            if (modified2) {
                                modified2 = false;
                                continue;
                            }
                        } else {
                            modified2 = true;
                            groupN.add(packageWeights.get(j));
                        }
                    }
                }
                listota.add(groupN);
            }

            for (List<Integer> list:
                 listota) {

                for(int item : list){
                    System.out.println(item);
                }
            }

            /*for(int i=0; i<groups; i++){
                int currentWeight = packageWeights.get(i);

                if(i < maxSize){
                    int nextWeight = packageWeights.get(i+1);

                    if(currentWeight < nextWeight){
                        int newWeight = currentWeight + nextWeight;
                        newPackages.add(newWeight);
                    }
               }
            }*/

            int maxWeight = 0;
            if (!newPackages.isEmpty()){
                for(int weight : newPackages){
                    maxWeight = weight  > maxWeight ? weight : maxWeight;
                   // System.out.println(maxWeight);
                }
                heaviestPackage = maxWeight;
            }
        }
        System.out.println("heaviest="+heaviestPackage);
        return heaviestPackage;
    }

}
