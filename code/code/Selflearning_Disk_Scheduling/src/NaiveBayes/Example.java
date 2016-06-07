package NaiveBayes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import javax.swing.JOptionPane;

public class Example {

    public static void main(int n) {

        try{
        final Classifier<String, String> bayes = new BayesClassifier<String, String>();

       File f=new File("cpu.txt");
       int a=(int) f.length();
        String[] v=new String[10000];
        String line="";
        
        BufferedReader br=new BufferedReader(new FileReader("cpu.txt"));
          int k=0;
        while((line=br.readLine())!=null)
        {
           v = line.split("\\s");
           
           if(k<n)
           {
           k++;
        
        final String[] postext = "true".split("\\s");
        bayes.learn("positives", Arrays.asList(postext));

        final String[] negtext = "false".split("\\s");
        bayes.learn("negatives", Arrays.asList(negtext));

        System.out.println(k+","+  bayes.classify(Arrays.asList(v)).getCategory());
        ((BayesClassifier<String, String>) bayes).classifyDetailed(
                Arrays.asList(v));
      
        }
           
        }
            JOptionPane.showMessageDialog(null, "NaiveBayes Process has been completed..");
        }catch(Exception ex)
        {
            System.out.println(ex);
        }
    }

}
