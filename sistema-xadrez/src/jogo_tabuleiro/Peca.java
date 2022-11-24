package jogo_tabuleiro;

public class Peca {
	
	//ATRIBUTOS CRIADOS A PARTIR DAS CLASSES PRESENTES//
	protected Posicao posicao;
	private Tabuleiro tabuleiro;
	
	//CONSTRUTOR C/ ARGUMENTO//
	public Peca(Tabuleiro tabuleiro) {
		//super();
		this.tabuleiro = tabuleiro;
		posicao = null;
	}

	//METODO//
	protected Tabuleiro getTabuleiro() {
		return tabuleiro;
	}

	

	
	
	
}
