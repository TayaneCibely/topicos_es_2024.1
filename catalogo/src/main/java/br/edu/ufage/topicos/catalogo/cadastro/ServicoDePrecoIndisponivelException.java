package br.edu.ufage.topicos.catalogo.cadastro;

public class ServicoDePrecoIndisponivelException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public ServicoDePrecoIndisponivelException(String msg) {
        super(msg);
    }
}
