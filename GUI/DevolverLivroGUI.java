package src.GUI;

import src.DAO.DevolucaoDAO;
import src.DAO.EmprestimoDAO;
import src.Emprestimo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DevolverLivroGUI extends JFrame {
    private JTextField raField;
    private JButton devolverButton;
    private JButton consultarButton;
    private JTable tabela;
    private DefaultTableModel tableModel;

    public DevolverLivroGUI() {
        setTitle("Devolver Livros");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(5, 2));
        formPanel.setBorder(BorderFactory.createTitledBorder("Aluno"));

        formPanel.add(new JLabel("RA do Aluno:"));
        raField = new JTextField();
        formPanel.add(raField);

        consultarButton = new JButton("Consultar devolução");
        formPanel.add(new JLabel()); // Espaço vazio
        formPanel.add(consultarButton);
        add(formPanel, BorderLayout.NORTH);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Lista dos Empréstimo"));

        String[] colunas = {"TItulo", "Data Emprestimo", "Data Devolucao", "Dias em atraso"};
        tableModel = new DefaultTableModel(colunas, 0);
        tabela = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabela);

        tablePanel.add(scrollPane, BorderLayout.CENTER);
        add(tablePanel, BorderLayout.CENTER);

        devolverButton = new JButton("Devolver Livros");
        devolverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ra = raField.getText();
                devolverLivros(ra);
            }
        });
        add(devolverButton, BorderLayout.SOUTH);

        carregarLista();

        consultarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarDevolucao();
            }
        });
    }

    private void cadastrarDevolucao() {
        String ra = raField.getText();

        if (ra.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        carregarLista();
    }

    private void carregarLista() {
        String ra = raField.getText();
        tableModel.setRowCount(0);

        EmprestimoDAO emprestimoDAO = new EmprestimoDAO();
        List<Emprestimo> emprestimos = emprestimoDAO.buscarTodosEmprestimosPorRa(ra);

        for (Emprestimo emp : emprestimos) {
            Object[] linha = {emp.getISBN(), emp.getDataEmprestimo(), emp.getDataPrevista(), emp.getDiasAtrasado()};
            tableModel.addRow(linha);
        }
    }

    public void devolverLivros(String ra) {
        DevolucaoDAO devolverDAO = new DevolucaoDAO();
        devolverDAO.cadastrarDevolucao(ra);

        JOptionPane.showMessageDialog(this, "Livros devolvidos com sucesso!");

        //valida geracaoDebitos
        //Inser débitos

        carregarLista();
        limparCampos();

    }

    private void limparCampos() {
        raField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DevolverLivroGUI().setVisible(true);
        });
    }
}
