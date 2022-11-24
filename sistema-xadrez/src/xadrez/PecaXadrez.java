package xadrez;

import jogo_tabuleiro.Peca;
import jogo_tabuleiro.Tabuleiro;

public class PecaXadrez extends Peca {
	
	//ATRIBUTO//
	private Cor cor;

	//CONSTRUTOR C/ ARGUMENTO//
	public PecaXadrez(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	//GET-SET//
	public Cor getCor() {
		return cor;
	}

	

	
	
	
}
