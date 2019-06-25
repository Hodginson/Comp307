package kNearest;

import java.io.File;

public class Main {
	public static void main(String[] args) {
		if(args.length!=2){
			System.out.println("You need to enter two files");
			return;
		}
		LoadFile data = new LoadFile(new File(args[0]), new File(args[1]));
		new K_Nearest_Neighbour(data);

	}
}
