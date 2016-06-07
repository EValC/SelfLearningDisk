package Algorithm;

import Items.Job;
import Items.Queue;

public class Priority1 extends MyAlgorithm{
    
    public Priority1(Queue workQueue)
    {
        super(workQueue);
    }
    
    @Override
    public Job nextStep (int simulationTime)
    {
        updateReadyQueue(simulationTime); 
        if(readyQueue.size() > 1) { readyQueue.OrderedByPriority(); }
        if(!busy) 
        {
            if(readyQueue.isEmpty()) {return null;}
            busy = true;
            setCurrentJob();  
        }
        return workInCPU(simulationTime); 
    }
   
    protected Job workInCPU(int simulationTime)
    {
        currentJob.jobWorked(simulationTime);
        if(currentJob.getRemainTime() == 0) {busy = false;} 
        return currentJob;
    }
    
}
