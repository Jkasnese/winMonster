package br.uefs.ecomp.winMonster.controller;


import java.io.File;
import java.io.IOException;

import br.uefs.ecomp.winMonster.exceptions.ArquivoNaoIntegroException;
import br.uefs.ecomp.winMonster.exceptions.ArquivoNaoPodeSerFechadoException;
import br.uefs.ecomp.winMonster.exceptions.ArquivoVazioException;
import br.uefs.ecomp.winMonster.exceptions.StreamVaziaException;
import br.uefs.ecomp.winMonster.model.Huffman;
import br.uefs.ecomp.winMonster.util.Arquivo;


public class Controller {
	
	Arquivo manipulaArquivo = new Arquivo();
	Huffman arvoreHuffman = new Huffman();
	
	public int funcaoHash(String string){
		
		int codigoHash = 0; 
		int i = 0;
		
		for (i = 0; i < string.length(); i++) 
		{
			char caractere = string.charAt(i); 
			int ascii = caractere; 
			
			codigoHash += (ascii*2 + i); 
		}
		
		return codigoHash/i;
	}
	
	// Chama métodos da classe Huffman para criação da árvore e compactação do arquivo
	public void compactarArquivo(File arquivo) throws IOException, ArquivoVazioException{

		// Caso arquivo esteja vazio
		if (arquivo.length() == 0){
			throw new ArquivoVazioException("Voce tentou compactar um arquivo vazio!");
		}
		
		// Le arquivo e transfere pra String texto
		String texto = manipulaArquivo.lerArquivo(arquivo);
		int hash = funcaoHash(texto);
		
		// Cria novo arquivo, que será usado pra saída do arquivo compactado
		File saida = new File (arquivo.getAbsolutePath() + ".monster");
		
		// Caso consiga criar novo arquivo, compacta.
		if (saida.createNewFile()){
			// Compacta
			arvoreHuffman.compactar(texto, saida, hash);
		} else{ // Caso não consiga, pede ao usuário pra digitar nome de saída do arquivo 
					
			// Modifica nome do arquivo a ser criado
			int i = 1;

			String[] nomes = arquivo.getAbsolutePath().split("\\.");
			
			do {
				saida = new File(nomes[0] + "(" + i +")" + "." + nomes[1] + ".monster");
				i++;
			} while(!saida.createNewFile());
			
			// Apos conseguir criar arquivo, compacta
			arvoreHuffman.compactar(texto, saida, hash);
		}
	}

	public void descompactarArquivo(File arquivoDescompactar) throws IOException, StreamVaziaException, ArquivoNaoPodeSerFechadoException, ArquivoNaoIntegroException{
		
		// Instancia novo arquivo. Substitui .monster por "", para que arquivo volte à extensão original
		File arquivo = new File(arquivoDescompactar.getAbsolutePath().replace(".monster", ""));
		
		// Caso consiga criar novo arquivo, descompacta
		if (arquivo.createNewFile()){
			descompactaAposCriacaoDeArquivo(arquivoDescompactar, arquivo);
		} else { // Caso nao consiga criar novo arquivo
						// Modifica nome do arquivo
			int i = 1;

			String[] nomes = arquivoDescompactar.getAbsolutePath().split("\\.");
			
			do {
				arquivo = new File(nomes[0] + "(" + i +")" + "." + nomes[1]);
				i++;
			} while(!arquivo.createNewFile());
			
			// Apos conseguir criar arquivo, descompacta
			descompactaAposCriacaoDeArquivo(arquivoDescompactar, arquivo);

		}
	}
	
		
	private void descompactaAposCriacaoDeArquivo(File arquivoDescompactar, File arquivo) throws IOException, StreamVaziaException, ArquivoNaoPodeSerFechadoException, ArquivoNaoIntegroException{
		
		// Descompacta
		int primeiroHash = arvoreHuffman.descompactar(arquivoDescompactar, arquivo);
		
		// Verifica integridade
		String textoDescompactado = manipulaArquivo.lerArquivo(arquivo);
		int segundoHash = funcaoHash(textoDescompactado);


		if (!(primeiroHash == segundoHash)){ // Caso hashes nao sejam iguais
			throw new ArquivoNaoIntegroException("A descompactacao nao foi cem por cento fiel ao arquivo original!");
		}
	}	
}
