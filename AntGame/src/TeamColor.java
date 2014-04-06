/**
 * color.java represents the team's colours. Used throughout the project,
 * to define the colour of the team, ants, anthills, markesr etc. 
 * @author D Kopic
 */
public enum TeamColor {
	RED, BLACK;
	
	public static TeamColor otherColor(TeamColor c){
		if(c == RED)return BLACK;
		return RED;
	}
}
