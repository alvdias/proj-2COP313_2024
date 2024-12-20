package src;

import src.GUI.CadastroAlunoGUI;
import src.GUI.CadastroLivroGUI;
import src.GUI.DevolverLivroGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BibliotecaGUI extends JFrame {
    private JTextField raField;
    private JTextField numLivrosField;
    private JTextField[] codigoLivrosFields;
    private JPanel codigosPanel;
    private JButton emprestarButton;
    private JButton devolverButton;
    private JTextArea resultadoArea;
    private JButton cadastrarAlunoButton;
    private JButton cadastrarLivroButton;
    private JButton listarEmprestimosButton;

    public BibliotecaGUI() {
        setTitle("Emprestar Livros");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(4, 2));

        cadastrarAlunoButton = new JButton("Cadastrar Aluno");
        cadastrarLivroButton = new JButton("Cadastrar Livro");
        listarEmprestimosButton = new JButton("Empréstimos");
        devolverButton = new JButton("Devolver");

        topPanel.add(cadastrarAlunoButton);
        topPanel.add(cadastrarLivroButton);
        topPanel.add(listarEmprestimosButton);
        topPanel.add(devolverButton);

        cadastrarAlunoButton.addActionListener(e -> abrirCadastroAluno());
        cadastrarLivroButton.addActionListener(e -> abrirCadastroLivro());
        listarEmprestimosButton.addActionListener(e -> listarEmprestimos());
        devolverButton.addActionListener(e -> devolverLivros());

        topPanel.add(new JLabel("Digite o RA do Aluno:"));
        raField = new JTextField();
        topPanel.add(raField);

        topPanel.add(new JLabel("Digite a qtd de livros emprestados:"));
        numLivrosField = new JTextField();
        topPanel.add(numLivrosField);

        add(topPanel, BorderLayout.NORTH);

        codigosPanel = new JPanel();
        codigosPanel.setLayout(new GridLayout(0, 1));
        add(new JScrollPane(codigosPanel), BorderLayout.CENTER);

        numLivrosField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            }

            @Override
            public void focusLost(FocusEvent e) {
                adicionarCamposCodigos();
            }
        });

        resultadoArea = new JTextArea();
        resultadoArea.setEditable(false);
        resultadoArea.setLineWrap(true);
        resultadoArea.setWrapStyleWord(true);
        add(new JScrollPane(resultadoArea), BorderLayout.EAST);

        emprestarButton = new JButton("Emprestar Livros");
        emprestarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                emprestarLivros();
            }
        });
        add(emprestarButton, BorderLayout.SOUTH);

        emprestarButton = new JButton("Emprestar Livros");
        emprestarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                emprestarLivros();
            }
        });
        add(emprestarButton, BorderLayout.SOUTH);
    }

    private void adicionarCamposCodigos() {
        codigosPanel.removeAll();
        try {
            int num = Integer.parseInt(numLivrosField.getText());
            codigoLivrosFields = new JTextField[num];

            for (int i = 0; i < num; i++) {
                JLabel label = new JLabel("Digite o cod. do livro " + (i + 1) + ": ");
                JTextField textField = new JTextField();
                codigoLivrosFields[i] = textField;
                codigosPanel.add(label);
                codigosPanel.add(textField);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Digite um número válido para a quantidade de livros.");
        }

        codigosPanel.revalidate();
        codigosPanel.repaint();
    }

    private void devolverLivros() {
        SwingUtilities.invokeLater(() -> {
            DevolverLivroGUI devolverlivroGUI = new DevolverLivroGUI();
            devolverlivroGUI.setVisible(true);
        });
    }

    private void listarEmprestimos() {
        //to do listar emprestimos abertos
    }

    private void emprestarLivros() {
        String aluno = raField.getText();
        int num;
        try {
            num = Integer.parseInt(numLivrosField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Precis ter ao menos 1 livro para efetuar empréstimo.");
            return;
        }

        int[] codigos = new int[num];
        for (int i = 0; i < num; i++) {
            try {
                codigos[i] = Integer.parseInt(codigoLivrosFields[i].getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Código inválido no campo " + (i + 1) + ".");
                return;
            }
        }

        Controle c = new Controle();
        Emprestimo livrosEmprestados = c.emprestar(aluno, codigos, num);

        String[] colunas = {"Cod.", "Nome Livro", "Area", "Autor Nome", "Sobrenome", "Prazo max."};
        String[][] dados = new String[livrosEmprestados.item.size()][6];

        for (int i = 0; i < livrosEmprestados.item.size(); i++) {
            dados[i][0] = String.valueOf(livrosEmprestados.item.get(i).livro.codigoLivro);
            dados[i][1] = livrosEmprestados.item.get(i).livro.titulo.isbn;
            dados[i][2] = livrosEmprestados.item.get(i).livro.area.nome;
            dados[i][3] = livrosEmprestados.item.get(i).livro.autor.nome;
            dados[i][4] = livrosEmprestados.item.get(i).livro.autor.sobrenome;
            dados[i][5] = String.valueOf(livrosEmprestados.item.get(i).getDataDevolucao());
        }

        JTable tabela = new JTable(dados, colunas);
        JScrollPane scrollPane = new JScrollPane(tabela);

        LocalDate hoje = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");
        String dataFormatada = hoje.format(formatter);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("RA: " + aluno + " - Data de Hoje: " + dataFormatada), BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.SOUTH);

        JOptionPane.showMessageDialog(this, panel, "Livros Emprestados", JOptionPane.INFORMATION_MESSAGE);
    }

    private void abrirCadastroAluno() {
        SwingUtilities.invokeLater(() -> {
            CadastroAlunoGUI cadastroAlunoGUI = new CadastroAlunoGUI();
            cadastroAlunoGUI.setVisible(true);
        });
    }

    private void abrirCadastroLivro() {
        SwingUtilities.invokeLater(() -> {
            CadastroLivroGUI cadastrolivroGUI = new CadastroLivroGUI();
            cadastrolivroGUI.setVisible(true);
        });
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BibliotecaGUI().setVisible(true);
            }
        });
    }
}