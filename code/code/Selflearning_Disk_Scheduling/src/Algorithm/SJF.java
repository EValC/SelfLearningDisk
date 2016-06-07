package Algorithm;

import Items.Job;
import Items.Queue;

public class SJF extends MyAlgorithm{
    
    public SJF(Queue workQueue)
    {
        super(workQueue);
    }
    
    @Override
    public Job nextStep (int simulationTime)
    {
        updateReadyQueue(simulationTime); 
        if(readyQueue.size() > 1) { readyQueue.OrderedByShortest();}  
        if(!busy)  
        {
            if(readyQueue.isEmpty()) {return null;}   
            busy = true;
            setCurrentJob(); 
        }
        return workInCPU(simulationTime); 
    }
    
    @Override
    protected Job workInCPU(int simulationTime)
    {
        currentJob.jobWorked(simulationTime);
        if(currentJob.getRemainTime() == 0) {busy = false;} 
        return currentJob;
    }
   
}
