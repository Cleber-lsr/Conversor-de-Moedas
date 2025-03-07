import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao = 0;

        System.out.println("=== Conversor de Moedas ===");
        System.out.println("1. Converter Moeda");
        System.out.println("2. Sair");
        System.out.print("Escolha uma opção: ");

        while (true){
            opcao = scanner.nextInt();

            if (opcao == 1) {

                scanner.nextLine();
                System.out.print("Digite a moeda base (ex: BRL): ");
                String moedaBase = scanner.nextLine().toUpperCase();

                System.out.print("Digite a moeda de destino dentre as seguintes opções:\n" +
                        "ARS - Peso argentino\n" +
                        "BOB - Boliviano boliviano\n" +
                        "BRL - Real brasileiro\n" +
                        "CLP - Peso chileno\n" +
                        "COP - Peso colombiano\n" +
                        "USD - Dólar americano\n");
                String moedaDestino = scanner.nextLine().toUpperCase();

                System.out.print("Digite a quantidade a converter: ");
                double quantidade = scanner.nextDouble();

                try{
                    double taxaCambio = ObterTaxaCambio.obterTexaCambio(moedaBase, moedaDestino);
                    double valorConvertido = quantidade * taxaCambio;

                    System.out.println(quantidade + " " + moedaBase + " = " + valorConvertido + " " + moedaDestino);

                    GerarAquivoJSON.gerarArquivoJson(moedaBase, moedaDestino, quantidade, taxaCambio, valorConvertido);

                } catch (Exception e) {
                    System.out.println("Erro ao obter a taxa de câmbio " + e.getMessage());
                } break;
            } else if (opcao == 2) {
                System.out.println("Finalizando programa...");
                break;
            } else {
                System.out.println("Opção inválida!");
                break;
            }
        }
    }
}