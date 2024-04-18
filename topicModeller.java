package casey;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


public class topicModeller extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField textField1;
    private JTextField textField2;
    private FileSelector fileSelector1;
    private FileSelector fileSelector2;
    private JLabel similarityLabel; // Added JLabel to display similarity percentage

    public topicModeller() {
        setTitle("Topic Modeller");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();

        JLabel label1 = new JLabel("Selected File 1:");
        panel.add(label1);

        textField1 = new JTextField(20);
        textField1.setEditable(false);
        panel.add(textField1);

        fileSelector1 = new FileSelector(textField1);
        JButton button1 = new JButton("Select File 1");
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileSelector1.selectFile();
            }
        });
        panel.add(button1);

        JLabel label2 = new JLabel("Selected File 2:");
        panel.add(label2);

        textField2 = new JTextField(20);
        textField2.setEditable(false);
        panel.add(textField2);

        fileSelector2 = new FileSelector(textField2);
        JButton button2 = new JButton("Select File 2");
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileSelector2.selectFile();
            }
        });
        panel.add(button2);

        JButton compareButton = new JButton("Compare Files");
        compareButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                compareFiles();
            }
        });
        panel.add(compareButton);

        similarityLabel = new JLabel(); // Initialize the JLabel
        panel.add(similarityLabel); // Add the JLabel to the panel

        add(panel);
        setVisible(true);
    }

    private void compareFiles() {
        String file1Path = textField1.getText();
        String file2Path = textField2.getText();

        try {
            BufferedReader reader1 = new BufferedReader(new FileReader(file1Path));
            BufferedReader reader2 = new BufferedReader(new FileReader(file2Path));

            Set<String> words1 = getWords(reader1);
            Set<String> words2 = getWords(reader2);

            int intersectionSize = 0;
            for (String word : words1) {
                if (words2.contains(word)) {
                    intersectionSize++;
                }
            }

            double similarityPercentage = (double) intersectionSize / (words1.size() + words2.size() - intersectionSize) * 100;
            similarityLabel.setText("Similarity Percentage: " + similarityPercentage + "%"); // Set the text of the JLabel

            reader1.close();
            reader2.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private Set<String> createStopWordsSet() {
        Set<String> stopWords = new HashSet<>();
        //add stop worrds to list
        stopWords.add("a");
        stopWords.add("an");
        stopWords.add("and");
        stopWords.add("are");
        stopWords.add("as");
        stopWords.add("at");
        stopWords.add("be");
        stopWords.add("by");
        stopWords.add("for");
        stopWords.add("from");
        stopWords.add("has");
        stopWords.add("have");
        stopWords.add("in");
        stopWords.add("is");
        stopWords.add("it");
        stopWords.add("its");
        stopWords.add("of");
        stopWords.add("on");
        stopWords.add("that");
        stopWords.add("the");
        stopWords.add("to");
        stopWords.add("was");
        stopWords.add("were");
        stopWords.add("will");
        stopWords.add("with");

       
        return stopWords;
    }

    private Set<String> getWords(BufferedReader reader) throws IOException {
        Set<String> words = new HashSet<>();
        Set<String> stopWords = createStopWordsSet(); // Create set of stop words

        String line;
        while ((line = reader.readLine()) != null) {
            String[] lineWords = line.split("\\s+");
            for (String word : lineWords) {
                if (!stopWords.contains(word.toLowerCase())) { // Check if word is not a stop word
                    words.add(word.toLowerCase()); // Add lowercase version of word to words set
                }
            }
        }
        return words;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new topicModeller();
            }
        });
    }

}