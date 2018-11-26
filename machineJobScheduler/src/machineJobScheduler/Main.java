package machineJobScheduler;

import java.io.File;
import java.util.Scanner;
import java.util.Stack;

public class Main {
	
	public static void main(String[] args) {
        try{
            System.out.print("Enter file name:");
            Scanner input = new Scanner(System.in);
            File file = new File(input.nextLine());
            input = new Scanner(file);
            
            Stack<Job> jb = new Stack<Job>();	// Stack to store jobs
            int i = 1;
            while (input.hasNextLine()) {	// Read input into a Stack
                String line = input.nextLine();
                String[] l = line.split(" ");
                int jobNumber = i;
                int start = Integer.parseInt(l[0]);
                int end = Integer.parseInt(l[1]);

                Job j = new Job(jobNumber, start, end);
                jb.push(j);
                i++;
            }
            input.close();
            sortstack(jb);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
	
	// Sort Stack by job's end time
	private static Stack<Job> sortstack(Stack<Job> input) {
		Stack<Job> tmpStack = new Stack<Job>(); 
        while(!input.isEmpty()) 
        { 
            // pop out the first element 
            Job tmp = input.pop(); 
          
            // while temporary stack is not empty and 
            // top of stack is greater than temp 
            while(!tmpStack.isEmpty() && tmpStack.peek().end  
                                                 > tmp.end) 
            { 
                // pop from temporary stack and  
                // push it to the input stack 
            input.push(tmpStack.pop()); 
            } 
              
            // push temp in tempory of stack 
            tmpStack.push(tmp); 
        } 
        return tmpStack;
	}

}
class Job {
	
	int jobNumber;
    int start;
    int end;

    public Job(int jobNumber, int start, int end) {
    		this.jobNumber = jobNumber;
        this.start = start;
        this.end = end;
    }

    public int getJobNumber() {
    		return jobNumber;
    }
    
    public void setJobNumber(int jobNumber) {
    		this.jobNumber = jobNumber;
    }
    
    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public String toString() {
        return this.jobNumber + " " + this.start + " " + this.end;
    }
}

