package br.ecomp.uefs.winMonster.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Principal {
	
	// Testando leitura de arquivo 
	public static void main (String[] args)
	{
		Scanner scanner = null; 
		try
		{
			File file = new File("arquivo.txt"); // Instancia objeto do tipo File passando como par�metro nome do arquivo a ser aberto (ou diret�rio)
			scanner = new Scanner(file); 

		} catch(FileNotFoundException e){ // Lan�a exception caso o arquivo n�o seja encontrado
			e.printStackTrace();
		}
		
		String texto = ""; // Inicializa uma String vazia
		
		while(scanner.hasNext()) // Enquanto n�o for encontrado um caracter nulo no arquivo
		{
			texto += "" + scanner.nextLine(); // Linhas lidas s�o concatenadas � String texto
		}
		
		scanner.close();
		
		System.out.println(texto); // Printa String lida. Por enquanto, s� para efeito de teste 

	}

}
