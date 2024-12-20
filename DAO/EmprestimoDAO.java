package src.DAO;

import src.Emprestimo;
import src.Livro;
import src.Titulo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class EmprestimoDAO extends GenericDAO<Emprestimo> {

    public Optional<Emprestimo> cadastrarEmprestimo(List<Emprestimo> emp) {
        String sql = "INSERT INTO emprestimo (aluno_fk, livro_fk) VALUES (?, ?)";
        inserir(sql, stmt -> {
            stmt.setInt(1, emp.get(0).getAlunoID());
            stmt.setInt(2, emp.get(0).getLivroCodigo());
        });
        return null;
    }

    public List<Emprestimo> buscarTodosEmprestimosPorRa(String ra) {
        String sql = "select *, (e.dataemprestimo+ (l.prazo * INTERVAL '1 day')) as datadevolucao," +
                "case" +
                "\twhen NOW() > e.dataemprestimo THEN TRUE\n" +
                "\t\tELSE FALSE\n" +
                "\tend as atrasado," +
                "NOW()::DATE - (e.dataemprestimo+ (l.prazo * INTERVAL '1 day'))::DATE AS diasatrasado" +
                " from" +
                " emprestimo e inner join aluno a on a.id = e.aluno_fk " +
                "inner join livro l on l.id = e.livro_fk " +
                "where e.devolvido is FALSE AND a.ra ilike ?";
        return buscarComParametro(
                sql,
                stmt -> stmt.setString(1, ra),
                this::mapEmprestimo
        );
    }

    private Emprestimo mapEmprestimo(ResultSet rs) throws SQLException {
        Emprestimo emp = new Emprestimo();
        Livro livro = new Livro();
        Titulo titulo = new Titulo();

        titulo.setISBN(rs.getString("isbn"));
        livro.setTitulo(titulo);
        emp.setLivro(livro);

        emp.setDataEmprestimo(rs.getDate("dataEmprestimo"));
        emp.setDataPrevista(rs.getDate("datadevolucao"));
        emp.setAtrasado(rs.getBoolean("atrasado"));
        emp.setDiasAtrasado(rs.getInt("diasatrasado"));
        return emp;
    }
}
