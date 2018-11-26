package machineJobScheduler;

import java.io.File;
import java.util.Scanner;
import java.util.Stack;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class Main {
	
	static int machine=0;	// initialize variable for number of machines
	
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
            // Create array for schedule by JobNumber
            int[][] schedule = scheduleJobs(sortstack(jb), machine);	
            
            // Output Machine Job Schedule to a txt file
    			PrintStream out = new PrintStream(new FileOutputStream("Output.txt"));
    			for (int r=0; r<schedule.length; r++) {
    				for (int c=0; c<schedule[r].length; c++) {
    					out.print(schedule[r][c] + "\t");
    				}
    				out.println("\n");
    			}
    			out.close();
    			
        }catch (Exception ex) {
            ex.printStackTrace();
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

	// Method to create array for Job schedule by Job Number (final output)
	// jobs is a sorted stack
	// m is number of machines
	public static int[][] scheduleJobs(Stack<Job> jobs, int m) {
		
		int i, nj=0;	// nj is number of jobs
		
		Job[][] machines = new Job[m][]; // Array for machines that holds Jobs. Needed to compare jobs
		int[][] schedule = new int[m][];	// Array showing schedule by Job Number. (final output)
		
		// first row is number of jobs
		//machines[1][0] = jobs.pop(); // first job is always chosen
		schedule[1][0] = machines[1][0].jobNumber;
		
		// Schedule jobs to machines
		while (!jobs.isEmpty()) {
			for (i=1; i<=m; i++) {
				// Assign job to machine if machine has no jobs
				if (machines[i].length == 0) {
					machines[i][machines[i].length] = jobs.pop();
					nj++;
				}
				// Compare job's start time to job's end time
				if (jobs.peek().getStart() >= machines[i][machines[i].length - 1].getEnd()) {
					machines[i][machines[i].length] = jobs.pop();
					nj++;
				}
				// delete job if it will not fit
				else
					jobs.pop();
			}		
		}
		
		// First row of array is number of jobs
		schedule[0][0] = nj;
		// Create array for for the schedule by job number
		for (int t=1; t<=machines.length; t++) {
			for (int s=0; s < machines[t].length; s++) {
				schedule[t][s] = machines[t][s].getJobNumber();
			}
		}
		
		return schedule;
		
	}
	
}


