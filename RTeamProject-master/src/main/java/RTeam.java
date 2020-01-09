import Graph.Graph;
import Graph.Relationship;
import files.model.JavaFile;
import files.model.JavaFileContent.JavaClass;
import files.model.JavaFileContent.JavaMethod;
import files.model.JavaFileContent.PredefinedJavaClass;
import files.model.PackageFile;

import files.service.ClassDeclaration;
import files.service.Parser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class RTeam {

 static JFrame frame = new JFrame();
 static double scale=1.0;
    public static void frameInit(String title){
        frame.setTitle(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel=new JPanel();
        panel.setLayout(new BorderLayout());
        frame.getContentPane().add(panel);
        JMenuBar mb = new JMenuBar();
        JButton  saveMenuItem = new JButton ("Export");
        JButton  story1=new JButton ("Story 1");
        JButton  story2=new JButton ("Story 2");
        JButton  scaleIncrease =new JButton ("Scale++");
        JButton  scaleDecrease =new JButton ("Scale--");
        mb.add(saveMenuItem);
        mb.add(story1);
        mb.add(story2);
        mb.add(scaleIncrease);
        mb.add(scaleDecrease);
        story1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.removeAll();
                story1(scale,panel);
            }
        });
        story2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.removeAll();
                story2(scale,panel);
            }
        });
        scaleIncrease.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scale+=0.1;
                System.out.println(scale);
            }
        });
        scaleDecrease.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scale-=0.1;
                System.out.println(scale);
            }
        });
        frame.setMinimumSize(new Dimension(700,700));
        frame.setJMenuBar(mb);
        frame.pack();
        frame.setVisible(true);

    }


    public static void story1(double scale,JPanel panel){
        PackageFile pack = new PackageFile(JavaFile.getProjectPath());
        List<JavaClass> classes = pack.getSubClasses();
        List<JavaFile> files = pack.getSubFiles();
        Map<JavaMethod, Integer> map = classes.get(3).getMethodsCall();

        ArrayList<String> fileName = new ArrayList<>();
        ArrayList<Long> fileSize = new ArrayList<>();
        ArrayList<Relationship> relationships = new ArrayList<>();
        try {
            for (JavaFile jf : pack.getSubFiles()) {
                fileName.add(jf.getName());
                fileSize.add(jf.getSize());

                ArrayList<String> relationshipNames = new ArrayList<>();
                for(JavaFile a : jf.getImports()) {
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

    public static void story2(double scale,JPanel panel){
        PackageFile pack = new PackageFile(JavaFile.getProjectPath());

        ArrayList<String> methodName = new ArrayList<>();
        ArrayList<Long> methodCallCount = new ArrayList<>();
        ArrayList<Relationship> relationships = new ArrayList<>();

        List<JavaFile> files =pack.getSubFiles();
        List<JavaMethod> methods= new ArrayList<>();

        for(JavaFile jf: files){
            methods.addAll(jf.getMethods());
        }

        for (JavaMethod jm : methods) {
            Map<JavaMethod, Integer> calledMethods = jm.getCalledMethod();

            Iterator<Map.Entry<JavaMethod, Integer>> itr = calledMethods.entrySet().iterator();

            while(itr.hasNext()) {
                Map.Entry<JavaMethod, Integer> entry = itr.next();

                if(entry.getKey() != null) {
                    methodName.add(entry.getKey().getClassMethodName());
                    methodCallCount.add((long) entry.getValue());
                    ArrayList<String> calls = new ArrayList<>();

                    Map<JavaMethod, Integer> innerCalls = entry.getKey().getCalledMethod();
                    Iterator<Map.Entry<JavaMethod, Integer>> innerItr = innerCalls.entrySet().iterator();

                    while(innerItr.hasNext()) {
                        Map.Entry<JavaMethod, Integer> call = innerItr.next();
                        if(call.getKey() != null) {
                            calls.add(call.getKey().getClassMethodName());
                        }
                    }

                    relationships.add(new Relationship(entry.getKey().getClassMethodName(), calls));
                }
            }
        }

        Graph applet = new Graph(scale);
        applet.importData(methodName, methodCallCount, relationships);
        applet.draw( panel);
    }

    public static void main(String[] args){
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