package src.Service;

import src.DAO.DebitoDAO;
import src.Debito;
import src.Emprestimo;

import java.util.List;
import java.util.Optional;

public class DebitoService {

    private final DebitoDAO debitoDAO = new DebitoDAO();

    /**
     * Busca um aluno pelo RA.
     * @param ra RA do aluno.
     * @return Uma instância de Aluno, se encontrada.
     */
    public Optional<Debito> buscarDebitoPorRa(String ra) {
        return debitoDAO.buscarDebitoPorRA(ra);
    }

    public Debito inserirDebito(List<Emprestimo> emp) {
        return debitoDAO.inserirDebito(emp);

    }

}
