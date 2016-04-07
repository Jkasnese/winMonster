package testesFuncionalidades;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import br.uefs.ecomp.winMonster.controller.Controller;
import br.uefs.ecomp.winMonster.exceptions.ArquivoNaoIntegroException;
import br.uefs.ecomp.winMonster.exceptions.ArquivoNaoPodeSerFechadoException;
import br.uefs.ecomp.winMonster.exceptions.ArquivoVazioException;
import br.uefs.ecomp.winMonster.exceptions.StreamVaziaException;
import br.uefs.ecomp.winMonster.util.Arquivo;

public class DescompactacaoTest {

	private Controller controller;
	private Arquivo manipulaArquivo;
	
	
	@Before 
	public void setUp(){
		controller = new Controller();
		manipulaArquivo = new Arquivo();
	}
	
	
	@Test
	public void lerArquivoTest() throws IOException {
		
		File arquivo = new File("arquivo.txt");
		
		String texto = manipulaArquivo.lerArquivo(arquivo);
		String texto2 = "Uma frase aleatoria para teste de leitura";
		
		assertEquals(true, texto.equals(texto2));
		
	}
	

	@Test
	public void lerArquivoVazioTest() throws IOException { 
		File arquivoVazio = new File("arquivoVazio.txt");
		
		try {
			controller.compactarArquivo(arquivoVazio);
		} catch (IOException e) {
			fail();
		} catch (ArquivoVazioException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void descompactarTest() throws IOException, ArquivoVazioException {

		File arquivo = new File("teste.txt");
		
		String texto = manipulaArquivo.lerArquivo(arquivo);
		
		
		try {
			controller.compactarArquivo(arquivo);
		} catch (IOException e) {
			fail();
		}
		
		File arquivoDescompactar = new File("teste.txt.monster");
		
		try {
			controller.descompactarArquivo(arquivoDescompactar);
		} catch (StreamVaziaException e) {
			fail();
		} catch (ArquivoNaoPodeSerFechadoException e) {
			fail();
		} catch (ArquivoNaoIntegroException e) {
			fail();
		}
		
		File arquivo2 = new File("teste(1).txt");
		String texto2 = manipulaArquivo.lerArquivo(arquivo2);
		
		assertEquals(true, texto.equals(texto2));
		
		// depois deleta ambos arquivos teste(1).txt e teste.txt.monster
		arquivoDescompactar.delete();
		arquivo2.delete();
		
	}

}
