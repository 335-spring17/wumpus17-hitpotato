package model;

public class Room {
	private char element;
	private boolean visited;
	
	public Room(){
		element = ' ';
		visited = false;
	}
	
	public void setElement(char element){
		this.element = element;
	}
	public void setVisited(boolean visited){
		this.visited = visited;
	}
	public char getElement(){
		return this.element;
	}
	public boolean isVisited(){
		return this.visited;
	}
}
