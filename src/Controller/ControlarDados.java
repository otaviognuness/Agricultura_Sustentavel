package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Connection.ConexaoMySQL;
import Model.DadosSafra;
import java.sql.Date;
import java.sql.Statement;

public class ControlarDados {

    public void atualizarPesticidaEFertilizante(int idSafra, String novosFertilizantes, String novoPesticida) {
        Connection conn = ConexaoMySQL.getInstance();

        try {
            conn.setAutoCommit(false);

            String sqlSelectAgroquimicos = "SELECT idAgroquimicos FROM safra WHERE idSafra = ?";
            PreparedStatement stmtSelectAgroquimicos = conn.prepareStatement(sqlSelectAgroquimicos);
            stmtSelectAgroquimicos.setInt(1, idSafra);
            ResultSet rsAgroquimicos = stmtSelectAgroquimicos.executeQuery();
            if (!rsAgroquimicos.next()) {
                throw new RuntimeException("Safra não encontrada com ID: " + idSafra);
            }
            int idAgroquimicos = rsAgroquimicos.getInt("idAgroquimicos");
            stmtSelectAgroquimicos.close();

            String sqlUpdateAgroquimicos = "UPDATE agroquimicos SET fertilizantes = ?, pesticida = ? WHERE idAgroquimicos = ?";
            PreparedStatement stmtUpdateAgroquimicos = conn.prepareStatement(sqlUpdateAgroquimicos);
            stmtUpdateAgroquimicos.setString(1, novosFertilizantes);
            stmtUpdateAgroquimicos.setString(2, novoPesticida);
            stmtUpdateAgroquimicos.setInt(3, idAgroquimicos);
            stmtUpdateAgroquimicos.executeUpdate();
            stmtUpdateAgroquimicos.close();

            conn.commit();
            conn.close();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException rollbackException) {
                throw new RuntimeException(rollbackException);
            }
            throw new RuntimeException("Erro ao atualizar fertilizantes e pesticida: " + e.getMessage(), e);
        }
    }

    public void adicionarAgrotoxicos(String fertilizantes, String pesticida) {
        Connection conn = ConexaoMySQL.getInstance();

        try {
            String sql = "INSERT INTO agroquimicos (fertilizantes, pesticida) VALUES (?, ?)";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, fertilizantes);
            stmt.setString(2, pesticida);
            stmt.execute();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void adicionarDados(DadosSafra safra) {
        Connection conn = ConexaoMySQL.getInstance();

        try {
            conn.setAutoCommit(false);

            String sqlInsertAgroquimicos = "INSERT INTO agroquimicos (fertilizantes, pesticida) VALUES (?, ?)";
            PreparedStatement stmtInsertAgroquimicos = conn.prepareStatement(sqlInsertAgroquimicos, Statement.RETURN_GENERATED_KEYS);
            stmtInsertAgroquimicos.setString(1, safra.getFertilizantes());
            stmtInsertAgroquimicos.setString(2, safra.getPesticida());
            stmtInsertAgroquimicos.executeUpdate();

            ResultSet rs = stmtInsertAgroquimicos.getGeneratedKeys();
            rs.next();
            int idAgroquimicos = rs.getInt(1);
            stmtInsertAgroquimicos.close();

            String sqlInsertSafra = "INSERT INTO safra (nome, dataPlantio, dataColheita, variedaCultivada, areaPlantada, produtividade, condicaoClimatica, idAgroquimicos) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmtInsertSafra = conn.prepareStatement(sqlInsertSafra);
            stmtInsertSafra.setString(1, safra.getNome());
            stmtInsertSafra.setDate(2, Date.valueOf(safra.getDataPlantio()));
            stmtInsertSafra.setDate(3, Date.valueOf(safra.getDataColheita()));
            stmtInsertSafra.setString(4, safra.getVariedaCultivada());
            stmtInsertSafra.setDouble(5, safra.getAreaPlantada());
            stmtInsertSafra.setDouble(6, safra.getProdutividade());
            stmtInsertSafra.setString(7, safra.getCondicaoClimatica());
            stmtInsertSafra.setInt(8, idAgroquimicos);

            stmtInsertSafra.executeUpdate();
            stmtInsertSafra.close();

            conn.commit();
            conn.close();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException rollbackException) {
                throw new RuntimeException(rollbackException);
            }
            throw new RuntimeException(e);
        }
    }

    public void removerDados(int idSafra) {
        Connection conn = ConexaoMySQL.getInstance();

        try {
            conn.setAutoCommit(false);

            String sqlSelectAgroquimicos = "SELECT idAgroquimicos FROM safra WHERE idSafra = ?";
            PreparedStatement stmtSelectAgroquimicos = conn.prepareStatement(sqlSelectAgroquimicos);
            stmtSelectAgroquimicos.setInt(1, idSafra);
            ResultSet rsAgroquimicos = stmtSelectAgroquimicos.executeQuery();
            if (!rsAgroquimicos.next()) {
                throw new RuntimeException("Safra não encontrada com ID: " + idSafra);
            }
            int idAgroquimicos = rsAgroquimicos.getInt("idAgroquimicos");
            stmtSelectAgroquimicos.close();

            String sqlSafra = "DELETE FROM safra WHERE idSafra = ?";
            PreparedStatement stmtSafra = conn.prepareStatement(sqlSafra);
            stmtSafra.setInt(1, idSafra);
            stmtSafra.execute();
            stmtSafra.close();

            String sqlAgroquimicos = "DELETE FROM agroquimicos WHERE idAgroquimicos = ?";
            PreparedStatement stmtAgroquimicos = conn.prepareStatement(sqlAgroquimicos);
            stmtAgroquimicos.setInt(1, idAgroquimicos);
            stmtAgroquimicos.execute();
            stmtAgroquimicos.close();

            conn.commit();
            conn.close();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException rollbackException) {
                throw new RuntimeException(rollbackException);
            }
            throw new RuntimeException(e);
        }
    }

    public void atualizarDados(int idAtualizar, DadosSafra safra) {
        Connection conn = ConexaoMySQL.getInstance();

        try {
            conn.setAutoCommit(false);

            String sqlSelectAgroquimicos = "SELECT idAgroquimicos FROM safra WHERE idSafra = ?";
            PreparedStatement stmtSelectAgroquimicos = conn.prepareStatement(sqlSelectAgroquimicos);
            stmtSelectAgroquimicos.setInt(1, idAtualizar);
            ResultSet rsAgroquimicos = stmtSelectAgroquimicos.executeQuery();
            rsAgroquimicos.next();
            int idAgroquimicos = rsAgroquimicos.getInt("idAgroquimicos");

            String sqlSelectNewAgroquimicos = "SELECT * FROM agroquimicos WHERE idAgroquimicos = ?";
            PreparedStatement stmtSelectNewAgroquimicos = conn.prepareStatement(sqlSelectNewAgroquimicos);
            stmtSelectNewAgroquimicos.setInt(1, idAgroquimicos);
            ResultSet rsNewAgroquimicos = stmtSelectNewAgroquimicos.executeQuery();
            rsNewAgroquimicos.next();
            String oldFertilizantes = rsNewAgroquimicos.getString("fertilizantes");
            String oldPesticida = rsNewAgroquimicos.getString("pesticida");
            String newFertilizantes = safra.getFertilizantes();
            String newPesticida = safra.getPesticida();

            if (!oldFertilizantes.equals(newFertilizantes) || !oldPesticida.equals(newPesticida)) {
                String sqlUpdateAgroquimicos = "UPDATE agroquimicos SET fertilizantes = ?, pesticida = ? WHERE idAgroquimicos = ?";
                PreparedStatement stmtUpdateAgroquimicos = conn.prepareStatement(sqlUpdateAgroquimicos);
                stmtUpdateAgroquimicos.setString(1, newFertilizantes);
                stmtUpdateAgroquimicos.setString(2, newPesticida);
                stmtUpdateAgroquimicos.setInt(3, idAgroquimicos);
                stmtUpdateAgroquimicos.executeUpdate();
            }

            String sql = "UPDATE safra SET nome = ?, dataPlantio = ?, dataColheita = ?, variedaCultivada = ?, areaPlantada = ?, produtividade = ?, condicaoClimatica = ? WHERE idSafra = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, safra.getNome());
            stmt.setDate(2, Date.valueOf(safra.getDataPlantio()));
            stmt.setDate(3, Date.valueOf(safra.getDataColheita()));
            stmt.setString(4, safra.getVariedaCultivada());
            stmt.setDouble(5, safra.getAreaPlantada());
            stmt.setDouble(6, safra.getProdutividade());
            stmt.setString(7, safra.getCondicaoClimatica());
            stmt.setInt(8, idAtualizar);
            stmt.executeUpdate();

            conn.commit();
            conn.close();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException rollbackException) {
                throw new RuntimeException(rollbackException);
            }
            throw new RuntimeException(e);
        }
    }


    public List<DadosSafra> listarPlantacoes() {
        String sql = "SELECT s.*, a.fertilizantes, a.pesticida FROM safra s JOIN agroquimicos a ON s.idAgroquimicos = a.idAgroquimicos";
        List<DadosSafra> plantacoes = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rset = null;

        try {
            conn = ConexaoMySQL.getInstance();
            if (conn == null) {
                System.out.println("Falha ao obter a conexão com o banco de dados.");
                return plantacoes;
            }
            pstm = conn.prepareStatement(sql);
            rset = pstm.executeQuery();

            while (rset.next()) {
                DadosSafra plantacao = new DadosSafra();
                plantacao.setId(rset.getInt("idSafra"));
                plantacao.setNome(rset.getString("nome"));
                plantacao.setDataPlantio(rset.getDate("dataPlantio").toString());
                plantacao.setDataColheita(rset.getDate("dataColheita").toString());
                plantacao.setVariedaCultivada(rset.getString("variedaCultivada"));
                plantacao.setAreaPlantada(rset.getDouble("areaPlantada"));
                plantacao.setProdutividade(rset.getDouble("produtividade"));
                plantacao.setCondicaoClimatica(rset.getString("condicaoClimatica"));
                plantacao.setPesticida(rset.getString("pesticida"));
                plantacao.setFertilizantes(rset.getString("fertilizantes"));
                plantacoes.add(plantacao);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rset != null) {
                    rset.close();
                }
                if (pstm != null) {
                    pstm.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return plantacoes;
    }

    public DadosSafra listarDadosSafraPorID(int idSafra) {
        Connection conn = ConexaoMySQL.getInstance();
        DadosSafra safra = null;
        try {
            String sql = "SELECT s.*, a.fertilizantes, a.pesticida FROM safra s JOIN agroquimicos a ON s.idAgroquimicos = a.idAgroquimicos WHERE s.idSafra = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idSafra);
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                safra = new DadosSafra();
                safra.setId(resultado.getInt("idSafra"));
                safra.setNome(resultado.getString("nome"));
                safra.setDataPlantio(resultado.getDate("dataPlantio").toString());
                safra.setDataColheita(resultado.getDate("dataColheita").toString());
                safra.setVariedaCultivada(resultado.getString("variedaCultivada"));
                safra.setAreaPlantada(resultado.getDouble("areaPlantada"));
                safra.setProdutividade(resultado.getDouble("produtividade"));
                safra.setCondicaoClimatica(resultado.getString("condicaoClimatica"));
                safra.setFertilizantes(resultado.getString("fertilizantes"));
                safra.setPesticida(resultado.getString("pesticida"));
            }
            resultado.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return safra;
    }


}