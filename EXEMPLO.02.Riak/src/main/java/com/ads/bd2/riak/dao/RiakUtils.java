package com.ads.bd2.riak.dao;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.basho.riak.client.api.RiakClient;
import com.basho.riak.client.core.RiakCluster;
import com.basho.riak.client.core.RiakNode;

public class RiakUtils {

    private static volatile RiakClient client;

    //deixar essa classe como singleton
    private RiakUtils() {
       
    }

   
    /**
     * 
     * @return
     * @throws UnknownHostException
     */
    private static RiakCluster getCluster() throws UnknownHostException {
        List<RiakNode> nodes = new ArrayList<>();

        // Este exemplo usara somente um no no IP e porta informados
        RiakNode node1 = new RiakNode.Builder()
                .withRemoteAddress("127.0.0.1")
                .withRemotePort(8087)
                .build();

        nodes.add(node1);


        // Se tiver outros nodos crie-os como o node1 e adicione-os a lista nodes...

        // Criando o cluster com os nodos informados
        RiakCluster cluster = new RiakCluster.Builder(nodes)
                .build();

        // O cluster deverá estar funcionando, senão voce somente vai ver erros.
        cluster.start();

        return cluster;
    }

    

    /**
     * Retorna um singleton do RiakClient, que é thread-safe e pode ser usado em toda a aplicação.
     * @return
     * @throws UnknownHostException
     */
    public static RiakClient getClient() {
        try {
            if (client == null) { 
                synchronized (RiakUtils.class) {
                    if (client == null) {
                        client = new RiakClient(getCluster());
                    }
                }           
            }
            return client;
        } catch (UnknownHostException e) {
            throw new RuntimeException("Erro ao criar o RiakClient", e);
        }       
    }
}
