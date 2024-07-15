
import javafx.scene.control.Button;

public class GameButton extends Button{

	private String color;
	private String rowAndCol;
	private boolean pressed;
	private int whoPressed;
	
	public GameButton(String label, String color) {
		super(/*label*/);
		rowAndCol = label;
		this.color = color;
		pressed = false;
		whoPressed = 0;
		setStyle(" -fx-background-color: " + color + ";");
	}
	
	public String getColor() {
		return color;
	}
	
	public void setColor(String color) {
		setStyle(" -fx-background-color: " + color + ";");
		this.color = color;
	}
	
	public String getRowCol() {
		return rowAndCol;
	}
	
	public boolean wasPressed() {
		return pressed;
	}
	
	public void setPressed() {
		pressed = true;
	}
	
	public int getWhoPressed() {
		return whoPressed;
	}
	
	public void setWhoPressed(int player) {
		whoPressed = player;
	}
	
	public void reset() {
		setColor("grey");
		pressed = false;
		whoPressed = 0;
	}
}
