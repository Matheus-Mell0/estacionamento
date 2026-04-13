package estacionamento.negocio;

import estacionamento.controle.EstacionamentoControle;

/**
 * representa as informações relativas à vagas do estacionamento ou status
 * de comfirmação.
 */
public class vaga {
public static int TOTAL_VAGAS = 100;
private static int vagasOcupadas = inicializarOcupadas(); ;

private vaga(){}
/**
 * verificar a existencia de alguma vaga livre no estacionamento
 * 
 * @return true se houver alguma vaga e false se estiver lotado.
 */

public static  boolean temVagaLivre(){

    return (vagasOcupadas < TOTAL_VAGAS);
}
/**
 * buscar o status atual das vagas do estacionamento.
 */
public static int inicializarOcupadas(){
    EstacionamentoControle controle = new EstacionamentoControle();
   return controle.inicializarOcupadas();

  
}
/**
 * retorna o numero  de vags ocupadas
 * @return numero de vagas ocupadas em um determinado instante
 */
public static int ocupadas(){
    return vaga.vagasOcupadas;
}
/**
 * retornar o numero de vagas livres
 * @return numero de vagas livres em um determinado instante
 */
public static int livres(){
    return TOTAL_VAGAS - vaga.vagasOcupadas;
}

/**
 * atualiza o numero de vagas ocupadas apos a entrada do veiculo 
 */
public static void entrou(){
    vaga.vagasOcupadas ++;
}
public static void saiu() {
vaga.vagasOcupadas --;
}


}
