package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoMySQL {

    public static String status = "Não conectou...";

    public ConexaoMySQL() {
    }

    public static Connection getInstance() {
        Connection connection = null;
        try {
            String driverName = "com.mysql.cj.jdbc.Driver";
            Class.forName(driverName);

            String serverName = ""; // caminho do servidor do banco de dados
            String mydatabase = ""; // nome do seu banco de dados
            String url = "jdbc:mysql://" + serverName + "/" + mydatabase;
            String username = ""; // nome de um usuário de seu banco de dados
            String password = ""; // sua senha de acesso
            connection = DriverManager.getConnection(url, username, password);

            if (connection != null) {
                status = "Conectado com sucesso!";
            } else {
                status = "STATUS--->Não foi possível realizar conexão";
            }
            return connection;
        } catch (ClassNotFoundException e) {
            status = "O driver especificado não foi encontrado.";
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            status = "Não foi possível conectar ao Banco de Dados.";
            e.printStackTrace();
            return null;
        }
    }

    public static String statusConection() {
        return status;
    }

    public static boolean FecharConexao() {
        try {
            if (ConexaoMySQL.getInstance() != null) {
                ConexaoMySQL.getInstance().close();
                return true;
            }
            return false;
        } catch (SQLException e) {
            return false;
        }
    }

    public static Connection ReiniciarConexao() {
        FecharConexao();
        return ConexaoMySQL.getInstance();
    }
}
