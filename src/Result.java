
public class Result {

	private String run = "";
	private float turnaround = 0;
	private float waittime = 0;
	private float responsetime = 0;
	private float throughput = 0;
	
	public Result() {}
	public Result(String run, float turnaround, float waittime, float responsetime, float throughput) {
		this.run = run;
		this.turnaround = turnaround;
		this.waittime = waittime;
		this.responsetime = responsetime;
		this.throughput = throughput;
	}
	
	public String getRun() { return run; }
	public float getTurnaround() { return turnaround; }
	public float getWaittime() { return waittime; }
	public float getResponsetime() { return responsetime; }
	public float getThroughput() { return throughput; }
	public void setTurnaround(float turnaround) { this.turnaround = turnaround; }
	public void setWaittime(float waittime) { this.waittime = waittime; }
	public void setResponsetime(float responsetime) { this.responsetime = responsetime; }
	public void setThroughput(float throughput) { this.throughput = throughput; }
	
	public String toString() {
		return "Average turnaround: " + turnaround + "\nAverage wait time: "
				+ waittime + "\nAverage response time: " + responsetime
				+ "\nThroughput: " + throughput;
	}
}
