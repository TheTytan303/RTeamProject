import com.mxgraph.layout.*;
import com.mxgraph.swing.*;
import org.jgrapht.*;
import org.jgrapht.ext.*;
import org.jgrapht.graph.*;
import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class Graf extends JApplet
{
    private static final ArrayList<String> vs = new ArrayList<String>();
    private static final ArrayList<String>is = new ArrayList<String>();
    private static final ArrayList<Relationship> relationships = new ArrayList<>();

    private static final Dimension DEFAULT_SIZE = new Dimension(700, 700);
    private JGraphXAdapter<String, RelationshipEdge> jgxAdapter;

    public final void draw(String title, boolean visible){
        this.init();

        JFrame frame = new JFrame();
        frame.getContentPane().add(this);
        frame.setTitle(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(visible);
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

            Integer inCount = 0;
            for(int i=0; i<rel.getDependencies().size(); i++){

                String depName = rel.getDependencies().get(i);
                System.out.println("DEP:\t" + depName);

                Integer pos = Relationship.getIndexFromName(relationships, rel.getDependencies().get(i));
                if(pos != -1) {
                    relationships.get(pos).incrementInCount();
                    pos++;
                }
            }

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



        // add some sample data (graph manipulated via JGraphX)

        //jgxAdapter.getStylesheet().getDefaultEdgeStyle().put(mxConstants.STYLE_NOLABEL,"0");

        //add Vertices
        for(Relationship rel : relationships) {
            String name = rel.getName();
            g.addVertex(name);
        }


        //Add edges
        for(Relationship rel : relationships) {
            String name = rel.getName();

            for(int i=0; i<rel.getDependencies().size(); i++){
                String depName = rel.getDependencies().get(i);
                try {
                    g.addEdge(name, depName, new RelationshipEdge("IN:" + rel.getInCount() + ", OUT: " + rel.getOutCount()));
                } catch (Exception e) {

                }
            }
        }



       /*
        int n = vs.size();
        for(int i=0;i<n-1;i++){
           g.addEdge( vs.get(i), vs.get(i+1), new RelationshipEdge("Waga"));
           g.addEdge( vs.get(i+1), vs.get(i), new RelationshipEdge("") );
        }
*/
        // positioning via JGraphX layouts
        mxCircleLayout layout = new mxCircleLayout(jgxAdapter);

        // center the circle
        int radius = 150;
        layout.setX0((DEFAULT_SIZE.width / 2.0) - radius);
        layout.setY0((DEFAULT_SIZE.height / 2.0) - radius);
        layout.setRadius(radius);
        layout.setMoveCircle(true);

        layout.execute(jgxAdapter.getDefaultParent());
    }
}