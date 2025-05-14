public class NaoConformidade {
    private String itemRelacionado;
    private String descricao;
    private String responsavel;
    private String prazo;
    private boolean resolvido;

    public NaoConformidade(String itemRelacionado, String descricao, String responsavel, String prazo) {
        this.itemRelacionado = itemRelacionado;
        this.descricao = descricao;
        this.responsavel = responsavel;
        this.prazo = prazo;
        this.resolvido = false;
    }

    public void resolver() {
        this.resolvido = true;
    }

    public boolean isResolvido() {
        return resolvido;
    }

    public void comunicar() {
        System.out.println("⚠ Comunicação de NC:");
        System.out.println("Item: " + itemRelacionado);
        System.out.println("Descrição: " + descricao);
        System.out.println("Responsável: " + responsavel);
        System.out.println("Prazo: " + prazo);
        System.out.println("Status: " + (resolvido ? "Resolvido" : "Pendente"));
        System.out.println("-------------------------");
    }

    public String toLinha() {
        return itemRelacionado + ";" + descricao + ";" + responsavel + ";" + prazo + ";" + resolvido;
    }

    public static NaoConformidade fromLinha(String linha) {
        String[] partes = linha.split(";");
        NaoConformidade nc = new NaoConformidade(partes[0], partes[1], partes[2], partes[3]);
        if (Boolean.parseBoolean(partes[4])) {
            nc.resolver();
        }
        return nc;
    }
}
