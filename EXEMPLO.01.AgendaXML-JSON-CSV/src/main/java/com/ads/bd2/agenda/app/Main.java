package com.ads.bd2.agenda.app;

import com.ads.bd2.agenda.modelo.Lembrete;
import com.ads.bd2.agenda.modelo.Usuario;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.mongodb.MongoClientSettings;
import org.bson.BsonBinaryWriter;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.codecs.BsonDocumentCodec;
import org.bson.codecs.EncoderContext;
import org.bson.io.BasicOutputBuffer;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Usuario usuario = new Usuario(
                "Usuario de Teste",
                new Date(),
                "usuarioTeste",
                "123456",
                "usuario-teste@gmail.com"
        );

        Lembrete lembrete = new Lembrete(
                new Date(),
                "Estudar para BD2",
                "Estudar para BD2, considerando os slides 1 até 3. Fazer as práticas 1 e 2"                
        );

        usuario.getLembretes().add(lembrete);

        gerarXML(usuario);
        gerarJSON(usuario);
        gerarBSON(usuario);
    }

    public static void gerarXML(Usuario usuario) {
        XStream xstream = new XStream(new StaxDriver());
        xstream.addImplicitCollection(Usuario.class, "lembretes");

        String xml = xstream.toXML(usuario);
        System.out.println("XML =");
        System.out.println(xml);

        byte[] xmlBytes = xml.getBytes(StandardCharsets.UTF_8);
        System.out.println("Tamanho XML: " + xmlBytes.length);
    }

    public static void gerarJSON(Usuario usuario) {
        XStream xstream = new XStream(new JettisonMappedXmlDriver());
        xstream.alias("usuario", Usuario.class);
        xstream.alias("lembrete", Lembrete.class);
        xstream.addImplicitCollection(Usuario.class, "lembretes");

        String json = xstream.toXML(usuario);
        System.out.println("JSON =");
        System.out.println(json);

        byte[] jsonBytes = json.getBytes(StandardCharsets.UTF_8);
        System.out.println("Tamanho JSON: " + jsonBytes.length);
    }

    public static void gerarBSON(Usuario usuario) {
        Document document = new Document("nome", usuario.getNome())
                .append("dataNascimento", usuario.getDataNascimento())
                .append("login", usuario.getLogin())
                .append("senha", usuario.getSenha())
                .append("email", usuario.getEmail());

        List<Document> lembretes = new ArrayList<>();
        for (Lembrete lembrete : usuario.getLembretes()) {
            Document lembreteDoc = new Document("dataCriacao", lembrete.getDataCriacao())
                    .append("titulo", lembrete.getTitulo())
                    .append("texto", lembrete.getTexto())
                    .append("posicaoEntreLembretes", lembrete.getPosicaoEntreLembretes());
            lembretes.add(lembreteDoc);
        }

        document.append("lembretes", lembretes);

        BsonDocument bsonDocument = document.toBsonDocument(
                BsonDocument.class,
                MongoClientSettings.getDefaultCodecRegistry()
        );

        BasicOutputBuffer outputBuffer = new BasicOutputBuffer();
        BsonBinaryWriter writer = new BsonBinaryWriter(outputBuffer);
        new BsonDocumentCodec().encode(writer, bsonDocument, EncoderContext.builder().build());

        byte[] bsonBytes = outputBuffer.toByteArray();

        System.out.println("BSON =");
        System.out.println(bsonDocument);
        System.out.println("Tamanho BSON: " + bsonBytes.length);
    }
}
