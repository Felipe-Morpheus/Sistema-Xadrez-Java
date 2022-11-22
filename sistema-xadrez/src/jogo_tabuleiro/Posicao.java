package jogo_tabuleiro;

public class Posicao {
	
	//ATRIBUTO
	private int linha;
	private int coluna;
	
	//CONSTRUTOR C/ ARGUMENTO
	public Posicao(int linha, int coluna) {
	//	super();
		this.linha = linha;
		this.coluna = coluna;
	}
	
	//GET-SET
	public int getLinha() {
		return linha;
	}

	public void setLinha(int linha) {
		this.linha = linha;
	}

	public int getColuna() {
		return coluna;
	}

	public void setColuna(int coluna) {
		this.coluna = coluna;
	}
	
	//toString
	@Override // = Sobreposição
	public String toString() {
		return linha + ", " + coluna;
	}

}
