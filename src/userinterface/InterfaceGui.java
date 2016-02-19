package userinterface;

import game.Patch;
import game.World;
import readmapfiles.Reader;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class InterfaceGui extends JFrame {

    private JFrame mainMap;
    private Polygon poly;
    private Map<Integer,Queue<Polygon>> territoryPolygons;

    public InterfaceGui() {
        initComponents();
    }

    private void initComponents() {

        mainMap = new JFrame();
        mainMap.setResizable(false);
        mainMap.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        territoryPolygons=new HashMap<Integer, Queue<Polygon>>();

        File file = new File("C:\\Users\\Felix\\Desktop\\Uni\\AllThoseTerritories\\angabe\\world.map");
        Reader readWorld = new Reader(file);

        World newWorld = readWorld.getWorld();

        Map<Integer, Patch[]> patches = newWorld.getTerritoryPatchesMap();


        JPanel p = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {

                Point[] patchPoints = null;
                int[] x = null;
                int[] y = null;


                super.paintComponent(g);
                g.setColor(Color.BLUE);


                for (Map.Entry<Integer,Patch[]> q : patches.entrySet()) {

                    Queue<Polygon> oneTerritoriesPolygons=new LinkedList<Polygon>();


                    for (int i = 0; i < q.getValue().length; i++) {


                        patchPoints = q.getValue()[i].getBorders();


                        x = new int[patchPoints.length];
                        y = new int[patchPoints.length];


                        for (int j = 0; j < patchPoints.length; j++) {


                            x[j] = patchPoints[j].x;
                            y[j] = patchPoints[j].y;


                        }
                        poly=new Polygon(x,y,x.length);


                        g.drawPolygon(poly);

                        if(territoryPolygons!=null && territoryPolygons.containsKey(q.getKey())){
                            oneTerritoriesPolygons=territoryPolygons.get(q.getKey());
                            oneTerritoriesPolygons.add(poly);

                            territoryPolygons.put(q.getKey(),oneTerritoriesPolygons);
                        }

                        else{
                           oneTerritoriesPolygons.add(poly);
                            territoryPolygons.put(q.getKey(),oneTerritoriesPolygons);

                        }
                    }
                }
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(1300, 800);
            }
        };

        MouseAdapter ma = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                super.mouseClicked(me);


                for (Map.Entry<Integer,Queue<Polygon>> q : territoryPolygons.entrySet()){

                    for(Polygon pol : q.getValue()){
                        if(pol.contains(me.getPoint())){
                            System.out.println(q.getKey()+" clicked");
                        }
                    }


                }

            }
        };
        p.addMouseListener(ma);//add listener to panel
        mainMap.add(p);

        mainMap.pack();
        mainMap.setVisible(true);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new InterfaceGui();
            }
        });
    }
}