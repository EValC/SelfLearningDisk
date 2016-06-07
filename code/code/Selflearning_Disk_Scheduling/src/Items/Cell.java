package Items;

import java.awt.Color;
import javax.swing.JLabel;

public class Cell extends JLabel{
    
    private Cell(int x , int y , int width , int height , int jobNumber)
    {
        setSize(width , height);
        chooseColor(jobNumber);
        setLocation(x, y);
        setOpaque(true);
    }
    
    public static Cell createGanttCell(int x , int y , int jobNumber) 
    {
        return new Cell(x, y, 10, 80, jobNumber);
    }
        public static Cell createEmptyJobCell(int x , int y)
    {
        return new Cell(x,y,10,10,9);
    }

    public static Cell createReadyQueueCell(int x , int y , int jobNumber)
    {
        return new Cell(x,y, 36 , 80 ,jobNumber);
    }

    public static Cell createMark(int x , int y)
    {
        return new Cell(x,y,1,10,10);
    }
    
    private void chooseColor(int type)
    {
        setForeground(Color.WHITE);   
        setHorizontalAlignment(CENTER);  
        switch(type)
        {
            case 1: setBackground(new Color(0x9C0000)); setText("1"); break;
            case 2: setBackground(Color.DARK_GRAY);     setText("2"); break;
            case 3: setBackground(new Color(0x171515)); setText("3"); break;
            case 4: setBackground(new Color(0x0000E6)); setText("4"); break;
            case 5: setBackground(new Color(0x40A800)); setText("5"); break;
            case 6: setBackground(new Color(0x720E9E)); setText("6"); break;
            case 7: setBackground(new Color(0xef8236)); setText("7"); break;
            case 8: setBackground(new Color(0x634d40)); setText("8"); break;
            case 9: setBackground(Color.WHITE);  break; 
            case 10: setBackground(Color.BLACK); break;
            default:  
        }
    }       
}
