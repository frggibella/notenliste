package MVC;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Created by beahildehrandt on 05.12.15.
 */

public class View extends JFrame {

    public DefaultTableModel studentModel; // Liste der View

    public JPanel backgroundPanel;
    public JMenuBar jMenuBar;
    public JMenu options;
    public JMenuItem save;
    public JMenuItem saveAs;
    public JMenuItem load;
    public JMenuItem newKey;

    public JTextField prename;
    public JTextField lastname;
    public JTextField matrikel;
    public JTextField points;

    JFrame success;


    public JTextField totalPoints;

    public ArrayList<JTextField> textFields;

    public int eins = 0, zwei = 0, drei = 0, vier = 0, fünf = 0;
    public int students = 0;
    public double sum = 0.0;

    public double bestMark = 6.0;
    public double worstMark = 1.0;

    public ActionListener listner;

    public View(ActionListener listner){
        //Ein GUI Fenster wird in Form eines JFrames hergestellt und mit Optionen versehen. MetFrame
        super("Notenliste");
        this.listner = listner;

        validate();
        setSize(new Dimension(800, 600));
        setMinimumSize(new Dimension(600, 500));

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setJMenuBar(buildMenuBar());
    }

    public void initial(ArrayList<String[]> tableData){
        // Ein Panel wird erstellt auf das wir alle anderen Panels legen, also Backroundpanel.
        backgroundPanel = new JPanel(new BorderLayout());

        //Hier wird das "Studentenpanel" auf das Backroundpanel gelegt. Die Position BL.CENTER wird angewählt.
        backgroundPanel.add(buildListPanel(tableData), BorderLayout.CENTER);

        //Das befüllte Hilfspanel wird auf das Backroundpanel gelegt. Die Position BL.EAST wird angewählt.
        backgroundPanel.add(buildOverviewPanel(), BorderLayout.EAST);

        //Das Eintragenpanel wird auf das Backgroundpanel gelegt. Die Position BL.SOUTH wird angewählt.
        backgroundPanel.add(buildEntryPanel(), BorderLayout.SOUTH);

        //Das befüllte Backroundpanel wird auf den JFrame (Das GUI Fenster) gelegt.
        add(backgroundPanel);
        //Der Frame wird nachdem er zusammengebaut ist sichtbar gemacht.
        setVisible(true);

        // Berechneten Wert an  createDataset(int eins, int zwei,int drei,int vier,int fünf) weitergeben für Chartwerte
        createDataset( eins,zwei,drei,vier,fünf);
    }

