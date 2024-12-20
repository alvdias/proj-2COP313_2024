package src.DAO;

import src.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class GenericDAO<T> {

    public void inserir(String sql, PreparedStatementSetter setter) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            setter.setValues(stmt);
            stmt.executeUpdate();
            System.out.println("Inserção realizada com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int inserirReturn(String sql, PreparedStatementSetter setter) {
        int idInserido = -1;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            setter.setValues(stmt);

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    idInserido = rs.getInt(1);
                }
            }

            System.out.println("Inserção realizada com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idInserido;  // Retorna o ID inserido, ou -1 se falhar
    }

    public void inserirComTransacao(TransactionCallback callback) {
        try (Connection conn = DBConnection.getConnection()) {
            //conn.setAutoCommit(false);  // Desativa o auto-commit, permitindo controle da transação

            try {
                callback.execute(conn);  // Executa a lógica dentro da transação

                conn.commit();  // Confirma as operações se tudo ocorrer bem
                System.out.println("Transação realizada com sucesso!");

            } catch (SQLException e) {
                //conn.rollback();  // Em caso de erro, realiza o rollback para desfazer as operações
                e.printStackTrace();
                System.err.println("Transação falhou, rollback realizado!");
            } finally {
                ///conn.setAutoCommit(true);  // Restaura o auto-commit
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public T buscar(String sql, PreparedStatementSetter setter, ResultSetMapper<T> mapper) {
        T resultado = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            setter.setValues(stmt);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                resultado = mapper.map(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultado;
    }

    // Metodo para buscar todos
    public List<T> buscarTodos(String sql, ResultSetMapper<T> mapper) {
        List<T> resultados = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                resultados.add(mapper.map(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultados;
    }


    public void deletar(String sql, PreparedStatementSetter setter) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            setter.setValues(stmt);
            stmt.executeUpdate();
            System.out.println("Exclusão realizada com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<T> buscarComParametro(String sql, PreparedStatementSetter setter, ResultSetMapper<T> mapper) {
        List<T> resultados = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Define os valores do parâmetro
            setter.setValues(stmt);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    resultados.add(mapper.map(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultados;
    }

    public void alterar(String sql, PreparedStatementSetter setter) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            setter.setValues(stmt);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Atualização realizada com sucesso!");
            } else {
                System.out.println("Nenhum registro foi atualizado.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public interface PreparedStatementSetter {
        void setValues(PreparedStatement stmt) throws SQLException;
    }

    public interface ResultSetMapper<T> {
        T map(ResultSet rs) throws SQLException;
    }

    public interface TransactionCallback {
        void execute(Connection conn) throws SQLException;
    }
}
