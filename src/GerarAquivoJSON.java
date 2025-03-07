import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.FileWriter;

public class GerarAquivoJSON {

    public static void gerarArquivoJson(String moedaBase, String moedaDestino, double quantidade, double taxaCambio, double valorConvertido) {
        JsonObject conversao = new JsonObject();
        conversao.addProperty("moeda_base", moedaBase);
        conversao.addProperty("moeda_destino", moedaDestino);
        conversao.addProperty("quantidade", quantidade);
        conversao.addProperty("taxa_cambio", taxaCambio);
        conversao.addProperty("valor_convertido", valorConvertido);

        try (FileWriter fileWriter = new FileWriter("conversao.json")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(conversao, fileWriter);
            fileWriter.close();
            System.out.println("Arquivo JSON gerado com sucesso: conversao.json");
        } catch (Exception e) {
            System.out.println("Erro ao gerar o arquivo JSON: " + e.getMessage());
        }
    }
}
