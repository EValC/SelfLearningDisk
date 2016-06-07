package cpu;

import Items.Cell;
import Items.Job;
import Items.Queue;
import java.util.ArrayList;

public class finalchart {
    private static int X = 359; 
    private static final int Y = 246; 
    public static ArrayList<Cell> araylist = new ArrayList<Cell>(8); 

    public static void update(Queue list){
        
        for(int g=0 ; g<list.size() ; g++)
        {
            Job job = list.getJob(g);
            araylist.add(Cell.createReadyQueueCell(X, Y, job.jobNumber));
            X += 40;
        }
    }
    
    public static void clear(){
         araylist.clear();
         X = 359; 
     }
}
