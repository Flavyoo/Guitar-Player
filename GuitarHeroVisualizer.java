import java.lang.Math;


public class GuitarHeroVisualizer {
	public double startSample;
	public int startTic;
	public double xmax;
	public double xmin;

	public void initialize(int xsize, int ysize, double x1, double y1, double x2, double y2) {
	    StdDraw.setCanvasSize(xsize, ysize);
		xmax = x2;
		xmin = x1;
	    StdDraw.setXscale(xmin, xmax);
	    StdDraw.setYscale(y1, y2);
	    StdDraw.setPenColor(StdDraw.GREEN);
	}

	public void plot(double sample, int tic) {
		StdDraw.line(startTic, startSample, tic, sample);
		if (tic >= xmax) { StdDraw.setXscale(xmax, 2 * xmax); }
		if (tic == 1) {
			StdDraw.pause(10);
			StdDraw.setXscale(xmin, xmax);
		}
		startSample = sample;
		startTic = tic;
	}

	public static void main (String[] args) {
		GuitarHero gh = new GuitarHero();
		int pressed = gh.pressed;
		int SAMPLE_RATE = gh.SAMPLE_RATE;
	    double sample = gh.sample;
		int j = gh.j;
		long initial = System.currentTimeMillis();
		String keyboard = gh.keyboard;

		GuitarString[] keys = new GuitarString[37];
		for (int i = 0; i < 37; i++) {
			double CONCERT_I = 440 * Math.pow(1.05956, i - 24);
			keys[i] = new GuitarString(CONCERT_I);
		}

		GuitarHeroVisualizer ghv = new GuitarHeroVisualizer();
		ghv.initialize(800, 500, 0, -5, 500, 5);
		StdDraw.enableDoubleBuffering();
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

			// Compute Superposition of two waves.
			sample = 0.3 * keys[j].sample() + 0.2 * keys[(j + 1) % keys.length].sample();
			double a = gh.tone(440, sample, 1.0 / 220.0);
			keys[j].tic();
			keys[(j + 1) % keys.length].tic();
			StdAudio.play(a);
			StdDraw.clear();
			ghv.plot(a, keys[j].time());
			StdDraw.pause(10);
			initial = System.currentTimeMillis();
			if (System.currentTimeMillis() - initial > 0.01) {
				initial = System.currentTimeMillis();
				StdDraw.show();
			}
		}
	}
}
