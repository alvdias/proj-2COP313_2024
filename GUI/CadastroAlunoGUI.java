package src.GUI;

import src.DAO.AlunoDAO;
import src.Aluno;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CadastroAlunoGUI extends JFrame {
    private JTextField nomeField;
    private JTextField raField;
    private JTextField cpfField;
    private JTextField endField;
    private JButton salvarButton;
    private JTable tabela;
    private DefaultTableModel tableModel;

    public CadastroAlunoGUI() {
        setTitle("Cadastro de Aluno");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(5, 2));
        formPanel.setBorder(BorderFactory.createTitledBorder("Cadastro de Aluno"));

        formPanel.add(new JLabel("Nome do Aluno:"));
        nomeField = new JTextField();
        formPanel.add(nomeField);

        formPanel.add(new JLabel("RA do Aluno:"));
        raField = new JTextField();
        formPanel.add(raField);

        formPanel.add(new JLabel("CPF do Aluno:"));
        cpfField = new JTextField();
        formPanel.add(cpfField);

        formPanel.add(new JLabel("End. do Aluno:"));
        endField = new JTextField();
        formPanel.add(endField);

        salvarButton = new JButton("Salvar");
        formPanel.add(new JLabel());
        formPanel.add(salvarButton);

        add(formPanel, BorderLayout.NORTH);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Lista de Alunos"));

        String[] colunas = {"Nome", "RA", "CPF", "End."};
        tableModel = new DefaultTableModel(colunas, 0);
        tabela = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabela);

        tablePanel.add(scrollPane, BorderLayout.CENTER);
        add(tablePanel, BorderLayout.CENTER);

        carregarLista();

        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarAluno();
            }
        });
    }

    private void cadastrarAluno() {
        String ra = raField.getText();
        String nome = nomeField.getText();
        String cpf = cpfField.getText();
        String end = endField.getText();

        if (ra.isEmpty() || nome.isEmpty() || cpf.isEmpty() || end.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Aluno aluno = new Aluno(nome, cpf, ra, end);

        AlunoDAO alunoDAO = new AlunoDAO();
        alunoDAO.cadastrarAluno(aluno);

        JOptionPane.showMessageDialog(this, "Aluno cadastrado com sucesso!");

        carregarLista();
        limparCampos();
    }

    private void carregarLista() {
        tableModel.setRowCount(0);

        AlunoDAO alunoDAO = new AlunoDAO();
        List<Aluno> alunos = alunoDAO.buscarTodosAlunos();

        for (Aluno aluno : alunos) {
            Object[] linha = {aluno.getNome(), aluno.getRA(), aluno.getCPF(), aluno.getEnd()};
            tableModel.addRow(linha);
        }
    }

    private void limparCampos() {
        nomeField.setText("");
        raField.setText("");
        cpfField.setText("");
        endField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CadastroAlunoGUI().setVisible(true);
        });
    }
}
