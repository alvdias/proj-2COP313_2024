package src.DAO;

public class DevolucaoDAO extends GenericDAO {

    public void cadastrarDevolucao(String ra) {
        String sql = "UPDATE emprestimo e\n" +
                "SET devolvido = TRUE\n" +
                "FROM aluno a\n" +
                "WHERE e.aluno_fk = a.id\n" +
                "  AND a.ra = ?";
        alterar(sql, stmt -> stmt.setString(1, ra));
    }

}
