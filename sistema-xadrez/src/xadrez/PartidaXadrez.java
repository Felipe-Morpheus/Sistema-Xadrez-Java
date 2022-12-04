package xadrez;

import jogo_tabuleiro.Peca;
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
	public PecaXadrez performaMovimentoXadrez (PosicaoXadrez origemPosicao, PosicaoXadrez destinoPosicao) {
		Posicao origem  = origemPosicao.Posicionar();
		Posicao destino = destinoPosicao.Posicionar();
		validarOrigemPosicao(origem);
		Peca capturarPeca = fazerMovimento(origem, destino);
		return (PecaXadrez)capturarPeca;
	}
	
	private Peca fazerMovimento(Posicao origem, Posicao destino) {
		Peca p = tabuleiro.removePeca(origem);
		Peca capturarPeca = tabuleiro.removePeca(destino);
		tabuleiro.moverPeca(p, destino);
		return capturarPeca;
	}
	
	
	// METODO- OPERACAO - FUNCAO//
	private void validarOrigemPosicao(Posicao posicao) {
		if(!tabuleiro.haUmaPeca(posicao)) {
			throw new XadrezException("Não existe peça na posição de origem. ");
		}
		if(tabuleiro.peca(posicao).existePossiveisMovimentos()) {
			throw new XadrezException("Não existe movimentos possíveis para a peça escolhida. ");
			
		}
	}
	
	// METODO- OPERACAO - FUNCAO//
	//MOVER PEÇA NA COORDENADA DO TABULEIRO
	private void moverNovaPeca(char coluna, int linha, PecaXadrez peca ) {
		tabuleiro.moverPeca(peca, new PosicaoXadrez(coluna, linha).Posicionar());
	}
	
	//COORDENADAS DAS PEÇAS
	private void toqueInicial () {
		moverNovaPeca('c', 1, new Torre(tabuleiro, Cor.BRANCO));
        moverNovaPeca('c', 2, new Torre(tabuleiro, Cor.BRANCO));
        moverNovaPeca('d', 2, new Torre(tabuleiro, Cor.BRANCO));
        moverNovaPeca('e', 2, new Torre(tabuleiro, Cor.BRANCO));
        moverNovaPeca('e', 1, new Torre(tabuleiro, Cor.BRANCO));
        moverNovaPeca('d', 1, new Rei(tabuleiro, Cor.BRANCO));

        moverNovaPeca('c', 7, new Torre(tabuleiro, Cor.PRETO));
        moverNovaPeca('c', 8, new Torre(tabuleiro, Cor.PRETO));
        moverNovaPeca('d', 7, new Torre(tabuleiro, Cor.PRETO));
        moverNovaPeca('e', 7, new Torre(tabuleiro, Cor.PRETO));
        moverNovaPeca('e', 8, new Torre(tabuleiro, Cor.PRETO));
        moverNovaPeca('d', 8, new Rei(tabuleiro, Cor.PRETO));


	}
}
