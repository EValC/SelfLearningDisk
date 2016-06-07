package Algorithm;

import Items.Job;
import Items.Queue;

public class Priority2 extends MyAlgorithm
{
    
    public Priority2(Queue workQueue)
    {
        super(workQueue);
    }
    
    @Override
    public Job nextStep (int simulationTime)
    {
        updateReadyQueue(simulationTime); 
        if(simulationTime!=0 && currentJob.getRemainTime() !=0) {readyQueue.addJob(currentJob); }
        if(readyQueue.size() > 1){ readyQueue.OrderedByPriority();}
        if(readyQueue.isEmpty()) { return null;}
        setCurrentJob();  
        return workInCPU(simulationTime); 
    }
    
}
