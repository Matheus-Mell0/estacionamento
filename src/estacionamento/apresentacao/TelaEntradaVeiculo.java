package estacionamento.apresentacao;

import javax.swing.*;
import javax.swing.text.MaskFormatter;

import estacionamento.controle.EstacionamentoControle;
import estacionamento.controle.EstacionamentoException;
import estacionamento.controle.VeiculoException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

public class TelaEntradaVeiculo extends JFrame implements ActionListener {

    private JFrame parent;
    private JTextField txtModelo;
    private JTextField txtMarca;
    private JTextField txtCor;
    private JFormattedTextField txfPlaca;
    private JButton btnOk;
    private JButton btnCancel;

    public TelaEntradaVeiculo(JFrame parent) {

        this.parent = parent;

        setTitle("Entrada de Veículo");
        setResizable(false);
        setSize(378, 335);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);

        // BOTÃO CANCELAR
        btnCancel = new JButton("Cancelar");
        btnCancel.setBounds(0, 273, 362, 23);
        btnCancel.addActionListener(e -> {
            parent.setVisible(true);
            this.dispose();
        });
        getContentPane().add(btnCancel);

        // LABELS
        JLabel lblPlaca = new JLabel("Placa:");
        lblPlaca.setBounds(69, 56, 46, 14);
        getContentPane().add(lblPlaca);

        JLabel lblMarca = new JLabel("Marca:");
        lblMarca.setBounds(69, 81, 46, 14);
        getContentPane().add(lblMarca);

        JLabel lblModelo = new JLabel("Modelo:");
        lblModelo.setBounds(69, 106, 46, 14);
        getContentPane().add(lblModelo);

        JLabel lblCor = new JLabel("Cor:");
        lblCor.setBounds(69, 131, 36, 14);
        getContentPane().add(lblCor);

        // CAMPOS TEXTO
        txtModelo = new JTextField();
        txtModelo.setBounds(137, 103, 120, 20);
        getContentPane().add(txtModelo);

        txtMarca = new JTextField();
        txtMarca.setBounds(137, 77, 120, 20);
        getContentPane().add(txtMarca);

        txtCor = new JTextField();
        txtCor.setBounds(137, 128, 120, 20);
        getContentPane().add(txtCor);

        // CAMPO PLACA
        try {
            MaskFormatter formatter = new MaskFormatter("UUU-####");
            formatter.setPlaceholderCharacter('_');
            formatter.setAllowsInvalid(false);
            formatter.setOverwriteMode(true);

            txfPlaca = new JFormattedTextField(formatter);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Padrao de placa errado!",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        txfPlaca.setBounds(137, 53, 120, 20);
        getContentPane().add(txfPlaca);

        // BOTÃO OK
        btnOk = new JButton("Ok");
        btnOk.setBounds(134, 223, 89, 23);
        btnOk.setActionCommand("ok");
        btnOk.addActionListener(this);
        getContentPane().add(btnOk);

        // MOSTRA A TELA
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        if ("ok".equals(evento.getActionCommand())) {
            EstacionamentoControle controle = new EstacionamentoControle();
            try {
                controle.processarEntrada(txfPlaca.getText(),
                        txtMarca.getText(),
                        txtModelo.getText(),
                        txtCor.getText());

                JOptionPane.showMessageDialog(null,
                        "Veiculo registrado com sucesso",
                        "Entrada de veiculo", JOptionPane.INFORMATION_MESSAGE);

            } catch (EstacionamentoException | VeiculoException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(),
                        "Falha na Entrada", JOptionPane.ERROR_MESSAGE);

                e.printStackTrace();
            }

        }
        this.parent.setVisible(true);
        this.dispose();
    }
}