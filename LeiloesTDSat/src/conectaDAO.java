import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conectaDAO {

    private static final String DB_NAME = "uc11";
    private static final String DB_HOST = "localhost";
    private static final String DB_PORT = "3306";
    private static final String URL = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME
            + "?useSSL=false&serverTimezone=America/Sao_Paulo&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = "Magalhaes@1990"; 
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    public static Connection conectar() throws SQLException {
        Connection conn = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("Erro: Driver JDBC do MySQL não encontrado! Verifique se o JAR foi adicionado ao projeto.");
            e.printStackTrace();
            throw new SQLException("Driver MySQL não encontrado: " + e.getMessage(), e);
        }
        return conn;
    }

    public static void desconectar(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexão com o MySQL: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}