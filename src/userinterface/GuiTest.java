package userinterface; /**
 * Created by Felix on 13.01.2016.
 */


import readmapfiles.Reader;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Map;
import java.util.Queue;


public class GuiTest extends JFrame {

  public GuiTest(){
      setTitle("DTestGui");
        setSize(3000,1000);
      setVisible(true);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

    @Override
    public void paint(Graphics g){

        File file = new File("C:\\Users\\Felix\\Desktop\\Uni\\PK\\Abschlussaufgabe\\mapDateien\\world.map");
        Reader world = new Reader(file);
        String first;
        Point firstPoint=null;
        Point previousPoint=null;
        Point nextPoint=null;
        Queue<Point> onePatch;

        Map<String,Queue<Point>> patches = world.getTerritoryPatches();

        for(Queue<Point> q : patches.values()){
            firstPoint=q.poll();
            previousPoint=firstPoint;

            while(!q.isEmpty()){

                nextPoint=q.poll();

                g.drawLine(previousPoint.x+100,previousPoint.y+100,nextPoint.x+100,nextPoint.y+100);
                previousPoint=nextPoint;

            }

            g.drawLine(firstPoint.x+100,firstPoint.y+100,nextPoint.x+100,nextPoint.y+100);

        }


    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
      GuiTest g = new GuiTest();
    }

}


