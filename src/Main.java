import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
		try {
			System.out.println("\n===== Round Robin =====\n");
			System.out.println("Round Robin Results:\n" + analyzeAlgorithm(Simulator.class.getMethod("roundRobin", FutureStack.class, int.class), MAX_QUANTA, 5));
			System.out.println("\n===== First Come First Served =====\n");
			System.out.println("First Come First Serverd Results:\n" + analyzeAlgorithm(Simulator.class.getMethod("firstComeFirstServed", FutureStack.class, int.class), MAX_QUANTA, 5));
			System.out.println("\n===== Shortest Remaining Time =====\n");
			System.out.println("Shortest Job First Results:\n" + analyzeAlgorithm(Simulator.class.getMethod("shortestRemainingTime", FutureStack.class, int.class), MAX_QUANTA, 5));
			System.out.println("\n===== Shortest Job First =====\n");
			System.out.println("Shortest Job First Results:\n" + analyzeAlgorithm(Simulator.class.getMethod("shortestJobFirst", FutureStack.class, int.class), MAX_QUANTA, 5));
			System.out.println("\n===== Nonpreemptive Highest Priority First =====\n");
			System.out.println("Nonpreemptive Highest Priority First Results:\n" + analyzeAlgorithm(Simulator.class.getMethod("nonpreemptiveHighestPriorityFirst", FutureStack.class, int.class), MAX_QUANTA, 5));
			System.out.println("\n===== Preemptive Highest Priority First =====\n");
			System.out.println("Preemptive Highest Priority First Results:\n" + analyzeAlgorithm(Simulator.class.getMethod("preemptiveHighestPriorityFirst", FutureStack.class, int.class), MAX_QUANTA, 5));
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}
	
	public static Result analyzeAlgorithm(Method algorithm, int duration, int runs) {
		Result algorithmResult = new Result();
		FutureStack stack;
		for (int i = 0; i < runs; i++) {
			try {
				System.out.println("* " + algorithm.getName() + " run #" + (i + 1) + "*");
				Collections.sort(stack = generateStack(PROCESS_COUNT), Process.arrivalTimeComparator());
				System.out.println(stack);
				
				Result run = (Result) (algorithm.invoke(null, new Object[]{stack, duration}));

				System.out.println(run.getRun() + "\n");
				System.out.println(run + "\n");
				
				algorithmResult.setResponsetime(algorithmResult.getResponsetime() + run.getResponsetime());
				algorithmResult.setThroughput(algorithmResult.getThroughput() + run.getThroughput());
				algorithmResult.setTurnaround(algorithmResult.getTurnaround() + run.getTurnaround());
				algorithmResult.setWaittime(algorithmResult.getWaittime() + run.getWaittime());
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		algorithmResult.setResponsetime(algorithmResult.getResponsetime()/runs);
		algorithmResult.setThroughput(algorithmResult.getThroughput()/runs);
		algorithmResult.setTurnaround(algorithmResult.getTurnaround()/runs);
		algorithmResult.setWaittime(algorithmResult.getWaittime()/runs);
		return algorithmResult;
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
