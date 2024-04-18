package casey;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JTextField;

public class FileSelector {
	private JTextField textField;

	   public FileSelector(JTextField textField) {
	       this.textField = textField;
	   }

	   public void selectFile() {
	       JFileChooser fileChooser = new JFileChooser();
	       int returnValue = fileChooser.showOpenDialog(null);
	       if (returnValue == JFileChooser.APPROVE_OPTION) {
	           File selectedFile = fileChooser.getSelectedFile();
	           textField.setText(selectedFile.getAbsolutePath());
	       }
	   }
}
