package testesFuncionalidades;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import br.uefs.ecomp.winMonster.controller.Controller;
import br.uefs.ecomp.winMonster.exceptions.ArquivoNaoPodeSerFechadoException;
import br.uefs.ecomp.winMonster.exceptions.StreamVaziaException;
import br.uefs.ecomp.winMonster.util.Arquivo;

public class DescompactacaoTest {

	@Test
	public void test() throws IOException {
			
		Controller controller = new Controller();
		Arquivo manipulaArquivo = new Arquivo();
		
		
		File arquivo = new File("teste.txt");
		
		String texto = manipulaArquivo.lerArquivo(arquivo);
		
		
		try {
			controller.compactarArquivo(arquivo);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		File arquivoDescompactar = new File("teste.txt.monster");
		
		try {
			controller.descompactarArquivo(arquivoDescompactar);
		} catch (StreamVaziaException e) {
			e.printStackTrace();
		} catch (ArquivoNaoPodeSerFechadoException e) {
			e.printStackTrace();
		}
		
		File arquivo2 = new File("teste(1).txt");
		String texto2 = manipulaArquivo.lerArquivo(arquivo2);
		
		assertEquals(true, texto.equals(texto2));
		
	}

}
