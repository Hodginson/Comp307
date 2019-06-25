package kNearest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JOptionPane;

public class K_Nearest_Neighbour {
	private int kNeighbours;
	
	public K_Nearest_Neighbour(LoadFile data) {
		// TODO Auto-generated constructor stub
		String numNeighbours = JOptionPane.showInputDialog("Enter number of Neighbours: ");
		while(numNeighbours.equals("") || !numNeighbours.matches("\\d+")) {
			numNeighbours = JOptionPane.showInputDialog("Please Enter number of Neighbours: ");
		}
		kNeighbours = Integer.parseInt(numNeighbours);
		
		nearestNeighbour(data);
	}

	private void nearestNeighbour(LoadFile data) {
		// TODO Auto-generated method stub
		int lines = 75;
		int pass = 0;
		int fail = 0;
		double accuracy = 0;
		String MostCommnon = "";
	//	String predictedClass = "";
		for (Iris test : data.getTestSet()) {
			ArrayList<Iris> nearestFlower = new ArrayList<Iris>();
			// System.out.println("training set: " + p.toString());
			for (Iris train : data.getTrainingData()) {
				double distance = measureDis(test, train);
				train.setDistance(distance);
				nearestFlower.add(train);

			}

			Collections.sort(nearestFlower);

			for (int i = 0; i < kNeighbours; i++) {
				if (nearestFlower.get(i).getClassName().equals(test.getClassName())) {
					System.out.println("Correct -> Predicated class: " + test.getClassName() + " || Guessed Class: " + nearestFlower.get(i).getClassName());

					pass++;
				}

				else {
					System.out.println("Incorrect -> Predicted class: " + test.getClassName() + " || Guessed Class: " + nearestFlower.get(i).getClassName());
					fail++;
				}
				MostCommnon = getMajorityClassLable(nearestFlower);

			}

		}

		pass = Math.round((float)pass/kNeighbours);
		fail = Math.round((float)fail/kNeighbours);
		accuracy = ((double) pass) / (pass+fail);
		System.out.println();
		System.out.println("Number Passed: " + pass + "/" + lines);
		System.out.println("Number Failed: " + fail + "/" + lines);
		System.out.format("Accuracy: %4.3f",accuracy);
		System.out.println("%");
		System.out.println("Most Common Class: " + MostCommnon);
	}

	private String getMajorityClassLable(ArrayList<Iris> nearest) {
		// TODO Auto-generated method stub
		Map<String, Integer> map = new HashMap<String, Integer>();

		for (Iris n : nearest) {
			String className = n.getClassName();
			if (map.containsKey(className)) {
				int num = map.get(className) + 1;
				map.put(className, num);
			} else {
				map.put(className, 1);
			}
		}
		int max = Integer.MIN_VALUE;
		String lable = "";
		for (Entry<String, Integer> entry : map.entrySet()) {
			if (entry.getValue() > max) {
				lable = entry.getKey();
			}
		}

		return lable;
	}

	private double measureDis(Iris test, Iris train) {
		// TODO Auto-generated method stub
		double sum = 0;

		for (int i = 0; i < test.getFlowers().size(); i++) {

			double a = test.getFlowers().get(i);
			double b = train.getFlowers().get(i);
			sum += Math.pow(a - b, 2);
		}

		return Math.sqrt(sum);
	}
	

}
