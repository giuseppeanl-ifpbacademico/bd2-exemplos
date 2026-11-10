package com.ads.bd2.riak.old;

import com.ads.bd2.riak.dao.RiakUtils;
import com.basho.riak.client.api.RiakClient;
import com.basho.riak.client.api.commands.kv.DeleteValue;
import com.basho.riak.client.api.commands.kv.FetchValue;
import com.basho.riak.client.api.commands.kv.ListKeys;
import com.basho.riak.client.api.commands.kv.StoreValue;
import com.basho.riak.client.core.query.Location;
import com.basho.riak.client.core.query.Namespace;
import com.basho.riak.client.core.query.RiakObject;
import com.basho.riak.client.core.util.BinaryValue;

public class MainOld {

        public static void main(String[] args) {
                RiakClient client = null;
                try {
                        // Obtendo o objto que dá acesso ao cluster do Riak que nos permite
                        // executar as operacoes de escrita e leitura.
                        client = RiakUtils.getClient();

                        // Primeramente, criaremos um objeto basico para persistir uma frase qualquer
                        RiakObject frase = new RiakObject()
                                        // Estamos dizendo ao Riak que estamos a persistir um texto plano (text/plain) e
                                        // nao um JSON, HTML, etc.
                                        .setContentType("text/plain")
                                        // Objetos sao no final persistidos em formato binario no Riak
                                        .setValue(BinaryValue.create("Agua mole em pedra dura, tanto bate até que fura."));

                        // Criando um objeto Namespace, o qual determina o bucketName dele e o seu
                        // BucketType
                        // O BucketType não é obrigatório ao criar o Namespace, em que um BucketType
                        // "default" é aplicado.
                        // criando o Namespace (bucketName + bucket type)
                        Namespace bucketFrases = new Namespace("default", "frases");

                        // Com o nosso objeto namespace disponivel podemos criar um objeto Location.
                        // Esse Location permite que passemos uma key (chave) para o agregado
                        // considerado.
                        Location keyFrase = new Location(bucketFrases, "proverbio1");

                        // Com o nosso RiakObject (frase), criamos uma operacao StoreValue passando o
                        // objeto Location
                        // para persistí-lo.
                        StoreValue storeOp = new StoreValue.Builder(frase)
                                        .withLocation(keyFrase)
                                        .build();

                        // Executando uma operação de escrita para persistir a frase
                        StoreValue.Response storeOpResp = client.execute(storeOp);

                        // Gravando outra frase com outra Location (keyFrase2)
                        Location keyFrase2 = new Location(bucketFrases, "proverbio2");
                        storeOp = new StoreValue.Builder(frase)
                                        .withLocation(keyFrase2)
                                        .build();
                        storeOpResp = client.execute(storeOp);

                        // Com o nosso Location disponivel (keyFrase), criamos uma operacao FetchValue
                        // para recuperar o valor persistido (frase)
                        FetchValue fetchOp = new FetchValue.Builder(keyFrase)
                                        .build();

                        // Executando uma operação de leitura para recuperar a frase pela sua key
                        RiakObject fetchedObject = client.execute(fetchOp).getValue(RiakObject.class);
                        String fetchedValue = fetchedObject.getValue().toString();
                        System.out.println(fetchedValue);

                        // Comprovando que os objetos são os mesmos
                        assert (fetchedObject.getValue().equals(frase.getValue()));

                        // Agora atualizaremos o objeto recuperado
                        fetchedObject.setValue(BinaryValue.create("Se conselho fosse bom se vendia."));
                        StoreValue updateOp = new StoreValue.Builder(fetchedObject)
                                        .withLocation(keyFrase)
                                        .build();
                        StoreValue.Response updateOpResp = client.execute(updateOp);
                        updateOpResp = client.execute(updateOp);

                        // Lista todas as chaves do bucket
                        ListKeys listKeysCmd = new ListKeys.Builder(bucketFrases).build();
                        ListKeys.Response listKeysResponse = client.execute(listKeysCmd);

                        // Intera sobre as keys do bucket
                        for (Location location : listKeysResponse) {
                                FetchValue fetchCmd = new FetchValue.Builder(location).build();
                                FetchValue.Response fetchResponse = client.execute(fetchCmd);

                                // Pega o objeto retornado do Riak
                                RiakObject riakObject = fetchResponse.getValue(RiakObject.class);

                                if (riakObject != null) {
                                        String valor = riakObject.getValue().toString();
                                        System.out.println("Chave: " + location.getKeyAsString() + " | Valor: " + valor);
                                }
                        }

                        // E deletaremos o segundo objeto
                        DeleteValue deleteOp = new DeleteValue.Builder(keyFrase)
                        .build();
                        client.execute(deleteOp);
                    
                        // Livro meninoDeEngenho = new Livro();
                        // meninoDeEngenho.setTitulo("Menino de Engenho");
                        // meninoDeEngenho.setResumo("...");
                        // meninoDeEngenho.setIsbn("31321323132-0000");
                        // meninoDeEngenho.setCopiasObtidas(5);

                        // Autor autor = new Autor();
                        // autor.setNome("Jose Lins do Rego");
                        // autor.setDataNascimento(new Date());
                        // meninoDeEngenho.setAutor(autor);

                        // System.out.println("Objeto livro criado");

                        // // Agora atribuiremos uma Location para o livro, criando uma operacao
                        // StoreValue
                        // // para armazena-lo
                        // Namespace livrosBucket = new Namespace("livros");
                        // Location meninoDeEngenhoLocation = new Location(livrosBucket,
                        // "menino_engenho");
                        // StoreValue storeBookOp = new StoreValue.Builder(meninoDeEngenho)
                        // .withLocation(meninoDeEngenhoLocation)
                        // .build();
                        // client.execute(storeBookOp);
                        // System.out.println("Menino de engenho gravado no Riak com sucesso");

                        // // E iremos verificar que podem recuperar as inforacoes do livro armazenado e
                        // // que essa informacao bate com a do livro criado inicialmente
                        // FetchValue fetchMobyDickOp = new FetchValue.Builder(meninoDeEngenhoLocation)
                        // .build();
                        // Livro fetchedBook = client.execute(fetchMobyDickOp).getValue(Livro.class);
                        // System.out.println("Objeto livro recuperado(consultado) do Riak com
                        // sucesso");

                        // assert(meninoDeEngenho.getClass() == fetchedBook.getClass());
                        // assert(meninoDeEngenho.getTitulo().equals(fetchedBook.getTitulo()));
                        // assert(meninoDeEngenho.getAutor().equals(fetchedBook.getAutor()));
                        // // E assim por diante...

                        // // Atualizando o livro com mais copias
                        // meninoDeEngenho.setCopiasObtidas(5);
                        // LivroUpdateValue updatedBook = new LivroUpdateValue(meninoDeEngenho);
                        // UpdateValue updateValue = new UpdateValue.Builder(meninoDeEngenhoLocation)
                        // .withUpdate(updatedBook)
                        // .build();
                        // UpdateValue.Response response = client.execute(updateValue);
                        // System.out.println("Successo! Todos os testes de atualizacao passaram!");

                } catch (Throwable e) {
                        e.printStackTrace();
                } finally {
                        if (client != null) {
                                client.shutdown();
                        }
                }

        }
}
