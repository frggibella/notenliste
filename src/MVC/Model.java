package MVC;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by beahildehrandt on 05.12.15.
 */
public class Model {

    private View view;
    private String inFile;
    public String totPoints;
    public String windowTitle;

    public Model(View view){
        this.view = view;
    }

    public void serializer(ArrayList<JTextField> fields) {
        OutputStream fos = null;
        try {
            fos = new FileOutputStream("Notenspiegel");
            ObjectOutputStream o = new ObjectOutputStream(fos);
            ArrayList<String> safeFile = new ArrayList<String>();

            for(JTextField field : fields){
                safeFile.add(field.getText());
            }

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

    public void loadGrading(ArrayList<JTextField> fields) {
        FileInputStream fileStream;
        ArrayList<JTextField> TestFields = fields;
        try {
            fileStream = new FileInputStream("Notenspiegel");
            ObjectInputStream ois = new ObjectInputStream(fileStream);
            ArrayList<String> safeFile = ( ArrayList<String> )ois.readObject();
            int i=0;

            //TODO: if eingebaut
            //if(fields != null) {
                for (JTextField field : fields) {
                    field.setText(safeFile.get(i));
                    i++;
                }
             /* }
          else
            {
                for (String file : safeFile) {
                    fields.setText(safeFile.get(i));
                    i++;
                }
            }
            */
            ois.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> loadGrading2() {
        FileInputStream fileStream;
        ArrayList<String> safeFile = new ArrayList<String>();
        try {
            fileStream = new FileInputStream("Notenspiegel");
            ObjectInputStream ois = new ObjectInputStream(fileStream);
            safeFile = (ArrayList<String>)ois.readObject();
            ois.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return safeFile;
    }



    public boolean saveExam(JTextField totalPoints, DefaultTableModel studentModel, int saveAs){
        boolean success = false;

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
            int auswahl = chooser.showSaveDialog(view);
            if(auswahl == JFileChooser.APPROVE_OPTION);

            inFile = chooser.getSelectedFile().toString();
            windowTitle = "Notenliste" + " - " + inFile.substring(inFile.lastIndexOf("/")).replace("/", "");
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
            success = true;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return success;
    }

    public ArrayList<String[]> loadExam(){
        ArrayList<String[]> temp = new ArrayList<String[]>();

        JFileChooser chooser = new JFileChooser();
        int auswahl = chooser.showOpenDialog(view);
        if(auswahl == JFileChooser.APPROVE_OPTION);
        final String file = chooser.getSelectedFile().toString();

        inFile = file;

        windowTitle = "Notenliste" + " - " + file.substring(file.lastIndexOf("/")).replace("/", "");
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
        }
        return temp;
    }
    public static void main(String[] args) {
        View n = new View(new Control());
    }
}
