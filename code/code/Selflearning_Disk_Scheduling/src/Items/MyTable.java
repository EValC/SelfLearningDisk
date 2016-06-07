package Items;

import cpu.Simulation;
import javax.swing.table.AbstractTableModel;

public class MyTable extends AbstractTableModel{

    private Queue tableQueue ;  
    private String[] columnNames = {"#","Arrive","Burst","Priority","Start","Wait" , "Remain" ,"Finish" , "Turn" , "%"}; // table header
    
    public MyTable( Queue queue)
    {
        tableQueue = queue.getCopy();
        this.fireTableRowsUpdated(1, 1);
    } 
    
    public int getRowCount() {
        return tableQueue.size(); 
    }

    
    public int getColumnCount() {
        return 10; 
    }
    
   
    public double getAverageWaiting()
    {  
        double average = 0 ;
        for(int i =0 ; i< tableQueue.size() ; i++)
        {
            average += (Integer) getValueAt(i, 5); 
        }
        return (average/tableQueue.size());
    }
    
    
    public double getAverageTurn()
    {  
        double aveg = 0 ;
        for(int i =0 ; i< tableQueue.size() ; i++)
        {
            aveg += (Integer) getValueAt(i, 8); 
        }
        return (aveg/tableQueue.size());
    } 

    public Object getValueAt(int rowIndex, int columnIndex) {
        Job job = tableQueue.getJob(rowIndex);
        switch(columnIndex)
        {
            case 0 : return job.jobNumber;
            case 1 : return job.arrivalTime;
            case 2 : return job.burst;
            case 3 : return job.priority;
            case 4 : return job.getStart();
            case 5 : return job.getWaitTime(Simulation.Time);    
            case 6 : return job.getRemainTime();
            case 7 : return job.getFinish();
            case 8 : return job.getTurnaround(Simulation.Time);
            case 9 : return job.getPercent();
            default: return 0;
        }
    }
    
    
    public String getColumnName(int column)
    {
        return columnNames[column];
    }
    
    
    public void setValueAT(Job other)
    {
        int n = other.jobNumber;
        for(int i=0 ; i<tableQueue.size() ; i++)
        {
            if(tableQueue.getJob(i).jobNumber == n)
            {
                tableQueue.set(i, other);
                return;
            }
        }
    }
}
