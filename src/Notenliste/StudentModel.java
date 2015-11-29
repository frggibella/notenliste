package Notenliste;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by beahildehrandt on 14.11.15.
 */
public class StudentModel {

    public String totPoints;
    private JFrame jFrame;

    public StudentModel(JFrame jFrame){
        this.jFrame = jFrame;
    }

    public void saveExam(JTextField totalPoints, DefaultTableModel studentModel){
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

        JFileChooser chooser = new JFileChooser();
        int auswahl = chooser.showSaveDialog(jFrame);
        if(auswahl == JFileChooser.APPROVE_OPTION);
        final String file = chooser.getSelectedFile().toString();
        jFrame.setTitle("Notenliste" + " - " + file.substring(file.lastIndexOf("/")).replace("/", ""));
        File ausgabeDatei = new File(file);
        try {
            FileWriter writer = new FileWriter(ausgabeDatei);
            BufferedWriter writeBuffer = new BufferedWriter(writer);

            writeBuffer.write("Gesamtpunkte: ");
            writeBuffer.write(totalPoints.getText());
            writeBuffer.write("\n");

            writeBuffer.write(saveString);

            writeBuffer.close();
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

    public void serializer(StudentView studentView) {
        OutputStream fos = null;
        try {
            fos = new FileOutputStream("Notenspiegel");
            ObjectOutputStream o = new ObjectOutputStream(fos);
            ArrayList<String> safeFile = new ArrayList<String>();

            safeFile.add(studentView.onePercent.getText());
            safeFile.add(studentView.oneThreePercent.getText());
            safeFile.add(studentView.oneSevenPercent.getText());
            safeFile.add(studentView.twoPercent.getText());
            safeFile.add(studentView.twoThreePercent.getText());
            safeFile.add(studentView.twoSevenPercent.getText());
            safeFile.add(studentView.threePercent.getText());
            safeFile.add(studentView.threeThreePercent.getText());
            safeFile.add(studentView.threeSevenPercent.getText());
            safeFile.add(studentView.fourPercent.getText());
            o.writeObject(safeFile);
        } catch (IOException e) {
            System.err.println(e);
        } finally {
            try {
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



}
