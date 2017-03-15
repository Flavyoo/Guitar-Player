import java.lang.Math;


public class GuitarHeroVisualizer {
	public double[] samples;
	public int[] tics;
	public int index = 0;
	public int size = 2;

	public GuitarHeroVisualizer() {
		samples = new double[2];
		tics = new int[2];
	}

	public void initialize(int xsize, int ysize, double x1, double y1, double x2, double y2) {
	    StdDraw.setCanvasSize(xsize, ysize);
	    StdDraw.setXscale(x1, x2);
	    StdDraw.setYscale(y1, y2);
	    StdDraw.setPenColor(StdDraw.BLUE);
	}

	public void plot(double sample, int tic) {
		// Add the sample to samples array
		if (index > 1) { index = 0; }
		samples[index] = sample;
		tics[index] = tic;
	    StdDraw.line(tics[(index + 1) % size], samples[(index + 1) % size],tics[index],samples[index]);
		index++;
	}

	public static void main (String[] args) {
		GuitarHero gh = new GuitarHero();
		int pressed = gh.pressed;
		int SAMPLE_RATE = gh.SAMPLE_RATE;
	    double sample = gh.sample;
		int j = gh.j;
		long start = System.currentTimeMillis();
		double KEYBOARD_REFRESH_DELAY = gh.KEYBOARD_REFRESH_DELAY;
		String keyboard = gh.keyboard;

		GuitarString[] keys = new GuitarString[37];
		for (int i = 0; i < 37; i++) {
			double CONCERT_I = 440 * Math.pow(1.05956, i - 24);
			keys[i] = new GuitarString(CONCERT_I);
		}

		GuitarHeroVisualizer ghv = new GuitarHeroVisualizer();
		ghv.initialize(1000, 500, -1, -5, 500, 5);

		while (true) {
			if (StdDraw.hasNextKeyTyped()) {
				char key = StdDraw.nextKeyTyped();
				j = keyboard.indexOf(key);
				if (j == -1) {
					j = 0;
					pressed = 0;
				}
				else {
					pressed = j;
					keys[j].pluck();
					keys[(j + 1) % keys.length].pluck();
				}
				StdDraw.clear();
			}

			// Compute Superposition of tow waves.
			sample = 0.3 * keys[j].sample() + 0.2 * keys[(j + 1) % keys.length].sample();
			double a = gh.tone(440, sample, 1.0 / 220.0);
			StdAudio.play(a);
			ghv.plot(a, keys[j].time());
			keys[j].tic();
			keys[(j + 1) % keys.length].tic();

			// Refresh the keyboard.
			start = System.currentTimeMillis();
			if (System.currentTimeMillis() - start > KEYBOARD_REFRESH_DELAY) {
				start = System.currentTimeMillis();
				StdDraw.show(0);
			}
		}
	}
}
