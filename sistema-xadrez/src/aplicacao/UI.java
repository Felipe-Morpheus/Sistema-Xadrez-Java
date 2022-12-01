package aplicacao;

import java.util.InputMismatchException;
import java.util.Scanner;

import xadrez.Cor;
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
	public static void printTabuleiro(PecaXadrez[][] pecas) {
		for (int i = 0; i < pecas.length; i++) {
			System.out.print((8 - i) + " ");

			for (int j = 0; j < pecas.length; j++) {
				printPeca(pecas[i][j]);
			}
			System.out.println();
		}

		System.out.println("  a b c d e f g h");
	}

	// METODO-FUNCAO//
	private static void printPeca(PecaXadrez peca) {
		if (peca == null) {
			System.out.print("-");
		} else {
			if (peca.getCor() == Cor.BRANCO) {
				System.out.println(ANSI_AMARELO + peca + ANSI_REDEFINIR);
			}
		}
		System.out.print(" ");
	}
}
