import java.util.ArrayList;
import java.util.Collections;

/**
 * I decided to simulate a stack with an ArrayList since the
 * "top" of a stack is actually the last index in the vector.
 * This would make comparators work incorrectly.  Silly Java.
 * 
 * @author Jason
 *
 */
public class FutureStack extends ArrayList<Process> {
	public boolean hasActiveProcess(int time) {
		Collections.sort(this, Process.arrivalTimeComparator());
		return size() > 0 && get(0).getArrivalTime() <= time;
	}
	public Process pop() { return size() > 0 ? remove(0) : null; }
	public String toString() {
		String result = "";
		for (Process p : this) {
			result += p + "\n";
		}
		return result;
	}
}
