package src;

public class Autor {
    String nome;
    String sobrenome;
    String titulacao;

    public Autor() {
        this.nome = "";
    }

    public Autor(String nome, String sobrenome, String titulacao) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.titulacao = titulacao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getTitulacao() {
        return titulacao;
    }

    public void setTitulacao(String titulacao) {
        this.titulacao = titulacao;
    }

}