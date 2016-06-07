package cpu;

import Items.Queue;
public class MainQueue {
 
    private static Queue mainqueue;  
    private static Queue temporaryqueue;  
   
    public static Queue createNew(int numjobs){
        mainqueue = new Queue(numjobs);
        mainqueue.fill();   
        temporaryqueue = mainqueue.getClearCopy(); 
        return mainqueue;
    }
  
    public static void reset()
    {
        mainqueue = temporaryqueue.getClearCopy();
    }
    
   
    public static void add(Queue queue)
    {
        mainqueue = queue.getClearCopy();
        temporaryqueue = mainqueue.getClearCopy();
    }
    
    
    public static Queue get(){
        return mainqueue.getCopy();
    }
    
}
