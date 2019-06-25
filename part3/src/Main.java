package Perceptron;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class Main {
	private int numFeatures = 50,numRepetitions = 100,defaultWidth = 10, defaultHeight = 10,width, height;
	private double intialLearningRate = 1, minLearningrate = 0.1, Modifier = 0.9, threshold = 0.8, learningRate;
//	private String category = "other";
//	private String categoryType;
//	private boolean[][] image = new boolean[defaultWidth][defaultHeight];
//	private double[] weights;
	private Feature[] features;
	private ArrayList<Image> imageList = new ArrayList<Image>();

	public Main(String fname) {
		width = defaultWidth;
		height = defaultHeight;
		learningRate = intialLearningRate;
		File file = new File(fname);
		load(file);
		makeFeatures();
		Train();
	}
	
	private void load(File file) {
		imageList = new ArrayList<Image>();
		try {
			Scanner scan = new Scanner(file);
			while (scan.hasNext()) {
				imageList.add(loadImage(scan));
			}
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private Image loadImage(Scanner scan) {
		boolean[][] New = null;
		boolean isO = false;
		java.util.regex.Pattern b = java.util.regex.Pattern.compile("[01]");
		if (!scan.next().equals("P1"))
			System.out.println("Not a P1 PBM file");
		String category = scan.next().substring(1);
		if (!category.equals("yes"))
			isO = true;
		int rows = scan.nextInt();
		int cols = scan.nextInt();

		New = new boolean[rows][cols];
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				New[r][c] = (scan.findWithinHorizon(b, 0).equals("1"));
			}
		}

		return new Image(New, isO);
		}
	
/*	private double[] setWeight() {
		weights = new double[51];
			
		Random random = new Random();
		for(int i=1;i<51;i++) {
            weights[i] = random.nextDouble();
        }
		weights[0] = 1;
		return weights;
	}*/
	
	private void makeFeatures() {
		features = new Feature[numFeatures];
		features[0] = new Dummy(width, height, 0, 0 - threshold);
		for(int i = 0; i <numFeatures;i++) {
		features[i] = new Feature(width, height, i);
		}
		
	}
	
	public static void main(String[] args) {
		if(args.length < 1){
			System.out.println("You need to enter a file");
			return;
		}else if(args.length > 1) {
			System.out.println("You can only enter one file");
			return;
		}
		new Main(args[0]);
	}
	
	private void Train() {
		int numImagesWrong = imageList.size();
		int numRepsDone = 0;

		for (int repetition = 0; repetition < numRepetitions; repetition++) {
			int numImagesCorrect = 0;
			
			for (Image image : imageList) {
				double input = 0;

				for (Feature feature : features) {
					input += feature.evaluate(image) * feature.getWeight();
				}

				
				if (input <= 0) {
					if (image.isItO()) { 
						numImagesCorrect++;
					} else {
						for (Feature feat : features) {
							double New = feat.getWeight()
									+ feat.evaluate(image) * learningRate;
							feat.setWeight(New);
						}
					}
				} else {

					if (!image.isItO()) { 
						numImagesCorrect++;
					} else {
						for (Feature feat : features) {
							double newWeight = feat.getWeight()
									- feat.evaluate(image) * learningRate;
							feat.setWeight(newWeight);
						}
					}
				}
			}

			numImagesWrong = imageList.size() - numImagesCorrect;

			if (learningRate > minLearningrate) {
				learningRate *= Modifier;
			}

			numRepsDone++;
		}
		report(numImagesWrong,numRepsDone);
	}
	
	public void report(int imagesWrong, int repsDone) {
		if (imagesWrong == 0) {
			System.out.println("All images passed");
			System.out.println("Number of repetitions: " + repsDone);
		} else {
			System.out.println("Some images failed");
			System.out.println("Number of incorrect images : " + imagesWrong);
		}		
		System.out.println("\nFinal learning Rate: " + String.valueOf(learningRate).substring(0, 6));	
		for (Feature feature : features){
			System.out.println(feature.toString());	
		}
		
	}
}
