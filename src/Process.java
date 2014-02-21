import java.util.Comparator;

/**
 * This class will be used to simulate processes
 * 
 * @author Jason
 */
public class Process {

	private char name; //A-Z
	private float arrival_time; //0 to 99
	private float run_time; //0.1 to 10
	private int priority; //1, 2, 3, or 4 (1 is highest)
	private int first_process_time;
	private int last_process_time;
	private float time_remaining;
	private float completion_time;
	
	public Process(char name, float arrival_time, float run_time, int priority) {
		this.name = name;
		this.arrival_time = arrival_time;
		this.run_time = run_time;
		this.time_remaining = run_time;
		this.name = name;
		this.completion_time = -1;
		this.first_process_time = -1;
	}
	
	/**
	 * @return true if the process is complete
	 */
	public boolean process(int time) { 
		if (first_process_time == -1 )
			first_process_time = time;
		last_process_time = time;
		return (time_remaining -= 1) <= 0; 
	}

	public char getName() { return name; }
	public float getArrivalTime() { return arrival_time; }
	public float getRunTime() { return run_time; }
	public float getTimeRemaining() { return time_remaining; }
	public int getLastProcessTime() { return last_process_time; } //TODO this would be for extra credit
	public int getPriority() { return priority; }
	
	/**
	 * since time_remaining becomes negative for non whole number
	 * run times, it will make the completion time more accurate by
	 * calculating the exact completion time.
	 * 
	 * i.e. Process A starts at time 0 and takes 0.25 quanta
	 *      time_remaining becomes -.75 after that process call
	 *      setCompletion() gets called with 1 as the time value
	 *      completion_time = 1 + (-.75) = .25 quanta
	 * 
	 * @param time
	 * @return
	 */
	public Process setCompletion(int time) {
		completion_time = time + time_remaining; 
		return this;
	}
	
	public float turnaround() { return completion_time - arrival_time; }
	public float waittime() { return completion_time - arrival_time - run_time; }
	public float responsetime() { return first_process_time - arrival_time; }
	
	public static Comparator<Process> arrivalTimeComparator() {
		return new Comparator<Process>() {
			@Override
			public int compare(Process procA, Process procB) {
				if (procB.getArrivalTime() - procA.getArrivalTime() < 0) return 1;
				if (procB.getArrivalTime() - procA.getArrivalTime() > 0) return -1;
				return 0;
			}
		};
	}
	public static Comparator<Process> timeRemainingComparator() {
		return new Comparator<Process>() {
			@Override
			public int compare(Process procA, Process procB) {
				if (procB.getTimeRemaining() - procA.getTimeRemaining() < 0) return 1;
				if (procB.getTimeRemaining() - procA.getTimeRemaining() > 0) return -1;
				return 0;
			}
		};
	}
}
