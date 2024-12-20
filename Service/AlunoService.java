package src.Service;

import src.Aluno;
import src.DAO.AlunoDAO;

import java.util.Optional;

public class AlunoService {

    private final AlunoDAO alunoDAO = new AlunoDAO();

    /**
     * Busca um aluno pelo RA.
     * @param ra RA do aluno.
     * @return Uma instância de Aluno, se encontrada.
     */
    public Optional<Aluno> buscarAlunoPorRA(String ra) {
        return alunoDAO.buscarAlunoPorRA(ra);
    }

    public boolean verificarAlunoExiste(String ra) {
        return buscarAlunoPorRA(ra).isPresent();
    }


}
