import java.util.Calendar;
import java.lang.Math;



public class GuitarHero {
	public static int pressed = -1;
	public static int SAMPLE_RATE = 44100;
	public static double sample = 0;
	public static int j = 0;
	public static long start = System.currentTimeMillis();
	public static double KEYBOARD_REFRESH_DELAY = 0.001;
	public static String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";

	public static void black_key(int x, int y, boolean pressed) {
		if (pressed) { StdDraw.setPenColor(StdDraw.RED); }
		if (!pressed) { StdDraw.setPenColor(StdDraw.BLACK);}
		StdDraw.filledRectangle(x, y, 12, 170);
	}

	public static void white_key(int x, int y, boolean pressed) {
		if (pressed) { StdDraw.setPenColor(StdDraw.RED); }
		if (!pressed) {
			StdDraw.setPenRadius(0.01);
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.rectangle(x, y, 48, 300);
			StdDraw.setPenColor(StdDraw.WHITE);
		}
		StdDraw.filledRectangle(x, y, 48, 300);
	}

    public static void drawKeyboard(int pressed) {
            StdDraw.clear();
	    white_key(0, 0, pressed == 0);
	    white_key(48, 0, pressed == 2);
	    white_key(96, 0, pressed == 3);
	    white_key(144, 0, pressed == 5);
	    white_key(192, 0, pressed == 7);
	    white_key(240, 0, pressed == 8);
	    white_key(288, 0, pressed == 10);
	    white_key(336, 0, pressed == 12);
	    white_key(384, 0, pressed == 14);
	    white_key(432, 0, pressed == 15);
	    white_key(480, 0, pressed == 17);
	    white_key(528, 0, pressed == 19);
	    white_key(576, 0, pressed == 20);
	    white_key(624, 0, pressed == 22);
	    white_key(672, 0, pressed == 24);
	    white_key(720, 0, pressed == 26);
	    white_key(768, 0, pressed == 27);
	    white_key(816, 0, pressed == 29);
	    white_key(864, 0, pressed == 31);
	    white_key(912, 0, pressed == 32);
	    white_key(960, 0, pressed == 34);
	    white_key(1008, 0, pressed == 36);
	    black_key(36, 130, pressed == 1);
	    black_key(132, 130, pressed == 4);
	    black_key(180, 130, pressed == 6);
	    black_key(276, 130, pressed == 9);
	    black_key(324, 130, pressed == 11);
	    black_key(372, 130, pressed == 13);
	    black_key(468, 130, pressed == 16);
	    black_key(516, 130, pressed == 18);
	    black_key(612, 130, pressed == 21);
	    black_key(660, 130, pressed == 23);
	    black_key(708, 130, pressed == 25);
	    black_key(804, 130, pressed == 28);
	    black_key(852, 130, pressed == 30);
	    black_key(948, 130, pressed == 33);
	    black_key(996, 130, pressed == 35);
	}

	// Add a tone effect to the sample.
    public static double tone(int hz, double sample, double duration) {
			double a = Math.sin(Math.PI * sample * hz / 100);
		return a;
	}

	public static void main(String[] args) {
		StdDraw.setCanvasSize(1056, 300);
	    StdDraw.setXscale(0, 1056);
	    StdDraw.setYscale(0, 300);


		GuitarString[] keys = new GuitarString[37];
		for (int i = 0; i < 37; i++) {
			double CONCERT_I = 440 * Math.pow(1.05956, i - 24);
			keys[i] = new GuitarString(CONCERT_I);
		}

		GuitarHeroVisualizer ghv = new GuitarHeroVisualizer();
		//ghv.initialize(500, 500, -1, -5, 500, 5);
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
			double a = tone(440, sample, 1.0 / 220.0);
			StdAudio.play(a);
			keys[j].tic();
			keys[(j + 1) % keys.length].tic();
			//ghv.plot(sample, keys[j].time());

			// Refresh the keyboard.
			start = System.currentTimeMillis();
			if (System.currentTimeMillis() - start > KEYBOARD_REFRESH_DELAY) {
				start = System.currentTimeMillis();
				drawKeyboard(pressed);
				StdDraw.show(0);
			}
		}
	}
}
