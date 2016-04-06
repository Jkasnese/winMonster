package br.uefs.ecomp.winMonster.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class Arquivo {
	

	public String lerArquivo(File arquivo) throws IOException
	{
			BufferedReader ler = new BufferedReader(new FileReader(arquivo));
			
			String aux = ""; // Inicializa uma String vazia
			StringBuffer texto = new StringBuffer();
			
			// Enquanto não for encontrado caractere nulo, percorre o arquivo 
			while((aux = ler.readLine()) != null)
			{
				texto.append(aux).append("\n"); // Linhas lidas são concatenadas à String texto
			}
			ler.close();
			
			// Printa String lida, só para efeito de teste. APAGAR DEPOIS
			System.out.println(texto);  
			return texto.toString();
	}	
	

	
}
