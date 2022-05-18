package com.company.mx.framework.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class TestSearch {

    public static void main(String[] args) {
        TestSearch t = new TestSearch();

        String customerQuery = "bags";

        List<List<String>> result = t.print(customerQuery);

        result.stream()
                .map(
                        r -> r.stream()
                                .collect(joining(" "))
                )
                .map(r -> r + "\n")
                .collect(toList())
                .forEach(e -> {
                    System.out.println(e);
                    /*try {
                        bufferedWriter.write(e);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }*/
                });
    }



    public List<List<String>> print(String customerQuery){

        java.util.List<String> repository = new java.util.ArrayList<String>();
        repository.add("bags");
        repository.add("baggage");
        repository.add("banner");
        repository.add("box");
        repository.add("cloths");


        List<List<String>> results = new ArrayList<>();
        int maxKeywords = 3;
        int minCharacters = 2;
        int queryLength = customerQuery.length();

        if(queryLength >= minCharacters){
            int retries = queryLength - 1;

            Collections.sort(repository);

            for(int i=0; i<retries; i++) {
                List<String> keyword = new ArrayList<String>();
                String substringToCompare = customerQuery.substring(0, minCharacters + i);
                int lengthToCompare = substringToCompare.length();
                int foundKeywords = 0;
                //System.out.println(substringToCompare);

                for (String comment : repository) {
                    if (maxKeywords > foundKeywords) {
                        boolean matches = comment.regionMatches(true, 0, substringToCompare, 0, lengthToCompare);

                        if (matches) {
                            keyword.add(comment);
                            foundKeywords++;
                            //System.out.println(comment);
                        }
                    }
                }
                results.add(keyword);
            }
        }

        return results;
    }

}
