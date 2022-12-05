package xadrez;

import jogo_tabuleiro.Peca;
import jogo_tabuleiro.Posicao;
import jogo_tabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {

	// ATRIBUTO//
	private int turno;
	private Cor JogadorAtual;
	private Tabuleiro tabuleiro;

	// CONSTRUTOR C/ ARGUMENTO//
	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		turno = 1;
		JogadorAtual = Cor.BRANCO;

		// INICIAR PARTIDA
		toqueInicial();
	}
	//GET-SET
	public int getTurno() {
		return turno;
	}
	
	public Cor getJogadorAtual() {
		return JogadorAtual;
	}

	// METODO- OPERACAO - FUNCAO//
	public PecaXadrez[][] getPecas() {
		PecaXadrez[][] mat = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i = 0; i < tabuleiro.getLinhas(); i++) {
			for (int j = 0; j < tabuleiro.getColunas(); j++) {
				mat[i][j] = (PecaXadrez) tabuleiro.peca(i, j);
			}
		}
		return mat;
	}
	
	public boolean[][] possiveisMovimentos(PosicaoXadrez origemPosicao) {
		Posicao posicao = origemPosicao.Posicionar();
		validarOrigemPosicao(posicao);
		return tabuleiro.peca(posicao).possiveisMovimentos();
	}

	// METODO- OPERACAO - FUNCAO//
	public PecaXadrez performaMovimentoXadrez(PosicaoXadrez origemPosicao, PosicaoXadrez destinoPosicao) {
		Posicao origem = origemPosicao.Posicionar();
		Posicao destino = destinoPosicao.Posicionar();
		validarOrigemPosicao(origem);
		validarDestinoPosicao(origem, destino);
		Peca capturarPeca = fazerMovimento(origem, destino);
		proximoTurno();
		return (PecaXadrez) capturarPeca;
	}

	private Peca fazerMovimento(Posicao origem, Posicao destino) {
		Peca p = tabuleiro.removePeca(origem);
		Peca capturarPeca = tabuleiro.removePeca(destino);
		tabuleiro.moverPeca(p, destino);
		return capturarPeca;
	}

	// METODO- OPERACAO - FUNCAO//
	private void validarOrigemPosicao(Posicao posicao) {
		if (!tabuleiro.haUmaPeca(posicao)) {
			throw new XadrezException("Não existe peça na posição de origem. ");
		}
		if(JogadorAtual != ((PecaXadrez)tabuleiro.peca(posicao)).getCor()) {
			throw new XadrezException("A peça escolhida não é sua. ");
		}
		if (!tabuleiro.peca(posicao).existePossiveisMovimentos()) {
			throw new XadrezException("Não existe movimentos possíveis para a peça escolhida. ");

		}
	}

	private void validarDestinoPosicao (Posicao origem, Posicao destino) {
		if (!tabuleiro.peca(origem).possivelMovimento(destino)) {
			throw new XadrezException("A peça escolhida, não pode se mover para a posição destinada. ");
		}
	}
	//TROCA DE TURNO
	private void proximoTurno() {
		turno++;
		//Expressão condicional ternaria
		JogadorAtual =  (JogadorAtual == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
	}

	// METODO- OPERACAO - FUNCAO//
	// MOVER PEÇA NA COORDENADA DO TABULEIRO
	private void moverNovaPeca(char coluna, int linha, PecaXadrez peca) {
		tabuleiro.moverPeca(peca, new PosicaoXadrez(coluna, linha).Posicionar());
	}

	// COORDENADAS DAS PEÇAS
	private void toqueInicial() {
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
