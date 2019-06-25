package Perceptron;

public class Dummy extends Feature {


	public Dummy(int width, int height, int Modifier, double weight) {
		super(width, height, Modifier);
	}

	public double evaluate(Image image) {
		return 1;
	}



}
