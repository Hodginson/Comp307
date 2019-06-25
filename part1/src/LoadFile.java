package kNearest;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class LoadFile {
	private ArrayList<Iris> TrainingData = new ArrayList<Iris>();
	private ArrayList<Iris> TestData = new ArrayList<Iris>();
	
	public LoadFile(File training, File testing) {
		// TODO Auto-generated constructor stub
		setTrainingData(training);
		setTestingData(testing);
		
	}

	private void setTestingData(File testing) {
		// TODO Auto-generated method stub
		try {
			Scanner data = new Scanner(testing);
			while (data.hasNext()) {
				ArrayList<Double> flower = new ArrayList<Double>();
				double sepalLength = data.nextDouble();
				double sepalWidth = data.nextDouble();
				double petalLength = data.nextDouble();
				double petalWidth = data.nextDouble();
				String className = data.next();

				flower.add(sepalLength);
				flower.add(sepalWidth);
				flower.add(petalLength);
				flower.add(petalWidth);

				TestData.add(new Iris(flower, className));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void setTrainingData(File training) {
		// TODO Auto-generated method stub
		try {
			Scanner data = new Scanner(training);
			while (data.hasNext()) {
				ArrayList<Double> flower = new ArrayList<Double>();
				double sepalLength = data.nextDouble();
				double sepalWidth = data.nextDouble();
				double petalLength = data.nextDouble();
				double petalWidth = data.nextDouble();
				String className = data.next();

				flower.add(sepalLength);
				flower.add(sepalWidth);
				flower.add(petalLength);
				flower.add(petalWidth);

				TrainingData.add(new Iris(flower, className));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Iris> getTrainingData() {
		return TrainingData;
	}

	public ArrayList<Iris> getTestSet() {
		return TestData;
	}
}
