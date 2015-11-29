package GradeList;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

import javax.swing.*;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.io.*;
import java.util.ArrayList;



/**
 * Created by beahildehrandt on 06.11.15.
 */
public class StudentListView implements ActionListener {

    //Das Listenkonstrukt in dem wir die Studenten speichen wollen als Klassenvariable um sie
    //überall in der Klasse verfügbar zu machen.
    private String[] TABLEHEADDATA = { "Vorname", "Nachname", "Matrikelnummer", "Punkte", "Note"};
    private ArrayList<String[]> tableData; // Liste des Models
    private DefaultTableModel studentModel; // Liste der View

    private JFrame jFrame;
    private JPanel backgroundPanel;
    private JMenuBar jMenuBar;
    private JMenu options;
    private JMenuItem save;
    private JMenuItem saveAs;
    private JMenuItem load;
    private JMenuItem newKey;

    private JTextField prename;
    private JTextField lastname;
    private JTextField matrikel;
    private JTextField points;

    private JTextField totalPoints;

    private Filehandler filehandler;


    public ArrayList<JTextField> percentfields = new ArrayList<JTextField>();
    public JTextField onePercent;
    public JTextField oneThreePercent;
    public JTextField oneSevenPercent;
    public JTextField twoPercent;
    public JTextField twoThreePercent;
    public JTextField twoSevenPercent;
    public JTextField threePercent;
    public JTextField threeThreePercent;
    public JTextField threeSevenPercent;
    public JTextField fourPercent;


    /**
     * Constructor
     */
    public StudentListView(){
        //Variablen müssen vor deren Nutzung initalisiert werden -> sonst Nullpointer exeption.
        tableData = new ArrayList<String[]>();
        //Aufruf selbstgeschiebener Hilfsmethoden
        //fillDummyList();
        loadGrading();


        //Ein GUI Fenster wird in Form eines JFrames hergestellt und mit Optionen versehen.
        jFrame = new JFrame("Notenliste");
        jFrame.setSize(new Dimension(800, 600));
        jFrame.setMinimumSize(new Dimension(600, 400));
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setJMenuBar(buildMenuBar());

        filehandler = new Filehandler(jFrame);
                //Hilfsklasse für GUI wird aufgerufen
        initial();

    }

    /**
     * Create window
     */
    private void initial(){
        // Ein Panel wird erstellt auf das wir alle anderen Panels legen, also Backroundpanel.
        backgroundPanel = new JPanel(new BorderLayout());

        //Hier wird das "Studentenpanel" auf das Backroundpanel gelegt. Die Position BL.CENTER wird angewählt.
        backgroundPanel.add(buildListPanel(), BorderLayout.CENTER);

        //Das befüllte Hilfspanel wird auf das Backroundpanel gelegt. Die Position BL.EAST wird angewählt.
        backgroundPanel.add(buildOverviewPanel(), BorderLayout.EAST);


        //Das Eintragenpanel wird auf das Backgroundpanel gelegt. Die Position BL.SOUTH wird angewählt.
        backgroundPanel.add(buildEntryPanel(), BorderLayout.SOUTH);

        //Das befüllte Backroundpanel wird auf den JFrame (Das GUI Fenster) gelegt.
        jFrame.add(backgroundPanel);
        //Der Frame wird nachdem er zusammengebaut ist sichtbar gemacht.
        jFrame.setVisible(true);
    }

