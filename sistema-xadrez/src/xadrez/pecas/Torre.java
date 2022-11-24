package xadrez.pecas;

import jogo_tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Torre extends PecaXadrez {

	//CONSTRUTOR C/ ARGUMENTO
	public Torre(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);

	}
	
	// OPERADOR, METODO, FUNCAO
	@Override
	public String toString() {
		return "T";
	}

}
