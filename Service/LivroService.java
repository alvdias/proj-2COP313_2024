package src.Service;

import src.DAO.LivroDAO;
import src.Livro;

import java.util.List;
import java.util.Optional;

public class LivroService {

    private final LivroDAO livroDAO = new LivroDAO();

    public List<Livro> buscarLivroPorCodigo(int codigo) {
        return livroDAO.buscarLivroPorCodigo(codigo);
    }

}
