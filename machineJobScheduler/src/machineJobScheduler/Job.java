package machineJobScheduler;

public class Job {

	int jobNumber;
    int start;
    int end;

    public Job(int jobNumber, int start, int end) {
    		this.jobNumber = jobNumber;
        this.start = start;
        this.end = end;
    }

    public Job() {
    		jobNumber=0;
    		start=0;
    		end=0;
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
