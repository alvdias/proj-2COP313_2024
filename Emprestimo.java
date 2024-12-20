package src;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Emprestimo {
    Aluno aluno;
    Livro livro;
	Date dataEmprestimo = new Date();
	Date dataPrevista = new Date();
	Date data_aux = new Date();
	List<Item> item = new ArrayList<Item>();
	int emprestimo = 0;
	int diasatrasado;
	boolean atrasado;

    public Emprestimo() {

    }

    public Emprestimo(Aluno aluno, Livro livro) {
        this.aluno = aluno;
        this.livro = livro;
    }


    public Date getDataEmprestimo() {
        return dataEmprestimo;
    }

    public Date getDataPrevista() {
        return dataPrevista;
    }

    public void setDataEmprestimo(Date dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public void setDataPrevista(Date dataPrevista) {
        this.dataPrevista = dataPrevista;
    }

    public void setAtrasado(boolean atrasado) {
        this.atrasado = atrasado;
    }

    public void setDiasAtrasado(int diasatrasado) {
        this.diasatrasado = diasatrasado;
    }

    public int getDiasAtrasado() {
        return diasatrasado;
    }


    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public Emprestimo emprestar(List<Livro> livros) {
        for (int i = 0; i < livros.size(); i++) {
            item.add(new Item(livros.get(i)));
            emprestimo += 1;
        }
        CalculaDataDevolucao();
        return this;

    }

    private Date CalculaDataDevolucao() {
        Date date = new Date();

        for (int j = 0; j < item.size(); j++) {
            data_aux = item.get(j).calculaDataDevolucao(date);
            if (dataPrevista.compareTo(data_aux) < 0)
                dataPrevista = data_aux;
        }

        dataPrevista = this.AddDias(dataPrevista);

        for (int j = 0; j < item.size(); j++)
            item.get(j).setDataDevolucao(dataPrevista);

        return dataPrevista;
    }

    private Date AddDias(Date data) {
        if (item.size() > 2) {
            int tam = item.size() - 2;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(data);
            calendar.add(Calendar.DATE, (tam * 2));
            return calendar.getTime();
        }
        return data;
    }

    public int getAlunoID() {
        return aluno.id;
    }

    public String getISBN() {
        return livro.getISBN();
    }

    public int getLivroCodigo() {
        return livro.codigoLivro;
    }

}