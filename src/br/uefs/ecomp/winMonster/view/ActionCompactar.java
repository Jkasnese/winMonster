package br.uefs.ecomp.winMonster.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import br.uefs.ecomp.winMonster.controller.Controller;
import br.uefs.ecomp.winMonster.exceptions.ArquivoVazioException;

/**
 * Classe que implementa a interface ActionListener para indicar qual ação deve ser realizada quando o botaoCompactar for ativado 
 */
public class ActionCompactar implements ActionListener{

	Controller controller = new Controller();
	
	@Override
	public void actionPerformed(ActionEvent e) {

		JOptionPane aviso = new JOptionPane();
		
		JFileChooser seletorDeArquivo = new JFileChooser(); // Cria janela para seleção do arquivo a
													                              // ser compactado
		
		// Retorno indica se usuário selecionou um arquivo ou cancelou
		int retorno = seletorDeArquivo.showOpenDialog(null);
		
		if (retorno == JFileChooser.APPROVE_OPTION) { 
			File arquivo = seletorDeArquivo.getSelectedFile(); // Recebe o arquivo selecionado pelo usuário
			try {
				controller.compactarArquivo(arquivo); // Chama método de compactação de arquivo no Controller
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (ArquivoVazioException e1) {
				
				aviso.showMessageDialog(null, "Arquivo selecionado est� vazio e nao pode ser compactado.");
			}
			
				aviso.showMessageDialog(null, "Compactacao concluida");
		}
			else {
					return;
			}

	}

	
}
