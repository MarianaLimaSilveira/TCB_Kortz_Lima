package br.edu.ifpr.view;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class FilePicker {

    public static String selecionarFotoPerfil() {
        JFileChooser chooser = new JFileChooser();
        
        // filtro pra só imagens
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Imagens (JPG, PNG)", "jpg", "jpeg", "png");
        chooser.setFileFilter(filter);

        int resultado = chooser.showOpenDialog(null);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            File arquivo = chooser.getSelectedFile();
            return arquivo.getAbsolutePath(); // 👑 só o caminho
        } else {
            return null; // cancelou
        }
    }
}