package src;

import src.Service.AlunoService;
import src.Service.EmprestimoService;
import src.Service.LivroService;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class Controle {

    private final LivroService livroService = new LivroService();
    private final AlunoService alunoService = new AlunoService();
    private final EmprestimoService emprestimoService = new EmprestimoService();

    private JFrame frame;

    public Emprestimo emprestar(String aluno, int[] codigos, int num) {
        AtomicBoolean retorno = new AtomicBoolean();
        AtomicReference<String> alert = new AtomicReference<>("");

        frame = new JFrame("Erro");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        Aluno a = new Aluno();
        a.RA = aluno;

        Optional<Aluno> alun = alunoService.buscarAlunoPorRA(a.RA);

        if (alun.isEmpty()) {
            alert.set("Aluno inexistente");
            retorno.set(false);
        }

        alun.ifPresent(al -> {
            if (al.verificaDebito()) {
                retorno.set(true);
                alert.set("Aluno em débito");
                System.out.println("Aluno em débito");
            } else {
                retorno.set(false);
                System.out.println("Aluno sem débito");
            }
        });

        if (alert.get() != "") {
            JOptionPane.showMessageDialog(frame, alert.get(), "Erro", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException("Exceção interna interrompeu a execução do método");
        }

        if (!retorno.get()) {
            List<Livro> livros = new ArrayList<Livro>();

            for (int i = 0; i < num; i++) {
                Livro l = new Livro(codigos[i]);

                if (!l.verificaLivro(codigos[i]))
                    livros.add(l);
                else {
                    alert.set("Livro exclusivo da Biblioteca");
                    JOptionPane.showMessageDialog(frame, alert.get(), "Erro", JOptionPane.ERROR_MESSAGE);
                    throw new RuntimeException("Exceção interna interrompeu a execução do método");
                }
            }

            if (!livros.isEmpty()) {

                List<Emprestimo> emprestimos = livros.stream()
                        .map(livro -> new Emprestimo(alun.get(), livro))
                        .collect(Collectors.toList());

                emprestimoService.inserirEmprestimo(emprestimos);
                return a.emprestar(livros);
            } else
                return new Emprestimo();

        } else
            return new Emprestimo();
    }

}