import com.mxgraph.layout.*;
import com.mxgraph.swing.*;
import org.jgrapht.*;
import org.jgrapht.ext.*;
import org.jgrapht.graph.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Graf extends JApplet
{
    private static final Dimension DEFAULT_SIZE = new Dimension(700, 700);

    private JGraphXAdapter<String, RelationshipEdge> jgxAdapter;


    public static final void draw(String title, boolean visible){
        Graf applet = new Graf();
        applet.init();

        JFrame frame = new JFrame();
        frame.getContentPane().add(applet);
        frame.setTitle(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(visible);
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
        int n=10;

        // add some sample data (graph manipulated via JGraphX)

        ArrayList<String> vs= new ArrayList<String>();
        ArrayList<String> is= new ArrayList<String>();
        //jgxAdapter.getStylesheet().getDefaultEdgeStyle().put(mxConstants.STYLE_NOLABEL,"0");

        for(int i=0;i<n;i++){
            vs.add("v"+i);
            is.add(Integer.toString(i));
            g.addVertex(vs.get(i));
        }

        for(int i=0;i<n-1;i++){
           g.addEdge(vs.get(i),vs.get(i+1),new RelationshipEdge(is.get(i)));
           g.addEdge(vs.get(i+1),vs.get(i),new RelationshipEdge(""));
        }

        // positioning via jgraphx layouts
        mxCircleLayout layout = new mxCircleLayout(jgxAdapter);

        // center the circle
        int radius = 170;
        layout.setX0((DEFAULT_SIZE.width / 2.0) - radius);
        layout.setY0((DEFAULT_SIZE.height / 2.0) - radius);
        layout.setRadius(radius);
        layout.setMoveCircle(true);

        layout.execute(jgxAdapter.getDefaultParent());
        // that's all there is to it!...
    }
}

class RelationshipEdge
        extends
        DefaultEdge
{
    private String label;

    public RelationshipEdge(String label)
    {
        this.label = label;
    }

    public String getLabel()
    {
        return label;
    }

    @Override
    public String toString()
    {
        return  label;
    }
}