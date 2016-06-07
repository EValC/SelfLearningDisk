package cpu;

import Items.Cell;
import Items.Job;
import java.util.ArrayList;

public class GanttChart {
    private static int X =20 ; 
    private static final int Y = 435; 
    private static int lastjob = 0; 
    public static ArrayList<Cell> arraylist = new ArrayList<Cell>(100);
    
    public static void addJob(Job job , int time){
       
       Cell cell ;
       if(job == null) 
       {cell = Cell.createEmptyJobCell(X, Y);} 
       else 
       {
            if(job.jobNumber != lastjob) 
            {
                X += 2;
                lastjob = job.jobNumber;
            }
            cell = Cell.createGanttCell(X, Y, job.jobNumber); 
       }
       X += 11; 
       arraylist.add(cell); 
       if( (time+1) % 10 == 0 ) 
       {
           arraylist.add(Cell.createMark(X -1 , Y+80));
       }
    }
    
   
    public static void clear(){
         arraylist.clear();
         X = 30;  
         lastjob = 0;  
     }
}
