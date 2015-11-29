package Notenliste;

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
import java.util.ArrayList;

/**
 * Created by FyahMayah on 29.11.15.
 */


public class StudentView extends JFrame {

    public DefaultTableModel studentModel; // Liste der View
    public JPanel backgroundPanel;
    public JTextField prename;
    public JTextField lastname;
    public JTextField matrikel;
    public JTextField points;
    public JTextField tPoints;

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

    public ActionListener listener;


    public StudentView(ActionListener listener) {
        //Ein GUI Fenster wird in Form eines JFrames hergestellt und mit Optionen versehen.
        super("Notenliste");
        setSize(new Dimension(800, 600));
        setMinimumSize(new Dimension(600, 400));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setJMenuBar(buildMenuBar());

        initial(new ArrayList<String[]>(), "");
    }



    public void initial(ArrayList<String[]> tableData, String totalPoints ){
        // Ein Panel wird erstellt auf das wir alle anderen Panels legen, also Backroundpanel.
        backgroundPanel = new JPanel(new BorderLayout());

        //Hier wird das "Studentenpanel" auf das Backroundpanel gelegt. Die Position BL.CENTER wird angewählt.
        backgroundPanel.add(buildListPanel(tableData), BorderLayout.CENTER);

        //Das befüllte Hilfspanel wird auf das Backroundpanel gelegt. Die Position BL.EAST wird angewählt.
        backgroundPanel.add(buildOverviewPanel(totalPoints), BorderLayout.EAST);


        //Das Eintragenpanel wird auf das Backgroundpanel gelegt. Die Position BL.SOUTH wird angewählt.
        backgroundPanel.add(buildEntryPanel(), BorderLayout.SOUTH);

        //Das befüllte Backroundpanel wird auf den JFrame (Das GUI Fenster) gelegt.
        add(backgroundPanel);
        //Der Frame wird nachdem er zusammengebaut ist sichtbar gemacht.
        setVisible(true);
    }

    private JPanel buildOverviewPanel(String totalPoints){
        //----------------------------------NOTENSCHLUESSEL-------------------------------->
        //Das Panel für den Notenschlüssel wird erzugt und mit einem Button versehen
        JPanel gradekeyPanel = new JPanel(new BorderLayout());
        JButton newKeyButton = new JButton("Aktualisieren");
        newKeyButton.addActionListener(listener);
        gradekeyPanel.add(newKeyButton, BorderLayout.NORTH);


        PieDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset, "Notenverteilung");
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(250,200));
        gradekeyPanel.add(chartPanel, BorderLayout.CENTER);



        //----------------------------------UEBERSICHT------------------------------------->
        //Das Panel für den Überblick wird erzugt und mit Dummywerten versehen.
        JPanel overviewPanel = new JPanel(new GridLayout(6,1));
        JLabel overall = new JLabel("  Gesamtpunkte:");
        tPoints = new JTextField(totalPoints);


        overviewPanel.add(overall);
        overviewPanel.add(tPoints);

        //----------------------------------BAUARBEITEN------------------------------------->
        //Ein Hilfspanel wird mit einen Gridlayout(2,1) erzeugt um den BL.EAST Abschnitt zu unterteilen.
        //Auf dieses Hilfspanel wird das Studentenpanel und das Notenschlüsselpanel gelegt.
        JPanel leftSideSplitPanel = new JPanel(new GridLayout(2, 1));
        leftSideSplitPanel.add(gradekeyPanel);
        leftSideSplitPanel.add(overviewPanel);
        return  leftSideSplitPanel;
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

    public JMenuBar buildMenuBar(){
        JMenuItem save = new JMenuItem("Klausur speichern");
        save.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.CTRL_DOWN_MASK));
        save.addActionListener(listener);

        JMenuItem saveAs = new JMenuItem("Klausur speichern unter");
        saveAs.setAccelerator(KeyStroke.getKeyStroke('A', InputEvent.CTRL_DOWN_MASK));
        saveAs.addActionListener(listener);

        JMenuItem load = new JMenuItem("Klausur laden");
        load.setAccelerator(KeyStroke.getKeyStroke( 'L', InputEvent.CTRL_DOWN_MASK ));
        load.addActionListener(listener);

        JMenuItem newKey = new JMenuItem("Notenschlüssel");
        newKey.setAccelerator(KeyStroke.getKeyStroke( 'N', InputEvent.CTRL_DOWN_MASK ));
        newKey.addActionListener(listener);

        JMenuItem options = new JMenu("Optionen");
        options.add(save);
        options.add(saveAs);
        options.add(load);
        options.add(newKey);
        JMenuBar jMenuBar = new JMenuBar();
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
        String[] TABLEHEADDATA = { "Vorname", "Nachname", "Matrikelnummer", "Punkte", "Note"};
        studentModel = new DefaultTableModel(temp, TABLEHEADDATA);
        JTable table = new JTable(studentModel);
        table.setCellEditor(new DefaultCellEditor(new JTextField()));
        JScrollPane scrollPane = new JScrollPane(table);
        listPanel.add(scrollPane);

        return listPanel;
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
        SaveButton.addActionListener(listener);
        SaveButtonPanel.add(SaveButton);

        JPanel EntryPanel = new JPanel(new GridLayout(4, 1));
        EntryPanel.add(EntryTitlePanel);
        EntryPanel.add(InsertTextPanel);
        EntryPanel.add(InsertFieldPanel);
        EntryPanel.add(SaveButtonPanel);

        return EntryPanel;
    }

    public void markWindow(JFrame jFrame){
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
        markSetButton.addActionListener(listener);


        JPanel markBorderPanel = new JPanel(new BorderLayout());
        markBorderPanel.add(markWindowPanel, BorderLayout.CENTER);
        markBorderPanel.add(markSetButton, BorderLayout.SOUTH);
        mark.add(markBorderPanel);

        //loadGrading();
        mark.setVisible(true);
    }

}
