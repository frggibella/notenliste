package MVC;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by beahildehrandt on 05.12.15.
 */
public class Control implements ActionListener {

    private View view;
    private Model model;
    private ArrayList<String[]> tableData; // Liste des Controllers

    public Control(){
        this.model = new Model(view);
        tableData = new ArrayList<String[]>();
        this.view = new View(this);
        view.initial(tableData);

    }

    public String scoreCalculation(String points, String totalPoints){
        double score= 0.0;
        double scorePoint, scoreTotalPoint, one = 0, oneThree = 0, oneSeven = 0, two = 0, twoThree = 0, twoSeven = 0, three = 0, threeThree = 0, threeSeven = 0, four = 0;

        //--------überprüfen, ob Punkte des Studenten eingegeben sind
        try {
            scorePoint = Integer.parseInt(points);
        }catch (Exception E){
            scorePoint = 0;
        }

        //--------überprüfen, ob Gesamtpunkte eingegeben sind
        try {
            scoreTotalPoint = Integer.parseInt(totalPoints);
        }catch (Exception E){
            scoreTotalPoint= 0;
        }

        String source;
        //--------Notenspiegel-Array wird geladen
        ArrayList<String> saetze  = model.loadGrading2();

        //--------überprüfen, ob Notenspiegel eingegeben ist
        try {
            one = Integer.parseInt(saetze.get(0));
            oneThree = Integer.parseInt(saetze.get(1));
            oneSeven = Integer.parseInt(saetze.get(2));
            two = Integer.parseInt(saetze.get(3));
            twoThree = Integer.parseInt(saetze.get(4));
            twoSeven = Integer.parseInt(saetze.get(5));
            three = Integer.parseInt(saetze.get(6));
            threeThree = Integer.parseInt(saetze.get(7));
            threeSeven = Integer.parseInt(saetze.get(8));
            four = Integer.parseInt(saetze.get(9));
        }catch (Exception E){
            one = 0;
        }

        if(scoreTotalPoint != 0 && one != 0  && scorePoint != 0) {
            double x=  one / 100.0 * scoreTotalPoint;

            if (scorePoint >= (one / 100.0 * scoreTotalPoint)) {
                score = 1.0;
                view.eins += 1;
            } else if (scorePoint >= (oneThree / 100.0 * scoreTotalPoint) && scorePoint < (one / 100 * scoreTotalPoint)) {
                score = 1.3;
                view.eins += 1;
            } else if (scorePoint >= (oneSeven / 100.0 * scoreTotalPoint) && scorePoint < (oneThree / 100 * scoreTotalPoint)) {
                score = 1.7;
                view.eins += 1;
            } else if (scorePoint >= (two / 100.0 * scoreTotalPoint) && scorePoint < (oneSeven / 100 * scoreTotalPoint)) {
                score = 2.0;
                view.zwei += 1;
            } else if (scorePoint >= (twoThree / 100.0 * scoreTotalPoint) && scorePoint < (two / 100 * scoreTotalPoint)) {
                score = 2.3;
                view.zwei += 1;
            } else if (scorePoint >= (twoSeven / 100.0 * scoreTotalPoint) && scorePoint < (twoThree / 100 * scoreTotalPoint)) {
                score = 2.7;
                view.zwei += 1;
            } else if (scorePoint >= (three / 100.0 * scoreTotalPoint) && scorePoint < (twoSeven / 100 * scoreTotalPoint)) {
                score = 3.0;
                view.drei += 1;
            } else if (scorePoint >= (threeThree / 100.0 * scoreTotalPoint) && scorePoint < (three / 100 * scoreTotalPoint)) {
                score = 3.3;
                view.drei += 1;
            } else if (scorePoint >= (threeSeven / 100.0 * scoreTotalPoint) && scorePoint < (threeThree / 100 * scoreTotalPoint)) {
                score = 3.7;
                view.drei += 1;
            } else if (scorePoint >= (four / 100.0 * scoreTotalPoint) && scorePoint < (threeSeven / 100 * scoreTotalPoint)) {
                score = 4.0;
                view.vier += 1;
            } else if (scorePoint < (four / 100.0 * scoreTotalPoint)) {
                score = 5.0;
                view.fünf += 1;
            }
            if (score <= view.bestMark) {
                view.bestMark = score;
            }
            if (score >= view.worstMark) {
                view.worstMark = score;
            }
            view.students += 1;
            view.sum += score;

            source = Double.toString(score);
        }
        else
            source = "keine Angabe";

        return source;
    }

    /**
     * Reads the current data from the GUI studentlist and saves it in the ArrayList<String[]> tabledata.
     */
    private void readStudentModel(){
        //Die alte Studentenliste wird gelöscht
        tableData = new ArrayList<String[]>();

        //Die Studentenliste wird mit den tatsaechlichen Daten aus Der GUI befüllt.
        for(int i = 0; i < view.studentModel.getRowCount(); i++){
            String[] row = new String[5];
            for(int j = 0; j < view.studentModel.getColumnCount(); j++){
                row[j] = ( String ) view.studentModel.getValueAt(i, j);
            }
            tableData.add(row);
        }
    }

