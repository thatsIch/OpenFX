package de.thatsich.core.javafx;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.RectangleBuilder;

/**
 * 
 * @author Tran Minh Do
 *
 */
public class LedMatrix extends GridPane {

	private Rectangle[][] rects;
	private int width;
	private int height;
	
	final private int DEFAULT_SIZE = 10;
	
	/**
	 * Custom CTOR
	 * @param width Width of Matrix
	 * @param height Height of Matrix
	 */
	public LedMatrix(int width, int height) {
		this.resize(width, height);
	}
	
	/**
	 * Default CTOR
	 */
	public LedMatrix() {
		this.resize(DEFAULT_SIZE, DEFAULT_SIZE);
	}
	
	public void resize(int width, int height) {
		this.width = width;
		this.height = height;
		
		this.updateMatrix(width, height);
	}
	
//	private void updateColor(Mat color) {
//		// check if same size
//	}
	
	private void updateMatrix(int width, int height) {
		this.rects = new Rectangle[this.width][this.height];
		this.getChildren().clear();
		
		for (int x = 0; x < this.width; x++) {
			for (int y = 0; y < this.height; y++) {
				Rectangle rect = RectangleBuilder.create().width(25).height(25).fill(Color.rgb((int)Math.round(255 * Math.random()), 0, 0)).build();

				this.rects[x][y] = rect;
				this.add(rect, x, y);
			}
		}
	}
}
