package br.uefs.ecomp.winMonster.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import br.uefs.ecomp.winMonster.exceptions.ArquivoNaoPodeSerFechadoException;
import br.uefs.ecomp.winMonster.exceptions.StreamVaziaException;

public class BinarioIn {

    private BufferedInputStream in;
    private final int EOF = -1;    // end of file

    private int buffer;            // buffer
    private int n;                 // contador de bits significativos no buffer

    
    public BinarioIn(FileInputStream arquivo){
    	 in = new BufferedInputStream(arquivo);
    	 encherBuffer();
    }
	
    private void encherBuffer(){
    	try{
    		buffer = in.read();
    		n = 8;
    	} catch (IOException e){
    		System.out.println("EOF");
    		buffer = EOF;
    		n = -1;
    	}
    }
    
    
    public void fechar() throws ArquivoNaoPodeSerFechadoException{
    	try{
    		in.close();
    	} catch (IOException e){
    		e.printStackTrace();
    		throw new ArquivoNaoPodeSerFechadoException("Arquivo de leitura nao pode ser fechado");
    	}
    }
    
    public boolean estaVazio(){
    	return buffer == EOF;
    }
    
    
    /**
     * Le um bit de informação e retorna o boolean correspondente.
     * @throws StreamVaziaException 
     */
    public boolean lerBit() throws StreamVaziaException{
    	// Se a stream de bytes estiver vazia
    	if(estaVazio()) throw new StreamVaziaException("Tentando ler uma stream vazia!");
    	
    	
    	// Salva o buffer em um byte b, para que o buffer seja preservado
    	byte b = (byte) buffer;
    	
    	// Decrementa o contador do buffer e depois...
    	n--;
//    	Empurra o byte pra direita, complementando com 0s,
//    	e compara se o byte vale 1 (ou seja, se o bit que você quer ler é 1).
//    	Se for 1, bit vai valer true. Caso contrario vale false.
    	boolean bit = ((b >> n) & 1) == 1;
    	
    	
    	// Caso buffer esteja agora vazio, a ser lido
    	if (n==0) encherBuffer();
    	
    	return bit;
    }
    
    
    /**
     * Le um byte
     * @throws StreamVaziaException 
     */
    public byte lerByte() throws StreamVaziaException{ 
    	if(estaVazio()) throw new StreamVaziaException("Tentando ler uma stream vazia!");

        // Caso o buffer esteja cheio (byte alinhado)
        if (n == 8) {
            int x = buffer;
            encherBuffer();
            return (byte) (x & 0xff);
        }

        // Caso não esteja,
        // lê os n bits remanescentes do buffer e os 8-n bits do próximo buffer
        
        // Copia o buffer pra x, pra salvar o final (8-n) do buffer
        int x = buffer;
        x <<= (8 - n);
        
        // Salva n antigo
        int oldN = n;
        
        encherBuffer();
    	if(estaVazio()) throw new StreamVaziaException("Tentando ler uma stream vazia!");
    	
    	// Lê 8-n bits do próximo buffer 
    	for (int i = 0; i < 8-oldN; i++){
    		// Se o bit lido for 1, transforma em 1. Se não, deixa-o como 0.
    		if(lerBit()) x |= 1;
    	}
        return (byte) (x & 0xff);
    }
    
    /**
     * Le um char de 8 bits
     * @throws StreamVaziaException 
     */
    public char lerChar() throws StreamVaziaException{
    	return (char) lerByte();
    }
    
    /**
     * Le um int (4 bytes)
     * @throws StreamVaziaException 
     */
    public int lerInt() throws StreamVaziaException{
    	int x= 0;
    	byte b;
    	for(int i = 0; i < 4; i++){
    		b = lerByte();
    		x <<= 8;
    		x |= b;
    	}
    	return x;
    }
    
}