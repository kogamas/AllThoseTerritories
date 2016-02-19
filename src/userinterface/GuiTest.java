package userinterface; /**
 * Created by Felix on 13.01.2016.
 */


import game.Patch;
import game.World;
import readmapfiles.Reader;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Map;
import java.util.Queue;


public class GuiTest extends JFrame {

    public GuiTest() {
        setTitle("DTestGui");
        setSize(1300, 800);
        setVisible(true);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void paint(Graphics g) {

        JFrame gameMap = new JFrame();

        File file = new File("C:\\Users\\Felix\\Desktop\\Uni\\AllThoseTerritories\\angabe\\world.map");
        Reader readWorld = new Reader(file);
        String first;
        Point[] patchPoints;
        Point previousPoint = null;
        Point nextPoint = null;
        Queue<Polygon> oneTerritory = null;
        int[] x = null;
        int[] y = null;


        World newWorld = readWorld.getWorld();

        Map<Integer, Patch[]> patches = newWorld.getTerritoryPatchesMap();

        for (Patch[] q : patches.values()) {

            for (int i = 0; i < q.length; i++) {


                patchPoints = q[i].getBorders();


                x = new int[patchPoints.length];
                y = new int[patchPoints.length];


                for (int j = 0; j < patchPoints.length; j++) {


                    x[j] =  patchPoints[j].x;
                    y[j] =  patchPoints[j].y;



                    /*


                    previousPoint = patchPoints[j-1];


                    nextPoint = patchPoints[j];




                   // g.drawLine(previousPoint.x, previousPoint.y, nextPoint.x, nextPoint.y);
                    previousPoint = nextPoint;
                    */
                }
                final Polygon polyPatch =new Polygon(x,y,x.length);
                oneTerritory.add(polyPatch);






                //  g.drawLine(patchPoints[0].x, patchPoints[0].y, nextPoint.x , nextPoint.y);


            }

            gameMap= new JFrame(){

                public void paint(Graphics g){
                    super.paint(g);
                    g.setColor(Color.CYAN);

                    while(!oneTerritory.isEmpty()) {
                        g.drawPolygon(oneTerritory.poll());
                    }

                }



            };


        }


    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GuiTest();
            }


        });
    }

}


