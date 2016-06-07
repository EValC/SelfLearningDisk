package Algorithm;

import Items.Job;
import Items.Queue;

public class FCFS extends MyAlgorithm
{

    public FCFS(Queue workQueue)
    {  
        super(workQueue); 
    }

    public Job nextStep (int simulationTime)
    {
        updateReadyQueue(simulationTime);  
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
