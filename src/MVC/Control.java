package MVC;

import javax.swing.*;
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
        double score= 0;
        double scorePoint, scoreTotalPoint, one, oneThree, oneSeven, two, twoThree, twoSeven, three, threeThree, threeSeven, four;
        scorePoint = Double.parseDouble(points);
        scoreTotalPoint = Double.parseDouble(totalPoints);

        ArrayList<String> saetze  = model.loadGrading2();

        one = Double.parseDouble(saetze.get(0));
        oneThree = Double.parseDouble(saetze.get(1));
        oneSeven = Double.parseDouble(saetze.get(2));
        two = Double.parseDouble(saetze.get(3));
        twoThree = Double.parseDouble(saetze.get(4));
        twoSeven = Double.parseDouble(saetze.get(5));
        three = Double.parseDouble(saetze.get(6));
        threeThree = Double.parseDouble(saetze.get(7));
        threeSeven = Double.parseDouble(saetze.get(8));
        four = Double.parseDouble(saetze.get(9));

        if (scorePoint >= (one / 100.0 * scoreTotalPoint)) {
            score = 1.0;
            view.eins += 1;
        } else if (scorePoint >= (oneThree / 100.0 * scoreTotalPoint) && scorePoint <= (one / 100 * scoreTotalPoint)) {
            score = 1.3;
            view.eins += 1;
        } else if (scorePoint >= (oneSeven / 100.0 * scoreTotalPoint) && scorePoint <= (oneThree / 100 * scoreTotalPoint)) {
            score = 1.7;
            view.eins += 1;
        } else if (scorePoint >= (two / 100.0 * scoreTotalPoint) && scorePoint <= (oneSeven / 100 * scoreTotalPoint)) {
            score = 2.0;
            view.zwei += 1;
        } else if (scorePoint >= (twoThree / 100.0 * scoreTotalPoint) && scorePoint <= (two / 100 * scoreTotalPoint)) {
            score = 2.3;
            view.zwei += 1;
        } else if (scorePoint >= (twoSeven / 100.0 * scoreTotalPoint) && scorePoint <= (twoThree / 100 * scoreTotalPoint)) {
            score = 2.7;
            view.zwei += 1;
        } else if (scorePoint >= (three / 100.0 * scoreTotalPoint) && scorePoint <= (twoSeven / 100 * scoreTotalPoint)) {
            score = 3.0;
            view.drei += 1;
        } else if (scorePoint >= (threeThree / 100.0 * scoreTotalPoint) && scorePoint <= (three / 100 * scoreTotalPoint)) {
            score = 3.3;
            view.drei += 1;
        } else if (scorePoint >= (threeSeven / 100.0 * scoreTotalPoint) && scorePoint <= (threeThree / 100 * scoreTotalPoint)) {
            score = 3.7;
            view.drei += 1;
        } else if (scorePoint >= (four / 100.0 * scoreTotalPoint) && scorePoint <= (threeSeven / 100 * scoreTotalPoint)) {
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

        String source = Double.toString(score);
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
        String[] row = {view.prename.getText(), view.lastname.getText(),view.matrikel.getText(),view.points.getText()};
        tableData.add(row);
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
            view.remove(view.backgroundPanel);

            view.initial(tableData);
            readStudentModel();

            int j = 0;
            for (String[] row : tableData){
                tableData.get(j)[4]= scoreCalculation(row[3], view.totalPoints.getText());
                j++;
            }
            view.remove(view.backgroundPanel);
            view.initial(tableData);
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
            if(model.saveExam(view.totalPoints, view.studentModel, 0)== false){
                view.successWindow( false);

            }
            else{
                view.successWindow(true);
            }
            readStudentModel();
            //jFrame.remove(backgroundPanel); //Nicht nötig, denk ich
            //initial();
        }
        if(actionCommand.matches("OK")){
            view.success.dispose();

        }
        if(actionCommand.matches("Klausur speichern unter")){
            if(model.saveExam(view.totalPoints, view.studentModel, 1)== false){
                view.successWindow(false);
            }
            else{
                view.successWindow(true);
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

            if(view.textFields != null)
            model.loadGrading(view.textFields);


            int j = 0;
            for (String[] row : tableData){
                tableData.get(j)[4]= scoreCalculation(row[3], view.totalPoints.getText());
                j++;
            }
            view.initial(tableData);
        }
        if(actionCommand.matches("Notenschlüssel")){;
            view.markWindow();
            model.loadGrading(view.textFields);
        }
        if(actionCommand.matches("Übernehmen")){
            model.serializer(view.textFields);
            readStudentModel();
            view.remove(view.backgroundPanel);
            view.initial(tableData);
        }
    }

    public static void main(String[] args) {
        Control n = new Control();
    }
}
