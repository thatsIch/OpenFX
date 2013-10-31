package de.thatsich.core.javafx.component;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class IntegerField extends TextField {
	
	// Properties
	private final IntegerProperty value = new SimpleIntegerProperty();
	
	public IntegerField() {
		this.textProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue.matches("\\d+")) {
					final int count = Integer.parseInt(newValue);
					value.set(count);
				} else {
					textProperty().set(oldValue);
				}
			}
		});
	}
	
	// Property Getter
	public IntegerProperty getValueProperty() { return this.value; }
	
	// Getter
	public int getValue() { return this.value.get(); }
	
	// Setter
	public void setValue(int value) { this.value.set(value); }
}
