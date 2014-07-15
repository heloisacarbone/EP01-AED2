/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaceGrafica;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Favoretti
 */
public class Filtro extends FileFilter{

    @Override
        public boolean accept(File file) {
            // Allow only directories, or files with ".txt" extension
            
        return file.isDirectory() || (file.getAbsolutePath().endsWith(".txt")) || (file.getAbsolutePath().endsWith(".xml")) ;
            
        }
        @Override
        public String getDescription() {
            // This description will be displayed in the dialog,
            // hard-coded = ugly, should be done via I18N
            return "Text documents (*.txt), XML documents (*.xml)";
        }
    
}
