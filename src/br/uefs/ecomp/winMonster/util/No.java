package br.uefs.ecomp.winMonster.util;

/**
 * @author guiga
 *
 */
public class No implements Comparable<No> {

	private No noDireita;
	private No noEsquerda;
	private int freq;
	private char ch;
	
	public No (){
	}
	
	public No (int freq, char ch, No noEsquerda, No noDireita)
	{
		this.noDireita = noDireita;
		this.noEsquerda = noEsquerda;
		this.freq = freq;
		this.setCh(ch);
	}
	
	public boolean ehFolha()
	{
		return noEsquerda == null && noDireita == null;
	}
	
	public char getCh() {
		return ch;
	}

	public void setCh(char ch) {
		this.ch = ch;
	}
	
	public void setFreq(int freq)
	{
		this.freq = freq;
	}
	
	public int getFreq(){
		return freq;
	}

	public No getNoDireita() {
		return noDireita;
	}

	public No getNoEsquerda() {
		return noEsquerda;
	}
	
	@Override
	public int compareTo(No noRecebido) {
		
		int a = this.freq;
		int b = noRecebido.freq;
		
		if(a<b) 
			return 1;
		if(b<a)
			return -1;
		return 0;
	}


	
	
}