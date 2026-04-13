package estacionamento.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import estacionamento.controle.EstacionamentoException;
import estacionamento.negocio.movimentacao;
import estacionamento.negocio.vaga;
import estacionamento.negocio.veiculo;
import estacionamento.utilitario.EstacionamentoUtil;

public class DAOEstacionamento {

    /**
     * Armazena os dados da movimentação
     * 
     * @param movimentacao
     *                     instancia de movimentacao
     * @throws EstacionamentoException se houver erro de registro
     */

    public void criar(movimentacao movimentacao) throws EstacionamentoException {
        String cmd1 = EstacionamentoUtil.get("insertMov");
        String cmd2 = EstacionamentoUtil.get("atualizavaga");

        Connection conexao = null;
        PreparedStatement stmt = null;
        try {
            conexao = getConnection();
            conexao.setAutoCommit(false);

            stmt = conexao.prepareStatement(cmd1);
            stmt.setString(1, movimentacao.getveiculo().getPlaca());
            stmt.setString(2, movimentacao.getveiculo().getMarca());
            stmt.setString(3, movimentacao.getveiculo().getModelo());
            stmt.setString(4, movimentacao.getveiculo().getCor());
            stmt.setString(5, EstacionamentoUtil.getDataAsString(movimentacao.getDataHoraEntrada()));
            stmt.execute();
            stmt.close(); 

            stmt = conexao.prepareStatement(cmd2);
            stmt.setInt(1, vaga.ocupadas() + 1);
            stmt.execute();

            conexao.commit();
        } catch (SQLException e) {
            if (conexao != null) {
                try { conexao.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            e.printStackTrace();
            throw new EstacionamentoException("Erro ao registrar veículo no banco de dados: " + e.getMessage());
        } finally {
            if (stmt != null) { try { stmt.close(); } catch (SQLException e) { e.printStackTrace(); } }
            closeConnection(conexao);
        }
    }

    public void atualizar(movimentacao movimentacao) throws EstacionamentoException {
      String cmd1 = EstacionamentoUtil.get("updateMov");
        String cmd2 = EstacionamentoUtil.get("atualizavaga");

        Connection conexao = null;
        PreparedStatement stmt = null;
        try {
            conexao = getConnection();
            conexao.setAutoCommit(false);

            stmt = conexao.prepareStatement(cmd1);
            stmt.setDouble(1, movimentacao.getValor());
            stmt.setString(2, EstacionamentoUtil.getDataAsString
                (movimentacao.getDataHoraEntrada()));
            stmt.setString(3, movimentacao.getveiculo().getPlaca());
            
            stmt.execute();
            stmt.close(); 

            stmt = conexao.prepareStatement(cmd2);
            stmt.setInt(1, vaga.ocupadas() - 1);
            stmt.execute();

            conexao.commit();
        } catch (SQLException e) {
            try { 
                e.printStackTrace();
                 conexao.rollback();
  throw new EstacionamentoException("Erro ao registrar veículo no banco de dados: " + e.getMessage());
   } catch (SQLException e1) {
    e1.printStackTrace();
}
        }
   
   }
    

    public movimentacao buscarMovimentacaoAberta(String placa) {
        String cmd = EstacionamentoUtil.get("getMovAberta");
    Connection conexao = null;
    movimentacao movimentacao = null;
        try {
            conexao = getConnection();
            PreparedStatement ps = conexao.prepareStatement(cmd);
            ps.setString(1, placa);
            ResultSet resultado = ps.executeQuery();

            if (resultado.next()){
                String rplaca = resultado.getString("placa");
                String rdataEntrada = resultado.getString("data_entrada");
                veiculo veiculo = new veiculo(rplaca);
                 movimentacao = new movimentacao(veiculo,
                     EstacionamentoUtil.getDate(rdataEntrada));
           
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            closeConnection(conexao);
        }
        return movimentacao;
    }

    public List<movimentacao> consultarMovimentacoes(LocalDateTime data) {
Connection conexao = null;
String cmd = EstacionamentoUtil.get("selectMovRelatorio");
List<movimentacao>movimentacoes = new ArrayList<>();

      try {
        conexao = getConnection();
        PreparedStatement ps = conexao.prepareStatement(cmd);
        ps.setString(1, data.toString());
        data = data.with(TemporalAdjusters.lastDayOfMonth());
        ps.setString(2, data.toString());

        ResultSet resultado = ps.executeQuery();
        while (resultado.next()){
            String placa = resultado.getString("placa");
            LocalDateTime entrada = EstacionamentoUtil.getDate
            (resultado.getString("data_entrada"));
            LocalDateTime saida = EstacionamentoUtil.getDate
            (resultado.getString("data_saida"));
            double valor = resultado.getDouble("valor");

            veiculo veiculo = new veiculo(placa);
            movimentacao movimentacao = new movimentacao(veiculo, entrada);
            movimentacao.setDataHoraSaida(saida);
            movimentacao.setValor(valor);

            movimentacoes.add(movimentacao);
            
        }

      } catch (SQLException e) {
        e.printStackTrace();
      }finally{
        closeConnection(conexao);
      }
        return movimentacoes;
    }

    public static Connection getConnection() throws SQLException {

        String url = EstacionamentoUtil.get("url");
        String usuario = EstacionamentoUtil.get("usuario");
        String senha = EstacionamentoUtil.get("senha");

        Connection conexao = DriverManager.getConnection(url, usuario, senha);

        return conexao;
    }

    public static void closeConnection(Connection conexao) {
        if (conexao != null) {
            try {
                conexao.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public int getOcupadas() {
       int ocupadas = 0;
        Connection conexao = null;
String cmd = EstacionamentoUtil.get("consultaOcupadas");
        try {
            conexao = getConnection();
            PreparedStatement ps = conexao.prepareStatement(cmd);

            ResultSet resultado = ps.executeQuery();
            if(resultado.next()){
                ocupadas = resultado.getInt("ocupadas");
            }
    } catch (SQLException e){
    e.printStackTrace();
    } finally{
        closeConnection(conexao);
    }
        return ocupadas;
 }
}
