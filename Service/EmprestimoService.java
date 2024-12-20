package src.Service;

import src.DAO.EmprestimoDAO;
import src.Emprestimo;

import java.util.List;
import java.util.Optional;

public class EmprestimoService {

    private final EmprestimoDAO emprestimoDAO = new EmprestimoDAO();

    public Optional<Emprestimo> inserirEmprestimo(List<Emprestimo> emp) {
        return emprestimoDAO.cadastrarEmprestimo(emp);
    }
}
