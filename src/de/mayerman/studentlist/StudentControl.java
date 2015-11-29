package de.mayerman.studentlist;

import GradeList.StudentListView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by FyahMayah on 29.11.15.
 */
public class StudentControl implements ActionListener{

    Filehandler filehandler;
    StudentListView studentListView;


    public StudentControl(){
        studentListView = new StudentListView();
        filehandler = new Filehandler(studentListView);
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

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
