package src;

public class Area {
    String nome;
    String descricao;

    public Area() {
    }

    public Area(String descricao) {
        this.descricao = descricao;
        this.nome = descricao;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}