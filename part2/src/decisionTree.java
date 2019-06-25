package decisionTree;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class decisionTree {
	List<String> Categories;
	private ArrayList<String> Attributes;
	private List<Instance> training;
	private Node Root;
	
	@SuppressWarnings("unchecked")
	public decisionTree(String trainingData, String testData) {
		readDataFile(trainingData);
		List<String> StartingAttributes = (ArrayList<String>) Attributes.clone();
		//System.out.println("aa" + StartingAttributes.size());
		Root = buildtree(training, StartingAttributes);
		Root.toString("");

		classifyTestdata(testData);
	}
	
	public static void main(String[] args) {
		if(args.length!=2){
			System.out.println("You need to enter two files");
			return;
		}
		//File data = new File("C:\\Users\\rawso\\Desktop\\hepatitis-training-run01");
		//File test = new File("C:\\Users\\rawso\\Desktop\\hepatitis-test-run01");
		new decisionTree(args[0],args[1]);
	}
	
	private Node buildtree(List<Instance> training2, List<String> startingAttributes) { //built using the pseudo code in the handout
		// TODO Auto-generated method stub
		if(training2.isEmpty()) {
			return mostCommon(training);
		}
		
		/*if(training2 == null) {
			System.out.println("List(Training2) is null";
		}*/
		
		if(Pure(training2)) {
			int category = training2.iterator().next().getCategory();
			return new ChildNode(category, 1);
			//return null;
		}
		if(startingAttributes.isEmpty()) {
			return mostCommon(training2);
		}
		//System.out.println("training2 Size: " + training2.size());
		int mostCommonAttribute = -1;
		double highestPurity = -1;
		List<Instance> True = new ArrayList<Instance>(), False = new ArrayList<Instance>();
		for(int i = 0; i<startingAttributes.size(); i++){

			int Index = Attributes.indexOf(startingAttributes.get(i));

			List<Instance> trueOutcome = new ArrayList<Instance>(), falseOutcome = new ArrayList<Instance>();;

			for(Instance instance: training2){
				if(instance.getAtt(Index)) trueOutcome.add(instance);
				else falseOutcome.add(instance);
			}


			double truePurity = impurityDegree(trueOutcome), falsePurity = impurityDegree(falseOutcome);
			double weightedPurity = trueOutcome.size()/(double)training2.size() * truePurity +
					falseOutcome.size()/(double)training2.size() * falsePurity;

			if(weightedPurity > highestPurity){
				True  = trueOutcome;
				False = falseOutcome;
				highestPurity = weightedPurity;
				mostCommonAttribute = Index;
				
				
			}

		}


		String bestAttribute = (String) Attributes.get(mostCommonAttribute);
		startingAttributes.remove(bestAttribute);
		Node left = buildtree(True, startingAttributes), right = buildtree(False, startingAttributes);

		return new treeNode(mostCommonAttribute, left, right);

	}

	private String findInTree(Node root2, Instance i) {
		// TODO Auto-generated method stub
		if(root2 instanceof treeNode){
			int attribute = ((treeNode)root2).getAttribute();


			if(i.getAtt(attribute)){
				return findInTree(((treeNode) root2).leftNode, i);
			}
			else {
				return findInTree(((treeNode) root2).rightNode, i);
			}
		}
		return ((ChildNode)root2).getCategory();
		//return null;
		
	}
	 
private void classifyTestdata(String testData) {
		// TODO Auto-generated method stub
		  try {
			  Scanner Scan = new Scanner(new File(testData));
			  Scan.nextLine();
			  Scan.nextLine();
			  
			  List<Instance> testInstances = readInstances(Scan);
			  int numCorrect = 0;
			  for (Instance i : testInstances) {
				 // System.out.println(i);
				  String Guess = findInTree(Root,i);
				 String Correct = Categories.get(i.getCategory());
				 /*if(Guess == null) {
					 System.out.println("Guess");
				 }else if(Correct == null) {
					 System.out.println("Correct");
				 }*/

				  if(Guess.equals(Correct)) {
					  numCorrect++;
				  }
			  }
			  System.out.println("\nNumber Correct: " + numCorrect +" /" + testInstances.size());
			  int total = testInstances.size();
			  double accuracy = (((double)numCorrect)/(total))*100;
			  System.out.format("Accuracy: %4.2f", accuracy);
			  System.out.print("%");
		  }catch (FileNotFoundException e) {
			  e.printStackTrace();
			  return;
		  }
	}

	
	
	private Map<Integer, Integer> categoryFrequencies(List<Instance> training2) {
		// TODO Auto-generated method stub
		Map<Integer, Integer> Frequency = new HashMap<Integer, Integer>();

		for(Instance i: training2){
			int category = i.getCategory();

			if(!Frequency.containsKey(category)){
				Frequency.put(category, 0);
			}
			Frequency.put(category, Frequency.get(category) + 1);
		}
		return Frequency;
		//return null;
	}
	
	private boolean Pure(List<Instance> training2) {
		// TODO Auto-generated method stub
		int lastCategory = training2.iterator().next().getCategory();
		for(Instance i : training2){
			if(i.getCategory()!=lastCategory) {
				return false;
			}
			lastCategory = i.getCategory();
		}
		return true;
		//return false;
	}

	private double impurityDegree(List<Instance> Outcome) {
		// TODO Auto-generated method stub
		Map<Integer, Integer> Frequency = categoryFrequencies(Outcome);

		double impurity = 1;

		for(Integer category: Frequency.keySet()){
			double frequency = Frequency.get(category);
			impurity = impurity * (frequency / Outcome.size());
		}
		return impurity;
		//return 0;
	}



	private Node mostCommon(List<Instance> training2) {
		// TODO Auto-generated method stub
		Map<Integer, Integer> Frequency = categoryFrequencies(training2);

		int maxFrequency = -1;
		int mostCommonCategory = -1;

		for(Integer category : Frequency.keySet()){
			int count = Frequency.get(category);
			if(count > maxFrequency){
				maxFrequency = count;
				mostCommonCategory = category;
			}
		}


		double probability = maxFrequency/(double)training2.size();

		return new ChildNode(mostCommonCategory, probability);
		//return null;
	}

	

	private void readDataFile(String fname){
		    /* format of names file:
		     * names of categories, separated by spaces
		     * names of attributes
		     * category followed by true's and false's for each instance
		     */
		    System.out.println("Reading data from file "+fname);
		    try {
		      Scanner din = new Scanner(new File(fname));
		   
		      Categories = new ArrayList<String>();
		      for (Scanner s = new Scanner(din.nextLine()); s.hasNext();) Categories.add(s.next());
		      int numCategories = Categories.size();
		      System.out.println(numCategories +" categories");

		      Attributes = new ArrayList<String>();
		      for (Scanner s = new Scanner(din.nextLine()); s.hasNext();) Attributes.add(s.next());
		      int numAtts = Attributes.size();
		      System.out.println(numAtts +" attributes");
		      
		      training = readInstances(din);
		      din.close();
		    }
		   catch (IOException e) {
		     throw new RuntimeException("Data File caused IO exception");
		   }
		  }

	  private List<Instance> readInstances(Scanner din){
		    /* instance = classname and space separated attribute values */
		    List<Instance> instances = new ArrayList<Instance>();
		    while (din.hasNext()){ 
		      Scanner line = new Scanner(din.nextLine());
		      instances.add(new Instance(Categories.indexOf(line.next()),line));
		    }
		    System.out.println("Read " + instances.size()+" instances");
		    return instances;
		  }
	  interface Node{
			public void toString(String indent);
		}

	
	private class Instance {
		   private int category;
		    private List<Boolean> vals;

		    public Instance(int cat, Scanner s){
		      category = cat;
		      vals = new ArrayList<Boolean>();
		      while (s.hasNextBoolean()) vals.add(s.nextBoolean());
		    }

		    public boolean getAtt(int index){
		      return vals.get(index);
		    }

		    public int getCategory(){
		      return category;
		    }
		    
		    public String toString(){
		      StringBuilder answer = new StringBuilder(Categories.get(category));
		      answer.append(" ");
		      for (Boolean Value : vals)
			answer.append(Value?"true  ":"false ");
		      return answer.toString();
		    }
	}
	
	private class ChildNode implements Node{
		
		private int category;
		private double probability;
		
		public ChildNode(int category, double probability) {
			this.category = category;
			this.probability = probability;
		}
	
		public String getCategory() {
			//return null;
			return Categories.get(category);
		}
	
		@Override
		public void toString(String str) {
			// TODO Auto-generated method stub
			System.out.format("%sClass %s, probability = %4.2f\n", str, Categories.get(category), probability);
		}
	}
	
	private class treeNode implements Node{
		
		private int treeAttributes;
		private Node leftNode, rightNode;
		
		public treeNode(int treeAttributes, Node Left, Node Right) {
			this.treeAttributes = treeAttributes;
			this.leftNode = Left;
			this.rightNode = Right;
		}
		
		public int getAttribute(){
			return treeAttributes;
		}
		
		@Override
		public void toString(String str) {
			// TODO Auto-generated method stub
			System.out.format("%s%s = True:\n", str, Attributes.get(treeAttributes));
			leftNode.toString(str+"   ");
			System.out.format("%s%s = False:\n", str, Attributes.get(treeAttributes));
			rightNode.toString(str+"   ");
		}
	}

	public List<String> getCategories(List<String> c) {
		// TODO Auto-generated method stub
		c = this.Categories;
		return c;
	}
}


