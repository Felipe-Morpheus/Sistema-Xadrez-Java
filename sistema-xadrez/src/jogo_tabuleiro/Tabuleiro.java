package jogo_tabuleiro;

public class Tabuleiro {
	
	//ATRIBUTO
	private int linhas;
	private int colunas;
	private Peca[][] pecas;
	
	
	//CONSTRUTOR C/ ARGUMENTO
	public Tabuleiro(int linhas, int colunas) {
		super();
		this.linhas = linhas;
		this.colunas = colunas;
		pecas = new Peca[linhas][colunas];
	}

	//GET-SETS
	public int getLinhas() {
		return linhas;
	}


	public void setLinhas(int linhas) {
		this.linhas = linhas;
	}


	public int getColunas() {
		return colunas;
	}


	public void setColunas(int colunas) {
		this.colunas = colunas;
	}
	
	
	

}
