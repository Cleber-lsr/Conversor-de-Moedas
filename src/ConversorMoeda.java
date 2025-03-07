import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.Gson;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ConversorMoeda {

    private static final String API_KEY = "97118a77214f10477009b886"; // Substitua pela sua chave da API
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/";

    public static double obterTaxaCambio(String moedaBase, String moedaDestino) throws Exception {
        String urlString = API_URL + moedaBase;
        URL url = new URL(urlString);
        HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
        conexao.setRequestMethod("GET");

        InputStreamReader reader = new InputStreamReader(conexao.getInputStream());
        JsonObject respostaJson = JsonParser.parseReader(reader).getAsJsonObject();
        return respostaJson.getAsJsonObject("conversion_rates").get(moedaDestino).getAsDouble();
    }

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

    public static void mostrarMenu() {
        System.out.println("=== Conversor de Moedas ===");
        System.out.println("1. Converter Moeda");
        System.out.println("2. Sair");
        System.out.print("Escolha uma opção: ");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            mostrarMenu();
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    scanner.nextLine(); // Limpar o buffer
                    System.out.print("Digite a moeda base (ex: USD): ");
                    String moedaBase = scanner.nextLine().toUpperCase();

                    System.out.print("Digite a moeda de destino (ex: EUR): ");
                    String moedaDestino = scanner.nextLine().toUpperCase();

                    System.out.print("Digite a quantidade a converter: ");
                    double quantidade = scanner.nextDouble();

                    try {
                        double taxaCambio = obterTaxaCambio(moedaBase, moedaDestino);
                        double valorConvertido = quantidade * taxaCambio;
                        System.out.printf("%.2f %s = %.2f %s\n", quantidade, moedaBase, valorConvertido, moedaDestino);

                        // Gerar arquivo JSON com os dados da conversão
                        gerarArquivoJson(moedaBase, moedaDestino, quantidade, taxaCambio, valorConvertido);
                    } catch (Exception e) {
                        System.out.println("Erro ao obter a taxa de câmbio: " + e.getMessage());
                    }
                    break;

                case 2:
                    System.out.println("Saindo do programa...");
                    break;

                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }

        } while (opcao != 2);

        scanner.close();
    }
}