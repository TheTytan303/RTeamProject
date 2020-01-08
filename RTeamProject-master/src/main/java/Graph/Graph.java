package Graph;

import com.mxgraph.layout.*;
import com.mxgraph.swing.*;
import com.mxgraph.view.mxGraphView;
import org.jgrapht.*;
import org.jgrapht.ext.*;
import org.jgrapht.graph.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Graph extends JApplet
{
    private static final ArrayList<String> vs = new ArrayList<String>();
    private static final ArrayList<String>is = new ArrayList<String>();
    private static final ArrayList<Relationship> relationships = new ArrayList<>();
    double scale=1.0;
    private static final Dimension DEFAULT_SIZE = new Dimension(1900, 1000);
    private JGraphXAdapter<String, RelationshipEdge> jgxAdapter;
    public Graph(double scale){
        this.scale=scale;
    }
    public final void draw(String title, boolean visible){
        this.init();

        JFrame frame = new JFrame();
        frame.getContentPane().add(this);
        frame.setTitle(title);
        frame.pack();
        frame.setVisible(visible);
        frame.setAlwaysOnTop(true);
    }

    public final ArrayList[] importData(ArrayList<String> fileName, ArrayList<Long> fileSize, ArrayList<Relationship> relationship) {

        vs.clear();
        is.clear();

        for(int i = 0; i < fileName.size(); i++) {
            vs.add(fileName.get(i) + "\n" + fileSize.get(i).toString());
            is.add("1");
            relationships.add(relationship.get(i));
        }

        //calculate relationships count
        for(Relationship rel : relationships) {
            String name = rel.getName();

            //find vs with this name
            System.out.println("FROM: " + name);

            Integer outCount = 0;
            for(int i=0; i<rel.getDependencies().size(); i++){
                outCount++;

                String depName = rel.getDependencies().get(i);
                System.out.println("DEP:\t" + depName);

                Integer pos = Relationship.getIndexFromName(relationships, rel.getDependencies().get(i));
                if(pos != -1) {
                    relationships.get(pos).incrementInCount();
                    pos++;
                }
            }
            rel.addOutCount(outCount);

            System.out.println("\n\n");
        }

        return new ArrayList[]{vs, is, relationships};

    }

    @Override

    public void init()
    {
        // create a JGraphT graph
        ListenableGraph<String, RelationshipEdge> g = new DefaultListenableGraph<>(new DefaultDirectedGraph<>(RelationshipEdge.class));

        // create a visualization using JGraph, via an adapter
        jgxAdapter = new JGraphXAdapter<>(g);
        setPreferredSize(DEFAULT_SIZE);
        mxGraphComponent component = new mxGraphComponent(jgxAdapter);
        component.setConnectable(false);
        component.getGraph().setAllowDanglingEdges(false);
        getContentPane().add(component);
        resize(DEFAULT_SIZE);

        //jgxAdapter.getStylesheet().getDefaultEdgeStyle().put(mxConstants.STYLE_NOLABEL,"0");

        //add vertices
        for(Relationship rel : relationships) {
            String name = rel.getName();
            g.addVertex(name);
        }

        //Add edges
        for(Relationship rel : relationships) {
            String name = rel.getName();

            for(int i=0; i<rel.getDependencies().size(); i++){
                String depName = rel.getDependencies().get(i);
                g.addEdge(name, depName, new RelationshipEdge("IN:" + rel.getInCount() + ", OUT: " + rel.getOutCount()));
            }
        }

        // positioning via JGraphX layouts
        mxCircleLayout layout = new mxCircleLayout(jgxAdapter);
        // center the circle
        int radius = 150;
        layout.setX0((DEFAULT_SIZE.width / 2.0) - radius);
        layout.setY0((DEFAULT_SIZE.height / 2.0) - radius);
        layout.setRadius(radius);
        layout.setMoveCircle(true);


        mxGraphView view = layout.getGraph().getView();
        view.setScale(scale);
        layout.execute(jgxAdapter.getDefaultParent());



    }
}