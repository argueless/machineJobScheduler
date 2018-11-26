package machineJobScheduler;

import java.util.Stack;

public class Scheduler {

	// jobs is a sorted stack
	// m is number of machines
	public static void scheduleJobs(Stack<Job> jobs, int m) {
	
// TODO output a text file
		int i, nj=0;	// nj is number of jobs
		
		Job[][] machines = new Job[m][]; // Array for machines that holds Jobs
		int[][] schedule = new int[m][];	// Array showing schedule by Job Number
		
// TODO create array of jobNumbers
		// first row is number of jobs
		//machines[1][0] = jobs.pop(); // first job is always chosen
		schedule[1][0] = machines[1][0].jobNumber;
		
		// Schedule jobs to machines
		while (!jobs.isEmpty()) {
			for (i=1; i<=m; i++) {
				if (machines[i].length == 0) {
					machines[i][machines[i].length] = jobs.pop();
					nj++;
				}
				// Compare job's start time to job's end time
				if (jobs.peek().getStart() >= machines[i][machines[i].length - 1].getEnd()) {
					machines[i][machines[i].length] = jobs.pop();
					nj++;
				}
				else
					jobs.pop();
			}		
		}
		
		// First row of array is number of jobs
		schedule[0][0] = nj;
		// Create array for for the schedule by job number
		for (int t=1; t<=machines.length; t++) {
			
		}
		
		
	}
}
