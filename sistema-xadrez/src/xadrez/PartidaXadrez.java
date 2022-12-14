package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jogo_tabuleiro.Peca;
import jogo_tabuleiro.Posicao;
import jogo_tabuleiro.Tabuleiro;
import xadrez.pecas.Bispo;
import xadrez.pecas.Cavalo;
import xadrez.pecas.Dama;
import xadrez.pecas.Peao;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {

	// ATRIBUTO//
	private int turno;
	private Cor jogadorAtual;
	private Tabuleiro tabuleiro;
	private boolean xeque;
	private boolean xequeMate;
	private PecaXadrez enPassantVulneravel;
	private PecaXadrez promocao;

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

	public PecaXadrez getEnPassantVulneravel() {
		return enPassantVulneravel;
	}
	public PecaXadrez getPromocao() {
		return promocao;
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
				
		if (testXeque(jogadorAtual)) {
			desfazerMovimento(origem, destino, capturarPeca);
			throw new XadrezException("Voc?? n??o pode se colocar em xeque. ");
		}

		PecaXadrez pecaMovida = (PecaXadrez) tabuleiro.peca(destino);

		//#Movimento Especial Promo????o
		promocao = null;
		if(pecaMovida instanceof Peao) {
			if((pecaMovida.getCor() == Cor.BRANCO && destino.getLinha() == 0 || pecaMovida.getCor() == Cor.PRETO && destino.getLinha() == 7 )){
				promocao = (PecaXadrez)tabuleiro.peca(destino);
				promocao = trocaPecaPromovida("D");
			}
		}
		
		xeque = (testXeque(oponente(jogadorAtual))) ? true : false;
		if (testXequeMate(oponente(jogadorAtual))) {
			xequeMate = true;
		} else {
			proximoTurno();
		}
		
		// #Movimento Especial En Passant
		if(pecaMovida instanceof Peao && (destino.getLinha() == origem.getLinha() -2 || destino.getLinha() == origem.getLinha() +2)) {
			enPassantVulneravel = pecaMovida;
		}
		else {
			enPassantVulneravel = null;
		}
		
		return (PecaXadrez) capturarPeca;
	}
	
	public PecaXadrez trocaPecaPromovida(String tipo) {
		if(promocao == null) {
			throw new IllegalStateException("N??o h?? pe??a para ser promovida. ");
		}
		if(!tipo.equals("B") && !tipo.equals("C") && !tipo.equals("T") && !tipo.equals("D")) {
			return promocao;
		}
		Posicao pos = promocao.getPosicaoXadrez().Posicionar();
		Peca p = tabuleiro.removePeca(pos);
		pecasNoTabuleiro.remove(p);
		
		PecaXadrez novaPeca = novaPeca(tipo, promocao.getCor());
		tabuleiro.moverPeca(novaPeca, pos);
		pecasNoTabuleiro.add(novaPeca);
		
		return novaPeca;
	}
	
	private PecaXadrez novaPeca(String tipo, Cor cor) {
		if(tipo.equals("B")) return new Bispo(tabuleiro, cor);
		if(tipo.equals("C")) return new Cavalo(tabuleiro, cor);
		if(tipo.equals("T")) return new Torre(tabuleiro, cor);
							 return new Dama(tabuleiro, cor);

	}

	// METODO- OPERACAO - FUNCAO//
	private Peca fazerMovimento(Posicao origem, Posicao destino) {
		PecaXadrez p = (PecaXadrez) tabuleiro.removePeca(origem);
		p.increaseContarMovimento();
		Peca capturarPeca = tabuleiro.removePeca(destino);
		tabuleiro.moverPeca(p, destino);

		if (capturarPeca != null) {
			pecasNoTabuleiro.remove(capturarPeca);
			pecasCapturadas.add(capturarPeca);
		}
		// FAZENDO OS ROQUES
		// #Especial movimento ROQUE PEQUENO
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() + 3);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() + 1);
			PecaXadrez torre = (PecaXadrez) tabuleiro.removePeca(origemT);
			tabuleiro.moverPeca(torre, destinoT);
			torre.increaseContarMovimento();

		}
		// #Especial movimento ROQUE GRANDE
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() - 4);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() - 1);
			PecaXadrez torre = (PecaXadrez) tabuleiro.removePeca(origemT);
			tabuleiro.moverPeca(torre, destinoT);
			torre.increaseContarMovimento();

		}

		// # Movimento Especial En Passant
		if (p instanceof Peao) {
			if (origem.getColuna() != destino.getColuna() && capturarPeca == null) {
				Posicao peaoPosicao;
				if (p.getCor() == Cor.BRANCO) {
					peaoPosicao = new Posicao(destino.getLinha() + 1, destino.getColuna());
				}
				else {
					peaoPosicao = new Posicao(destino.getLinha() - 1, destino.getColuna());
				}
				capturarPeca = tabuleiro.removePeca(peaoPosicao);
				pecasCapturadas.add(capturarPeca);
				pecasNoTabuleiro.remove(capturarPeca);
			}
		}
		
		return capturarPeca;
	}

	// METODO- OPERACAO - FUNCAO//
	private void desfazerMovimento(Posicao origem, Posicao destino, Peca capturarPeca) {
		PecaXadrez p = (PecaXadrez) tabuleiro.removePeca(destino);
		p.decreaseContarMovimento();
		tabuleiro.moverPeca(p, origem);

		if (capturarPeca != null) {
			tabuleiro.moverPeca(capturarPeca, destino);
			pecasCapturadas.remove(capturarPeca);
			pecasNoTabuleiro.add(capturarPeca);
		}

		// DESFAZENDO ROQUES
		// #Especial movimento ROQUE PEQUENO
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() + 3);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() + 1);
			PecaXadrez torre = (PecaXadrez) tabuleiro.removePeca(destinoT);
			tabuleiro.moverPeca(torre, origemT);
			torre.decreaseContarMovimento();

		}
		// #Especial movimento ROQUE GRANDE
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() - 4);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() - 1);
			PecaXadrez torre = (PecaXadrez) tabuleiro.removePeca(destinoT);
			tabuleiro.moverPeca(torre, origemT);
			torre.decreaseContarMovimento();

		}

		//DESFAZENDO En Passant
		// # Movimento Especial En Passant
		if (p instanceof Peao) {
			if (origem.getColuna() != destino.getColuna() && capturarPeca == enPassantVulneravel) {
				PecaXadrez peao = (PecaXadrez)tabuleiro.removePeca(destino);
				Posicao peaoPosicao;
				if (p.getCor() == Cor.BRANCO) {
					peaoPosicao = new Posicao(3, destino.getColuna());
				}
				else {
					peaoPosicao = new Posicao(4, destino.getColuna());
				}
				tabuleiro.moverPeca(peao, peaoPosicao);

			}
		}		
	}

	// METODO- OPERACAO - FUNCAO//
	private void validarOrigemPosicao(Posicao posicao) {
		if (!tabuleiro.haUmaPeca(posicao)) {
			throw new XadrezException("N??o existe pe??a na posi????o de origem. ");
		}
		if (jogadorAtual != ((PecaXadrez) tabuleiro.peca(posicao)).getCor()) {
			throw new XadrezException("A pe??a escolhida n??o ?? sua. ");
		}
		if (!tabuleiro.peca(posicao).existePossiveisMovimentos()) {
			throw new XadrezException("N??o existe movimentos poss??veis para a pe??a escolhida. ");

		}
	}

	// METODO- OPERACAO - FUNCAO//
	private void validarDestinoPosicao(Posicao origem, Posicao destino) {
		if (!tabuleiro.peca(origem).possivelMovimento(destino)) {
			throw new XadrezException("A pe??a escolhida, n??o pode se mover para a posi????o destinada. ");
		}
	}

	// TROCA DE TURNO
	private void proximoTurno() {
		turno++;
		// Express??o condicional ternaria (Se vari??vel for igual (==) , retornar tal
		// (?), sen??o retornar acol??( : ) )
		jogadorAtual = (jogadorAtual == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
	}

	// METODO- OPERACAO - FUNCAO//
	private Cor oponente(Cor cor) {
		// Express??o condicional ternaria (Se vari??vel for igual (==) , retornar tal
		// (?), sen??o retornar acol??( : ) )
		return (cor == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
	}

	// METODO- OPERACAO - FUNCAO//
	private PecaXadrez rei(Cor cor) {
		List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Peca p : list) {
			if (p instanceof Rei) {
				return (PecaXadrez) p;
			}
		}
		throw new IllegalStateException("N??o existe o rei " + cor + " no tabuleiro. ");
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
		if (!testXeque(cor)) {
			return false;
		}
		List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Peca p : list) {
			boolean[][] mat = p.possiveisMovimentos();
			for (int i = 0; i < tabuleiro.getLinhas(); i++) {
				for (int j = 0; j < tabuleiro.getColunas(); j++) {
					if (mat[i][j]) {
						Posicao origem = ((PecaXadrez) p).getPosicaoXadrez().Posicionar();
						Posicao destino = new Posicao(i, j);
						Peca capturarPeca = fazerMovimento(origem, destino);
						boolean testXeque = testXeque(cor);
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
	// MOVER PE??A NA COORDENADA DO TABULEIRO
	private void moverNovaPeca(char coluna, int linha, PecaXadrez peca) {
		tabuleiro.moverPeca(peca, new PosicaoXadrez(coluna, linha).Posicionar());
		pecasNoTabuleiro.add(peca);
	}

	// COORDENADAS DAS PE??AS
	private void toqueInicial() {

		moverNovaPeca('a', 1, new Torre(tabuleiro, Cor.BRANCO));
		moverNovaPeca('b', 1, new Cavalo(tabuleiro, Cor.BRANCO));
		moverNovaPeca('c', 1, new Bispo(tabuleiro, Cor.BRANCO));
		moverNovaPeca('d', 1, new Dama(tabuleiro, Cor.BRANCO));
		moverNovaPeca('e', 1, new Rei(tabuleiro, Cor.BRANCO, this));
		moverNovaPeca('f', 1, new Bispo(tabuleiro, Cor.BRANCO));
		moverNovaPeca('g', 1, new Cavalo(tabuleiro, Cor.BRANCO));
		moverNovaPeca('h', 1, new Torre(tabuleiro, Cor.BRANCO));
		moverNovaPeca('a', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		moverNovaPeca('b', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		moverNovaPeca('c', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		moverNovaPeca('d', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		moverNovaPeca('e', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		moverNovaPeca('f', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		moverNovaPeca('g', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		moverNovaPeca('h', 2, new Peao(tabuleiro, Cor.BRANCO, this));

		moverNovaPeca('a', 8, new Torre(tabuleiro, Cor.PRETO));
		moverNovaPeca('b', 8, new Cavalo(tabuleiro, Cor.PRETO));
		moverNovaPeca('c', 8, new Bispo(tabuleiro, Cor.PRETO));
		moverNovaPeca('d', 8, new Dama(tabuleiro, Cor.PRETO));
		moverNovaPeca('e', 8, new Rei(tabuleiro, Cor.PRETO, this));
		moverNovaPeca('f', 8, new Bispo(tabuleiro, Cor.PRETO));
		moverNovaPeca('g', 8, new Cavalo(tabuleiro, Cor.PRETO));
		moverNovaPeca('h', 8, new Torre(tabuleiro, Cor.PRETO));
		moverNovaPeca('a', 7, new Peao(tabuleiro, Cor.PRETO, this));
		moverNovaPeca('b', 7, new Peao(tabuleiro, Cor.PRETO, this));
		moverNovaPeca('c', 7, new Peao(tabuleiro, Cor.PRETO, this));
		moverNovaPeca('d', 7, new Peao(tabuleiro, Cor.PRETO, this));
		moverNovaPeca('e', 7, new Peao(tabuleiro, Cor.PRETO, this));
		moverNovaPeca('f', 7, new Peao(tabuleiro, Cor.PRETO, this));
		moverNovaPeca('g', 7, new Peao(tabuleiro, Cor.PRETO, this));
		moverNovaPeca('h', 7, new Peao(tabuleiro, Cor.PRETO, this));

	}
}
