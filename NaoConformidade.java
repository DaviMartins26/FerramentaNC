public class NaoConformidade {
    private String itemRelacionado;
    private String descricao;
    private String responsavel;
    private String classificacao;
    private String prazo;
    private boolean resolvido;
        

    public NaoConformidade(String itemRelacionado, String descricao, String responsavel,String classificacao, String prazo) {
        this.itemRelacionado = itemRelacionado;
        this.descricao = descricao;
        this.responsavel = responsavel;
        this.classificacao = classificacao;
        this.prazo = prazo;
        this.resolvido = false;
    }

    public void resolver() {
        this.resolvido = true;

    }

    public boolean isResolvido() {
        return resolvido;
    }

    public String getItemRelacionado() {
        return itemRelacionado;
    }

    public void comunicar() {
        System.out.println("⚠ Comunicação de NC:");
        System.out.println("Item: " + itemRelacionado);
        System.out.println("Descrição: " + descricao);
        System.out.println("Responsável: " + responsavel);
        System.out.println("Classificação: "+ classificacao);
        System.out.println("Prazo: " + prazo);
        System.out.println("Status: " + (resolvido ? "Resolvido" : "Pendente"));
        System.out.println("-------------------------");
    }

    public String toLinha() {
        return itemRelacionado + ";" + descricao + ";" + responsavel + ";" + classificacao + ";" + prazo + ";" + resolvido;
    }

    public static NaoConformidade fromLinha(String linha) {
        String[] partes = linha.split(";");
        NaoConformidade nc = new NaoConformidade(partes[0], partes[1], partes[2], partes[3],partes[4]);
        if (Boolean.parseBoolean(partes[5])) {
            nc.resolver();
        }
        return nc;
    }
}
