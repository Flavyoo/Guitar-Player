import java.util.Calendar;
import java.lang.Math;

public class GuitarHero {
	public static int pressed = -1;
	public static int SAMPLE_RATE = 44100;
	public static double sample;
	public static int j = 0;
	public static long initial;
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
		white_key(48, 0, pressed == 0);
	    white_key(96, 0, pressed == 2);
	    white_key(144, 0, pressed == 3);
	    white_key(192, 0, pressed == 5);
	    white_key(240, 0, pressed == 7);
	    white_key(288, 0, pressed == 8);
	    white_key(336, 0, pressed == 10);
	    white_key(384, 0, pressed == 12);
	    white_key(432, 0, pressed == 14);
	    white_key(480, 0, pressed == 15);
	    white_key(528, 0, pressed == 17);
	    white_key(576, 0, pressed == 19);
	    white_key(624, 0, pressed == 20);
	    white_key(672, 0, pressed == 22);
	    white_key(720, 0, pressed == 24);
	    white_key(768, 0, pressed == 26);
	    white_key(816, 0, pressed == 27);
	    white_key(864, 0, pressed == 29);
	    white_key(912, 0, pressed == 31);
	    white_key(960, 0, pressed == 32);
	    white_key(1008, 0, pressed == 34);
	    white_key(1056, 0, pressed == 36);
	    black_key(48, 270, pressed == 1);
	    black_key(144, 270, pressed == 4);
	    black_key(192, 270, pressed == 6);
	    black_key(288, 270, pressed == 9);
	    black_key(336, 270, pressed == 11);
	    black_key(384, 270, pressed == 13);
	    black_key(480, 270, pressed == 16);
	    black_key(528, 270, pressed == 18);
	    black_key(624, 270, pressed == 21);
	    black_key(672, 270, pressed == 23);
	    black_key(720, 270, pressed == 25);
	    black_key(814, 270, pressed == 28);
	    black_key(864, 270, pressed == 30);
	    black_key(960, 270, pressed == 33);
	    black_key(1008, 270, pressed == 35);
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

        // Creates 37 Notes
		GuitarString[] keys = new GuitarString[37];
		for (int i = 0; i < 37; i++) {
			double CONCERT_I = 440 * Math.pow(1.05956, i - 24);
			keys[i] = new GuitarString(CONCERT_I);
		}

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
			double a = tone(440, sample, 1.0 / 440.0);
			StdAudio.play(a);
			keys[j].tic();
			keys[(j + 1) % keys.length].tic();
			// Refresh the keyboard. / Keyboard does not update on time with key pressed.
			initial = System.currentTimeMillis();
			if (System.currentTimeMillis() - initial > 0.01) {
				initial = System.currentTimeMillis();
				drawKeyboard(pressed);
				StdDraw.show(0);
			}
		}
	}
}
