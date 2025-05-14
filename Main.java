import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Auditoria auditoria = new Auditoria();
        auditoria.carregarDados();

        int opcao;
        do {
            System.out.println("\n--- MENU DE AUDITORIA ---");
            System.out.println("1 - Adicionar Item");
            System.out.println("2 - Marcar Conformidade");
            System.out.println("3 - Registrar NC");
            System.out.println("4 - Exibir Checklist");
            System.out.println("5 - Exibir NCs");
            System.out.println("6 - Calcular Aderência");
            System.out.println("7 - Resolver NC e Calcular Aderência");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine(); // limpa buffer

            switch (opcao) {
                case 1 -> {
                    System.out.print("Descrição do item: ");
                    String descricao = scanner.nextLine();
                    auditoria.adicionarItem(descricao);
                }
                case 2 -> auditoria.marcarConformeManual(scanner);
                case 3 -> auditoria.registrarNCManual(scanner);
                case 4 -> auditoria.exibirChecklist();
                case 5 -> auditoria.exibirNCs();
                case 6 -> auditoria.calcularAderencia();
                case 7 -> {
                    auditoria.resolverNCManual(scanner);
                    auditoria.calcularAderencia();
                }
                case 0 -> System.out.println("Encerrando.");
                default -> System.out.println("Opção inválida.");
            }

        } while (opcao != 0);

        scanner.close();
    }
}
