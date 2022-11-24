package xadrez;

import jogo_tabuleiro.Posicao;
import jogo_tabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {
	
	//ATRIBUTO//
	private Tabuleiro tabuleiro;
	
	//CONSTRUTOR C/ ARGUMENTO//
	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		
		//INICIAR PARTIDA
		toqueInicial();
	}
	
	// METODO- OPERACAO - FUNCAO//
	public PecaXadrez[][] getPecas(){
		PecaXadrez[][] mat = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i=0; i<tabuleiro.getLinhas(); i++) {
			for (int j=0; j<tabuleiro.getColunas(); j++) {
				mat[i][j]= (PecaXadrez)tabuleiro.peca(i, j);
			}
		}
		return mat;
	}
	
	// METODO- OPERACAO - FUNCAO//
	//COORDENADAS DAS PEÇAS
	private void toqueInicial () {
		tabuleiro.moverPeca(new Torre(tabuleiro, Cor.BRANCO), new Posicao(2, 1));
		tabuleiro.moverPeca(new Rei(tabuleiro, Cor.PRETO), new Posicao(0, 4));
		tabuleiro.moverPeca(new Rei(tabuleiro, Cor.BRANCO), new Posicao(7, 4));


	}
}