    private void insertStudent(){

        boolean textCheck = false;
        boolean textLengthCheck = false;
        boolean matrikelCheck = false;
        boolean pointsCheck = false;
        boolean cloneMatrikel = false;

        double scoreTotalPoint = 0.0;
        try {
            scoreTotalPoint = Double.parseDouble(view.totalPoints.getText());
        }catch (Exception E){

        }

        if(view.prename.getText().trim().length() > 0 && view.lastname.getText().trim().length() > 0){
            textCheck = true;
        }

        if(view.prename.getText().trim().length() <20 && view.lastname.getText().trim().length() <20) {
            textLengthCheck = true;
        }

        try {
            int test= Integer.parseInt(view.matrikel.getText());
            if(test <1000000 && test >= 100000)
            {
                matrikelCheck = true;
            }
            for (String[] strings : tableData){
                if(strings[2].matches(view.matrikel.getText())){
                    cloneMatrikel = true;
                }
            }
        }catch (Exception E){
        }

        try {
            int test = Integer.parseInt(view.points.getText());
            if(test  > 0){
                pointsCheck = true;
            }
        }catch (Exception E){
        }

        //------------------------------erst hier wird der neue Student angelegt-----------------------------

        if(textCheck && matrikelCheck && textLengthCheck && pointsCheck && !cloneMatrikel && scoreTotalPoint != 0.0) {
            String[] row = {view.prename.getText().trim(), view.lastname.getText().trim(), view.matrikel.getText(), view.points.getText()};
            tableData.add(row);
            view.remove(view.backgroundPanel);
            view.initial(tableData);
            readStudentModel();

            int j = 0;
            for (String[] strings : tableData){
                tableData.get(j)[4]= scoreCalculation(strings[3], view.totalPoints.getText());
                j++;
            }
            view.remove(view.backgroundPanel);
            view.initial(tableData);
        }
        else {
            if(scoreTotalPoint == 0.0){
                view.successWindow(false, 5);
            }
            else  if(cloneMatrikel)
                view.successWindow(false, 4);
            else
                view.successWindow(false, 2);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        view.eins=0;
        view.zwei=0;
        view.drei=0;
        view.vier=0;
        view.fünf=0;

        view.bestMark= 6.0;
        view.worstMark= 1.0;

        view.sum=0.0;
        view.students=0;

        if(actionCommand.matches("Eintragen")){
            insertStudent();
            readStudentModel();
        }

        if(actionCommand.matches("Aktualisieren")){
            readStudentModel();
            view.remove(view.backgroundPanel);

            int j = 0;
            for (String[] row : tableData){
                tableData.get(j)[4]= scoreCalculation(row[3], view.totalPoints.getText());
                j++;
            }
            view.initial(tableData);
        }
        if(actionCommand.matches("Klausur speichern")){
            if(!model.saveExam(view.totalPoints, view.studentModel, 0)){
                view.successWindow(false, 1);
            }
            else{
                view.successWindow(true, 1);
            }
            readStudentModel();
            //jFrame.remove(backgroundPanel); //Nicht nötig, denk ich
            //initial();
        }
        if(actionCommand.matches("OK")){
            view.success.dispose();
        }
        if(actionCommand.matches("Klausur speichern unter")){
            if(!model.saveExam(view.totalPoints, view.studentModel, 1)){
                view.successWindow(false, 1);
            }
            else{
                view.successWindow(true, 1);
            }            readStudentModel();
            view.setTitle(model.windowTitle);
            //jFrame.remove(backgroundPanel);
            //initial();
        }
        if(actionCommand.matches("Klausur laden")){
            tableData = new ArrayList<String[]>();
            tableData = model.loadExam();
            view.setTitle(model.windowTitle);
            view.remove(view.backgroundPanel);
            view.totalPoints.setText(model.totPoints);

            int j = 0;
            for (String[] row : tableData){
                tableData.get(j)[4]= scoreCalculation(row[3], view.totalPoints.getText());
                j++;
            }
            view.initial(tableData);
        }
        if(actionCommand.matches("Notenschlüssel")){
            view.markWindow();
            model.loadGrading(view.textFields);
        }
        if(actionCommand.matches("Übernehmen")){
            model.serializer(view.textFields);
            readStudentModel();
            view.remove(view.backgroundPanel);
            int j = 0;
            for (String[] row : tableData){
                tableData.get(j)[4]= scoreCalculation(row[3], view.totalPoints.getText());
                j++;
            }
            view.initial(tableData);
        }

        if(actionCommand.matches("Löschen")) {
            try {
                int rowToRemove = view.table.getSelectedRow();
                if(rowToRemove >=0)
                    view.deleteWindow();
                else
                    view.successWindow(false, 7);

            } catch (Exception E) {
                view.successWindow(false, 7);
            }
        }

        if(actionCommand.matches("Ja")) {
            try {
                int rowToRemove = view.table.getSelectedRow();
                ((DefaultTableModel) view.table.getModel()).removeRow(rowToRemove);
                view.deleteWindowFrame.dispose();
            }catch (Exception E){
                view.successWindow(false, 8);
                view.deleteWindowFrame.dispose();
            }
        }
        if(actionCommand.matches("Nein")) {
            view.deleteWindowFrame.dispose();
        }
    }

    public static void main(String[] args) {
        Control n = new Control();
    }
}
