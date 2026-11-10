package com.ads.bd2.riak.dao;

import java.util.ArrayList;
import java.util.List;
import com.basho.riak.client.api.RiakClient;
import com.basho.riak.client.api.commands.kv.DeleteValue;
import com.basho.riak.client.api.commands.kv.FetchValue;
import com.basho.riak.client.api.commands.kv.ListKeys;
import com.basho.riak.client.api.commands.kv.StoreValue;
import com.basho.riak.client.core.query.Location;
import com.basho.riak.client.core.query.Namespace;
import com.basho.riak.client.core.query.RiakObject;
import com.basho.riak.client.core.util.BinaryValue;

public class FraseDAO {

    private static final String BUCKET_NAME = "frases";
    private static final String BUCKET_TYPE = "default";
    private static final Namespace BUCKET_NAMESPACE = new Namespace(BUCKET_TYPE, BUCKET_NAME);

    /**
     * Retorna a Location do objeto no Namespace com a key informada.
     * A Location é usada para localizar o objeto no bucket.
     * 
     * @param key
     * @return
     */
    private Location getLocation(String key) {
        return new Location(BUCKET_NAMESPACE, key);
    }

    /**
     * Operação de leitura recuperando pela key do objeto dentro de seu Namespace
     * (bucketName + bucketType)
     * 
     * @param key
     * @return
     */
    public String fetch(String key) {
        RiakClient client = RiakUtils.getClient();

        // Com o nosso Location disponivel (keyFrase), criamos uma operacao FetchValue
        // para recuperar o valor persistido (frase)
        FetchValue fetchOp = new FetchValue.Builder(getLocation(key))
                .build();

        // Executando uma operação de leitura para recuperar a frase pela sua key
        try {
            RiakObject fetchedObject = client
                    .execute(fetchOp)
                    .getValue(RiakObject.class);

            // se o objeto for encontrado é diferente de null
            if (fetchedObject != null)
                return fetchedObject.getValue().toString();

            return null;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar no bucket " + BUCKET_NAME + " com a key: " + key, e);
        }
    }

    public List<String> fetchAll() {
        RiakClient client = RiakUtils.getClient();

        // Lista todas as chaves do bucket
        ListKeys listKeysOp = new ListKeys.Builder(BUCKET_NAMESPACE).build();

        try {
            List<String> frases = new ArrayList<>();
            client.execute(listKeysOp).forEach(location -> {
                String frase = fetch(location.getKeyAsString());

                if (frase != null) {
                    frases.add(frase);
                }

            });

            return frases;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar todas as frases no bucket " + BUCKET_NAME, e);
        }

    }

    public void store(String key, String value) {
        RiakClient client = RiakUtils.getClient();

        // Primeramente, criaremos um objeto basico para persistir uma frase qualquer
        RiakObject frase = new RiakObject()
                // Estamos dizendo ao Riak que estamos a persistir um texto plano (text/plain) e
                // nao um JSON, HTML, etc.
                .setContentType("text/plain")
                // Objetos sao no final persistidos em formato binario no Riak
                .setValue(BinaryValue.create(value));

        StoreValue storeOp = new StoreValue.Builder(frase)
                .withLocation(getLocation(key))
                .build();

        // Executando uma operação de escrita para persistir a frase
        try {
            StoreValue.Response storeOpResp = client.execute(storeOp);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gravar frase no bucket: " + BUCKET_NAME, e);
        }

    }

    public void update(String key, String value) {
        RiakClient client = RiakUtils.getClient();

        // Primeramente, criaremos um objeto basico para persistir uma frase qualquer
        RiakObject frase = new RiakObject()
                // Estamos dizendo ao Riak que estamos a persistir um texto plano (text/plain) e
                // nao um JSON, HTML, etc.
                .setContentType("text/plain")
                // Objetos sao no final persistidos em formato binario no Riak
                .setValue(BinaryValue.create(value));

        StoreValue storeOp = new StoreValue.Builder(frase)
                .withLocation(getLocation(key))
                .build();

        // Executando uma operação de escrita para persistir a frase
        try {
            StoreValue.Response storeOpResp = client.execute(storeOp);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar frase no bucket: " + BUCKET_NAME, e);
        }
    }

    public void delete(String key) {
        RiakClient client = RiakUtils.getClient();

        // Com o nosso Location disponivel (keyFrase), criamos uma operacao DeleteValue
        // para remover o valor persistido (frase)
        DeleteValue deleteOp = new DeleteValue.Builder(getLocation(key))
                .build();

        // Executando uma operação de remoção para deletar a frase pela sua key
        try {
            client.execute(deleteOp);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar no bucket " + BUCKET_NAME + " com a key: " + key, e);
        }
    }

    public void deleteAll() {
        RiakClient client = RiakUtils.getClient();

        // Lista todas as chaves do bucket
        ListKeys listKeysOp = new ListKeys.Builder(BUCKET_NAMESPACE).build();

        try {
            List<String> frases = new ArrayList<>();
            client.execute(listKeysOp).forEach(location -> {
                delete(location.getKeyAsString());
            });
        } catch (Exception e) {
            throw new RuntimeException("Erro ao remover todas as frases no bucket " + BUCKET_NAME, e);
        }
    }

}
