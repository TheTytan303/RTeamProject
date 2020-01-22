package Rteam;

import Graph.Graph;
import Graph.Relationship;
import files.model.JavaFile;
import files.model.JavaFileContent.JavaMethod;
import files.model.PackageFile;
import files.service.GitInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class RTeam {

    public static JFrame frame = new JFrame();
    public static JPanel panel = new JPanel();
    public static ImageIcon img = new ImageIcon("images/Rlogo.jpg");
    public static JButton story1 = new JButton("Story 1");
    public static JButton story2 = new JButton("Story 2");
    public static JButton story3 = new JButton("Story 3");
    public static JButton story4 = new JButton("Story 4");
    static double scale = 1.0;
    static String version = GitInfo.getHeadHash(".");
    static int storyActive = 0;

    public static void frameInit(String title) {
        frame.setTitle(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setLayout(new BorderLayout());
        frame.getContentPane().add(panel);
        frame.setIconImage(img.getImage());
        JMenuBar mb = new JMenuBar();
        JLabel scaleLabel = new JLabel("scale: " + Double.toString(scale));
        JLabel versionLabel = new JLabel("  version: " + version);
        JButton saveMenuItem = new JButton("Export");

        JButton rem = new JButton("Remove");
        JButton scaleIncrease = new JButton("Scale++");
        JButton scaleDecrease = new JButton("Scale--");
        mb.add(saveMenuItem);
        mb.add(story1);
        mb.add(story2);
        mb.add(story3);
        mb.add(story4);
        mb.add(rem);
        mb.add(scaleIncrease);
        mb.add(scaleDecrease);
        mb.add(scaleLabel);
        mb.add(versionLabel);

        story1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.removeAll();
                story1(scale, panel);
                SwingUtilities.updateComponentTreeUI(frame);
                storyActive = 1;
            }
        });
        story2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.removeAll();
                story2(scale, panel);
                SwingUtilities.updateComponentTreeUI(frame);
                storyActive = 2;
            }
        });
        story3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.removeAll();
                story3(scale, panel);
                SwingUtilities.updateComponentTreeUI(frame);
                storyActive = 3;
            }
        });
        story4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.removeAll();
                story4(scale, panel);
                SwingUtilities.updateComponentTreeUI(frame);
                storyActive = 4;
            }
        });
        rem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.removeAll();
                SwingUtilities.updateComponentTreeUI(frame);
                storyActive = 0;
            }
        });
        scaleIncrease.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scale += 0.1;
                System.out.println(scale);
                scaleLabel.setText("scale: " + Double.toString(scale).substring(0, 3));
                panel.removeAll();
                switch (storyActive) {
                    case 1:
                        story1(scale, panel);
                        break;
                    case 2:
                        story2(scale, panel);
                        break;
                    case 3:
                        story3(scale, panel);
                        break;
                    case 4:
                        story4(scale, panel);
                        break;
                }
                SwingUtilities.updateComponentTreeUI(frame);
            }
        });
        scaleDecrease.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (scale - 0.1 > 0.1) {
                    scale -= 0.1;
                    scaleLabel.setText("scale: " + Double.toString(scale).substring(0, 3));
                    System.out.println(scale);
                    panel.removeAll();
                    switch (storyActive) {
                        case 1:
                            story1(scale, panel);
                            break;
                        case 2:
                            story2(scale, panel);
                            break;
                        case 3:
                            story3(scale, panel);
                            break;
                        case 4:
                            story4(scale, panel);
                            break;
                    }
                    SwingUtilities.updateComponentTreeUI(frame);
                }
            }
        });
        frame.setMinimumSize(new Dimension(700, 700));
        frame.setJMenuBar(mb);
        frame.pack();
        frame.setVisible(true);
    }

    public static void story1(double scale, JPanel panel) {
        PackageFile pack = new PackageFile(JavaFile.getProjectPath());
        ArrayList<String> fileName = new ArrayList<>();
        ArrayList<Long> fileSize = new ArrayList<>();
        ArrayList<Relationship> relationships = new ArrayList<>();
        try {
            for (JavaFile jf : pack.getSubFiles()) {
                fileName.add(jf.getName());
                fileSize.add(jf.getSize());
                ArrayList<String> relationshipNames = new ArrayList<>();
                for (JavaFile a : jf.getImports()) {
                    relationshipNames.add(a.getName());
                }
                relationships.add(new Relationship(jf.getName(), relationshipNames));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Graph applet = new Graph(scale);
        applet.importData(fileName, fileSize, relationships);
        applet.draw(panel);
    }

    public static void story2(double scale, JPanel panel) {
        PackageFile pack = new PackageFile(JavaFile.getProjectPath());
        ArrayList<String> methodName = new ArrayList<>();
        ArrayList<Long> methodCallCount = new ArrayList<>();
        ArrayList<Relationship> relationships = new ArrayList<>();
        List<JavaFile> files = pack.getSubFiles();
        List<JavaMethod> methods = new ArrayList<>();
        for (JavaFile jf : files) {
            methods.addAll(jf.getMethods());
        }
        for (JavaMethod jm : methods) {
            Map<JavaMethod, Integer> calledMethods = jm.getCalledMethod();
            Iterator<Map.Entry<JavaMethod, Integer>> itr = calledMethods.entrySet().iterator();
            while (itr.hasNext()) {
                Map.Entry<JavaMethod, Integer> entry = itr.next();
                if (entry.getKey() != null) {
                    methodName.add(entry.getKey().getClassMethodName());
                    methodCallCount.add((long) entry.getValue());
                    ArrayList<String> calls = new ArrayList<>();
                    Map<JavaMethod, Integer> innerCalls = entry.getKey().getCalledMethod();
                    Iterator<Map.Entry<JavaMethod, Integer>> innerItr = innerCalls.entrySet().iterator();
                    while (innerItr.hasNext()) {
                        Map.Entry<JavaMethod, Integer> call = innerItr.next();
                        if (call.getKey() != null) {
                            calls.add(call.getKey().getClassMethodName());
                        }
                    }
                    relationships.add(new Relationship(entry.getKey().getClassMethodName(), calls));
                }
            }
        }
        Graph applet = new Graph(scale);
        applet.importData(methodName, methodCallCount, relationships);
        applet.draw(panel);
    }

    public static void story3(double scale, JPanel panel) {
        PackageFile pack = new PackageFile(JavaFile.getProjectPath());

        ArrayList<String> packages = new ArrayList<>();
        ArrayList<Long> count = new ArrayList<>();
        ArrayList<Relationship> relationships = new ArrayList<>();

       for(var dependency : PackageFile.getAllDependeciesMap(pack).entrySet()) {

           var packageFile = dependency.getKey();
           var packageMap = dependency.getValue();
           packages.add(packageFile.getFullName());

           ArrayList<String> relationshipNames = new ArrayList<>();
           for(var innerPackage : packageMap.entrySet() ) {
               count.add(Long.valueOf(innerPackage.getValue()));
               relationshipNames.add(innerPackage.getKey().getFullName());
           }
           relationships.add(new Relationship(packageFile.getFullName(), relationshipNames));
       }

        Graph applet = new Graph(scale);
        applet.importData(packages, count, relationships);
        applet.draw(panel);
    }

    public static void story4(double scale, JPanel panel) {
        PackageFile pack2 = new PackageFile(JavaFile.getProjectPath());
        ArrayList<String> methodName2 = new ArrayList<>();
        ArrayList<Long> methodCallCount2 = new ArrayList<>();
        ArrayList<Relationship> relationships2 = new ArrayList<>();
        List<JavaFile> files2 = pack2.getSubFiles();
        List<JavaMethod> methods2 = new ArrayList<>();
        for (JavaFile jf : files2) {
            methods2.addAll(jf.getMethods());
        }
        for (JavaMethod jm : methods2) {
            Map<JavaMethod, Integer> calledMethods = jm.getCalledMethod();
            Iterator<Map.Entry<JavaMethod, Integer>> itr = calledMethods.entrySet().iterator();
            while (itr.hasNext()) {
                Map.Entry<JavaMethod, Integer> entry = itr.next();
                if (entry.getKey() != null) {
                    methodName2.add(entry.getKey().getClassMethodName());
                    methodCallCount2.add((long) entry.getValue());
                    ArrayList<String> calls = new ArrayList<>();
                    Map<JavaMethod, Integer> innerCalls = entry.getKey().getCalledMethod();
                    Iterator<Map.Entry<JavaMethod, Integer>> innerItr = innerCalls.entrySet().iterator();
                    while (innerItr.hasNext()) {
                        Map.Entry<JavaMethod, Integer> call = innerItr.next();
                        if (call.getKey() != null) {
                            calls.add(call.getKey().getClassMethodName());
                        }
                    }
                    relationships2.add(new Relationship(entry.getKey().getClassMethodName(), calls));
                }
            }
        }
        PackageFile pack = new PackageFile(JavaFile.getProjectPath());
        ArrayList<String> fileName = new ArrayList<>();
        ArrayList<Long> fileSize = new ArrayList<>();
        ArrayList<Relationship> relationships = new ArrayList<>();
        try {
            for (JavaFile jf : pack.getSubFiles()) {
                fileName.add(jf.getName());
                fileSize.add(jf.getSize());
                ArrayList<String> relationshipNames = new ArrayList<>();
                for (JavaFile a : jf.getImports()) {
                    relationshipNames.add(a.getName());
                }
                relationships.add(new Relationship(jf.getName(), relationshipNames));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Graph applet = new Graph(scale);
        applet.importData2(fileName, fileSize, relationships, methodName2, methodCallCount2, relationships2);
        applet.draw2(panel);
    }

    public static void main(String[] args) {
        frameInit("IO");
    }
}

/*
    //Test helper for PredefinedJavaClass:
    try {
            JavaClass Integer = PredefinedJavaClass.getPredefinedJavaClass("int");
            JavaClass Integer2 = PredefinedJavaClass.getPredefinedJavaClass("Integer");
            if(Integer == Integer2){        //if(Integer and Integer2 are references to same objects)
            System.out.println(true);
            }else{
            System.out.println(false);
            }

            JavaClass Double2 = PredefinedJavaClass.getPredefinedJavaClass("double");
            JavaClass Double = PredefinedJavaClass.getPredefinedJavaClass("Double");
            if(Integer == Double){          //if(Integer and Double are references to same objects)
            System.out.println(true);
            }else{
            System.out.println(false);
            }
            JavaClass Double3 = PredefinedJavaClass.getPredefinedJavaClass("City"); //throws exception

        }
    } catch (FileNotFoundException ignore) {}

*/