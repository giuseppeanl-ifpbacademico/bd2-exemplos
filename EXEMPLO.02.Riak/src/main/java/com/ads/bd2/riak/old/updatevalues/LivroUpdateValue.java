package com.ads.bd2.riak.old.updatevalues;

import com.ads.bd2.riak.old.Livro;
import com.basho.riak.client.api.commands.kv.UpdateValue;


//Essa classe viabiliza atualizar um objeto Livro, por meio do ciclo recuperar/modificar/atualizar do Riak.
public class LivroUpdateValue extends UpdateValue.Update<Livro> {
	
    private Livro update;
    
    public LivroUpdateValue(Livro update){
        this.update = update;
    }

    @Override
    public Livro apply(Livro t) {
    	//se não tem eh pq eh novo, sendo um insert. Senao, atualiza.
        if(t == null) {
            t = new Livro();
        }

        t.setAutor(update.getAutor());
        t.setResumo(update.getResumo());
        t.setCopiasObtidas(update.getCopiasObtidas());
        t.setIsbn(update.getIsbn());
        t.setTitulo(update.getTitulo());

        return t;
    }
}
