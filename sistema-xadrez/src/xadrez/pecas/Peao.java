package xadrez.pecas;

import jogo_tabuleiro.Posicao;
import jogo_tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Peao extends PecaXadrez {

	public Peao(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public boolean[][] possiveisMovimentos() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

		Posicao p = new Posicao(0, 0);

		// ACIMA
		if (getCor() == Cor.BRANCO) {
			p.setValores(posicao.getLinha() - 1, posicao.getColuna());
			if (getTabuleiro().posicaoExistente(p) && !getTabuleiro().haUmaPeca(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posicao.getLinha() - 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() - 1, posicao.getColuna());
			if (getTabuleiro().posicaoExistente(p) && !getTabuleiro().haUmaPeca(p)
					&& getTabuleiro().posicaoExistente(p2) && !getTabuleiro().haUmaPeca(p2)
					&& getContarMovimento() == 0) {
				mat[p.getLinha()][p.getColuna()] = true;
			}

			// NOROESTE
			p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
			if (getTabuleiro().posicaoExistente(p) && haUmaPecaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;

			}

			// NORDESTE
			p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
			if (getTabuleiro().posicaoExistente(p) && haUmaPecaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;

			}
		} else {
			p.setValores(posicao.getLinha() + 1, posicao.getColuna());
			if (getTabuleiro().posicaoExistente(p) && !getTabuleiro().haUmaPeca(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posicao.getLinha() + 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() + 1, posicao.getColuna());
			if (getTabuleiro().posicaoExistente(p) && !getTabuleiro().haUmaPeca(p)
					&& getTabuleiro().posicaoExistente(p2) && !getTabuleiro().haUmaPeca(p2)
					&& getContarMovimento() == 0) {
				mat[p.getLinha()][p.getColuna()] = true;
			}

			// NOROESTE
			p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
			if (getTabuleiro().posicaoExistente(p) && haUmaPecaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;

			}

			// NORDESTE
			p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
			if (getTabuleiro().posicaoExistente(p) && haUmaPecaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;

			}

		}

		return mat;
	}
	
	@Override
	public String toString() {
		return "P";
	}

}
