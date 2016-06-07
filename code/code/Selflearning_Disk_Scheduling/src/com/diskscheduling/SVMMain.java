package com.diskscheduling;
import javax.swing.JOptionPane;
import svmlearn.*;
public class SVMMain {
	public static void test() {  
		SVM s = new SVM();
		Problem train = new Problem();
		Problem test = new Problem();
                System.out.println(" SVM Process:\n");
                  System.out.println(" ============================:\n");
		train.loadBinaryProblem("cpu.svm");
		test.loadBinaryProblem("cpu.svm");
		System.out.println("Loaded.");
		System.out.println("Training...");
		KernelParams kp = new KernelParams(1,1,1,1);
		s.svmTrain(train, kp, 1);
		System.out.println("Testing...");
		int [] pred = s.svmTest(test);
		for (int i=0; i<pred.length; i++)
		System.out.println(pred[i]);
		EvalMeasures e = new EvalMeasures(test, pred, 2);
		System.out.println("Accuracy=" + e.Accuracy());
		System.out.println("Done.");
                  JOptionPane.showMessageDialog(null, "SVM Process completed..");
	}
}