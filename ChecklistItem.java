public class ChecklistItem {
    private String descricao;
    private String status; // "Adequado", "Não Adequado", "Não se Aplica"

    public ChecklistItem(String descricao) {
        this.descricao = descricao;
        this.status = "Não Avaliado"; // valor inicial
    }

    public String getDescricao() {
        return descricao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void adequar() {
        this.status = "Adequado";
    }


}
