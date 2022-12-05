package xadrez;

import jogo_tabuleiro.Peca;
import jogo_tabuleiro.Posicao;
import jogo_tabuleiro.Tabuleiro;

public abstract class PecaXadrez extends Peca {

	// ATRIBUTO//
	private Cor cor;

	// CONSTRUTOR C/ ARGUMENTO//
	public PecaXadrez(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	// GET-SET//
	public Cor getCor() {
		return cor;
	}

	protected boolean haUmaPecaOponente(Posicao posicao) {
		PecaXadrez p = (PecaXadrez) getTabuleiro().peca(posicao);
		return p != null && p.getCor() != cor;
	}

}
