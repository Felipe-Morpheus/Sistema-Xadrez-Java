package jogo_tabuleiro;

public class Tabuleiro {
	
	//ATRIBUTO//
	private int linhas;
	private int colunas;
	private Peca[][] pecas;
	
	
	//CONSTRUTOR C/ ARGUMENTO// Exception
	public Tabuleiro(int linhas, int colunas) {
		if (linhas < 1 || colunas <1) {
			throw new TabuleiroException("Erro criando o tabuleiro: Deve haver pelo menos 1 linha e 1 coluna");
		}
		this.linhas = linhas;
		this.colunas = colunas;
		pecas = new Peca[linhas][colunas];
	}

	//GET-SETS//
	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}

		
	//Operacao/ Metodo / Função//
	public Peca peca (int linha, int coluna) {
		if (!posicaoExistente(linha, coluna)) {
			throw new TabuleiroException("Posição fora do tabuleiro: ");
		}
		return pecas[linha][coluna];
	}
	//Operacao/ Metodo / Função//
	public Peca peca (Posicao posicao) {
		if (!posicaoExistente(posicao)) {
			throw new TabuleiroException("Posição fora do tabuleiro: ");
		}
		return pecas [posicao.getLinha()][posicao.getColuna()];
	}
	//Operacao/ Metodo / Função//
	public void moverPeca (Peca peca, Posicao posicao) {
		if (haUmaPeca(posicao)) {
			throw new TabuleiroException("Já existe uma peça em posição: " + posicao);
		}
		pecas[posicao.getLinha()][posicao.getColuna()] = peca;
		peca.posicao = posicao;
	}
	//Operacao/ Metodo / Função//
	public Peca removePeca(Posicao posicao) {
		if (!posicaoExistente(posicao)) {
			throw new TabuleiroException("Posição fora do tabuleiro: ");
		}
		if (peca(posicao) == null) {
			return null;
		}
		Peca aux = peca(posicao);
		aux.posicao = null;
		pecas[posicao.getLinha()][posicao.getColuna()] = null;
		return aux;
	}
	
	//Operacao/ Metodo / Função//
	private boolean posicaoExistente (int linha, int coluna) {
		return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas;
	}
	//Operacao/ Metodo / Função//
	public boolean posicaoExistente (Posicao posicao) {
		return posicaoExistente(posicao.getLinha(), posicao.getColuna());
	}
	//Operacao/ Metodo / Função//
	public boolean haUmaPeca(Posicao posicao) {
		if (!posicaoExistente(posicao)) {
			throw new TabuleiroException("Posição fora do tabuleiro: ");
		}
		return peca(posicao) != null;
	}

}
