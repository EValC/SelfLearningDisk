package Algorithm;

import Items.Job;
import Items.Queue;

public class RR extends MyAlgorithm
{
    
    private int Quantum; 
    private int processTime; 
   
    public RR(Queue workQueue , int quantum)
    {
        super(workQueue);
        this.Quantum = quantum;  
    }
   
    @Override
    public Job nextStep (int simulationTime)
    {
        updateReadyQueue(simulationTime); 
        if(!busy) 
        {
            if(simulationTime!=0 && currentJob.getRemainTime() !=0) 
            {readyQueue.addJob(currentJob); } 
            if(readyQueue.isEmpty()) {return null;}
            processTime = Quantum;  
            busy = true;
            setCurrentJob();  
        }
        return workInCPU(simulationTime); 
    }
   
    @Override
    protected Job workInCPU(int simulationTime)
    {
        currentJob.jobWorked(simulationTime);
        processTime--;  
        if(processTime == 0 || currentJob.getRemainTime() ==0 ) 
          {busy = false;} 
        return currentJob;
    }
    
}
