import com.mxgraph.layout.*;
import com.mxgraph.swing.*;
import org.jgrapht.*;
import org.jgrapht.ext.*;
import org.jgrapht.graph.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Graf extends JApplet
{
    private static final ArrayList<String> vs = new ArrayList<String>();
    private static final ArrayList<String> is = new ArrayList<String>();

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

    public final ArrayList[] importData(ArrayList<String> fileName, ArrayList<Long> fileSize) {

        vs.clear();
        is.clear();

        for(int i = 0; i < Math.ceil(Math.min(fileName.size(), fileSize.size())); i++) {
            vs.add(fileName.get(i) + "\n" + fileSize.get(i).toString());
            is.add(String.valueOf(Math.random()));
        }

        return new ArrayList[]{vs, is};

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

        int n = Math.min(vs.size(), is.size());

        // add some sample data (graph manipulated via JGraphX)

        //jgxAdapter.getStylesheet().getDefaultEdgeStyle().put(mxConstants.STYLE_NOLABEL,"0");

        for(int i=0;i<n;i++){
            g.addVertex(vs.get(i));
        }

        for(int i=0;i<n-1;i++){
           g.addEdge( vs.get(i), vs.get(i+1), new RelationshipEdge(is.get(i)) );
           g.addEdge( vs.get(i+1), vs.get(i), new RelationshipEdge("") );
        }

        // positioning via JGraphX layouts
        mxCircleLayout layout = new mxCircleLayout(jgxAdapter);

        // center the circle
        int radius = 170;
        layout.setX0((DEFAULT_SIZE.width / 2.0) - radius);
        layout.setY0((DEFAULT_SIZE.height / 2.0) - radius);
        layout.setRadius(radius);
        layout.setMoveCircle(true);

        layout.execute(jgxAdapter.getDefaultParent());
    }
}