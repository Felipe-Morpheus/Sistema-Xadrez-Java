package xadrez;

import jogo_tabuleiro.Tabuleiro;

public class PartidaXadrez {
	
	//ATRIBUTO
	private Tabuleiro tabuleiro;
	
	//CONSTRUTOR C/ ARGUMENTO
	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		
	}
	
	// METODO- OPERACAO - FUNCAO
	public PecaXadrez[][] getPecas(){
		PecaXadrez[][] mat = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i=0; i<tabuleiro.getLinhas(); i++) {
			for (int j=0; j<tabuleiro.getColunas(); j++) {
				mat[i][j]= (PecaXadrez)tabuleiro.peca(i, j);
			}
		}
		return mat;
	}
}
