package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jogo_tabuleiro.Peca;
import jogo_tabuleiro.Posicao;
import jogo_tabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {

	// ATRIBUTO//
	private int turno;
	private Cor jogadorAtual;
	private Tabuleiro tabuleiro;
	private boolean xeque;
	private boolean xequeMate;

	private List<Peca> pecasNoTabuleiro = new ArrayList<>();
	private List<Peca> pecasCapturadas = new ArrayList<>();

	// CONSTRUTOR C/ ARGUMENTO//
	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		turno = 1;
		jogadorAtual = Cor.BRANCO;

		// INICIAR PARTIDA
		toqueInicial();
	}

	// GET-SET
	public int getTurno() {
		return turno;
	}

	public Cor getJogadorAtual() {
		return jogadorAtual;
	}
	
	public boolean getXeque() {
		return xeque;
	}
	
	public boolean getXequeMate() {
		return xequeMate;
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
		
		if(testXeque(jogadorAtual)) {
			desfazerMovimento(origem, destino, capturarPeca);
			throw new XadrezException("Você não pode se colocar em xeque. ");
		}
		
		xeque = (testXeque(oponente(jogadorAtual))) ? true : false;
		if(testXequeMate(oponente(jogadorAtual))) {
			xequeMate = true;
		}
		else {
		proximoTurno();
		}
		
		return (PecaXadrez) capturarPeca;
	}

	// METODO- OPERACAO - FUNCAO//
	private Peca fazerMovimento(Posicao origem, Posicao destino) {
		Peca p = tabuleiro.removePeca(origem);
		Peca capturarPeca = tabuleiro.removePeca(destino);
		tabuleiro.moverPeca(p, destino);

		if (capturarPeca != null) {
			pecasNoTabuleiro.remove(capturarPeca);
			pecasCapturadas.add(capturarPeca);
		}
		return capturarPeca;
	}

	// METODO- OPERACAO - FUNCAO//
	private void desfazerMovimento(Posicao origem, Posicao destino, Peca capturarPeca) {
		Peca p = tabuleiro.removePeca(destino);
		tabuleiro.moverPeca(p, origem);

		if (capturarPeca != null) {
			tabuleiro.moverPeca(capturarPeca, destino);
			pecasCapturadas.remove(capturarPeca);
			pecasNoTabuleiro.add(capturarPeca);
		}
	}

	// METODO- OPERACAO - FUNCAO//
	private void validarOrigemPosicao(Posicao posicao) {
		if (!tabuleiro.haUmaPeca(posicao)) {
			throw new XadrezException("Não existe peça na posição de origem. ");
		}
		if (jogadorAtual != ((PecaXadrez) tabuleiro.peca(posicao)).getCor()) {
			throw new XadrezException("A peça escolhida não é sua. ");
		}
		if (!tabuleiro.peca(posicao).existePossiveisMovimentos()) {
			throw new XadrezException("Não existe movimentos possíveis para a peça escolhida. ");

		}
	}

	// METODO- OPERACAO - FUNCAO//
	private void validarDestinoPosicao(Posicao origem, Posicao destino) {
		if (!tabuleiro.peca(origem).possivelMovimento(destino)) {
			throw new XadrezException("A peça escolhida, não pode se mover para a posição destinada. ");
		}
	}

	// TROCA DE TURNO
	private void proximoTurno() {
		turno++;
		// Expressão condicional ternaria (Se variável for igual (==) , retornar tal
		// (?), senão retornar acolá( : ) )
		jogadorAtual = (jogadorAtual == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
	}

	// METODO- OPERACAO - FUNCAO//
	private Cor oponente(Cor cor) {
		// Expressão condicional ternaria (Se variável for igual (==) , retornar tal
		// (?), senão retornar acolá( : ) )
		return (cor == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
	}

	// METODO- OPERACAO - FUNCAO//
	private PecaXadrez rei(Cor cor) {
		List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez) x).getCor() == cor).collect(Collectors.toList());
		for (Peca p : list) {
			if (p instanceof Rei) {
				return (PecaXadrez) p;
			}
		}
		throw new IllegalStateException("Não existe o rei " + cor + " no tabuleiro. ");
	}

	private boolean testXeque(Cor cor) {
		Posicao reiPosicao = rei(cor).getPosicaoXadrez().Posicionar();
		List<Peca> pecasOponente = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez) x).getCor() == oponente(cor))
				.collect(Collectors.toList());
		for (Peca p : pecasOponente) {
			boolean[][] mat = p.possiveisMovimentos();
			if (mat[reiPosicao.getLinha()][reiPosicao.getColuna()]) {
				return true;
			}
		}
		return false;
	}
	
	private boolean testXequeMate(Cor cor) {
		if(!testXeque(cor)) {
			return false;
		}
		List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez) x).getCor() == cor).collect(Collectors.toList());
		for (Peca p : list) {
			boolean [][] mat = p.possiveisMovimentos();
			for (int i=0; i<tabuleiro.getLinhas(); i++) {
				for (int j=0; j<tabuleiro.getColunas(); j++) {
					if (mat[i][j]) {
						Posicao origem = ((PecaXadrez)p).getPosicaoXadrez().Posicionar();
						Posicao destino = new Posicao(i, j);
						Peca capturarPeca = fazerMovimento(origem, destino);
						boolean testXeque = testXeque (cor);
						desfazerMovimento(origem, destino, capturarPeca);
						if (!testXeque) {
							return false;
						}
					}
				}
				
			}
		}
		return true;
	}


	// METODO- OPERACAO - FUNCAO//
	// MOVER PEÇA NA COORDENADA DO TABULEIRO
	private void moverNovaPeca(char coluna, int linha, PecaXadrez peca) {
		tabuleiro.moverPeca(peca, new PosicaoXadrez(coluna, linha).Posicionar());
		pecasNoTabuleiro.add(peca);
	}

	// COORDENADAS DAS PEÇAS
	private void toqueInicial() {
		moverNovaPeca('h', 7, new Torre(tabuleiro, Cor.BRANCO));
		moverNovaPeca('d', 1, new Torre(tabuleiro, Cor.BRANCO));
		moverNovaPeca('e', 1, new Rei(tabuleiro, Cor.BRANCO));
		
		moverNovaPeca('b', 8, new Torre(tabuleiro, Cor.PRETO));
		moverNovaPeca('a', 8, new Rei(tabuleiro, Cor.PRETO));
		

		

	}
}
