package src;

import src.Service.DebitoService;

import java.util.Optional;

public class Debito {

    private final DebitoService debitoService = new DebitoService();

    String RA;
    double valor;

    public Debito() {
        this.RA = "";
        this.valor = 0;
    }

    public boolean verificaDebito(String ra) {
        Optional<Debito> debito = debitoService.buscarDebitoPorRa(ra);
        if (debito.isEmpty()) {
            System.out.println("Debito Inexistente");
            return false;
        } else {
            return true;
        }

    }

    public void setRA(String ra) {
        this.RA = ra;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getRA() {
        return RA;
    }

}