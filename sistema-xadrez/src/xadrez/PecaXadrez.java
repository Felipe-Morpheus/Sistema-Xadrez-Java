package xadrez;

import jogo_tabuleiro.Peca;
import jogo_tabuleiro.Posicao;
import jogo_tabuleiro.Tabuleiro;

public abstract class PecaXadrez extends Peca {

	// ATRIBUTO//
	private Cor cor;
	private int contarMovimento;

	// CONSTRUTOR C/ ARGUMENTO//
	public PecaXadrez(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	// GET-SET//
	public Cor getCor() {
		return cor;
	}
	public int getContarMovimento() {
		return contarMovimento;
	}
	
	public void increaseContarMovimento() {
		contarMovimento++;
	}
	
	public void decreaseContarMovimento() {
		contarMovimento--;
	}
	
	public PosicaoXadrez getPosicaoXadrez() {
		return PosicaoXadrez.daPosiccao(posicao);
	}

	protected boolean haUmaPecaOponente(Posicao posicao) {
		PecaXadrez p = (PecaXadrez) getTabuleiro().peca(posicao);
		return p != null && p.getCor() != cor;
	}

}
