package jogo_tabuleiro;

public abstract class Peca {

	// ATRIBUTOS CRIADOS A PARTIR DAS CLASSES PRESENTES//
	protected Posicao posicao;
	private Tabuleiro tabuleiro;

	// CONSTRUTOR C/ ARGUMENTO//
	public Peca(Tabuleiro tabuleiro) {
		// super();
		this.tabuleiro = tabuleiro;
		posicao = null;
	}

	// METODO//
	protected Tabuleiro getTabuleiro() {
		return tabuleiro;
	}

	// METODO//
	public abstract boolean[][] possiveisMovimentos();

	public boolean possivelMovimento(Posicao posicao) {
		return possiveisMovimentos()[posicao.getLinha()][posicao.getColuna()];
	}

	public boolean existePossiveisMovimentos() {
		boolean[][] mat = possiveisMovimentos();
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat.length; j++) {
				if (mat[i][j]) {
					return true;
				}
			}
		}
		return false;
	}

}
