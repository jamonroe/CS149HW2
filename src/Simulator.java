import java.util.ArrayList;
import java.util.Collections;

/**
 * Simulates algorithms on given process stacks
 * 
 * @author Jason
 */
public class Simulator {

	/**
	 * First Come First Served algorithm
	 * 
	 * @param future The list of processes which will "arrive"
	 * @param duration The length of the run in quanta
	 * @return The results of the run as a String
	 */
	public static String firstComeFirstServed(FutureStack future, int duration) {
		String result = "=== First Come First Served ===\n";
		int time;
		ArrayList<Process> active = new ArrayList<Process>();
		ArrayList<Process> completed = new ArrayList<Process>();
		for (time = 0; time <= duration; time++) {
			char proc;
			while (future.hasActiveProcess(time))
				active.add(future.pop());
			
			// **** FCFS starts here ****
			Collections.sort(active, Process.arrivalTimeComparator());
			if(active.size() > 0) {
				proc = active.get(0).getName();
				if (active.get(0).process(time))
					completed.add(active.remove(0).setCompletion(time + 1));
			} else {
				proc = '-';
			}
			// **** FCFS ends here ****
			
			result += proc;
		}
		result += "\n\n" + analyzeCompletion(completed) + "\n";
		return result;
	}
	
	/**
	 * Round Robin algorithm
	 * 
	 * @param future The list of processes which will "arrive"
	 * @param duration The length of the run in quanta
	 * @return The results of the run as a String
	 */
	public static String roundRobin(FutureStack future, int duration) {
		String result = "=== Round Robin ===\n";
		int time;
		ArrayList<Process> active = new ArrayList<Process>();
		ArrayList<Process> completed = new ArrayList<Process>();
		for (time = 0; time <= duration; time++) {
			char proc;
			while (future.hasActiveProcess(time))
				active.add(future.pop());
			
			// **** RR starts here **** 
			if(active.size() > 0) {
				proc = active.get(0).getName();
				if (active.get(0).process(time))
					completed.add(active.remove(0).setCompletion(time + 1));
				else
					//removes the process that just ran, and puts it in the back
					active.add(active.remove(0));
			} else {
				proc = '-';
			}
			// **** RR ends here ****
			
			result += proc;
		}
		result += "\n\n" + analyzeCompletion(completed) + "\n";
		return result;
	}
	
	/**
	 * Shortest Remaining Time algorithm
	 * 
	 * @param future The list of processes which will "arrive"
	 * @param duration The length of the run in quanta
	 * @return The results of the run as a String
	 */
	public static String shortestRemainingTime(FutureStack future, int duration) {
		String result = "=== Shortest Remaining Time ===\n";
		int time;
		ArrayList<Process> active = new ArrayList<Process>();
		ArrayList<Process> completed = new ArrayList<Process>();
		for (time = 0; time <= duration; time++) {
			char proc;
			while (future.hasActiveProcess(time))
				active.add(future.pop());
			
			// **** SRT starts here ****
			Collections.sort(active, Process.timeRemainingComparator());
			if(active.size() > 0) {
				proc = active.get(0).getName();
				if (active.get(0).process(time))
					completed.add(active.remove(0).setCompletion(time + 1));
			} else {
				proc = '-';
			}
			// **** SRT ends here ****
			
			result += proc;
		}
		result += "\n\n" + analyzeCompletion(completed) + "\n";
		return result;
	}
	
	/**
	 * Shortest Job First algorithm
	 * 
	 * sort at time = 0
	 * then sort after tasks have completed
	 * 
	 * @param future The list of processes which will "arrive"
	 * @param duration The length of the run in quanta
	 * @return The results of the run as a String
	 */
	public static String shortestJobFirst(FutureStack future, int duration) {
		String result = "=== Shortest Job First ===\n";
		int time;
		ArrayList<Process> active = new ArrayList<Process>();
		ArrayList<Process> completed = new ArrayList<Process>();
		for (time = 0; time <= duration; time++) {
			char proc;
			while (future.hasActiveProcess(time))
				active.add(future.pop());
			
			// **** SJF starts here ****
			if (time == 0)
				Collections.sort(active, Process.completionTimeComparator());
			if(active.size() > 0) {
				proc = active.get(0).getName();
				if (active.get(0).process(time))
				{
					completed.add(active.remove(0).setCompletion(time + 1));
					Collections.sort(active, Process.completionTimeComparator());
				}
			} else {
				proc = '-';
			}
			// **** SJF ends here ****
			
			result += proc;
		}
		result += "\n\n" + analyzeCompletion(completed) + "\n";
		return result;
	}
	
	/**
	 * Used to analyze algorithms
	 * 
	 * @param list The list of completed operations
	 * @return The results as a string
	 */
	private static String analyzeCompletion(ArrayList<Process> list) {
		String result;
		float turnaround = 0;
		float waittime = 0;
		float responsetime = 0;
		int count = list.size();
		for (Process p : list) {
			turnaround += p.turnaround();
			waittime += p.waittime();
			responsetime += p.responsetime();
		}
		turnaround /= count;
		waittime /= count;
		responsetime /= count;
		result = "Average turnaround: " + turnaround + "\nAverage wait time: "
		+ waittime + "\nAverage response time: " + responsetime;
		return result;
	}
}
