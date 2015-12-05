package GradeList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by beahildehrandt on 14.11.15.
 */
public class Filehandler {
    public String totPoints;

    private JFrame jFrame;
    private String inFile;


    public Filehandler(JFrame jFrame){
        this.jFrame = jFrame;
    }

    public void saveExam(JTextField totalPoints, DefaultTableModel studentModel, int saveAs){
        String saveString = "";

        for(int i = 0; i < studentModel.getRowCount(); i++) {
            for (int j = 0; j < studentModel.getColumnCount(); j++) {
                if (j == 0) {
                    saveString += studentModel.getValueAt(i, j);
                }
                else{
                    saveString += "," + studentModel.getValueAt(i, j);
                }
            }
            saveString += "\n";
        }
        if(saveAs==1) {
            JFileChooser chooser = new JFileChooser();
            int auswahl = chooser.showSaveDialog(jFrame);
            if (auswahl == JFileChooser.APPROVE_OPTION) ;
            final String file = chooser.getSelectedFile().toString();
            jFrame.setTitle("Notenliste" + " - " + file.substring(file.lastIndexOf("/")).replace("/", ""));
        }
        File ausgabeDatei = new File(inFile);
        try {
            FileWriter writer = new FileWriter(ausgabeDatei);
            BufferedWriter writeBuffer = new BufferedWriter(writer);

            writeBuffer.write("Gesamtpunkte: ");
            writeBuffer.write(totalPoints.getText());
            writeBuffer.write("\n");

            writeBuffer.write(saveString);

            writeBuffer.close();

            JFrame success= new JFrame("Eingabe erfolgreich");
            success.setSize(new Dimension(400, 80));
            success.setLocationRelativeTo(jFrame);

            JLabel testLabel = new JLabel("Klausur erfolgreich gespeichert.\n\n  ");
            testLabel.setHorizontalAlignment(0);
            JButton ok = new JButton("OK");
            ok.setHorizontalAlignment(0);

            JPanel ButtonPanel = new JPanel();
            JPanel WindowPanel = new JPanel(new GridLayout(2,1));
            JPanel WindowPanel2 = new JPanel();

            ButtonPanel.add(ok);
            WindowPanel.setLayout(new BoxLayout(WindowPanel, BoxLayout.PAGE_AXIS));
            WindowPanel.add(testLabel);
            WindowPanel.add(ButtonPanel);
            WindowPanel2.add(WindowPanel);
            success.add(WindowPanel2);
            success.setVisible(true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String[]> loadExam(){
        ArrayList<String[]> temp = new ArrayList<String[]>();

        JFileChooser chooser = new JFileChooser();
        int auswahl = chooser.showOpenDialog(jFrame);
        if(auswahl == JFileChooser.APPROVE_OPTION);
        final String file = chooser.getSelectedFile().toString();

        inFile = file;

        jFrame.setTitle("Notenliste" + " - " + file.substring(file.lastIndexOf("/")).replace("/", ""));
        File eingabeDatei = new File(file);
        try {
            Scanner scanner = new Scanner(eingabeDatei);

            totPoints =  scanner.nextLine();
            totPoints = totPoints.replace("Gesamtpunkte: ","");
            totPoints = totPoints.replace("\n","");

            while(scanner.hasNextLine()) {
                String p1 = scanner.nextLine();
                p1 = p1.replace("\n", "");
                String[] row = p1.split(",");
                temp.add(row);
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return temp;
    }



}
