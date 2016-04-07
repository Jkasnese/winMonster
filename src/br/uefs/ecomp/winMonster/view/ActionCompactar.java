package br.uefs.ecomp.winMonster.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

import br.uefs.ecomp.winMonster.controller.Controller;

/**
 * Classe que implementa a interface ActionListener para indicar qual ação deve ser realizada quando o botaoCompactar for ativado 
 */
public class ActionCompactar implements ActionListener{

	Controller controller = new Controller();
	
	@Override
	public void actionPerformed(ActionEvent e) {

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
			}
			
				JOptionPane avisoConclusao = new JOptionPane();
				avisoConclusao.showMessageDialog(null, "Compactação concluída");
		}
			else {
			// Faz não sei que não sei que lá se tiver cancelado
		}

	}

	
}
