package com.canessa.maven.test.concurrencia;

import java.util.HashMap;

public class TransactionPool {
    private static TransactionPool instance;
    private HashMap transactions;

    // Singlenton
    public static TransactionPool getTransactionPool() {
        if (instance == null) {
            instance = new TransactionPool();
            System.out.println("Creado y cargado");
        } else {
            System.out.println("Existe y cargado");
        }

        return instance;
    }

    // Constructor
    private TransactionPool() {
        transactions= new HashMap();
    }

    // Metodos
    public void saveEmpresa(String timeStamp, String transaction) {
        transactions.put(timeStamp, transaction);
    }
    public String getEmpresa(String timeStamp){
        return (String) transactions.get(timeStamp);
    }
}
