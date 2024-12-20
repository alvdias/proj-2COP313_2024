package src.GUI;

import src.Autor;
import src.DAO.LivroDAO;
import src.Livro;
import src.Titulo;
import src.Area;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class CadastroLivroGUI extends JFrame {
    private JTextField nomeField;
    private JTextField edicaoField;
    private JTextField anoField;
    private JTextField prazoField;
    private JTextField nomeAutorField;
    private JTextField titulcaoAutorField;
    private JTextField sobrenomeAutorField;
    private JTextField areaField;
    private JButton salvarButton;
    private JTable tabela;
    private DefaultTableModel tableModel;

    public CadastroLivroGUI() {
        setTitle("Cadastro de Livros");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(9, 2));
        formPanel.setBorder(BorderFactory.createTitledBorder("Cadastro de Livros"));

        formPanel.add(new JLabel("Livro - Titulo:"));
        nomeField = new JTextField();
        formPanel.add(nomeField);

        formPanel.add(new JLabel("Livro - Edição:"));
        edicaoField = new JTextField();
        formPanel.add(edicaoField);

        formPanel.add(new JLabel("Livro - Ano:"));
        anoField = new JTextField();
        formPanel.add(anoField);

        formPanel.add(new JLabel("Livro - Prazo:"));
        prazoField = new JTextField();
        formPanel.add(prazoField);

        formPanel.add(new JLabel("Livro - Área:"));
        areaField = new JTextField();
        formPanel.add(areaField);

        formPanel.add(new JLabel("Autor - Nome:"));
        nomeAutorField = new JTextField();
        formPanel.add(nomeAutorField);

        formPanel.add(new JLabel("Autor - Sobrenome:"));
        sobrenomeAutorField = new JTextField();
        formPanel.add(sobrenomeAutorField);

        formPanel.add(new JLabel("Autor - Titulação:"));
        titulcaoAutorField = new JTextField();
        formPanel.add(titulcaoAutorField);

        salvarButton = new JButton("Salvar");
        formPanel.add(new JLabel()); // Espaço vazio
        formPanel.add(salvarButton);

        add(formPanel, BorderLayout.NORTH);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Lista de Livros"));

        String[] colunas = {"TItulo", "Autor", "Edição", "Ano"};
        tableModel = new DefaultTableModel(colunas, 0);
        tabela = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabela);

        tablePanel.add(scrollPane, BorderLayout.CENTER);
        add(tablePanel, BorderLayout.CENTER);

        carregarLista();

        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    cadastrarLivro();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    private void cadastrarLivro() throws SQLException {
        String nome = nomeField.getText();
        Integer edicao = Integer.valueOf(edicaoField.getText());
        Integer ano = Integer.valueOf(anoField.getText());
        Integer prazo = Integer.valueOf(prazoField.getText());
        String area = areaField.getText();
        String nomeAutor = nomeAutorField.getText();
        String sobrenomeAutor = sobrenomeAutorField.getText();
        String titulcaoAutor = titulcaoAutorField.getText();

        if (nome.isEmpty() || (edicao == null) || (ano == null) || (prazo == null) || nomeAutor.isEmpty()
                || sobrenomeAutor.isEmpty() || titulcaoAutor.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Livro livro = new Livro();
        Autor autor = new Autor(nomeAutor, sobrenomeAutor, titulcaoAutor);
        livro.setAutor(autor);

        Titulo titulo = new Titulo(nome, edicao, ano);
        livro.setTitulo(titulo);

        Area areaLivro = new Area(area);
        livro.setArea(areaLivro);

        LivroDAO livroDAO = new LivroDAO();
        livroDAO.cadastrarLivro(livro, autor);

        JOptionPane.showMessageDialog(this, "Livro cadastrado com sucesso!");

        carregarLista();
        limparCampos();
    }

    private void carregarLista() {
        tableModel.setRowCount(0);

        LivroDAO livroDAO = new LivroDAO();
        List<Livro> livros = livroDAO.buscarTodosLivros();

        for (Livro livro : livros) {
            Object[] linha = {livro.getISBN(), livro.getAutor(), livro.getEdicao(), livro.getAno()};
            tableModel.addRow(linha);
        }
    }

    private void limparCampos() {
        nomeField.setText("");
        edicaoField.setText("");
        anoField.setText("");
        prazoField.setText("");
        nomeField.setText("");
        nomeAutorField.setText("");
        sobrenomeAutorField.setText("");
        titulcaoAutorField.setText("");
        areaField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CadastroAlunoGUI().setVisible(true);
        });
    }
}
