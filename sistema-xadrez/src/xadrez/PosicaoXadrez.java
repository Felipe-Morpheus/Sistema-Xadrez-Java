package xadrez;

import jogo_tabuleiro.Posicao;

public class PosicaoXadrez {
	
	
	//ATRIBUTO
	private char coluna;
	private int linha;
	
	
	//CONSTRUTOR C/ ARGUMENTO// Exception
	public PosicaoXadrez(char coluna, int linha) {
		if (coluna < 'a' || coluna > 'h' || linha <1 || linha > 8) {
			throw new XadrezException(" Erro instanciando PosicaoXadrez. Valores válidos são: de 'a1' até 'h8'. ");
		}
		this.coluna = coluna;
		this.linha = linha;
	}

	//GET-SET// 
	public char getColuna() {
		return coluna;
	}


	

	public int getLinha() {
		return linha;
	}


	//METODO- OPERAÇÃO - FUNCAO//
	protected Posicao Posicionar() {
		return new Posicao (8 - linha, coluna - 'a');
	}
	
	protected  static PosicaoXadrez daPosiccao(Posicao posicao) {
		return new PosicaoXadrez ( (char)('a' + posicao.getColuna() ), 8 - posicao.getLinha() );
	}
	
	
	//toString// - o String vazio "" é um macete para o compilador entender que é uma concatenação 
	@Override
	public String toString() {
		return "" + coluna + linha;
	}
	
	
}
