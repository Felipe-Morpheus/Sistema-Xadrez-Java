package aplicacao;

import java.util.Scanner;

import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;

public class Programa {

	public static void main(String[] args) {
		
		Scanner ler = new Scanner(System.in);
		PartidaXadrez partidaXadrez = new PartidaXadrez();
		
		while(true) {
		UI.printTabuleiro(partidaXadrez.getPecas());
		System.out.println();
		System.out.print("Origem: ");
		PosicaoXadrez origem = UI.leiaPosicaoXadrez(ler);
				
		System.out.println();
		System.out.print("Destino: ");
		PosicaoXadrez destino = UI.leiaPosicaoXadrez(ler);
		
		PecaXadrez capturarPeca = partidaXadrez.performaMovimentoXadrez(origem, destino);

		}
   }
}