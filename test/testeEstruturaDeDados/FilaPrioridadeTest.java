package testeEstruturaDeDados;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.uefs.ecomp.winMonster.util.FilaPrioritaria;
import br.uefs.ecomp.winMonster.util.Iterador;
import br.uefs.ecomp.winMonster.util.MeuIterador;
import br.uefs.ecomp.winMonster.util.No;


public class FilaPrioridadeTest {


	@Test
	public void testInserirEmFilaPrioridadeSucesso(){
		
		FilaPrioritaria fila = new FilaPrioritaria();
		
		No no = new No();
		no.setFreq(4);
		
		int index = 0;
		MeuIterador iterador = (MeuIterador) fila.iterador();
		int freq = no.getFreq();
		
		if (fila.estaVazia()){ // Se fila de Nos estiver vazia, insere na primeira posicao
			fila.inserirOrdenado(true, 1, 3, 2, no);
		} else{ // Caso contrario percorre a fila até achar o local certo de inserção.
			while (!(fila.inserirOrdenado(true, ++index, freq, no.getFreq(), no)) && iterador.obterProximo() != null){
				freq = ((No)iterador.getAtual().getElemento()).getFreq();
				
		}
		
		iterador = (MeuIterador) fila.iterador();	
		No no2 = new No();
		no2.setFreq(5);
		
		if (fila.estaVazia()){ 
			fila.inserirOrdenado(true, 1, 3, 2, no2);
		} else{ 
			while (!(fila.inserirOrdenado(true, ++index, freq, no2.getFreq(), no2)) && iterador.obterProximo() != null){
				freq = ((No)iterador.getAtual().getElemento()).getFreq();
				
		}
		}
		
		iterador = (MeuIterador) fila.iterador();	
		No no3 = new No();
		no3.setFreq(1);
		
		if (fila.estaVazia()){ 
			fila.inserirOrdenado(true, 1, 3, 2, no3);
		} else{ 
			while (!(fila.inserirOrdenado(true, ++index, freq, no3.getFreq(), no3)) && iterador.obterProximo() != null){
				freq = ((No)iterador.getAtual().getElemento()).getFreq();
		
		}
		
		assertEquals(3, fila.obterTamanho());
		
		Iterador i = fila.iterador(); 
		
		No noRecuperado = (No) i.obterProximo();
		assertEquals(1, noRecuperado.getFreq());

		noRecuperado = (No) i.obterProximo();
		assertEquals(4, noRecuperado.getFreq());
	
		noRecuperado = (No) i.obterProximo();
		assertEquals(5, noRecuperado.getFreq());
	}
	
}
}
}
