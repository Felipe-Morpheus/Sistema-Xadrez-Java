package xadrez.pecas;

import jogo_tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Rei extends PecaXadrez{

	//CONSTRUTOR C/ ARGUMENTO
	public Rei(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);

	}
	
	//METODO - FUNCAO - OPERADOR
	@Override
	public String toString() {
		return "R";
	}

	@Override
	public boolean[][] possiveisMovimentos() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		return mat;
	}
	
	

}
