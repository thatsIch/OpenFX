package de.thatsich.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;


/**
 * 
 * @author Minh
 *
 */
public class ConfigRegistry {

	/**
	 * 
	 */
	private Path configPath;
	
	/**
	 * 
	 */
	private Properties props;
	
	/**
	 * Default CTOR
	 */
	public ConfigRegistry() {
		this.configPath = Paths.get("config.properties");
		this.props = new Properties();
	}
	
	
	/**
	 * 
	 * @return
	 */
	public Properties load() {
		File configFile = this.configPath.toFile();
		
		try (FileInputStream stream = new FileInputStream(configFile)) {
			this.props.load(stream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return this.props;
	}
	
	public void store() {
		File configFile = this.configPath.toFile();
		
		try (FileOutputStream stream = new FileOutputStream(configFile)) {
			this.props.store(stream, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
