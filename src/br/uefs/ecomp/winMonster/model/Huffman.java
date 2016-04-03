package br.uefs.ecomp.winMonster.model;

import br.uefs.ecomp.winMonster.util.FilaPrioritaria;
import br.uefs.ecomp.winMonster.util.Iterador;
import br.uefs.ecomp.winMonster.util.No;

public class Huffman {
	
	
	/**
	 * Constroi vetor de frequencias dos caracteres em um determinado texto
	 */
		public int[] construirVetorFreq(String texto){
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
		Iterador iterador;
		int index;

		for (char c = 0; c < 256; c++){
			if (ascii[c] > 0){ // Se o char for usado
				index = 0;
				iterador = fila.iterador();
				No no = new No(null, null, ascii[c], c); // Cria No do char
				if (fila.estaVazia()){ // Se fila de Nos estiver vazia, insere na primeira posicao
					fila.inserirOrdenado(true, 1, 3, 2, no);
				} else{ // Caso contrario percorre a fila até achar o local certo de inserção.
					while (!(fila.inserirOrdenado(true, index++, ((No) iterador.obterProximo()).getFreq(), ascii[c], no))){} // Insere na fila
				}
			}
		}
		
		while (fila.obterTamanho() > 1){
			index = 0;
			iterador = fila.iterador();
			No menor = (No) fila.removerInicio();
			No segundoMenor = (No)  fila.removerInicio();
			No pai = new No(menor, segundoMenor, menor.getFreq() + segundoMenor.getFreq(), '\0');
			while (!(fila.inserirOrdenado(true, index++, ((No) iterador.obterProximo()).getFreq(), pai.getFreq(), pai))){}
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
	
	
	public void compactar(){

		No raiz;
		raiz = construirArvore(construirVetorFreq());
		
		String[] codigos;
		codigos = codificarArvore(raiz);
		
		
		// Escrever a arvore no arquivo
		
		// escrever tamanho?
		
		// Escrever codigo
		
		
		
		
	}
}
