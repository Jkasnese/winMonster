package br.uefs.ecomp.winMonster.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

import br.uefs.ecomp.winMonster.controller.Controller;

public class ActionDescompactar implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Controller controller = new Controller();
		
			JFileChooser seletorDeArquivo = new JFileChooser(); // Cria janela para seleção do arquivo a
														// ser compactado
			
			// Retorno indica se usuário selecionou um arquivo ou cancelou
			int retorno = seletorDeArquivo.showOpenDialog(null);
			
			if (retorno == JFileChooser.APPROVE_OPTION) { 
				File arquivo = seletorDeArquivo.getSelectedFile(); // Recebe o arquivo selecionado pelo usuário
				controller.descompactarArquivo(arquivo);
			}
				else {
					return;
				}

		}
		
	}


