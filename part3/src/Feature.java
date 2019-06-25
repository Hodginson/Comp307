package Perceptron;

import java.util.Random;

public class Feature {
	//private static final char[] Weight = null;
	int[] row;
	int[] col;
	boolean[] signs;
	int featurePoints = 4;
	int randomSeed = 1998;
	double weight;
	
	public Feature(int rows, int cols, int random) {
		Random r = new Random(randomSeed + random);
		this.weight = r.nextDouble();
		populateFeatures(rows, cols, r);
	}
	
	public double getWeight() {
		return weight;
	}
	
	public void setWeight(double New) {
		this.weight = New;
	}
	
	public boolean[] getSign() {
		return signs;
	}
	
	 void populateFeatures(int width, int height, Random random) {
		//System.out.println(width);
		row = new int[featurePoints];
    	col = new int[featurePoints];
    	signs = new boolean[featurePoints];
    	for (int i = 0; i < featurePoints; i++) {
			row[i] = random.nextInt(height);
			col[i] = random.nextInt(width);
			signs[i] = random.nextBoolean();
		}
    }

	public double evaluate(Image image) {
		// TODO Auto-generated method stub
		int sum = 0;
		for (int i = 0; i < 4; i++) {
			if (image.getValue(col[i], row[i]) == signs[i]) {
				sum++;
			}
		}
		return (sum >= 3) ? 1 : 0;
	}
	
	public String toString() {
		StringBuilder str = new StringBuilder("\n");
		for(int i = 0; i<4;i++) {
			str.append("\nPixel " + i + ": Column: " + col[i] + ", Row: " + row[i] );
		}
		str.append("\nWeight: " + String.valueOf(weight).substring(0, 6)); 
		return str.toString();
	}

}
