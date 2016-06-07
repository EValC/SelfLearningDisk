package cpu;

import Algorithm.FCFS;
import Algorithm.MyAlgorithm;
import Algorithm.Priority1;
import Algorithm.Priority2;
import Algorithm.RR;
import Algorithm.SJF;

import Items.Job;
import Items.Queue;
import cpu.MainQueue;

public class Simulation {
 
    private static MyAlgorithm myAlgorithm; 
    public static int Time;   
    public static String AlgorithmType = "FCFS";  
    public static int Quantum = 2;  
    public static boolean Finished = false; 
    public static boolean Stoped = true; 
   
    public static void reset()
    {
       Time = 0;  
       Finished = false;  
    }
    public static Queue getReadyQueue()
    {
        return myAlgorithm.getReadyQueue();
    }
    public static Job workStep()
    {
        Job job;
        if(Time == 0) {selectAlgorithm();} 
        job = myAlgorithm.nextStep(Time); 
        if(myAlgorithm.isFinished()){Finished = true;} 
        return job;
    }

    private static void selectAlgorithm()
    {
   
        if(AlgorithmType.equals("FCFS")) {myAlgorithm = new FCFS(MainQueue.get());}  
        else if(AlgorithmType.equals("ED")) {myAlgorithm = new SJF(MainQueue.get());} 
        else if(AlgorithmType.equals("CRRN")) {myAlgorithm = new Priority1(MainQueue.get());} 
        else if(AlgorithmType.equals("TCLOO")) {myAlgorithm = new Priority2(MainQueue.get());} 
        else if(AlgorithmType.equals("TCLOO")) {myAlgorithm = new RR(MainQueue.get() , Quantum);}  
    }
}
