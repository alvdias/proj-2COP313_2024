package src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/biblioteca"; // URL do banco
    private static final String USER = "app"; // Substitua pelo seu usuário do PostgreSQL
    private static final String PASSWORD = "1234"; // Substitua pela sua senha do PostgreSQL

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            throw new SQLException("Erro ao conectar com o banco de dados", e);
        }
    }
}
