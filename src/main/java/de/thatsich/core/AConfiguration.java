package de.thatsich.core;

import java.util.prefs.Preferences;

public abstract class AConfiguration {
	final private Preferences prefs = Preferences.userNodeForPackage(this.getClass());
	
	public Preferences getPreferences() {
		return this.prefs;
	}
	
	public void set(String key, String value) {
		this.prefs.put(key, value);
	}
	
	public void set(String key, int value) {
		this.prefs.putInt(key, value);
	}
	
	public void set(String key, long value) {
		this.prefs.putLong(key, value);
	}

	public void set(String key, float value) {
		this.prefs.putFloat(key, value);
	}
	
	public void set(String key, double value) {
		this.prefs.putDouble(key, value);
	}
	
	public void set(String key, boolean value) {
		this.prefs.putBoolean(key, value);
	}
	
	public void set(String key, byte[] value) {
		this.prefs.putByteArray(key, value);
	}
}
