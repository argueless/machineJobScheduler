package machineJobScheduler;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class Main {
	
	// TODO some jobs aren't selected
	
	
	
	
	static int machine=0;	// initialize variable for number of machines
	static int nj=0;		// initialize variable for number of jobs
	
	public static void main(String[] args) {
        try{
        		// Read input file
            System.out.print("Enter file name:");
            Scanner input = new Scanner(System.in);
            File file = new File(input.nextLine());
            input = new Scanner(file);
            
            // get number of machines from first line of input file
            String first = input.nextLine();
            String[] f = first.split(" ");
            machine = Integer.parseInt(f[1]);
            
            // Stack to store jobs
            Stack<Job> jb = new Stack<Job>();
            int i = 1;
            // Read input into a Stack
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
            Stack<Job> ordFTime = new Stack<Job>();
            ordFTime = sortstack(jb);
            // Create arraylist holding the job schedule (final output)
            ArrayList <ArrayList<Job>> ans = schedule(sortstack(ordFTime), machine);
            
            // Output Machine Job Schedule to a txt file
    			PrintStream out = new PrintStream(new FileOutputStream("Output.txt"));
    			out.print(nj);
    			out.println("\n");
    			for (int j=0; j<ans.size(); j++) {
    				for (int k=0; k<ans.get(j).size(); k++) {
    					out.print(ans.get(j).get(k).getJobNumber() + " ");
    				}
    				out.println("\n");
    			}
    			//out.close();
    			
        }catch (Exception ex) {
            ex.printStackTrace();
        }
	}

	// Method to create array for Job schedule by Job Number (final output)
	// Greedy Algorithm
	// jobs is a sorted stack
	// m is number of machines	
	public static ArrayList <ArrayList<Job>> schedule(Stack<Job> j, int m) {
		
		ArrayList <ArrayList<Job>> jobs = new ArrayList<>();	// List to store job schedule
		// initialize arraylist
		for (int r=0; r<m; r++)
			jobs.add(new ArrayList<Job>());
		
		// Greedy Algorithm
		// Schedule jobs to machines	
		while (!j.isEmpty()) {				
			for (int i = 0; i < m; i++) {	
				// Assign job to machine if machine has no jobs
				if (jobs.get(i).isEmpty()) {
					jobs.get(i).add(j.pop());
					nj++;
				}				
				// Compare job's start time to job's end time
				if ((!j.isEmpty()) && (j.peek().getStart() >= jobs.get(i).get(jobs.get(i).size() - 1).getEnd())) {
					jobs.get(i).add(j.pop());
					nj++;
				}
				// delete job if it will not fit
				else if (!j.isEmpty())
					j.pop();
			}
		} 
		
		return jobs;		
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


