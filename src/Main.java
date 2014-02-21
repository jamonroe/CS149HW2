/**
 * Useful for testing for now
 * 
 * @author Jason
 */
public class Main {

	public static void main(String[] args) {
		FutureStack future = new FutureStack();
		
		future.add(new Process('A', 0, 2, 1));
		future.add(new Process('B', 0, 2, 1));
		future.add(new Process('C', 0, 2, 1));
		System.out.println(Simulator.roundRobin(future, 5));

		future.add(new Process('A', 0, 2, 1));
		future.add(new Process('B', 0, 2, 1));
		future.add(new Process('C', 0, 2, 1));
		System.out.println(Simulator.firstComeFirstServed(future, 5));
	}
}
