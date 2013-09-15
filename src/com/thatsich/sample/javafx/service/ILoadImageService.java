package com.thatsich.sample.javafx.service;

import java.io.File;
import java.net.URL;

public interface ILoadImageService {
	public File getImage(String filePath);
	public File getImage(URL url);
}
