package br.uefs.ecomp.winMonster.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import br.uefs.ecomp.winMonster.controller.Controller;
import br.uefs.ecomp.winMonster.exceptions.ArquivoNaoIntegroException;
import br.uefs.ecomp.winMonster.exceptions.ArquivoNaoPodeSerFechadoException;
import br.uefs.ecomp.winMonster.exceptions.StreamVaziaException;

public class ActionDescompactar implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Controller controller = new Controller();
		
			JFileChooser seletorDeArquivo = new JFileChooser(); // Cria janela para seleção do arquivo a
														// ser compactado
			
			// Filtra para somente compactar arquivos monster
			seletorDeArquivo.setFileFilter(new FileNameExtensionFilter("Arquivos Monster", "monster"));
			
			// Retorno indica se usuário selecionou um arquivo ou cancelou
			int retorno = seletorDeArquivo.showOpenDialog(null);
			
			if (retorno == JFileChooser.APPROVE_OPTION) { 
				File arquivo = seletorDeArquivo.getSelectedFile(); // Recebe o arquivo selecionado pelo usuário
				try {
					controller.descompactarArquivo(arquivo);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (StreamVaziaException e) {
					e.printStackTrace();
				} catch (ArquivoNaoPodeSerFechadoException e) {
					e.printStackTrace();
				} catch (ArquivoNaoIntegroException e) {
			//		Pop janela dizendo que arquivo nao foi 100% integro
					JOptionPane.showMessageDialog(null, "Arquivo que foi descompactado esta corrompido.");
				}
				
				JOptionPane.showMessageDialog(null, "Descompactacao concluida!");
			}
				else {
					return;
				}

		}
		
	}


