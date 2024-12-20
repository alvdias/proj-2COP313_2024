package src.DAO;

import src.Autor;
import src.Livro;
import src.Titulo;
import src.Area;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class LivroDAO extends GenericDAO<Livro> {

    public void cadastrarLivro(Livro livro, Autor autor) {
        String sqlAutor = "INSERT INTO autor(nome, sobrenome, titulacao) VALUES (?, ?, ?)";
        String sqlLivro = "INSERT INTO livro(isbn, edicao, ano, prazo, areanome, autor_fk) VALUES (?, ?, ?, ?, ?, ?)";

        int autorId = inserirReturn(sqlAutor, stmt -> {
            stmt.setString(1, autor.getNome());
            stmt.setString(2, autor.getSobrenome());
            stmt.setString(3, autor.getTitulacao());
        });

        if (autorId > 0) {
            inserir(sqlLivro, stmt -> {
                stmt.setString(1, livro.getISBN());
                stmt.setInt(2, livro.getEdicao());
                stmt.setInt(3, livro.getAno());
                stmt.setInt(4, livro.getPrazo());
                stmt.setString(5, livro.getArea());
                stmt.setInt(6, autorId);  // Passando o ID do autor gerado
            });
        }
    }

    public List<Livro> buscarTodosLivros() {
        String sql = "SELECT * FROM livro l INNER JOIN autor a ON a.id = l.autor_fk";
        return buscarTodos(sql, this::mapLivro);
    }

    public List<Livro> buscarLivroPorCodigo(int livro) {
        String sql = "SELECT * FROM livro l INNER JOIN autor a ON a.id = l.autor_fk where l.id = " + livro;
        return buscarTodos(sql, this::mapLivro);
    }

    private Livro mapLivro(ResultSet rs) throws SQLException {
        Titulo titulo = new Titulo();
        titulo.setISBN(rs.getString("isbn"));
        titulo.setEdicao(rs.getInt("edicao"));
        titulo.setAno(rs.getInt("ano"));

        Autor autor = new Autor();
        autor.setNome(rs.getString("nome"));
        autor.setSobrenome(rs.getString("sobrenome"));
        autor.setTitulacao(rs.getString("titulacao"));

        Livro livro = new Livro();
        livro.setTitulo(titulo);
        livro.setAutor(autor);
        livro.setPrazo(rs.getInt("prazo"));
        livro.setExemplarBiblioteca(rs.getBoolean("exemplarBiblioteca"));
        livro.setCodigoLivro(rs.getInt("id"));
        ;

        Area area = new Area();
        area.setNome(rs.getString("areanome"));

        livro.setArea(area);

        return livro;
    }
}
