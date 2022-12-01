package xadrez;

import jogo_tabuleiro.TabuleiroException;

public class XadrezException extends TabuleiroException {

	// nº serial PADRÃO
	private static final long serialVersionUID = 1L;
	
	//CONSTRUTOR C/ ARGUMENTO String msg
	public XadrezException (String msg) {
		super(msg);
	}

}
