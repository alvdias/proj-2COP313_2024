package src.DAO;

import src.Aluno;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AlunoDAO extends GenericDAO<Aluno> {

    public void cadastrarAluno(Aluno aluno) {
        String sql = "INSERT INTO aluno (RA, nome, cpf, endereo) VALUES (?, ?, ?, ?)";
        inserir(sql, stmt -> {
            stmt.setString(1, aluno.getRA());
            stmt.setString(2, aluno.getNome());
            stmt.setString(3, aluno.getCPF());
            stmt.setString(4, aluno.getEnd());
        });
    }

    public Aluno buscarAluno(String ra) {
        String sql = "SELECT * FROM aluno WHERE RA = ?";
        return buscar(sql, stmt -> stmt.setString(1, ra), this::mapAluno);
    }

    public List<Aluno> buscarTodosAlunos() {
        String sql = "SELECT * FROM aluno ORDER BY RA";
        return buscarTodos(sql, this::mapAluno);
    }

    public void deletarAluno(String ra) {
        String sql = "DELETE FROM aluno WHERE RA = ?";
        deletar(sql, stmt -> stmt.setString(1, ra));
    }

    // Mapeador para transformar ResultSet em objeto Aluno
    private Aluno mapAluno(ResultSet rs) throws SQLException {
        Aluno aluno = new Aluno();
        aluno.setRA(rs.getString("RA"));
        aluno.setNome(rs.getString("nome"));
        aluno.setCPF(rs.getString("cpf"));
        aluno.setEnd(rs.getString("endereo"));
        aluno.setId(rs.getInt("id"));
        return aluno;
    }

    public Optional<Aluno> buscarAlunoPorRA(String ra) {
        String sql = "SELECT * FROM aluno WHERE ra = ?";
        return Optional.ofNullable(buscar(sql, stmt -> stmt.setString(1, ra), this::mapearAluno));
    }

    private Aluno mapearAluno(ResultSet rs) throws SQLException {
        Aluno aluno = new Aluno();
        aluno.setRA(rs.getString("ra"));
        aluno.setNome(rs.getString("nome"));
        aluno.setCPF(rs.getString("cpf"));
        aluno.setEnd(rs.getString("endereo"));
        aluno.setId(rs.getInt("id"));
        return aluno;
    }
}
