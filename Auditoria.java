import java.io.*;
import java.util.*;
import java.time.LocalDate;

public class Auditoria {
    private List<ChecklistItem> checklist = new ArrayList<>();
    private List<NaoConformidade> ncs = new ArrayList<>();

    private final String CHECKLIST_ARQ = "checklist.txt";
    private final String NCS_ARQ = "ncs.txt";

    public void salvarDados() {
        try {
            PrintWriter checklistWriter = new PrintWriter(CHECKLIST_ARQ);
            for (ChecklistItem item : checklist) {
                checklistWriter.println(item.getDescricao() + ";" + item.getStatus());
            }
            checklistWriter.close();

            PrintWriter ncWriter = new PrintWriter(NCS_ARQ);
            for (NaoConformidade nc : ncs) {
                ncWriter.println(nc.toLinha());
            }
            ncWriter.close();
        } catch (IOException e) {
            System.out.println("Erro ao salvar dados: " + e.getMessage());
        }
    }

    public void carregarDados() {
        try {
            BufferedReader checklistReader = new BufferedReader(new FileReader(CHECKLIST_ARQ));
            String linha;
            while ((linha = checklistReader.readLine()) != null) {
                String[] partes = linha.split(";");
                ChecklistItem item = new ChecklistItem(partes[0]);
                item.setStatus(partes[1]);
                checklist.add(item);
            }
            checklistReader.close();

            BufferedReader ncReader = new BufferedReader(new FileReader(NCS_ARQ));
            while ((linha = ncReader.readLine()) != null) {
                NaoConformidade nc = NaoConformidade.fromLinha(linha);
                ncs.add(nc);
            }
            ncReader.close();

        } catch (IOException e) {
            System.out.println("Arquivos não encontrados, iniciando novo.");
        }
    }

    public void adicionarItem(String descricao) {
        checklist.add(new ChecklistItem(descricao));
        salvarDados();
    }

    public void marcarConformeManual(Scanner scanner) {
        for (ChecklistItem item : checklist) {
            System.out.println("Item: " + item.getDescricao());
            System.out.println("1 - Adequado\n2 - Não Adequado\n3 - Não se Aplica");
            int escolha = scanner.nextInt();
            scanner.nextLine(); // Limpa buffer
            switch (escolha) {
                case 1 -> item.setStatus("Adequado");
                case 2 -> item.setStatus("Não Adequado");
                case 3 -> item.setStatus("Não se Aplica");
                default -> item.setStatus("Não Avaliado");
            }
        }
        salvarDados();
    }

    public void registrarNCManual(Scanner scanner) {
        exibirChecklist();
        System.out.print("Informe o índice do item com NC: ");
        int indice = scanner.nextInt();
        scanner.nextLine();

        String itemRelacionado = checklist.get(indice - 1).getDescricao();

        System.out.print("Descrição da NC: ");
        String descricao = scanner.nextLine();

        System.out.print("Responsável: ");
        String responsavel = scanner.nextLine();

        System.out.println("Classificação da NC:\n1 - Alta (2 dias)\n2 - Média (3 dias)\n3 - Baixa (5 dias)");
        int classif = scanner.nextInt();
        scanner.nextLine();

        int dias;
        if (classif == 1) dias = 2;
        else if (classif == 2) dias = 3;
        else dias = 5;

        String prazo = LocalDate.now().plusDays(dias).toString();

        registrarNC(itemRelacionado, descricao, responsavel, prazo);
    }

    public void registrarNC(String item, String descricao, String responsavel, String prazo) {
        NaoConformidade nc = new NaoConformidade(item, descricao, responsavel, prazo);
        nc.comunicar();
        ncs.add(nc);
        salvarDados();
    }

    public void exibirChecklist() {
        for (int i = 0; i < checklist.size(); i++) {
            ChecklistItem item = checklist.get(i);
            System.out.println((i + 1) + ". " + item.getDescricao() + " [" + item.getStatus() + "]");
        }
    }

    public void exibirNCs() {
        for (int i = 0; i < ncs.size(); i++) {
            System.out.println("NC #" + (i + 1));
            ncs.get(i).comunicar();
        }
    }

    public void calcularAderencia() {
        int total = checklist.size();
        int adequados = 0;
        for (ChecklistItem item : checklist) {
            if (item.getStatus().equals("Adequado")) {
                adequados++;
            }
        }
        float percentual = total > 0 ? (float) adequados / total * 100 : 0;
        System.out.printf("✔ Aderência: %.2f%% (%d/%d itens adequados)\n", percentual, adequados, total);
    }

    public void resolverNCManual(Scanner scanner) {
        exibirNCs();
        System.out.print("Informe o número da NC a ser resolvida: ");
        int i = scanner.nextInt();
        scanner.nextLine();
        if (i >= 1 && i <= ncs.size()) {
            ncs.get(i - 1).resolver();
            salvarDados();
            System.out.println("NC resolvida.");
        }
    }
}
