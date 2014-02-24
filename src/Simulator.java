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
		result += "\n\n" + analyzeCompletion(completed, duration) + "\n";
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
		result += "\n\n" + analyzeCompletion(completed, duration) + "\n";
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
		result += "\n\n" + analyzeCompletion(completed, duration) + "\n";
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
		result += "\n\n" + analyzeCompletion(completed, duration) + "\n";
		return result;
	}

	/**
	 * Nonpreemptive Highest Priority First Algorithm
	 * 
	 * @param future The list of processes which will "arrive"
	 * @param duration The length of the run in quanta
	 * @return The results of the run as a String
	 */
	public static String nonpreemptiveHighestPriorityFirst(FutureStack future, int duration)
	{
		//Implemented with FCFS
		String result = "=== Highest Priority First (Nonpreemptive) ===\n";
		
		//Setup
		int time;
		ArrayList<Process> active = new ArrayList<Process>();
		ArrayList<Process> completed = new ArrayList<Process>();
		
		//Set up priority queues
		ArrayList<LinkedList<Process>> priorityQueues = new ArrayList<LinkedList<Process>>();
		priorityQueues.add(new LinkedList<Process>());
		priorityQueues.add(new LinkedList<Process>());
		priorityQueues.add(new LinkedList<Process>());
		priorityQueues.add(new LinkedList<Process>());
		
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
			
			//Empty process queues into active queue
			for(int priority=0;priority<4;priority++)
				while(!priorityQueues.get(priority).isEmpty())
					active.add(priorityQueues.get(priority).remove());
			
			//Start Non-preemptive HPF starts here
			if(active.size() > 0) 
			{
				proc = active.get(0).getName();
				if (active.get(0).process(time))
					completed.add(active.remove(0).setCompletion(time + 1));
			} 
			else 
			{
				proc = '-';
			}
			//End Non-preemptive HPF
			result += proc;
		}
		result += "\n\n" + analyzeCompletion(completed, duration) + "\n";
		return result;
	}
	
	/**
	 * Preemptive Highest Priority First Algorithm
	 * 
	 * @param future The list of processes which will "arrive"
	 * @param duration The length of the run in quanta
	 * @return The results of the run as a String
	 */
	public static String preemptiveHighestPriorityFirst(FutureStack future, int duration)
	{
		//Implemented with RR
		String result = "=== Highest Priority First (Preemptive) ===\n";
		int time;
		ArrayList<Process> completed = new ArrayList<Process>();
		
		//Set up priority queues
		ArrayList<LinkedList<Process>> priorityQueues = new ArrayList<LinkedList<Process>>();
		priorityQueues.add(new LinkedList<Process>());
		priorityQueues.add(new LinkedList<Process>());
		priorityQueues.add(new LinkedList<Process>());
		priorityQueues.add(new LinkedList<Process>());
		
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
			
			Queue<Process> queue = null;
			
			//Select priority queue to process
			for (int i = 0; i < priorityQueues.size(); i++) {
				if (!priorityQueues.get(i).isEmpty()) {
					queue = priorityQueues.get(i);
					break;
				}
			}
			
			if (queue == null) {
				result += '-';
				continue;
			}
			
			//Preemptive HPF starts here
			
			//Get process from the queue
			process = queue.remove();
			proc = process.getName();
			
			//Process the process (yea it's a bit confusing, sorry :( )
			if(process.process(time))
			{
				completed.add(process.setCompletion(time + 1));
			}
			else//Time quanta is up, add to back of queue"
				queue.add(process);
			
			result += proc;
			//End Preemptive HPF
		}
		
		result += "\n\n" + analyzeCompletion(completed, duration) + "\n";
		
		return result;
	}
	
	/**
	 * Used to analyze algorithms
	 * 
	 * @param list The list of completed operations
	 * @return The results as a string
	 */
	private static String analyzeCompletion(ArrayList<Process> list, int duration) {
		String result;
		float turnaround = 0;
		float waittime = 0;
		float responsetime = 0;
		float throughput = 0;
		int count = list.size();
		for (Process p : list) {
			turnaround += p.turnaround();
			waittime += p.waittime();
			responsetime += p.responsetime();
		}
		throughput = (float) list.size() / (duration + 1);
		turnaround /= count;
		waittime /= count;
		responsetime /= count;
		result = "Average turnaround: " + turnaround + "\nAverage wait time: "
		+ waittime + "\nAverage response time: " + responsetime
		+ "\nThroughput: " + throughput;
		return result;
	}
}
