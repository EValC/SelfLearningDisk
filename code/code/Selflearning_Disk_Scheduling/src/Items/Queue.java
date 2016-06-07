package Items;

import java.util.ArrayList;

public class Queue {
    
    private ArrayList<Job> mainList;  
    private int number;
    
    
    public Queue(int number)
    {
        mainList = new ArrayList<Job>(number);
        this.number = number; 
    }
    
    public Queue(ArrayList<Job> list)
    {
        mainList = new ArrayList<Job>(list.size());
        mainList.addAll(list);
    }
    
    
    public void fill()
    { 
        for(int i=0 ; i<number ; i++)
        {
            Job temp = new Job(i+1); 
            mainList.add(i, temp);
        }
    }
    
    public Job getJob(int num)
    {
        return mainList.get(num);
    }
    
    public void removeJob(int num)
    {
        mainList.remove(num);
    }
    
    
    public void addJob(Job job)
    {
        mainList.add(job);
    }
    
    public void set(int i , Job job)
    {
        mainList.set(i, job);
    }
    
    public boolean isEmpty()
    {
        return (mainList.isEmpty());
    }
    
    public int size()
    {
        return mainList.size();
    }
    
    public void clearQueue()
    {
        for(int i =0 ; i< mainList.size() ; i++)
        {
           mainList.remove(i);
        }
    }
    
    public void OrderedByArrive()
    {
        for(int i=0 ; i<mainList.size()-1 ; i++)
        {
            for(int j=i+1 ; j<mainList.size() ;j++)
            {
                Job j1 = mainList.get(i);
                Job j2 = mainList.get(j);
                if(j2.isFirst(j1))
                {
                    mainList.set(i, j2);
                    mainList.set(j, j1);
                }
            }
        }
    }
    
    
    public void OrderedByShortest()
    {
        for(int i=0 ; i<mainList.size()-1 ; i++)
        {
            for(int j=i+1 ; j<mainList.size() ;j++)
            {
                Job j1 = mainList.get(i);
                Job j2 = mainList.get(j);
                if(j2.isShort(j1))
                {
                    mainList.set(i, j2);
                    mainList.set(j, j1);
                }
            }
        }
    }
    
    
    public void OrderedByPriority()
    {
        for(int i=0 ; i<mainList.size()-1 ; i++)
        {
            for(int j=i+1 ; j<mainList.size() ;j++)
            {
                Job j1 = mainList.get(i);
                Job j2 = mainList.get(j);
                if(j2.isPrior(j1))
                {
                    mainList.set(i, j2);
                    mainList.set(j, j1);
                }
            }
        }
    }
    
    
    public void OrderedByShortRemain()
    {
        for(int i=0 ; i<mainList.size()-1 ; i++)
        {
            for(int j=i+1 ; j<mainList.size() ;j++)
            {
                Job j1 = mainList.get(i);
                Job j2 = mainList.get(j);
                if(j2.isShortRemain(j1))
                {
                    mainList.set(i, j2);
                    mainList.set(j, j1);
                }
            }
        }
    }
    
    
    public Queue getCopy ()
    {
        return new Queue(mainList);
    }

    public Queue getClearCopy()
    {
        ArrayList<Job> list = new ArrayList<Job>(mainList.size());
        for(int i=0 ; i<mainList.size() ; i++)
        {
            Job temp = mainList.get(i).getClearCopyJob(); 
            list.add(temp);
        }
        return new Queue(list);
    }
         public void showQueue(int simulationTime)
    {
        if(mainList.isEmpty()){System.out.println("Empty Queue");  return;} 
        System.out.println("number of jobs " + mainList.size() );
        System.out.println("# "+" Arrive "+" Burst "+" Priority "+" Start "+" Wait "+" Remain "+" Finish "+" Turn "+" % ");
        for(int i=0 ; i<mainList.size() ; i++) 
        {
            Job temp = mainList.get(i);
            System.out.print(temp.jobNumber + "    " + temp.arrivalTime + "      " + temp.burst +"       ");
            System.out.print(temp.priority + "      " + temp.getStart() + "      " + temp.getWaitTime(simulationTime)+ "      ");
            System.out.print(temp.getRemainTime() + "       " + temp.getFinish() + "      " + temp.getTurnaround(simulationTime)+ "    ");
            System.out.println(temp.getPercent());
        }
    }
}
