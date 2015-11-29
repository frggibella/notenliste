package Notenliste;

import GradeList.StudentListView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by FyahMayah on 29.11.15.
 */
public class StudentControl implements ActionListener{

    StudentModel studentModel;
    StudentView studentListView;
    HashMap<String, String> key; // Notenschlüssel
    private ArrayList<String[]> tableData; // Liste des Models


    public StudentControl(){
        studentListView = new StudentView(this);
        studentModel = new StudentModel(studentListView);
    }

    public String scoreCalculation(String points, String totalPoints){
        double score= 0;
        double scorePoint, scoreTotalPoint, one, oneThree, oneSeven, two, twoThree, twoSeven, three, threeThree, threeSeven, four;
        scorePoint = Double.parseDouble(points);
        scoreTotalPoint = Double.parseDouble(totalPoints);

        one = Double.parseDouble(studentListView.onePercent.getText());
        oneThree = Double.parseDouble(studentListView.oneThreePercent.getText());
        oneSeven = Double.parseDouble(studentListView.oneSevenPercent.getText());
        two = Double.parseDouble(studentListView.twoPercent.getText());
        twoThree = Double.parseDouble(studentListView.twoThreePercent.getText());
        twoSeven = Double.parseDouble(studentListView.twoSevenPercent.getText());
        three = Double.parseDouble(studentListView.threePercent.getText());
        threeThree = Double.parseDouble(studentListView.threeThreePercent.getText());
        threeSeven = Double.parseDouble(studentListView.threeSevenPercent.getText());
        four = Double.parseDouble(studentListView.fourPercent.getText());

        if(scorePoint >=  (one/100.0 * scoreTotalPoint)){
            score = 1.0;
        }
        else if(scorePoint >=  (oneThree/100.0 * scoreTotalPoint) && scorePoint <=  (one/100 * scoreTotalPoint)){
            score = 1.3;
        }
        else if(scorePoint >=  (oneSeven/100.0 * scoreTotalPoint) && scorePoint <=  (oneThree/100 * scoreTotalPoint)){
            score = 1.7;
        }
        else if(scorePoint >=  (two/100.0 * scoreTotalPoint) && scorePoint <=  (oneSeven/100 * scoreTotalPoint)){
            score = 2.0;
        }
        else if(scorePoint >=  (twoThree/100.0 * scoreTotalPoint) && scorePoint <=  (two/100 * scoreTotalPoint)){
            score = 2.3;
        }
        else if(scorePoint >=  (twoSeven/100.0 * scoreTotalPoint) && scorePoint <=  (twoThree/100 * scoreTotalPoint)){
            score = 2.7;
        }
        else if(scorePoint >=  (three/100.0 * scoreTotalPoint) && scorePoint <=  (twoSeven/100 * scoreTotalPoint)){
            score = 3.0;
        }
        else if(scorePoint >=  (threeThree/100.0 * scoreTotalPoint) && scorePoint <=  (three/100 * scoreTotalPoint)){
            score = 3.3;
        }
        else if(scorePoint >=  (threeSeven/100.0 * scoreTotalPoint) && scorePoint <=  (threeThree/100 * scoreTotalPoint)){
            score = 3.7;
        }
        else if(scorePoint >=  (four/100.0 * scoreTotalPoint) && scorePoint <=  (threeSeven/100 * scoreTotalPoint)){
            score = 4.0;
        }
        else if(scorePoint <  (four/100.0 * scoreTotalPoint)){
            score = 5.0;
        }

        String source = Double.toString(score);
        return source;
    }

    private void insertStudent(){
        String[] row = {studentListView.prename.getText(), studentListView.lastname.getText(),studentListView.matrikel.getText(),studentListView.points.getText()};
        tableData.add(row);
    }

    private void readStudentModel(){
        //Die alte Studentenliste wird gelöscht
        tableData = new ArrayList<String[]>();

        //Die Studentenliste wird mit den tatsaechlichen Daten aus Der GUI befüllt.
        for(int i = 0; i < studentListView.studentModel.getRowCount(); i++){
            String[] row = new String[5];
            for(int j = 0; j < studentListView.studentModel.getColumnCount(); j++){
                row[j] = ( String ) studentListView.studentModel.getValueAt(i, j);
            }
            tableData.add(row);
        }
    }

