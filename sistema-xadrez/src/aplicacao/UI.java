package aplicacao;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import xadrez.Cor;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;

public class UI {

	// METODO-FUNCAO//
	// https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println

	public static final String ANSI_REDEFINIR = "\u001B[0m";
	public static final String ANSI_PRETO = "\u001B[30m";
	public static final String ANSI_VERMELHO = "\u001B[31m";
	public static final String ANSI_VERDE = "\u001B[32m";
	public static final String ANSI_AMARELO = "\u001B[33m";
	public static final String ANSI_AZUL = "\u001B[34m";
	public static final String ANSI_ROXO = "\u001B[35m";
	public static final String ANSI_CIANO = "\u001B[36m";
	public static final String ANSI_BRANCO = "\u001B[37m";

	public static final String ANSI_PRETO_BACKGROUND = "\u001B[40m";
	public static final String ANSI_VERMELHO_BACKGROUND = "\u001B[41m";
	public static final String ANSI_VERDE_BACKGROUND = "\u001B[42m";
	public static final String ANSI_AMARELO_BACKGROUND = "\u001B[43m";
	public static final String ANSI_AZUL_BACKGROUND = "\u001B[44m";
	public static final String ANSI_ROXO_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CIANO_BACKGROUND = "\u001B[46m";
	public static final String ANSI_BRANCO_BACKGROUND = "\u001B[47m";

	// METODO-FUNCAO//
	// https://stackoverflow.com/questions/2979383/java-clear-the-console
	public static void limparTela() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	// METODO-FUNCAO//
	public static PosicaoXadrez leiaPosicaoXadrez(Scanner ler) {
		try {
			String s = ler.nextLine();
			char coluna = s.charAt(0);
			int linha = Integer.parseInt(s.substring(1));
			return new PosicaoXadrez(coluna, linha);
		} catch (RuntimeException e) {
			throw new InputMismatchException("Erro lendo posica PosicaoXadrez. Valores válido são de 'a1' até 'h8' ");
		}

	}

	// METODO-FUNCAO//
	public static void printPartida(PartidaXadrez partidaXadrez, List<PecaXadrez> capturado) {
		printTabuleiro(partidaXadrez.getPecas());
		System.out.println();
		printCapturarPecas(capturado);
		System.out.println();
		System.out.println("Turno: " + partidaXadrez.getTurno());
		if (!partidaXadrez.getXequeMate()) {
			System.out.println("Aguardando jogador: " + partidaXadrez.getJogadorAtual());
			if(partidaXadrez.getXeque()) {
				System.out.println("XEQUE! ");
			}
		}
		else {
			System.out.println("XEQUE-MATE! ");
			System.out.println("Vencedor: " + partidaXadrez.getJogadorAtual());
		}
	}

	public static void printTabuleiro(PecaXadrez[][] pecas) {
		for (int i = 0; i < pecas.length; i++) {
			System.out.print((8 - i) + " ");

			for (int j = 0; j < pecas.length; j++) {
				printPeca(pecas[i][j], false);
			}
			System.out.println();
		}

		System.out.println("  a b c d e f g h");
	}

	// METODO-FUNCAO//
	public static void printTabuleiro(PecaXadrez[][] pecas, boolean[][] possiveisMovimentos) {
		for (int i = 0; i < pecas.length; i++) {
			System.out.print((8 - i) + " ");

			for (int j = 0; j < pecas.length; j++) {
				printPeca(pecas[i][j], possiveisMovimentos[i][j]);
			}
			System.out.println();
		}

		System.out.println("  a b c d e f g h");
	}

	// METODO-FUNCAO//
	private static void printPeca(PecaXadrez peca, boolean fundo) {
		if (fundo) {
			System.out.print(ANSI_AZUL_BACKGROUND);
		}
		if (peca == null) {
			System.out.print("-" + ANSI_REDEFINIR);
		} else {
			if (peca.getCor() == Cor.BRANCO) {
				System.out.println(ANSI_AMARELO + peca + ANSI_REDEFINIR);
			}
		}
		System.out.print(" ");
	}

	// METODO FUNÇÃO PARA FILTRAR LISTA
	private static void printCapturarPecas(List<PecaXadrez> capturado) {
		List<PecaXadrez> branco = capturado.stream().filter(x -> x.getCor() == Cor.BRANCO).collect(Collectors.toList());
		List<PecaXadrez> preto = capturado.stream().filter(x -> x.getCor() == Cor.PRETO).collect(Collectors.toList());
		System.out.println("Peças capturadas: ");
		System.out.print("Brancas: ");
		System.out.println(ANSI_BRANCO);
		System.out.println(Arrays.toString(branco.toArray()));// PadraO pra imprimir arrays no java
		System.out.println(ANSI_REDEFINIR);

		System.out.print("Pretas: ");
		System.out.println(ANSI_PRETO);
		System.out.println(Arrays.toString(preto.toArray()));// PadraO pra imprimir arrays no java
		System.out.println(ANSI_REDEFINIR);

	}
}
