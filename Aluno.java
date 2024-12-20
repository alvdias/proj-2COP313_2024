package src;

import java.util.List;

public class Aluno {
    String RA;
    String nome;
    String cpf;
    String end;
    int id;

    public Aluno() {
        this.nome = "";
        this.cpf = "";
        this.RA = "";
        this.end = "";
        this.id = 0;
    }

    public Aluno(String nome, String cpf, String RA, String end) {
        this.RA = RA;
        this.cpf = cpf;
        this.nome = nome;
        this.end = end;

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCPF() {
        return cpf;
    }

    public void setCPF(String cpf) {
        this.cpf = cpf;
    }

    public String getRA() {
        return RA;
    }

    public void setRA(String RA) {
        this.RA = RA;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public boolean verificaDebito() {       //instancia um objeto d�bito
        Debito debito = new Debito();
        return debito.verificaDebito(this.RA);
    }

    public Emprestimo emprestar(List<Livro> livros) {
        Emprestimo e = new Emprestimo();
        return e.emprestar(livros);
    }
}