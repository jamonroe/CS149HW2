import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

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
	public static Result firstComeFirstServed(FutureStack future, int duration) {
		String result = "";
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
		for (Process p : active)
			if (p.getFirstProcessTime() != -1)
				completed.add(p);
		return analyzeCompletion(result, completed, duration);
	}
	
	/**
	 * Round Robin algorithm
	 * 
	 * @param future The list of processes which will "arrive"
	 * @param duration The length of the run in quanta
	 * @return The results of the run as a String
	 */
	public static Result roundRobin(FutureStack future, int duration) {
		String result = "";
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
		for (Process p : active)
			if (p.getFirstProcessTime() != -1)
				completed.add(p);
		return analyzeCompletion(result, completed, duration);
	}
	
	/**
	 * Shortest Remaining Time algorithm
	 * 
	 * @param future The list of processes which will "arrive"
	 * @param duration The length of the run in quanta
	 * @return The results of the run as a String
	 */
	public static Result shortestRemainingTime(FutureStack future, int duration) {
		String result = "";
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
		for (Process p : active)
			if (p.getFirstProcessTime() != -1)
				completed.add(p);
		return analyzeCompletion(result, completed, duration);
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
	public static Result shortestJobFirst(FutureStack future, int duration) {
		String result = "";
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

		for (Process p : active)
			if (p.getFirstProcessTime() != -1)
				completed.add(p);
		return analyzeCompletion(result, completed, duration);
	}

	/**
	 * Nonpreemptive Highest Priority First Algorithm
	 * 
	 * @param future The list of processes which will "arrive"
	 * @param duration The length of the run in quanta
	 * @return The results of the run as a String
	 */
	public static Result nonpreemptiveHighestPriorityFirst(FutureStack future, int duration)
	{
		//Implemented with FCFS
		String result = "";
		
		//Setup
		int time;
		ArrayList<Process> active = null;
		ArrayList<Process> completed = new ArrayList<Process>();
		
		//Set up priority queues
		ArrayList<ArrayList<Process>> priorityQueues = new ArrayList<ArrayList<Process>>();
		priorityQueues.add(new ArrayList<Process>());
		priorityQueues.add(new ArrayList<Process>());
		priorityQueues.add(new ArrayList<Process>());
		priorityQueues.add(new ArrayList<Process>());
		
		for(time = 0; time <= duration; time++)
		{
			Process process;
			char proc;
			//Process is arriving, add it into the proper queue
			while(future.hasActiveProcess(time))
			{
				process = future.pop();
				priorityQueues.get(process.getPriority() - 1).add(process);
			}
			
			//Active will be set to the highest priority queue with a Process
			if (active == null) {
				for (int i = 0; i < priorityQueues.size(); i++) {
					if (priorityQueues.get(i).size() > 0) {
						active = priorityQueues.get(i);
						break;
					}
				}
			}
			
			//Start Non-preemptive HPF starts here
			if(active != null) 
			{
				proc = active.get(0).getName();
				if (active.get(0).process(time)) {
					completed.add(active.remove(0).setCompletion(time + 1));
					//active is set to null, indicating that a new process must be found
					active = null;
				}
			} 
			else 
			{
				proc = '-';
			}
			//End Non-preemptive HPF
			result += proc;
		}
		for (ArrayList<Process> list : priorityQueues)
			for (Process p : list)
				if (p.getFirstProcessTime() != -1)
					completed.add(p);
		return analyzeCompletion(result, completed, duration);
	}
	
	/**
	 * Preemptive Highest Priority First Algorithm
	 * 
	 * @param future The list of processes which will "arrive"
	 * @param duration The length of the run in quanta
	 * @return The results of the run as a String
	 */
	public static Result preemptiveHighestPriorityFirst(FutureStack future, int duration)
	{
		//Implemented with RR
		
		String result = "";
		int time;
		ArrayList<Process> active;
		ArrayList<Process> completed = new ArrayList<Process>();
		
		//Set up priority queues
		ArrayList<ArrayList<Process>> priorityQueues = new ArrayList<ArrayList<Process>>();
		priorityQueues.add(new ArrayList<Process>());
		priorityQueues.add(new ArrayList<Process>());
		priorityQueues.add(new ArrayList<Process>());
		priorityQueues.add(new ArrayList<Process>());
		
		for(time = 0; time <= duration; time++)
		{
			Process process;
			char proc;
			//Process is arriving, add it into the proper queue
			while(future.hasActiveProcess(time))
			{
				process = future.pop();
				priorityQueues.get(process.getPriority() - 1).add(process);
			}

			//Active will be set to the highest priority queue with a Process
			active = null;
			for (int i = 0; i < priorityQueues.size(); i++) {
				if (!priorityQueues.get(i).isEmpty()) {
					active = priorityQueues.get(i);
					break;
				}
			}
			
			//Start preemptive HPF starts here
			if(active != null) 
			{
				proc = active.get(0).getName();
				if (active.get(0).process(time))
					completed.add(active.remove(0).setCompletion(time + 1));
				else
					//removes the process that just ran, and puts it in the back
					active.add(active.remove(0));
			} 
			else 
			{
				proc = '-';
			}
			result += proc;
			//End Preemptive HPF
		}
		for (ArrayList<Process> list : priorityQueues)
			for (Process p : list)
				if (p.getFirstProcessTime() != -1)
					completed.add(p);
		return analyzeCompletion(result, completed, duration);
	}
	
	/**
	 * Used to analyze algorithms
	 * 
	 * @param list The list of completed operations
	 * @return The results as a string
	 */
	private static Result analyzeCompletion(String run, ArrayList<Process> list, int duration) {
		float turnaround = 0;
		float waittime = 0;
		float responsetime = 0;
		float throughput = 0;
		int count = list.size();
		for (Process p : list) {
			turnaround = p.getCompletionTime() != -1 ? p.turnaround() + turnaround : turnaround;
			waittime = p.getCompletionTime() != -1 ? p.waittime() + waittime : waittime;
			responsetime += p.responsetime();
		}
		throughput = (float) list.size() / (duration + 1);
		turnaround /= count;
		waittime /= count;
		responsetime /= count;
		return new Result(run, turnaround, waittime, responsetime, throughput);
	}
}
