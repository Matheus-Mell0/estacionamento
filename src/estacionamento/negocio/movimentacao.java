package estacionamento.negocio;

import java.time.LocalDateTime;
/**
 * representa o fluxo do veiculo dentro do estacionamento, ou seja
 * ele contem as informações de entrada e saida do veiculo e o valor pago na estada.
 */

public class movimentacao {
private veiculo veiculo;
private LocalDateTime dataHoraEntrada;
private LocalDateTime dataHoraSaida;
private double valor;

public movimentacao(veiculo veiculo, LocalDateTime dataEntrada){
    this.veiculo =  veiculo;
    this.dataHoraEntrada = dataEntrada;
}

public veiculo getveiculo(){
    return veiculo;
}

public void setveiculo(veiculo veiculo) {
    this.veiculo = veiculo;
}

public LocalDateTime getDataHoraEntrada(){
    return dataHoraEntrada;
}

public void setDataHoraEntrada(LocalDateTime dataHoraEntrada) {
    this.dataHoraEntrada = dataHoraEntrada;
}

public LocalDateTime getDataHoraSaida() {
    return dataHoraSaida;
}

public void setDataHoraSaida(LocalDateTime dataHoraSaida) {
    this.dataHoraSaida = dataHoraSaida;
}

public double getValor() {
    return valor;
}

public void setValor(double valor) {
    this.valor = valor;
}

}
