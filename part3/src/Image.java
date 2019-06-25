package Perceptron;

public class Image {
	boolean[][] New;
	boolean isO;
	String category;
	public Image(boolean[][] New, boolean isO) {
		// TODO Auto-generated constructor stub
		this.New = New;
		this.isO = isO;
		
	}

	public boolean isItO() {
		// TODO Auto-generated method stub
		return isO;
	}
	
	public boolean getValue(int x, int y) {
		// TODO Auto-generated method stub
		return New[y][x];
	}
	
    public void setCategory(String c) {
        this.category = category;
    }
    
    public String getCategory() {
        return category;
    }


}
