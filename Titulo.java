package src;

public class Titulo {
    Area area;
    String isbn;
    int edicao;
    int ano;
    int prazo;

    public Titulo() {

    }

    public Titulo(String isbn, int edicao, int ano) {
        this.isbn = isbn;
        this.edicao = edicao;
        this.ano = ano;
    }

    public int getPrazo() {
        return prazo;
    }

    public void setPrazo(int prazo) {
        this.prazo = prazo;
    }

    public void setISBN(String isbn) {
        this.isbn = isbn;
    }

    public void setEdicao(int edicao) {
        this.edicao = edicao;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

}