package Algorithm;

import Items.Job;
import Items.Queue;
public abstract class MyAlgorithm {
    
    protected Queue list ; 
    protected Queue readyQueue;  
    protected Job currentJob;  
    protected boolean busy ;  
    
    public MyAlgorithm(Queue workQueue)
    {
        readyQueue = new Queue(workQueue.size()); 
        currentJob = new Job(9);  
        busy = false;  
        list = workQueue.getCopy(); 
        list.OrderedByArrive();  
    }
    
    public abstract Job nextStep (int simulationTime);
        protected Job workInCPU(int simulationTime)
    {
        currentJob.jobWorked(simulationTime);
        return currentJob;
    }
    
    public Queue getReadyQueue ()
    {
        return readyQueue.getCopy();
    }
    
    
    public boolean isFinished()
    {
        return (list.isEmpty() && readyQueue.isEmpty()  && !busy &&  currentJob.getRemainTime() == 0);
    }
    protected void updateReadyQueue(int simulationTime)
    {
        for (int i = 0 ; i<list.size() ; i++)
        {
            Job temp = list.getJob(i);
            if(temp.arrivalTime == simulationTime)   
            {
                readyQueue.addJob(temp);  
                list.removeJob(i);   
                i--; 
            }
        }
    }
    
    protected void setCurrentJob()
    {
        currentJob = readyQueue.getJob(0); 
        readyQueue.removeJob(0);
    }
    
}
