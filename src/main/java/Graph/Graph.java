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

public class Graph extends JApplet {
    private static final ArrayList<String> vs = new ArrayList<String>();
    private static final ArrayList<String> is = new ArrayList<String>();
    private static final ArrayList<Relationship> relationships = new ArrayList<>();

    private static final ArrayList<String> vs2 = new ArrayList<String>();
    private static final ArrayList<String> is2 = new ArrayList<String>();
    private static final ArrayList<Relationship> relationships2 = new ArrayList<>();

    private static final Dimension DEFAULT_SIZE = new Dimension(500, 500);
    double scale = 1.0;
    private JGraphXAdapter<String, RelationshipEdge> jgxAdapter;

    public Graph(double scale) {
        if (scale <= 0) {
            throw new IllegalArgumentException("Scale can not be a negative number");
        }
        this.scale = scale;
    }

    public final void draw(JPanel panel) {
        this.init();
        panel.add(this);
    }

    public final void draw2(JPanel panel) {
        this.init2();
        panel.add(this);
    }

    public final ArrayList[] importData(ArrayList<String> fileName, ArrayList<Long> fileSize, ArrayList<Relationship> relationship) throws IllegalArgumentException {
        if (fileName.isEmpty() || fileSize.isEmpty() || relationship.isEmpty()) {
            throw new IllegalArgumentException("No files to load");
        }
        vs.clear();
        is.clear();
        relationships.clear();

        for (int i = 0; i < fileName.size(); i++) {
            vs.add(fileName.get(i) + "\n" + fileSize.get(i).toString());
            is.add("1");
            relationships.add(relationship.get(i));
        }

        //calculate relationships count
        for (Relationship rel : relationships) {
            String name = rel.getName();
            //find vs with this name
            System.out.println("FROM: " + name);
            Integer outCount = 0;
            for (int i = 0; i < rel.getDependencies().size(); i++) {
                outCount++;
                String depName = rel.getDependencies().get(i);
                System.out.println("DEP:\t" + depName);
                Integer pos = Relationship.getIndexFromName(relationships, rel.getDependencies().get(i));
                if (pos != -1) {
                    relationships.get(pos).incrementInCount();
                    pos++;
                }
            }
            rel.addOutCount(outCount);
            System.out.println();
        }
        return new ArrayList[]{vs, is, relationships};
    }

    public final ArrayList[] importData2(ArrayList<String> fileName, ArrayList<Long> fileSize, ArrayList<Relationship> relationship, ArrayList<String> fileName2, ArrayList<Long> fileSize2, ArrayList<Relationship> relationship2) throws IllegalArgumentException {
        if (fileName.isEmpty() || fileSize.isEmpty() || relationship.isEmpty() || fileName2.isEmpty() || fileSize2.isEmpty() || relationship2.isEmpty()) {
            throw new IllegalArgumentException("No files to load");
        }
        vs.clear();
        is.clear();
        relationships.clear();
        vs2.clear();
        is2.clear();
        relationships2.clear();
        for (int i = 0; i < fileName.size(); i++) {
            vs.add(fileName.get(i) + "\n" + fileSize.get(i).toString());
            is.add("1");
            relationships.add(relationship.get(i));
        }
        for (int i = 0; i < fileName2.size(); i++) {
            vs2.add(fileName2.get(i) + "\n" + fileSize2.get(i).toString());
            is2.add("1");
            relationships2.add(relationship2.get(i));
        }
        //calculate relationships count
        for (Relationship rel : relationships) {
            String name = rel.getName();
            //find vs with this name
            System.out.println("FROM: " + name);
            Integer outCount = 0;
            for (int i = 0; i < rel.getDependencies().size(); i++) {
                outCount++;
                String depName = rel.getDependencies().get(i);
                System.out.println("DEP:\t" + depName);
                Integer pos = Relationship.getIndexFromName(relationships, rel.getDependencies().get(i));
                if (pos != -1) {
                    relationships.get(pos).incrementInCount();
                    pos++;
                }
            }
            rel.addOutCount(outCount);
            System.out.println();
        }
        for (Relationship rel : relationships2) {
            String name = rel.getName();
            //find vs with this name
            System.out.println("FROM: " + name);
            Integer outCount = 0;
            for (int i = 0; i < rel.getDependencies().size(); i++) {
                outCount++;
                String depName = rel.getDependencies().get(i);
                System.out.println("DEP:\t" + depName);
                Integer pos = Relationship.getIndexFromName(relationships, rel.getDependencies().get(i));
                if (pos != -1) {
                    relationships.get(pos).incrementInCount();
                    pos++;
                }
            }
            rel.addOutCount(outCount);
            System.out.println();
        }
        return new ArrayList[]{vs, is, relationships, relationships2, vs2, is2};
    }

    @Override
    public void init() {
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
        for (Relationship rel : relationships) {
            String name = rel.getName();
            g.addVertex(name);
        }

        //Add edges
        for (Relationship rel : relationships) {
            String name = rel.getName();
            for (int i = 0; i < rel.getDependencies().size(); i++) {
                String depName = rel.getDependencies().get(i);
                g.addEdge(name, depName, new RelationshipEdge("IN:" + rel.getInCount() + ", OUT: " + rel.getOutCount()));
            }
        }

        // positioning via JGraphX layouts
        mxCircleLayout layout = new mxCircleLayout(jgxAdapter);
        // center the circle
        int radius = 150;
        layout.setX0(DEFAULT_SIZE.width - radius);
        layout.setY0(DEFAULT_SIZE.height - radius);
        layout.setRadius(radius);
        layout.setMoveCircle(true);

        mxGraphView view = layout.getGraph().getView();
        view.setScale(scale);
        layout.execute(jgxAdapter.getDefaultParent());
    }

    public void init2() {
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
        for (Relationship rel : relationships) {
            String name = rel.getName();
            g.addVertex(name);
        }
        for (Relationship rel : relationships2) {
            String name = rel.getName();
            g.addVertex(name);
        }

        //Add edges
        for (Relationship rel : relationships) {
            String name = rel.getName();
            for (int i = 0; i < rel.getDependencies().size(); i++) {
                String depName = rel.getDependencies().get(i);
                g.addEdge(name, depName, new RelationshipEdge("IN:" + rel.getInCount() + ", OUT: " + rel.getOutCount()));
            }
        }
        for (Relationship rel : relationships2) {
            String name = rel.getName();
            for (int i = 0; i < rel.getDependencies().size(); i++) {
                String depName = rel.getDependencies().get(i);
                g.addEdge(name, depName, new RelationshipEdge("IN:" + rel.getInCount() + ", OUT: " + rel.getOutCount()));
            }
        }
        // positioning via JGraphX layouts
        mxCircleLayout layout = new mxCircleLayout(jgxAdapter);
        // center the circle
        int radius = 150;
        layout.setX0(DEFAULT_SIZE.width - radius);
        layout.setY0(DEFAULT_SIZE.height - radius);
        layout.setRadius(radius);
        layout.setMoveCircle(true);

        mxGraphView view = layout.getGraph().getView();
        view.setScale(scale);
        layout.execute(jgxAdapter.getDefaultParent());
    }
}