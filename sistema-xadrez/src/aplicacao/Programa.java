package aplicacao;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;
import xadrez.XadrezException;

public class Programa {

	public static void main(String[] args) {

		Scanner ler = new Scanner(System.in);
		PartidaXadrez partidaXadrez = new PartidaXadrez();
		List<PecaXadrez> capturado = new ArrayList<>();

		while (!partidaXadrez.getXequeMate()) {
			try {
				UI.limparTela();
				UI.printPartida(partidaXadrez, capturado);
				System.out.println();
				System.out.print("Origem: ");
				PosicaoXadrez origem = UI.leiaPosicaoXadrez(ler);

				boolean[][] possiveisMovimentos = partidaXadrez.possiveisMovimentos(origem);
				UI.limparTela();
				UI.printTabuleiro(partidaXadrez.getPecas(), possiveisMovimentos);
				System.out.println();
				System.out.print("Destino: ");
				PosicaoXadrez destino = UI.leiaPosicaoXadrez(ler);

				PecaXadrez capturarPeca = partidaXadrez.performaMovimentoXadrez(origem, destino);

				if (capturarPeca != null) {
					capturado.add(capturarPeca);
				}
				
				if(partidaXadrez.getPromocao() != null) {
					System.out.print("Entre com a Inicial correspondente a promoção desejada (B/C/T/D). ");
					String tipo = ler.nextLine().toUpperCase();
					while(!tipo.equals("B") && !tipo.equals("C") && !tipo.equals("T") && !tipo.equals("D")) {
						System.out.print("Valor inválido! Entre com a Inicial correspondente a promoção desejada (B/C/T/D). ");
						tipo = ler.nextLine().toUpperCase();
					}
					partidaXadrez.trocaPecaPromovida(tipo);
				}
			
			} catch (XadrezException e) {
				System.out.println(e.getMessage());
				ler.nextLine();
				;
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				ler.nextLine();
				
			}
		}
		UI.limparTela();
		UI.printPartida(partidaXadrez, capturado);
	}
}