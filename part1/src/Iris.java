package kNearest;

import java.util.ArrayList;

public class Iris implements Comparable<Iris>{
	
	private ArrayList<Double> Flowers;
	private String className;
	private double Distance;
	
	public Iris(ArrayList<Double> Flowers, String className) {
		// TODO Auto-generated constructor stub
		this.Flowers = Flowers;
		this.className = className;
	}
	public ArrayList<Double> getFlowers(){
		return Flowers;
	}
	public void setFlowers(ArrayList<Double> Flowers) {
		this.Flowers = Flowers;
	}
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	public double getDistance() {
		return Distance;
	}

	public void setDistance(double Distance) {
		this.Distance = Distance;
	}
	
	@Override
	public String toString() {
		return "Properties [Flowers=" + Flowers + ", className=" + className + "]";
	}
	
	@Override
	public int compareTo(Iris arg0) {
		// TODO Auto-generated method stub
		if (this.getDistance() - arg0.getDistance() > 0) {
			return 1;
		}

		else if (this.getDistance() - arg0.getDistance() < 0) {
			return -1;
		}

		else {
			return 0;
		}
	}

}
