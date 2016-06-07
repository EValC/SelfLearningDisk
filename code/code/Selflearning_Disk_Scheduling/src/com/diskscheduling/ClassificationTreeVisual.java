package com.diskscheduling;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.FileReader;
import weka.classifiers.*;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.gui.treevisualizer.PlaceNode2;
import weka.gui.treevisualizer.TreeVisualizer;

public class ClassificationTreeVisual {
public static void main(String args[]) throws Exception {
     J48 cls = new J48();
     Instances data = new Instances(new BufferedReader(new FileReader("cpu.arff")));
     data.setClassIndex(data.numAttributes() - 1);
     cls.buildClassifier(data);
     final javax.swing.JFrame jf = 
       new javax.swing.JFrame("Classification Tree View");
     jf.setSize(500,400);
     jf.getContentPane().setLayout(new BorderLayout());
     TreeVisualizer tv = new TreeVisualizer(null,cls.graph(),new PlaceNode2());
     jf.getContentPane().add(tv, BorderLayout.CENTER);
     jf.addWindowListener(new java.awt.event.WindowAdapter() {
       public void windowClosing(java.awt.event.WindowEvent e) {
         jf.dispose();
       }
     });

     jf.setVisible(true);
     tv.fitToScreen();
   }

}
