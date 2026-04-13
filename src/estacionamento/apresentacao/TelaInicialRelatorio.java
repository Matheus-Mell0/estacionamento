package estacionamento.apresentacao;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JLabel;

import estacionamento.controle.EstacionamentoControle;
import estacionamento.negocio.movimentacao;

import javax.swing.JComboBox;
import javax.swing.JButton;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.List;

public class TelaInicialRelatorio extends JFrame implements ActionListener {
    private JComboBox<?> cboAno;
    private JComboBox<String> cboMes;

    public TelaInicialRelatorio() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(600, 200));
        setResizable(false);
        setTitle("Filtro do Relatório");
        getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 15, 40));

        JLabel lblAno = new JLabel("Ano:");
        lblAno.setFont(new Font("Tahoma", Font.BOLD, 14));
        getContentPane().add(lblAno);

        cboAno = new JComboBox();
        cboAno.setModel(new DefaultComboBoxModel(new String[] { "2026", "2025", "2024", "2023", "2022" }));
        cboAno.setFont(new Font("Tahoma", Font.PLAIN, 14));
        getContentPane().add(cboAno);

        JLabel lblMes = new JLabel("Mês:");
        lblMes.setFont(new Font("Tahoma", Font.BOLD, 14));
        getContentPane().add(lblMes);

        cboMes = new JComboBox();
        cboMes.setModel(new DefaultComboBoxModel<>(new String[] {
                "Janeiro", "Fevereiro", "Março", "Abril", "Maio",
                "Junho", "Julho", "Agosto", "Setembro", "Outubro",
                "Novembro", "Dezembro"
        }));
        cboMes.setFont(new Font("Tahoma", Font.PLAIN, 14));
        getContentPane().add(cboMes);

        JButton btnGerar = new JButton("Gerar");
        btnGerar.addActionListener(this);
        btnGerar.setFont(new Font("Tahoma", Font.BOLD, 14));
        getContentPane().add(btnGerar);

        setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        int ano = Integer.parseInt( (String) cboAno.getSelectedItem().toString());
        int mes = cboMes.getSelectedIndex() + 1;

        EstacionamentoControle controle = new EstacionamentoControle();
        LocalDateTime data = LocalDateTime.of(ano, mes, 1, 0, 0);
        List<movimentacao> movimentacoes = controle.emitirRelatorio(data);

        TelaResultadoRelatorio relatorio = new TelaResultadoRelatorio(this, movimentacoes, data);

        relatorio.setVisible(true);
     dispose();
    }
}