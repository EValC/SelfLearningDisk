package Items;

import java.util.Random;
public class Job {
    
    public int jobNumber;   
    public int arrivalTime;
    public int burst;
    private int start;
    public int priority;
    public boolean finished; 
    private int finish;        
    private int remaining;

    
    public Job(int jobNumber)
    {
        this.jobNumber = jobNumber;
        finished = false;
        Random rand = new Random();
        if(jobNumber == 1) {arrivalTime =0;}    
        else {arrivalTime = rand.nextInt(30)+1;}  
        burst = rand.nextInt(12)+1;
        priority = rand.nextInt(125)+1;
        finish = 0 ;
        remaining = burst;
    }
    
    public Job(int jobNumber , int arrive , int burst , int prior)
    {
        this.jobNumber = jobNumber;
        finished = false;
        arrivalTime = arrive;
        this.burst = burst;
        priority = prior;
        finish = 0 ;        
        remaining = burst;
    }
    
    public void jobWorked(int simulationTime){
        if( burst == remaining) 
        { start = simulationTime;}
        remaining--;
        if(remaining == 0)  
        {
            finish = simulationTime + 1;
            finished = true;
        }
    }
    
    public Job copyJob()
    {
        Job temp = new Job(this.jobNumber);
        temp.arrivalTime = this.arrivalTime;
        temp.burst = this.burst;
        temp.finished = this.finished;
        temp.jobNumber = this.jobNumber;
        temp.priority = this.priority;
        temp.setStart(this.start);
        temp.setFinish(this.finish);
        return temp;
    }
    public Job getClearCopyJob()
    {
        Job temp = new Job(this.jobNumber);
        temp.arrivalTime = this.arrivalTime;
        temp.burst = this.burst;
        temp.priority = this.priority;
        temp.remaining = this.remaining;
        return temp;
    }
    
    
    public int getPercent() {
        return (int)((burst - getRemainTime())*100) / burst;
    }
    
  
    public int getWaitTime(int SimulationTime) {
        return (getTurnaround(SimulationTime) - (burst - getRemainTime()));
    }
    
  
    public int getRemainTime(){
        return this.remaining;
    }
    
    
    public int getTurnaround(int SimulationTime){
        if(finished){ 
            return (finish - arrivalTime );
        }
        if(SimulationTime > arrivalTime){ 
           return (SimulationTime - arrivalTime);
        }
        return 0; 
    }
    public int getFinish(){
        if(finished) 
        {
            return finish;
        }
        return 0;
    }
 
    public int getStart(){
        return start;
    }
   
    public void setRemainTime(int remaining){
        this.remaining = remaining;
    }
    
    
    public void setFinish(int finish){
        this.finish = finish;
    }
    
   
    public void setStart(int start){
        this.start = start;
    }
    
   
    
    
    public boolean isFirst(Job other) {
        if(this.arrivalTime == other.arrivalTime) // if both have the same arrive time
        {
            return (this.jobNumber < other.jobNumber);
        }
        return (this.arrivalTime < other.arrivalTime);
    }
   
    public boolean isShort(Job other){
        if(this.burst == other.burst) 
        {
            return isFirst(other);
        }
        return (this.burst < other.burst);
    }
    
    
    public boolean isPrior(Job other){
        if(this.priority == other.priority) {
            return isFirst(other);
        }
        return (this.priority < other.priority);
    }
    
    
    public boolean isShortRemain(Job other){
        if(this.remaining == other.remaining) 
        {
            return isFirst(other);
        }
        return (this.remaining < other.remaining);
    }
    
   
    
    
    
    
    public void ShowData()
    {
        System.out.println("Showing job data");
        if(this == null) {System.out.println("Empty job"); return;}
        System.out.println("# = " + this.jobNumber + " , arrive = " + this.arrivalTime + " , burst = " + this.burst);
    }
}

