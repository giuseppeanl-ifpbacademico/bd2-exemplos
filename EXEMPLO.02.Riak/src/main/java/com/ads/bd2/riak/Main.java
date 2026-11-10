package com.ads.bd2.riak;

import com.ads.bd2.riak.dao.FraseDAO;
import com.ads.bd2.riak.dao.RiakUtils;

public class Main {

    public static void main(String[] args) {
        try {
            FraseDAO fraseDAO = new FraseDAO();

            //BUSCANDO PELA KEY
            String key = "proverbio2";
            String frase = fraseDAO.fetch(key);

            System.out.println("Frase recuperada com a key '" + key + "': " + frase);
            
            //BUSCANDO TODOS
            System.out.println(fraseDAO.fetchAll());


            //PERSISTINDO UMA FRASE 01
            fraseDAO.store("proverbio1", "A pressa é inimiga da perfeição");
            System.out.println("Frase gravada com a key 'proverbio1'");

            //PERSISTINDO UMA FRASE 02
            fraseDAO.store("proverbio2", "Casa de ferreiro espeto de pau");
            System.out.println("Frase gravada com a key 'proverbio2'");
            
            //BUSCANDO TODOS
            System.out.println(fraseDAO.fetchAll());

            //ATUALIZANDO FRASE 01
            fraseDAO.update("proverbio1", "Agua mole em pedra dura tanto bate até que fura");
            System.out.println("Frase atualizada com a key 'proverbio1'");
            System.out.println(fraseDAO.fetchAll());

             //REEMOVENDO FRASE 02
            fraseDAO.delete("proverbio2");
            System.out.println("Frase removida com a key 'proverbio2'");

             //BUSCANDO TODOS
            System.out.println(fraseDAO.fetchAll());

            //REMOVENDO TODAS AS FRASES
            fraseDAO.deleteAll();
            System.out.println("Frases removidas");
            System.out.println(fraseDAO.fetchAll());

        } finally {
            RiakUtils.getClient().shutdown();   
        }
    }

}
