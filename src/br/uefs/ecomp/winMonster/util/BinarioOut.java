package br.uefs.ecomp.winMonster.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 	Classe baseada na classe BinaryStdOut, encontrada em:
 *	http://algs4.cs.princeton.edu/55compression/BinaryStdOut.java na data 06/04/2016
 *	
 *	Classe que escreve em bits em um determinado arquivo, fornecido como parâmetro no construtor da classe. 
 */
public class BinarioOut {

	private int buffer, n; // Buffer e contador de quantos bits existem no buffer.
    private BufferedOutputStream out;
    
    public BinarioOut(File arquivo) throws FileNotFoundException {
    	this.out = new BufferedOutputStream (new FileOutputStream(arquivo));
    }
    
    
    /**
     * Escreve o bit numa cadeia de bits, que mais tarde será escrita e concatenada no arquivo de saída
     * Recebe o bit que será escrito.
     */
    private void escreverBit (boolean bit){
    	// Move a cadeia de bits para a esquerda, de forma a criar um novo bit.
    	// Este novo bit criado será 0.
    	buffer <<= 1; 
    	
    	// Caso o bit que se deseja escrever seja 1, converte o bit previamente criado em 1.
    	if (bit){
    		buffer |= 1;
    	}
    	
    	// Incrementa numero de bits escritos no buffer
    	n++;
    	
    	// Caso tenha-se enchido o buffer, escreve-se ele no buffer da classe BufferedOutputStream
    	// para posteriormente ser escrito na stream de saída (arquivo)
    	if (n == 8) limparBuffer();
    }
    
    /**
     * Escreve o byte de 8 bits para o buffer da classe BufferedOutputStream0
     */
    private void escreverByte(int x) {
        assert x >= 0 && x < 256;

        // Se não houver nada no buffer, simplesmente escreva o byte
        if (n == 0) {
            try {
                out.write(x);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        // Caso contrário, escreva um bit de cada vez
        boolean bit;
        byte y;
        for (int i = 0; i < 8; i++) {
        	y = (byte) x;
            bit = ((y >>> (8 - i - 1)) & 1) == 1;
            escreverBit(bit);
        }
    }
    
    /**
     * Escreve bits remanescentes no buffer da classe BufferedOutputStream
     */
    private void limparBuffer(){
    	//Caso não exista nada no buffer, não há nada a se fazer
    	if (n == 0) return; 
    	
    	// Completa com a quantidade de zeros à esquerda necessária para encher o buffer
    	if (n >0) buffer <<= (8 - n);  
    	
    	// Escreve bits no buffer da classe BufferedOutputStream
    	try {
    		out.write(buffer);
    	} catch (IOException e){
    		e.printStackTrace();
    	}
    	
    	// Zera buffer e sua contagem
    	n = 0;
    	buffer = 0;
    }
    
    
   /**
    * Escreve forçosamente tudo o que existir no buffer de BinarIO e BufferedOutputStream para o arquivo.
    */
    private void flush(){
    	limparBuffer();
    	try {
    		out.flush();
    	} catch (IOException e){
    		e.printStackTrace();
    	}
    }
    
    /**
     * Método usado para escrever no arquivo, também fechando-o,
     *  pois a compactação é um processo único e definitivo.
     * flush e fecha o arquivo.
     */
    public void fechar(){
    	flush();
    	try {
    		out.close();
    	} catch (IOException e){
    		e.printStackTrace();
    	}
    }
    
    
    /**
     * Escreve o bit
     */
    public void escrever(boolean bit){
    	escreverBit(bit);
    }
    
    /**
     * Escreve um int
     */
    public void escrever(int x){
    	int y;
    	for (int i=3; i > -1; i--){
    		y = x;
    		escreverByte((y >>> (8*i)));
    	}
    }
    
    /**
     * Escreve um char
     * @param c
     */
    public void escrever(char c){
    	escreverByte(c);
    }
}
