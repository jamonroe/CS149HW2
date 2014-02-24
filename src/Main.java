/**
 * Useful for testing for now
 * 
 * @author Jason
 */
public class Main {

	public static void main(String[] args) {
		FutureStack future = new FutureStack();
		
		future.add(new Process('A', 2, 2, 1));
		future.add(new Process('B', 0, 2, 1));
		future.add(new Process('C', 0, 2, 1));
		System.out.println(Simulator.roundRobin(future, 5));

		future.add(new Process('A', 0, 2, 1));
		future.add(new Process('B', 0, 2, 1));
		future.add(new Process('C', 0, 2, 1));
		System.out.println(Simulator.firstComeFirstServed(future, 5));
		
		future.add(new Process('A', 0, 3, 1));
		future.add(new Process('B', 0, 2, 1));
		future.add(new Process('C', 2, 1, 1));
		System.out.println(Simulator.shortestRemainingTime(future, 5));
		
		future.add(new Process('A', 0, 3, 1));
		future.add(new Process('B', 0, 2, 1));
		future.add(new Process('C', 1, 1, 1));
		System.out.println(Simulator.shortestJobFirst(future, 5));
		
		future.add(new Process('A', 0, 2, 1));
		future.add(new Process('B', 0, 2, 1));
		future.add(new Process('C', 2, 2, 2));
		System.out.println(Simulator.preemtiveHighestPriorityFirst(future, 5));
		
		future.add(new Process('A', 2, 2, 1));
		future.add(new Process('B', 2, 2, 2));
		future.add(new Process('C', 0, 2, 2));
		System.out.println(Simulator.nonpreemtiveHighestPriorityFirst(future, 5));
	}
}
