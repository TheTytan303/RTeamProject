package Graph;

import Rteam.RTeam;
import com.mxgraph.layout.*;
import com.mxgraph.swing.*;
import com.mxgraph.view.mxGraphView;
import files.service.StoriesExportAdapter;
import org.jgrapht.*;
import org.jgrapht.ext.*;
import org.jgrapht.graph.*;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Graph extends JApplet {
    private static final ArrayList<String> vs = new ArrayList<String>();
    private static final ArrayList<String> is = new ArrayList<String>();
    private static final ArrayList<Relationship> relationships = new ArrayList<>();

    private static final ArrayList<String> vs2 = new ArrayList<String>();
    private static final ArrayList<String> is2 = new ArrayList<String>();
    private static final ArrayList<Relationship> relationships2 = new ArrayList<>();

    public static StoriesExportAdapter storiesExportAdapter;

    private static final Dimension DEFAULT_SIZE = new Dimension(500, 500);
    private static final ArrayList<String> vs3 = new ArrayList<String>();
    private static final ArrayList<String> is3 = new ArrayList<String>();
    private static final ArrayList<Relationship> relationships3 = new ArrayList<>();
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

    public final void drawAllStories(JPanel panel) {
        this.initAllStories();
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

            var str = fileName.get(i) + "\n";

            if(i < fileSize.size()) {
                str += fileSize.get(i).toString();
            }

            vs.add(str);
            is.add("1");
            if(i < relationship.size()) {
                relationships.add(relationship.get(i));
            }
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

    public final ArrayList[] importDataAllStories(ArrayList<String> fileName, ArrayList<Long> fileSize, ArrayList<Relationship> relationship, ArrayList<String> fileName2, ArrayList<Long> fileSize2, ArrayList<Relationship> relationship2,
                                         ArrayList<String> packages, ArrayList<Long> count, ArrayList<Relationship> relationship3) throws IllegalArgumentException {
       if (fileName.isEmpty() || fileSize.isEmpty() || relationship.isEmpty() || fileName2.isEmpty() || fileSize2.isEmpty() || relationship2.isEmpty()||packages.isEmpty()||count.isEmpty()||relationship3.isEmpty()) {
            throw new IllegalArgumentException("No files to load");
        }
        vs.clear();
        is.clear();
        relationships.clear();
        vs2.clear();
        is2.clear();
        relationships2.clear();
        vs3.clear();
        is3.clear();
        relationships3.clear();

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
        for (int i = 0; i < packages.size(); i++) {
            vs3.add(packages.get(i) + "\n" + packages.get(i).toString());
            is3.add("1");
            relationships3.add(relationship3.get(i));
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
                Integer pos = Relationship.getIndexFromName(relationships2, rel.getDependencies().get(i));
                if (pos != -1) {
                    relationships2.get(pos).incrementInCount();
                    pos++;
                }
            }
            rel.addOutCount(outCount);
            System.out.println();
        }
        for (Relationship rel : relationships3) {
            String name = rel.getName();
            //find vs with this name
            System.out.println("FROM: " + name);
            Integer outCount = 0;
            for (int i = 0; i < rel.getDependencies().size(); i++) {
                outCount++;
                String depName = rel.getDependencies().get(i);
                System.out.println("DEP:\t" + depName);
                Integer pos = Relationship.getIndexFromName(relationships3, rel.getDependencies().get(i));
                if (pos != -1) {
                    relationships3.get(pos).incrementInCount();
                    pos++;
                }
            }
            rel.addOutCount(outCount);
            System.out.println();
        }
        return new ArrayList[]{vs, is, relationships,  vs2, is2,relationships2,vs3,is3, relationships3};
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

        storiesExportAdapter = new StoriesExportAdapter();


        //add vertices
        for (Relationship rel : relationships) {
            String name = rel.getName();
            g.addVertex(name);
            for (int i = 0; i < rel.getDependencies().size(); i++) {
                name = rel.getDependencies().get(i);
                g.addVertex(name);
            }
            storiesExportAdapter.addStoryParticipant(name);
        }

        for (Relationship rel : relationships) {
            String name = rel.getName();
            for (int i = 0; i < rel.getDependencies().size(); i++) {
                String depName = rel.getDependencies().get(i);
                try {
                    g.addEdge(name, depName, new RelationshipEdge("IN:" + rel.getInCount() + ", OUT: " + rel.getOutCount()));
                } catch ( Exception e) {
                    System.out.println("Cannot add link from " + name + " to: " + depName);
                }

                storiesExportAdapter.addStoryRelation(name,depName, rel.getInCount()+"", rel.getOutCount()+"");

                }
            }




        // positioning via JGraphX layouts
        mxCircleLayout layout = new mxCircleLayout(jgxAdapter);
        // center the circle
        int radius = 20;
        layout.setX0(DEFAULT_SIZE.width - radius);
        layout.setY0(DEFAULT_SIZE.height - radius);
        layout.setRadius(radius);
        layout.setMoveCircle(true);

        mxGraphView view = layout.getGraph().getView();
        view.setScale(scale);
        layout.execute(jgxAdapter.getDefaultParent());
    }

    public void initAllStories() {
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
        for (Relationship rel : relationships3) {
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
        for (Relationship rel : relationships3) {
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