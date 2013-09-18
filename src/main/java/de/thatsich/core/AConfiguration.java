package de.thatsich.core;

import java.util.prefs.Preferences;

public abstract class AConfiguration {
	final private Preferences prefs = Preferences.userNodeForPackage(this.getClass());

	// Getter byte[], boolean, int, long, float, double, String  
	protected byte[] get(String key, byte[] def) {
		return this.prefs.getByteArray(key, def);
	}

	protected boolean get(String key, boolean def) {
		return this.prefs.getBoolean(key, def);
	}

	protected int get(String key, int def) {
		return this.prefs.getInt(key, def);
	}

	protected long get(String key, long def) {
		return this.prefs.getLong(key, def);
	}

	protected float get(String key, float def) {
		return this.prefs.getFloat(key, def);
	}

	protected double get(String key, double def) {
		return this.prefs.getDouble(key, def);
	}

	protected String get(String key, String def) {
		return this.prefs.get(key, def);
	}
	
	// Setter byte[], boolean, int, long, float, double, String
	protected void set(String key, String value) {
		this.prefs.put(key, value);
	}
	
	protected void set(String key, int value) {
		this.prefs.putInt(key, value);
	}
	
	protected void set(String key, long value) {
		this.prefs.putLong(key, value);
	}

	protected void set(String key, float value) {
		this.prefs.putFloat(key, value);
	}
	
	protected void set(String key, double value) {
		this.prefs.putDouble(key, value);
	}
	
	protected void set(String key, boolean value) {
		this.prefs.putBoolean(key, value);
	}
	
	protected void set(String key, byte[] value) {
		this.prefs.putByteArray(key, value);
	}
}