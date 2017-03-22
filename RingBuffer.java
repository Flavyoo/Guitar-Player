import java.util.*;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdAudio;

public class RingBuffer {
	public int size = 0;      // Number of items in the buffer.
	public int first = 0;     // Index of least recently inserted item.
	public int last = 0;      // Index one beyond the most recentley inserted item.
	public double[] buffer;   // Contains N number of samples.

	// create an empty ring buffer, with given max capacity
	public RingBuffer(int capacity) {
		// Create a buffer of size capacity.
		buffer = new double[capacity];
	}

	// Return number of items currently in the buffer
	public int size() {
		return size;
	}

	// Is the buffer empty (size equals zero)?
	public boolean isEmpty() {
		return size() == 0;
	}

	// Is the buffer full  (size equals capacity)?
	public boolean isFull() {
		return size() == buffer.length;

	}

	// Add item x to the end of the buffer.
	public void enqueue(double x) {
		if (this.isFull()) {
			throw new IndexOutOfBoundsException("Cannot enqueue a full buffer.");
		}
		buffer[last] = x;
		last++;
		size++;
		if (last == buffer.length) { last = 0; }
	}

	// Delete and return item from the front
	public double dequeue() {
	    if (this.isEmpty()) {
			throw new IndexOutOfBoundsException("Cannot dequeue an empty buffer.");
		}
		double item = buffer[first];
		first++;
		size--;
		if (first == buffer.length) { first = 0; }
		return item;
	}

	// Return (but do not delete) item from the front
	public double peek() {
		if (this.isEmpty()) {
			throw new IndexOutOfBoundsException("Cannot peek an empty buffer.");
		}
		return buffer[first];
	}

	public static void main(String[] args) {
		int N = Integer.parseInt(args[0]);
		RingBuffer rb = new RingBuffer(N);
		for (int i = 1; i < N + 1; i++) { rb.enqueue(i); }
		double item = rb.dequeue();
		System.out.println(item);
		rb.enqueue(item);
		System.out.println(rb.size());
	}
}
