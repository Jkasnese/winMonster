package br.uefs.ecomp.winMonster.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import br.uefs.ecomp.winMonster.exceptions.ArquivoNaoPodeSerFechadoException;
import br.uefs.ecomp.winMonster.exceptions.StreamVaziaException;
import br.uefs.ecomp.winMonster.util.BinarioIn;
import br.uefs.ecomp.winMonster.util.BinarioOut;
import br.uefs.ecomp.winMonster.util.FilaPrioritaria;
import br.uefs.ecomp.winMonster.util.MeuIterador;
import br.uefs.ecomp.winMonster.util.No;

public class Huffman {
	
	// Baseado no site http://www.ime.usp.br/~pf/estruturas-de-dados/aulas/huffman.html
	
	/**
	 * Constroi vetor de frequencias dos caracteres em uma determinada String
	 */
	private int[] construirVetorFreq(String texto){
		char c = 0;
		int[] ascii = new int[256];

		for(int i = 0; i < texto.length(); i++)
		{
			c = texto.charAt(i);
			ascii[c]++;
		}
		return ascii;
	}
	
	/**
	 * Constroi arvore de Huffman conforme vetor de frequencias
	 * @return
	 */
	private No construirArvore(int[] ascii){
		
		FilaPrioritaria fila = new FilaPrioritaria();
		MeuIterador iterador;
		int index;

		for (char c = 0; c < 256; c++){
			if (ascii[c] > 0){ // Se o char for usado
				index = 0;
				iterador = (MeuIterador) fila.iterador();
				No no = new No(ascii[c], c, null, null); // Cria No do char
				int freq = 0;
				if (fila.estaVazia()){ // Se fila de Nos estiver vazia, insere na primeira posicao
					fila.inserirOrdenado(true, 1, 3, 2, no);
				} else{ // Caso contrario percorre a fila até achar o local certo de inserção.
					while (!(fila.inserirOrdenado(true, ++index, freq, no.getFreq(), no)) && iterador.obterProximo() != null){
						freq = ((No)iterador.getAtual().getElemento()).getFreq();
				}
				
					} // Insere na fila
				}
			}

		
		while (fila.obterTamanho() > 1){
			index = 0;
			iterador = (MeuIterador) fila.iterador();
			No menor = (No) fila.removerInicio();
			No segundoMenor = (No)  fila.removerInicio();
			No pai = new No(menor.getFreq() + segundoMenor.getFreq(), '\0', menor, segundoMenor);
			int freq = 0;
			
			while (!(fila.inserirOrdenado(true, ++index, freq, pai.getFreq(), pai)) && iterador.obterProximo() != null){
				freq =((No)iterador.getAtual().getElemento()).getFreq();
			}
		}
		
		return (No) fila.removerInicio();
	}
	
	/**
	 * Constroi codigos para todos os caracteres da arvore
	 * @param raiz
	 * @return
	 */
	private String[] codificarArvore(No raiz){
		
		String[] codigos = new String[256];
		codificarNo(codigos, raiz, "");
		return codigos;
	}
	
	/**
	 * Constroi codigo para um no de uma arvore, porem no final percorre todos
	 * @param codigos
	 * @param no
	 * @param codigo
	 */
	private void codificarNo(String[] codigos, No no, String codigo){
		if (no.ehFolha()){
			codigos[no.getCh()] = codigo;
			return;
		}
		codificarNo(codigos, no.getNoEsquerda(), codigo + '0');
		codificarNo(codigos, no.getNoDireita(), codigo + '1');
	}
	
	private void escreverArvore(No raiz, BinarioOut arquivoSaida){
		if (raiz.ehFolha()){
			arquivoSaida.escrever(true);
			arquivoSaida.escrever(raiz.getCh());
			return;
		}
		arquivoSaida.escrever(false);
		escreverArvore(raiz.getNoEsquerda(), arquivoSaida);
		escreverArvore(raiz.getNoDireita(), arquivoSaida);
	}
	
	
	public void compactar(String texto, File arquivoSaida, int hash) throws FileNotFoundException{

		//Constroi arvore
		No raiz;
		raiz = construirArvore(construirVetorFreq(texto));
		
		// Constroi String com codigos da arvore
		String[] codigos;
		codigos = codificarArvore(raiz);
		
		// Printando mensagem só pra saber até onde tá rodando. APAGAR DEPOIS
		System.out.println("Chegamos aqui");
		

		// Cria instancia de BinarioOut pra poder escrever no arquivo de saida (compactado)
		BinarioOut compactado = new BinarioOut(arquivoSaida);
		

		// Escrever tamanho
		compactado.escrever(texto.length());
		
		// Escreve funcao hash
		compactado.escrever(hash);
		
		// Escreve a arvore no arquivo
		escreverArvore(raiz, compactado);
		
		
		// Escrever codigo
		char[] entrada = texto.toCharArray();
		String codigo;
		for (int i = 0; i < entrada.length; i++) {
		      codigo = codigos[entrada[i]];
		      for (int j = 0; j < codigo.length(); j++)
		      if (codigo.charAt(j) == '1')
		           compactado.escrever(true);
		      else compactado.escrever(false);
		}
		
		
		// Fecha arquivo escrevendo tudo que continha no buffer
		compactado.fechar();
	}
	
	private No leArvore(BinarioIn descompactando) throws StreamVaziaException{
		if (descompactando.lerBit()){
			char c = descompactando.lerChar();
			return new No (0, c, null, null);
		}
		return new No(0, '\0', leArvore(descompactando), leArvore(descompactando));
	}
	
	public int descompactar(File arquivoEntrada, File arquivoSaida) throws StreamVaziaException, IOException, ArquivoNaoPodeSerFechadoException{ 
		
		// Cria instancia de BinarioIn pra poder ler arquivo compactado e descompactar (descompactando)
		BinarioIn descompactando = new BinarioIn(new FileInputStream (arquivoEntrada));
		
		// Cria instancia arquivo de saida normal
		BufferedWriter escreve = new BufferedWriter(new FileWriter(arquivoSaida));

		
		// Le tamanho
		int n = descompactando.lerInt();
		
		// Le funcao hash
		int hash = descompactando.lerInt();
		
		// Le arvore
		No raiz = leArvore(descompactando);
		
		// Le codigo
	   for (int i = 0; i < n; i++) {  // decodifica próximo caractere
		   No x = raiz;
		   while (!x.ehFolha()){
			   if (descompactando.lerBit())  
	        	 x = x.getNoDireita();
			   else
	        	 x = x.getNoEsquerda();
		   }
		   escreve.write(x.getCh());
		}
		   
		// Fecha arquivo que foi lido ao descompactar e fecha arquivo de saida
	   descompactando.fechar();
	   escreve.close();
		
		return hash;
	}
}
