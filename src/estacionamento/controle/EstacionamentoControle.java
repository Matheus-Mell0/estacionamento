package estacionamento.controle;

import java.time.LocalDateTime;
import java.util.List;

import estacionamento.negocio.movimentacao;
import estacionamento.negocio.vaga;
import estacionamento.negocio.veiculo;
import estacionamento.persistencia.DAOEstacionamento;
import estacionamento.utilitario.EstacionamentoUtil;

public class EstacionamentoControle {

    public void processarEntrada(String placa, String marca, String modelo, String cor)

            throws EstacionamentoException, VeiculoException {
        // verificar se o estacionamento esta cheio
        if (!vaga.temVagaLivre()) {
            throw new EstacionamentoException("Estacionamento lotado!");
        }
        // verificar o padrão de string de placa
        if (!EstacionamentoUtil.validarPadraoPlaca(placa)) {
            throw new VeiculoException("Placa informada inválida!");

        }
        // criar uma instancia do veiculo
        veiculo veiculo = new veiculo(placa, marca, modelo, cor);
        // criar a movimentação vinculado o veiculo e com data de entrada corrente
        movimentacao movimentacao = new movimentacao(veiculo, LocalDateTime.now());
        // registrar na base de dados a informação
        DAOEstacionamento dao = new DAOEstacionamento();
        dao.criar(movimentacao);
        // atualizar o numero de vagas ocupadas
        vaga.entrou();
        // fim
    }

    public movimentacao processarSaida(String placa) throws VeiculoException, EstacionamentoException {

        if (!EstacionamentoUtil.validarPadraoPlaca(placa))
            throw new VeiculoException("Placa informada inválida!");

        DAOEstacionamento dao = new DAOEstacionamento();
        movimentacao movimentacao = dao.buscarMovimentacaoAberta(placa);

        if (movimentacao == null)
            throw new EstacionamentoException("Veiculo não encontrado!");
        movimentacao.setDataHoraSaida(LocalDateTime.now());
        EstacionamentoUtil.calcularValorPago(movimentacao);

        dao.atualizar(movimentacao);
        vaga.saiu();

        return movimentacao;
    }

    public List<movimentacao> emitirRelatorio(LocalDateTime data) {
     DAOEstacionamento dao = new DAOEstacionamento();
        return dao.consultarMovimentacoes(data);
    }

    public int inicializarOcupadas() {
        DAOEstacionamento dao = new DAOEstacionamento();
        return dao.getOcupadas();

    }

}
