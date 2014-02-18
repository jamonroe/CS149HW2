
/**
 * This class will be used to simulate processes
 * 
 * @author Jason
 */
public class Process {

	//0 to 99
	private float arrival_time;
	
	//0.1 to 10
	private float run_time;
	
	//1, 2, 3, or 4 (1 is highest)
	private int priority;
	
	public Process() {
		arrival_time = (float) (Math.random() * 99);
		//TODO finish this
	}
	
	public float getArrivalTime() { return arrival_time; }
	public float getRunTime() { return run_time; }
	public int getPriority() { return priority; }
}
