package estacionamento.apresentacao;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class telaInicialMovimentacao extends JFrame implements ActionListener {

	private JButton btnEntrar;
	private JButton btnSair;

	static void main() {
		telaInicialMovimentacao tela = new telaInicialMovimentacao();
		tela.setVisible(true);
	}

	public telaInicialMovimentacao() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(new Dimension(270, 220));
		setResizable(false);
		setTitle("Sistema de Estacionamento");
		getContentPane().setLayout(new GridLayout(1, 2, 0, 0));

		btnEntrar = new JButton("");
		btnEntrar.setIcon(new ImageIcon(
				telaInicialMovimentacao.class.getResource("/recursos/entrada_do_estacionamento.png")
		));
		btnEntrar.addActionListener(this);
		btnEntrar.setActionCommand("entrada");
		getContentPane().add(btnEntrar);

		btnSair = new JButton("");
		btnSair.setIcon(new ImageIcon(telaInicialMovimentacao.class.getResource("/recursos/saida_do_estacionamento.png")
		));
		btnSair.addActionListener(this);
		btnSair.setActionCommand("saida");
		getContentPane().add(btnSair);

		setLocationRelativeTo(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		JFrame tela;

		if (cmd.equals("entrada")) {
            tela = new TelaEntradaVeiculo(this);
        } else {
            
                tela = new TelaSaidaVeiculo(this);
            
        }

		tela.setVisible(true);
		this.setVisible(false);
	}
}