    private JMenuBar buildMenuBar(){
        save = new JMenuItem("Klausur speichern");
        save.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.CTRL_DOWN_MASK));
        save.addActionListener(this);

        saveAs = new JMenuItem("Klausur speichern unter");
        saveAs.setAccelerator(KeyStroke.getKeyStroke('A', InputEvent.CTRL_DOWN_MASK));
        saveAs.addActionListener(this);

        load = new JMenuItem("Klausur laden");
        load.setAccelerator(KeyStroke.getKeyStroke( 'L', InputEvent.CTRL_DOWN_MASK ));
        load.addActionListener(this);

        newKey = new JMenuItem("Notenschlüssel");
        newKey.setAccelerator(KeyStroke.getKeyStroke( 'N', InputEvent.CTRL_DOWN_MASK ));
        newKey.addActionListener(this);

        options = new JMenu("Optionen");
        options.add(save);
        options.add(saveAs);
        options.add(load);
        options.add(newKey);
        jMenuBar = new JMenuBar();
        jMenuBar.add(options);
        return jMenuBar;
    }

    public String scoreCalculation(String points, String totalPoints){
        double score= 0;
        double scorePoint, scoreTotalPoint, one, oneThree, oneSeven, two, twoThree, twoSeven, three, threeThree, threeSeven, four;
            scorePoint = Double.parseDouble(points);
            scoreTotalPoint = Double.parseDouble(totalPoints);

            one = Double.parseDouble(onePercent.getText());
            oneThree = Double.parseDouble(oneThreePercent.getText());
            oneSeven = Double.parseDouble(oneSevenPercent.getText());
            two = Double.parseDouble(twoPercent.getText());
            twoThree = Double.parseDouble(twoThreePercent.getText());
            twoSeven = Double.parseDouble(twoSevenPercent.getText());
            three = Double.parseDouble(threePercent.getText());
            threeThree = Double.parseDouble(threeThreePercent.getText());
            threeSeven = Double.parseDouble(threeSevenPercent.getText());
            four = Double.parseDouble(fourPercent.getText());

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
    public void serializer() {
        OutputStream fos = null;
        try {
            fos = new FileOutputStream("Notenspiegel");
            ObjectOutputStream o = new ObjectOutputStream(fos);
            ArrayList<String> safeFile = new ArrayList<String>();

            safeFile.add(onePercent.getText());
            safeFile.add(oneThreePercent.getText());
            safeFile.add(oneSevenPercent.getText());
            safeFile.add(twoPercent.getText());
            safeFile.add(twoThreePercent.getText());
            safeFile.add(twoSevenPercent.getText());
            safeFile.add(threePercent.getText());
            safeFile.add(threeThreePercent.getText());
            safeFile.add(threeSevenPercent.getText());
            safeFile.add(fourPercent.getText());
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

    public void loadGrading() {

        FileInputStream fileStream;
        try {
            fileStream = new FileInputStream("Notenspiegel");
            ObjectInputStream ois = new ObjectInputStream(fileStream);
            ArrayList<String> safeFile = ( ArrayList<String> )ois.readObject();

            if(onePercent == null){
                onePercent = new JTextField();
                oneThreePercent = new JTextField();
                oneSevenPercent = new JTextField();
                twoPercent= new JTextField();
                twoThreePercent= new JTextField();
                twoSevenPercent= new JTextField();
                threePercent= new JTextField();
                threeThreePercent= new JTextField();
                threeSevenPercent= new JTextField();
                fourPercent= new JTextField();
            }

            onePercent.setText(safeFile.get(0));
            oneThreePercent.setText(safeFile.get(1));
            oneSevenPercent.setText(safeFile.get(2));
            twoPercent.setText(safeFile.get(3));
            twoThreePercent.setText(safeFile.get(4));
            twoSevenPercent.setText(safeFile.get(5));
            threePercent.setText(safeFile.get(6));
            threeThreePercent.setText(safeFile.get(7));
            threeSevenPercent.setText(safeFile.get(8));
            fourPercent.setText(safeFile.get(9));

            ois.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    private JPanel buildListPanel(){
        //----------------------------------NOTENLISTE------------------------------------->
        //Hier werden dem "Studentenpanel" Einträge hinzugefügt.
        JPanel listPanel = new JPanel(new BorderLayout());


        //Aufspannen der Tabelle
        String[][] temp = new String[tableData.size()][5];

        int i = 0;
        for (String[] row : tableData){
            temp[i] = row;
            i++;
        }

        studentModel = new DefaultTableModel(temp, TABLEHEADDATA);
        JTable table = new JTable(studentModel);
        table.setCellEditor(new DefaultCellEditor(new JTextField()));
        JScrollPane scrollPane = new JScrollPane(table);
        listPanel.add(scrollPane);

        return listPanel;
    }

    private PieDataset createDataset(){
        DefaultPieDataset result = new DefaultPieDataset();
        result.setValue("Windows", 45);
        result.setValue("Linux", 12);
        result.setValue("Mac", 23);
        return result;

    }

    private JFreeChart createChart(PieDataset dataset, String title){
        JFreeChart chart = ChartFactory.createPieChart(title, dataset, false, true, false);
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setStartAngle(0);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
        return chart;
    }

    private JPanel buildOverviewPanel(){
        //----------------------------------NOTENSCHLUESSEL-------------------------------->
        //Das Panel für den Notenschlüssel wird erzugt und mit einem Button versehen
        JPanel gradekeyPanel = new JPanel(); //Kein Layout für das Panel gewählt = Borderlayout by default.
        JButton newKeyButton = new JButton("Aktualisieren");
        newKeyButton.addActionListener(this);
        gradekeyPanel.add(newKeyButton, BorderLayout.NORTH);


        PieDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset, "Notenverteilung");
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(200,200));
        gradekeyPanel.add(chartPanel, BorderLayout.CENTER);



        //----------------------------------UEBERSICHT------------------------------------->
        //Das Panel für den Überblick wird erzugt und mit Dummywerten versehen.
        JPanel overviewPanel = new JPanel(new GridLayout(6,1));
        JLabel overall = new JLabel("  Gesamtpunkte:");
        if(totalPoints != null) {
            totalPoints = new JTextField(totalPoints.getText());
        }
        else{
            totalPoints = new JTextField();
        }

        overviewPanel.add(overall);
        overviewPanel.add(totalPoints);

        //----------------------------------BAUARBEITEN------------------------------------->
        //Ein Hilfspanel wird mit einen Gridlayout(2,1) erzeugt um den BL.EAST Abschnitt zu unterteilen.
        //Auf dieses Hilfspanel wird das Studentenpanel und das Notenschlüsselpanel gelegt.
        JPanel leftSideSplitPanel = new JPanel(new GridLayout(2, 1));
        leftSideSplitPanel.add(gradekeyPanel);
        leftSideSplitPanel.add(overviewPanel);
        return  leftSideSplitPanel;
    }

    private JPanel buildEntryPanel(){
        //----------------------------------UEBERSCHRIFT------------------------------------>
        //Das Panel für einen neuen Eintrag wird erzugt.
        JPanel EntryTitlePanel = new JPanel();
        //Das Label für das Panel wird erzeugt.
        JLabel newEntryTitle = new JLabel("Neuer Eintrag");
        EntryTitlePanel.add(newEntryTitle);

        //----------------------------------TEXTFELDBESCHRIFTUNG---------------------------->
        JPanel InsertTextPanel = new JPanel(new GridLayout(1, 4));
        JLabel PreNameLabel = new JLabel(" Vorname");
        JLabel LastNameLabel = new JLabel(" Nachname");
        JLabel MatrikelLabel = new JLabel(" Matrikelnummer");
        JLabel PointsLabel = new JLabel(" Punkte");

        InsertTextPanel.add(PreNameLabel);
        InsertTextPanel.add(LastNameLabel);
        InsertTextPanel.add(MatrikelLabel);
        InsertTextPanel.add(PointsLabel);

        //----------------------------------EINGABEZEILE------------------------------------>
        JPanel InsertFieldPanel = new JPanel(new GridLayout(1, 4));
        prename = new JTextField();
        lastname = new JTextField();
        matrikel = new JTextField();
        points = new JTextField();

        InsertFieldPanel.add(prename);
        InsertFieldPanel.add(lastname);
        InsertFieldPanel.add(matrikel);
        InsertFieldPanel.add(points);

        //----------------------------------BAUARBEITEN------------------------------------->
        JPanel SaveButtonPanel = new JPanel();
        JButton SaveButton = new JButton("Speichern");
        SaveButton.addActionListener(this);
        SaveButtonPanel.add(SaveButton);

        JPanel EntryPanel = new JPanel(new GridLayout(4, 1));
        EntryPanel.add(EntryTitlePanel);
        EntryPanel.add(InsertTextPanel);
        EntryPanel.add(InsertFieldPanel);
        EntryPanel.add(SaveButtonPanel);

        return EntryPanel;
    }

    public void markWindow(){
        JFrame mark= new JFrame("Notenschlüssel");
        mark.setSize(new Dimension(200, 400));
        mark.setMinimumSize(new Dimension(150, 200));
        mark.setLocationRelativeTo(jFrame);

        JPanel markWindowPanel = new JPanel(new GridLayout(10,3));

        JLabel oneLabel = new JLabel("    1.0 : ");
        onePercent = new JTextField();
        JLabel percent = new JLabel("%");

        JLabel oneThreeLabel = new JLabel("    1.3 : ");
        oneThreePercent = new JTextField();
        JLabel percent1 = new JLabel("%");

        JLabel oneSevenLabel = new JLabel("    1.7 : ");
        oneSevenPercent = new JTextField();
        JLabel percent2 = new JLabel("%");

        JLabel twoLabel = new JLabel("    2.0 : ");
        twoPercent = new JTextField();
        JLabel percent3 = new JLabel("%");

        JLabel twoThreeLabel = new JLabel("    2.3 : ");
        twoThreePercent = new JTextField();
        JLabel percent4 = new JLabel("%");

        JLabel twoSevenLabel = new JLabel("    2.7 : ");
        twoSevenPercent = new JTextField();
        JLabel percent5 = new JLabel("%");

        JLabel threeLabel = new JLabel("    3.0 : ");
        threePercent = new JTextField();
        JLabel percent6 = new JLabel("%");

        JLabel threeThreeLabel = new JLabel("    3.3 : ");
        threeThreePercent = new JTextField();
        JLabel percent7 = new JLabel("%");

        JLabel threeSevenLabel = new JLabel("    3.7 : ");
        threeSevenPercent = new JTextField();
        JLabel percent8 = new JLabel("%");

        JLabel fourLabel = new JLabel("    4.0 : ");
        fourPercent = new JTextField();
        JLabel percent9 = new JLabel("%");

        markWindowPanel.add(oneLabel);
        markWindowPanel.add(onePercent);
        markWindowPanel.add(percent);

        markWindowPanel.add(oneThreeLabel);
        markWindowPanel.add(oneThreePercent);
        markWindowPanel.add(percent1);

        markWindowPanel.add(oneSevenLabel);
        markWindowPanel.add(oneSevenPercent);
        markWindowPanel.add(percent2);

        markWindowPanel.add(twoLabel);
        markWindowPanel.add(twoPercent);
        markWindowPanel.add(percent3);

        markWindowPanel.add(twoThreeLabel);
        markWindowPanel.add(twoThreePercent);
        markWindowPanel.add(percent4);

        markWindowPanel.add(twoSevenLabel);
        markWindowPanel.add(twoSevenPercent);
        markWindowPanel.add(percent5);

        markWindowPanel.add(threeLabel);
        markWindowPanel.add(threePercent);
        markWindowPanel.add(percent6);

        markWindowPanel.add(threeThreeLabel);
        markWindowPanel.add(threeThreePercent);
        markWindowPanel.add(percent7);

        markWindowPanel.add(threeSevenLabel);
        markWindowPanel.add(threeSevenPercent);
        markWindowPanel.add(percent8);

        markWindowPanel.add(fourLabel);
        markWindowPanel.add(fourPercent);
        markWindowPanel.add(percent9);

        JButton markSetButton = new JButton("Übernehmen");
        markSetButton.addActionListener(this);


        JPanel markBorderPanel = new JPanel(new BorderLayout());
        markBorderPanel.add(markWindowPanel, BorderLayout.CENTER);
        markBorderPanel.add(markSetButton, BorderLayout.SOUTH);
        mark.add(markBorderPanel);

        loadGrading();
        mark.setVisible(true);
    }

    /**
     * Reads the current data from the GUI studentlist and saves it in the ArrayList<String[]> tabledata.
     */
    private void readStudentModel(){
        //Die alte Studentenliste wird gelöscht
        tableData = new ArrayList<String[]>();

        //Die Studentenliste wird mit den tatsaechlichen Daten aus Der GUI befüllt.
        for(int i = 0; i < studentModel.getRowCount(); i++){
            String[] row = new String[5];
            for(int j = 0; j < studentModel.getColumnCount(); j++){
                row[j] = ( String ) studentModel.getValueAt(i, j);
            }
            tableData.add(row);
        }
    }

    private void insertStudent(){
        String[] row = {prename.getText(), lastname.getText(),matrikel.getText(),points.getText()};
        tableData.add(row);
    }

    public static void main(String[] args) {
        StudentListView n = new StudentListView();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        if(actionCommand.matches("Speichern")){
            insertStudent();
            jFrame.remove(backgroundPanel);

            initial();

            int j = 0;
            for (String[] row : tableData){
                tableData.get(j)[4]= scoreCalculation(row[3], totalPoints.getText());
                j++;
            }

            initial();
        }
        if(actionCommand.matches("Aktualisieren")){
            readStudentModel();
            jFrame.remove(backgroundPanel);

            int j = 0;
            for (String[] row : tableData){
                tableData.get(j)[4]= scoreCalculation(row[3], totalPoints.getText());
                j++;
            }

            initial();
        }
        if(actionCommand.matches("Klausur speichern")){
            filehandler.saveExam(totalPoints, studentModel);
            readStudentModel();
            jFrame.remove(backgroundPanel);
            initial();
        }
        if(actionCommand.matches("Klausur speichern unter")){
            filehandler.saveExam(totalPoints, studentModel);
            readStudentModel();
            jFrame.remove(backgroundPanel);
            initial();
        }
        if(actionCommand.matches("Klausur laden")){
            tableData = new ArrayList<String[]>();
            tableData = filehandler.loadExam();

           // gradekeyPanel.add(newKeyButton);

            jFrame.remove(backgroundPanel);
            totalPoints.setText(filehandler.totPoints);
            int j = 0;
            for (String[] row : tableData){
                tableData.get(j)[4]= scoreCalculation(row[3], totalPoints.getText());
                j++;
            }
            initial();
        }
        if(actionCommand.matches("Notenschlüssel")){;
            markWindow();
        }

        if(actionCommand.matches("Übernehmen")){
            serializer();
            readStudentModel();
            jFrame.remove(backgroundPanel);
            initial();
        }
    }




}

