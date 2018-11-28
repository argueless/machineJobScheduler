package machineJobScheduler;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class Main {
	
	
	static int machine=0;	// variable for number of machines
	static int nj=0;		// variable for number of jobs
	
	public static void main(String[] args) {
		
		// Stack to store jobs
        Stack<Job> jb = new Stack<Job>();
        // list of the job schedule (final output)
        ArrayList <ArrayList<Job>> schedule = new ArrayList<>();
        
        try{
        		// Ask user to input file
            System.out.print("Enter file name:");
            Scanner input = new Scanner(System.in);
            File file = new File(input.nextLine());
            input = new Scanner(file);
            
            // get number of machines from first line of input file
            String first = input.nextLine();
            String[] f = first.split(" ");
            machine = Integer.parseInt(f[1]);
            
            // Read input of jobs into a Stack
            int i = 1;
            while (input.hasNextLine()) {
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
            
            // Sort input by Job's finish time
            Stack<Job> ordJobs = new Stack<Job>();
            ordJobs = sortstack(jb);
            
            // initialize schedule list with number of machines
            for (int r=0; r<machine; r++)
            		schedule.add(new ArrayList<Job>());
            
            // Call greedy algorithm to assign jobs
            while (!ordJobs.isEmpty()) {
    				boolean added = false;
    				assign(schedule, ordJobs.peek(), machine, added);
    				if (added == false)
    					ordJobs.pop();
    				else
    					continue;    				
    			}
            
            // Output Machine Job Schedule to a txt file
    			PrintStream out = new PrintStream(new FileOutputStream("Output.txt"));
    			out.print(nj);
    			out.println("\n");
    			for (int j=0; j<schedule.size(); j++) {
    				for (int k=0; k<schedule.get(j).size(); k++) {
    					out.print(schedule.get(j).get(k).getJobNumber() + " ");
    				}
    				out.println("\n");
    			}
    			out.close();
    			
        }catch (Exception ex) {
            ex.printStackTrace();
        }
	}

	// Method to create list for Job schedule by Job Number (final output)
	// Greedy Algorithm
	// jobs is a sorted stack
	// m is number of machines	
	public static void assign(ArrayList <ArrayList<Job>> jobs, Job j, int m, boolean added) {
		
		// Iterate through machines
		for (int i=0; i < m; i++) {
			// Assign job to machine if machine has no jobs
			if (jobs.get(i).isEmpty()) {
				jobs.get(i).add(j);
				nj++;
				added = true;
				break;
			}
			// Compare open job's start time to scheduled job's end time
			if (j.getStart() >= jobs.get(i).get(jobs.get(i).size() - 1).getEnd()) {
				jobs.get(i).add(j);
				nj++;
				added = true;
				break;
			}
			// Delete job if cannot be scheduled
			else
				added = false;
				
		}

	}	
	
	// Method to Sort Stack by job's end time
	private static Stack<Job> sortstack(Stack<Job> input) {
		Stack<Job> tmpStack = new Stack<Job>(); 
        while(!input.isEmpty()) 
        { 
            // pop out the first element 
            Job tmp = input.pop(); 
          
            // while temporary stack is not empty and 
            // top of stack is greater than temp 
            while(!tmpStack.isEmpty() && tmpStack.peek().end  
                                                 < tmp.end) 
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


