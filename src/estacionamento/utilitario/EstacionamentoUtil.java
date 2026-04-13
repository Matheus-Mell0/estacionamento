package estacionamento.utilitario;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import estacionamento.negocio.Tarifario;
import estacionamento.negocio.movimentacao;
/**
 * representa uma classe de apoio as demais do sistema.
 */
public class EstacionamentoUtil {
    /**
     * validar a placa com o padrao nacional LLL_NNNN
     * L = letra
     * N = numero
     * @param placa placa do veiculo 
     * @return true se atender o padrao e false senao
     */
public static boolean validarPadraoPlaca(String placa){
    String padrao = "[A-Z][A-Z][A-Z]-\\d\\d\\d\\d";
    Pattern p = Pattern.compile(padrao);
    Matcher m = p.matcher(placa);

    return m.matches();
}
/**
 * o calculo do valor da estada do veiculo baseado no tarifario
 * e na hora e saida do veiculo
 * 
 * altera a propia instancia do parametro
 * 
 * @param propiedade
 * @return valor associado a propiedade
 */
public void calcularPagamento(movimentacao movimentacao){

}
public static String get(String propiedade) {
Properties prop = new Properties();
String valor = null;
try {
    prop.load(EstacionamentoUtil.class.getResourceAsStream("/recursos/configuration.txt"));
    valor = prop.getProperty(propiedade);

} catch (IOException e) {
assert false : "Configuração não carregada";
}
    return valor;
  
  
}
public static String getDataAsString(LocalDateTime dataHoraEntrada){
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    return dataHoraEntrada.format(formatter);
}
public static void calcularValorPago(movimentacao movimentacao) {
    LocalDateTime inicio = movimentacao.getDataHoraEntrada();
    LocalDateTime fim = movimentacao.getDataHoraSaida();
    double valor = 0;

    long diffHoras = inicio.until(fim, ChronoUnit.HOURS);

    if (diffHoras > 0 ){
        valor+= Tarifario.VALOR_HORA;
        fim = fim.minus(1, ChronoUnit.HOURS);
    }

long diffMinutos = inicio.until(fim, ChronoUnit.MINUTES);

valor += (diffMinutos/Tarifario.INCREMENTO_MINUTOS)*Tarifario.VALOR_INCREMENTAL;

movimentacao.setValor(valor);
}
public static LocalDateTime getDate(String rdataEntrada) {
    // Ajustado de mm (minutos) para MM (mês) e removido .S para coincidir com o formato salvo
    return LocalDateTime.parse(rdataEntrada, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
}
public static String getDisplayData(LocalDateTime data) {
   return data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
}
public static String gerarTextoFaturamento(LocalDateTime data, List<movimentacao> movimentacoes) {
   double totalFaturado = 0;
   String texto = "";
for (movimentacao movimentacao : movimentacoes){
    totalFaturado += movimentacao.getValor();
}

String sAno =  ""+data.getYear() ;
String sMes = data.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());
texto = "Faturamento do mês de " + sMes;
texto += " de " + sAno +" " + "Foi de R$ " + totalFaturado;

return texto;
}
}
