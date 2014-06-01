package de.thatsich.core.javafx.component;

import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class IntegerField extends TextField
{
	// Properties
	public final IntegerProperty value = new SimpleIntegerProperty();

	public IntegerField()
	{
		this.textProperty().addListener(new ChangeListener<String>()
		{
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
			{
				if (newValue.matches("\\d+"))
				{
					final int count = Integer.parseInt(newValue);
					value.set(count);
				}
				else
				{
					textProperty().set(oldValue);
				}
			}
		});

		this.value.addListener(new ChangeListener<Number>()
		{
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
			{
				textProperty().set(newValue.toString());
			}
		});

		this.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent paramT)
			{
				Platform.runLater(new Runnable()
				{
					@Override
					public void run()
					{
						if (isFocused() && !getText().isEmpty())
						{
							selectAll();
						}
					}
				});
			}
		});
	}
}
