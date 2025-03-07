import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class ObterTaxaCambio {

    public static double  obterTexaCambio(String moedaBase, String moedaDestino) throws IOException {

        URI api_url = URI.create("https://v6.exchangerate-api.com/v6/97118a77214f10477009b886/latest/");

        String urlString = api_url + moedaBase;
        URL url = new URL(urlString);
        HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
        conexao.setRequestMethod("GET");


        InputStreamReader reader = new InputStreamReader(conexao.getInputStream());
        JsonObject respostaJson = JsonParser.parseReader(reader).getAsJsonObject();
        return respostaJson.getAsJsonObject("conversion_rates").get(moedaDestino).getAsDouble();
    }
}
