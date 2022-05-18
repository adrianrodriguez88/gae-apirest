package com.company.mx.framework.dbdriver;

import java.util.TreeMap;

public class Manager {
    private static Manager instance = null;
    private TreeMap<String,String> adapterMap = null;

    public static Manager Instance(){
        if (instance == null)
            instance = new Manager();
        return instance;
    }

    Manager(){
        adapterMap = new TreeMap<>();
        adapterMap.put("mongo", "com.company.mx.framework.dbdriver.impl.Mongo");
        adapterMap.put("sql", "com.company.mx.framework.dbdriver.impl.SQL");
    }

    public IAdapter create(String driverId){
        try {
            if (adapterMap.containsKey(driverId)){
                Class clazz= Class.forName(adapterMap.get(driverId));
                Object o = clazz.newInstance();

                if (o instanceof IAdapter){
                    return (IAdapter) o;
                }
            }
            else {
                System.out.println("El driverId no existe en el Manager");
                return null;
            }
        }
        catch(Exception e){
            System.out.println("Error inesperado");
            e.printStackTrace();
        }

        return null;
    }
}
