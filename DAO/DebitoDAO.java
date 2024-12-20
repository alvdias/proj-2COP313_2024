package src.DAO;

import src.Debito;
import src.Emprestimo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DebitoDAO extends GenericDAO<Debito> {

    public Optional<Debito> buscarDebitoPorRA(String ra) {
        String sql = "select * from debito d\n" +
                "inner join aluno a\n" +
                "on a.id = d.aluno_fk\n" +
                "where a.ra = ?";
        return Optional.ofNullable(buscar(sql, stmt -> stmt.setString(1, ra), this::mapearDebito));
    }

    private Debito mapearDebito(ResultSet rs) throws SQLException {
        Debito deb = new Debito();
        deb.setRA(rs.getString("ra"));
        deb.setValor(rs.getDouble("valor"));

        return deb;
    }

    public Debito inserirDebito(List<Emprestimo> emp) {
        String sql = "INSERT INTO debito (aluno_fk, valor) VALUES (?, ?)";
        inserir(sql, stmt -> {
            stmt.setInt(1, emp.get(0).getAlunoID());
            stmt.setInt(2, emp.get(0).getDiasAtrasado());
        });
        return null;
    }

}
