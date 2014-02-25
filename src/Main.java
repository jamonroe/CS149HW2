import java.util.Collections;
import java.util.Random;

/**
 * Useful for testing for now
 * 
 * @author Jason
 */
public class Main {

	public static final int MAX_QUANTA = 100;
	public static final float MIN_RUNTIME = (float) 0.1;
	public static final float MAX_RUNTIME = (float) 10;
	public static final int MIN_PRIORITY = 1;
	public static final int MAX_PRIORITY = 4;
	public static final int PROCESS_COUNT = 26;
	
	public static void main(String[] args) {
		FutureStack stack;
		Collections.sort(stack = generateStack(PROCESS_COUNT), Process.arrivalTimeComparator());
		System.out.println("===== Round Robin =====\n");
		System.out.println(stack);
		System.out.println(Simulator.roundRobin(stack, MAX_QUANTA));
		System.out.println("===== First Come First Served =====\n");
		Collections.sort(stack = generateStack(PROCESS_COUNT), Process.arrivalTimeComparator());
		System.out.println(stack);
		System.out.println(Simulator.firstComeFirstServed(stack, MAX_QUANTA));
		System.out.println("===== Shortest Remaining Time =====\n");
		Collections.sort(stack = generateStack(PROCESS_COUNT), Process.arrivalTimeComparator());
		System.out.println(stack);
		System.out.println(Simulator.shortestRemainingTime(stack, MAX_QUANTA));
		System.out.println("===== Shortest Job First =====\n");
		Collections.sort(stack = generateStack(PROCESS_COUNT), Process.arrivalTimeComparator());
		System.out.println(stack);
		System.out.println(Simulator.shortestJobFirst(stack, MAX_QUANTA));
		System.out.println("===== Preemptive Highest Priority First =====\n");
		Collections.sort(stack = generateStack(PROCESS_COUNT), Process.arrivalTimeComparator());
		System.out.println(stack);
		System.out.println(Simulator.preemptiveHighestPriorityFirst(stack, MAX_QUANTA));
		System.out.println("===== Nonpreemptive Highest Priority First =====\n");
		Collections.sort(stack = generateStack(PROCESS_COUNT), Process.arrivalTimeComparator());
		System.out.println(stack);
		System.out.println(Simulator.nonpreemptiveHighestPriorityFirst(stack, MAX_QUANTA));
	}
	
	public static FutureStack generateStack(int count) {
		FutureStack future = new FutureStack();
		
		for (int i = 0; i < count; i++)
			future.add(generateProcess((char) ('A' + i)));
		return future;
	}
	
	public static Process generateProcess(char name) {
		Random rand = new Random();
		float arrival_time = rand.nextFloat() * MAX_QUANTA;
		float run_time = rand.nextFloat() * (MAX_RUNTIME - MIN_RUNTIME) + MIN_RUNTIME;
		int priority = rand.nextInt(MAX_PRIORITY) + 1;
		return new Process(name, arrival_time, run_time, priority);
	}
}
