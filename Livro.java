package src;


import src.Service.LivroService;

import java.util.List;

public class Livro {

    private final LivroService livroService = new LivroService();

    boolean exemplarBiblioteca;
    boolean disponivel;
    int codigoLivro;
    public Titulo titulo;
    public Area area;
    public Autor autor;

    public Livro() {

    }

    public Livro(int codigo) {

        List<Livro> livro = livroService.buscarLivroPorCodigo(codigo);

        this.codigoLivro = livro.get(0).codigoLivro;
        this.exemplarBiblioteca = livro.get(0).exemplarBiblioteca;
        this.titulo = livro.get(0).titulo;
        this.autor = livro.get(0).autor;
        this.area = livro.get(0).area;
        this.disponivel = livro.get(0).disponivel;

    }

    public int getPrazo() {
        return titulo.getPrazo();
    }

    public String getISBN() {
        return titulo.isbn;
    }

    public int getEdicao() {
        return titulo.edicao;
    }

    public int getAno() {
        return titulo.ano;
    }

    public String getArea() {
        return area.nome;
    }

    public boolean verificaLivro(int cod) {
        return this.exemplarBiblioteca;
    }

    public void setTitulo(Titulo titulo) {
        this.titulo = titulo;
    }

    public void setPrazo(int prazo) {
        this.titulo.setPrazo(prazo);
    }

    public String getAutor() {
        return autor.nome;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public void setExemplarBiblioteca(boolean exemplarBiblioteca) {
        this.exemplarBiblioteca = exemplarBiblioteca;
    }

    public boolean getExemplarBiblioteca() {
        return exemplarBiblioteca;
    }

    public int getCodigoLivro() {
        return this.codigoLivro;
    }

    public void setCodigoLivro(int codigoLivro) {
        this.codigoLivro = codigoLivro;
    }

}