package com.thatsich.sample.javafx.command;

import java.io.File;
import java.net.URL;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import com.google.inject.Inject;
import com.thatsich.sample.javafx.service.ILoadImageService;

public class LoadImageCommand extends Service<File> {

	@Inject
	private ILoadImageService loadImageService;
	private String filePath;
	private URL url;
	
	@Override
	protected Task<File> createTask() {
        return new Task<File>() {
            @Override
            protected File call() throws Exception {
            	if (filePath != null) {
            		return loadImageService.getImage(filePath);
            	}
            	else if (url != null) {
            		return loadImageService.getImage(url);
            	}
            	else {
            		throw new IllegalArgumentException("Neither filePath nor url was defined to fetch Image.");
            	}
            }
        };
	}
	
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public void setUrl(URL url) {
		this.url = url;
	}
}
