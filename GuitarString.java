import java.lang.Math;
import java.util.Random;


public class GuitarString {
	public double ENERGY_FACTOR = 0.994;
	public int SAMPLE_RATE = 44100;
	public int capacity;
	public RingBuffer rb;
	public int timeCount;

	// Create a guitar string of the given frequency, using a sampling rate of 44,100.
	public GuitarString(double frequency) {
		capacity = (int) Math.ceil(SAMPLE_RATE / frequency);
		rb = new RingBuffer(capacity);
		for (int i = 0; i < capacity; i++) {
			rb.enqueue(0);
		}

	}
	// Create a guitar string whose size and initial values are given by the array.
	public GuitarString(double[] init) {
		rb = new RingBuffer(init.length);
		for (int i = 0; i < init.length; i++) {
			rb.enqueue(init[i]);
		}

	}

	// Set the buffer to white noise.
	public void pluck() {
		// Displacement gets sampled at N numbers.
		Random random = new Random();
		for (int i = 0; i < rb.buffer.length; i++) {
			rb.dequeue();
			rb.enqueue(-0.5 + random.nextInt(10) / 10.0);
		}
	}

	// Advance the simulation one time step.
	public void tic() {
		double a = rb.dequeue();
		double b = rb.peek();
		double average = ENERGY_FACTOR * 0.5 * (a + b);
		rb.enqueue(average);
		timeCount++;

	}

	// Return the current sample.
	public double sample() {
		return rb.peek();

	}

	// Return number of tics.
	public int time() {
		return timeCount;
	}

	public static void main(String[] args) {
		int N = Integer.parseInt(args[0]);
		double[] samples = {0.2, 0.4, 0.5, 0.3, -0.2, 0.4, 0.3, 0.0, -0.1, -0.3};
		GuitarString a = new GuitarString(samples);
		for (int t = 0; t < N; t++) {
			System.out.format("%6d %8.4f\n", t, a.sample());
			a.tic();
		}
	}
}