    public void loadGrading() {

        FileInputStream fileStream;
        try {
            fileStream = new FileInputStream("Notenspiegel");
            ObjectInputStream ois = new ObjectInputStream(fileStream);
            ArrayList<String> safeFile = ( ArrayList<String> )ois.readObject();

            if(studentListView.onePercent == null){
                studentListView.onePercent = new JTextField();
                studentListView.oneThreePercent = new JTextField();
                studentListView.oneSevenPercent = new JTextField();
                studentListView.twoPercent= new JTextField();
                studentListView.twoThreePercent= new JTextField();
                studentListView.twoSevenPercent= new JTextField();
                studentListView.threePercent= new JTextField();
                studentListView.threeThreePercent= new JTextField();
                studentListView.threeSevenPercent= new JTextField();
                studentListView.fourPercent= new JTextField();
            }

            studentListView.onePercent.setText(safeFile.get(0));
            studentListView.oneThreePercent.setText(safeFile.get(1));
            studentListView.oneSevenPercent.setText(safeFile.get(2));
            studentListView.twoPercent.setText(safeFile.get(3));
            studentListView.twoThreePercent.setText(safeFile.get(4));
            studentListView.twoSevenPercent.setText(safeFile.get(5));
            studentListView.threePercent.setText(safeFile.get(6));
            studentListView.threeThreePercent.setText(safeFile.get(7));
            studentListView.threeSevenPercent.setText(safeFile.get(8));
            studentListView.fourPercent.setText(safeFile.get(9));

            ois.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        StudentListView n = new StudentListView();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
            String actionCommand = e.getActionCommand();
            if(actionCommand.matches("Speichern")){
                insertStudent();
                studentListView.remove(studentListView.backgroundPanel);

                studentListView.initial(tableData, studentListView.tPoints.getText());

                int j = 0;
                for (String[] row : tableData){
                    tableData.get(j)[4]= scoreCalculation(row[3], studentListView.tPoints.getText());
                    j++;
                }

                studentListView.initial(tableData, studentListView.tPoints.getText());
            }
            if(actionCommand.matches("Aktualisieren")){
                readStudentModel();
                studentListView.remove(studentListView.backgroundPanel);

                int j = 0;
                for (String[] row : tableData){
                    tableData.get(j)[4]= scoreCalculation(row[3], studentListView.tPoints.getText());
                    j++;
                }

                studentListView.initial(tableData, studentListView.tPoints.getText());
            }
            if(actionCommand.matches("Klausur speichern")){
                studentModel.saveExam(studentListView.tPoints, studentListView.studentModel);
                readStudentModel();
                studentListView.remove(studentListView.backgroundPanel);
                studentListView.initial(tableData, studentListView.tPoints.getText());
            }
            if(actionCommand.matches("Klausur speichern unter")){
                studentModel.saveExam(studentListView.tPoints, studentListView.studentModel);
                readStudentModel();
                studentListView.remove(studentListView.backgroundPanel);
                studentListView.initial(tableData, studentListView.tPoints.getText());
            }
            if(actionCommand.matches("Klausur laden")){
                tableData = new ArrayList<String[]>();
                tableData = studentModel.loadExam();

                // gradekeyPanel.add(newKeyButton);

                studentListView.remove(studentListView.backgroundPanel);
                studentListView.tPoints.setText(studentModel.totPoints);
                int j = 0;
                for (String[] row : tableData){
                    tableData.get(j)[4]= scoreCalculation(row[3], studentListView.tPoints.getText());
                    j++;
                }
                studentListView.initial(tableData, studentListView.tPoints.getText());
            }
            if(actionCommand.matches("Notenschlüssel")){;
                studentListView.markWindow(studentListView);
            }

            if(actionCommand.matches("Übernehmen")){
                studentModel.serializer(studentListView);
                readStudentModel();
                studentListView.remove(studentListView.backgroundPanel);
                studentListView.initial(tableData, studentListView.tPoints.getText());
            }
        }
}