    private JMenuBar buildMenuBar(){
        save = new JMenuItem("Klausur speichern");
        save.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.CTRL_DOWN_MASK));
        save.addActionListener(listner);

        saveAs = new JMenuItem("Klausur speichern unter");
        saveAs.setAccelerator(KeyStroke.getKeyStroke('A', InputEvent.CTRL_DOWN_MASK));
        saveAs.addActionListener(listner);

        load = new JMenuItem("Klausur laden");
        load.setAccelerator(KeyStroke.getKeyStroke( 'L', InputEvent.CTRL_DOWN_MASK ));
        load.addActionListener(listner);

        newKey = new JMenuItem("Notenschlüssel");
        newKey.setAccelerator(KeyStroke.getKeyStroke( 'N', InputEvent.CTRL_DOWN_MASK ));
        newKey.addActionListener(listner);

        options = new JMenu("Optionen");
        options.add(save);
        options.add(saveAs);
        options.add(load);
        options.add(newKey);
        jMenuBar = new JMenuBar();
        jMenuBar.add(options);
        return jMenuBar;
    }

    private JPanel buildListPanel(ArrayList<String[]> tableData){
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

        String[] tableHeadData = { "Vorname", "Nachname", "Matrikelnummer", "Punkte", "Note"};
        studentModel = new DefaultTableModel(temp, tableHeadData);
        JTable table = new JTable(studentModel);
        table.setCellEditor(new DefaultCellEditor(new JTextField()));
        JScrollPane scrollPane = new JScrollPane(table);
        listPanel.add(scrollPane);

        return listPanel;
    }

    private PieDataset createDataset(int eins, int zwei,int drei,int vier,int fünf){
        DefaultPieDataset result = new DefaultPieDataset();
        result.setValue("1er", eins);
        result.setValue("2er", zwei);
        result.setValue("3er", drei);
        result.setValue("4er", vier);
        result.setValue("5er", fünf);
        return result;
    }

    private JFreeChart createChart(PieDataset dataset, String title){
        JFreeChart chart = ChartFactory.createPieChart(title, dataset, false, true, false);

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setStartAngle(0);
        plot.setShadowXOffset(0);
        plot.setShadowYOffset(0);

        plot.setSectionPaint("1er", Color.green);
        plot.setSectionPaint("2er", Color.yellow);
        plot.setSectionPaint("3er", Color.orange);
        plot.setSectionPaint("4er", Color.pink);
        plot.setSectionPaint("5er", Color.red);
        plot.setBaseSectionOutlinePaint(Color.white);

        plot.setBackgroundPaint(Color.white);
        plot.setDirection(Rotation.CLOCKWISE);

        plot.setOutlineVisible(false);

        plot.setLabelOutlinePaint(Color.white);
        plot.setLabelBackgroundPaint(Color.white);
        plot.setLabelShadowPaint(Color.white);
        return chart;
    }

    private JPanel buildOverviewPanel(){
        //----------------------------------NOTENSCHLUESSEL-------------------------------->
        //Das Panel für den Notenschlüssel wird erzugt und mit einem Button versehen
        JPanel gradekeyPanel = new JPanel(new BorderLayout());
        JButton newKeyButton = new JButton("Aktualisieren");
        newKeyButton.addActionListener(listner);
        gradekeyPanel.add(newKeyButton, BorderLayout.NORTH);

        JPanel chartAllPanel = new JPanel(new BorderLayout());
        JLabel chartTitle = new JLabel("Notenverteilung");
        chartTitle.setHorizontalAlignment(0);
        chartTitle.setPreferredSize(new Dimension(30,25));

        PieDataset dataset = createDataset(eins,zwei,drei,vier,fünf);
        JFreeChart chart = createChart(dataset, "");
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(290,160));
        chartAllPanel.add(chartTitle, BorderLayout.NORTH);
        chartAllPanel.add(chartPanel, BorderLayout.CENTER);

        gradekeyPanel.add(chartAllPanel, BorderLayout.CENTER);
        JPanel overviewPanel = new JPanel(new GridLayout(4,2));
        gradekeyPanel.add(overviewPanel, BorderLayout.SOUTH);

        //----------------------------------UEBERSICHT------------------------------------->
        //Das Panel für den Überblick wird erzugt und mit Dummywerten versehen.
        JLabel overall = new JLabel(" Gesamtpunkte:");
        if(totalPoints != null) {
            totalPoints = new JTextField(totalPoints.getText());
        }
        else{
            totalPoints = new JTextField();
        }
        JLabel meanLabel = new JLabel(" Durchschnitt");
        NumberFormat numberFormat = new DecimalFormat("0.00");
        double temp= (sum/students);
        JLabel meanValue = new JLabel(numberFormat.format(temp));

        JLabel bestMarkLabel = new JLabel(" beste Note");
        JLabel worstMarkLabel = new JLabel(" schlechteste Note");

        JLabel bestMarkValue = new JLabel(Double.toString(bestMark));
        JLabel worstMarkValue = new JLabel(Double.toString(worstMark));

        overviewPanel.add(overall);
        overviewPanel.add(totalPoints);

        overviewPanel.add(meanLabel);
        overviewPanel.add(meanValue);

        overviewPanel.add(bestMarkLabel);
        overviewPanel.add(bestMarkValue);

        overviewPanel.add(worstMarkLabel);
        overviewPanel.add(worstMarkValue);

        //----------------------------------BAUARBEITEN------------------------------------->
        //Ein Hilfspanel wird mit einen Gridlayout(2,1) erzeugt um den BL.EAST Abschnitt zu unterteilen.
        //Auf dieses Hilfspanel wird das Studentenpanel und das Notenschlüsselpanel gelegt.
        return  gradekeyPanel;
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
        JButton SaveButton = new JButton("Eintragen");
        SaveButton.addActionListener(listner);
        SaveButtonPanel.add(SaveButton);

        JPanel EntryPanel = new JPanel(new GridLayout(4, 1));
        EntryPanel.add(EntryTitlePanel);
        EntryPanel.add(InsertTextPanel);
        EntryPanel.add(InsertFieldPanel);
        EntryPanel.add(SaveButtonPanel);

        return EntryPanel;
    }

    public void markWindow() {
        JFrame mark = new JFrame("Notenschlüssel");
        mark.setSize(new Dimension(200, 400));
        mark.setMinimumSize(new Dimension(150, 200));
        mark.setLocationRelativeTo(this);

        JPanel markWindowPanel = new JPanel(new GridLayout(10, 3));

        String[] labels = {"    1.0 : ", "    1.3 : ", "    1.7 : ", "    2.0 : ", "    2.3 : ", "    2.7 : ", "    3.0 : ", "    3.3 : ", "    3.7 : ", "    4.0 : "};

        textFields = new ArrayList<JTextField>();
        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i]);
            JTextField percentField = new JTextField();
            textFields.add(percentField);
            JLabel percent = new JLabel("%");
            markWindowPanel.add(label);
            markWindowPanel.add(percentField);
            markWindowPanel.add(percent);
        }

        JButton markSetButton = new JButton("Übernehmen");
        markSetButton.addActionListener(listner);

        JPanel markBorderPanel = new JPanel(new BorderLayout());
        markBorderPanel.add(markWindowPanel, BorderLayout.CENTER);
        markBorderPanel.add(markSetButton, BorderLayout.SOUTH);
        mark.add(markBorderPanel);

        mark.setVisible(true);
    }

    public void successWindow(boolean result, int i){
        success = new JFrame("Eingabe überprüft");
        success.setSize(new Dimension(400, 80));
        success.setLocationRelativeTo(this);

        JPanel ButtonPanel = new JPanel();
        JPanel WindowPanel = new JPanel(new GridLayout(2,1));
        JPanel WindowPanel2 = new JPanel();

        String choiceTrue = "Eingabe korrekt";
        String choiceFalse= "Eingabe inkorrekt";

        if(i== 1){
            choiceTrue =  "Klausur erfolgreich gespeichert.\n\n  ";
            choiceFalse = "Klausur wurde nicht gespeichert.\n\n  ";
        }
        else if(i== 2){
            choiceFalse = "Pflichtfelder inkorrekt befüllt\n\n  ";
        }
        else if(i== 3){
            choiceFalse = "Bitte Gesamtpunkte eintragen.\n\n  ";
        }
        else if(i== 4){
            choiceFalse = "Matrikelnummer bereits eingetragen.\n\n  ";
        }

        WindowPanel.setLayout(new BoxLayout(WindowPanel, BoxLayout.PAGE_AXIS));

        if(result) {
            JLabel testLabel = new JLabel(choiceTrue);
            testLabel.setHorizontalAlignment(0);
            WindowPanel.add(testLabel);
        }
        else{
            JLabel testLabel = new JLabel(choiceFalse);
            testLabel.setHorizontalAlignment(0);
            WindowPanel.add(testLabel);
        }

        JButton ok = new JButton("OK");
        ok.addActionListener(listner);
        ok.setHorizontalAlignment(0);
        ButtonPanel.add(ok);

        WindowPanel.add(ButtonPanel);
        WindowPanel2.add(WindowPanel);
        success.add(WindowPanel2);
        success.setVisible(true);

    }
}
