package de.thatsich.core;

import java.util.prefs.Preferences;

/**
 * Simple Service to access stored values in the registry.
 * Just allows simple Dataformats but you can use byte[]
 * to apply to every object if handled correctly.
 *
 * It uses the package name to store
 * so each and every different package is handled seperatly
 * and can store equal key-values
 *
 * @author Minh
 */
public abstract class AConfigurationService
{
	final private Preferences preferences = Preferences.userNodeForPackage(this.getClass());

	protected byte[] get(String key, byte[] def) { return this.preferences.getByteArray(key, def); }

	protected boolean get(String key, boolean def) { return this.preferences.getBoolean(key, def); }

	protected int get(String key, int def) { return this.preferences.getInt(key, def); }

	protected long get(String key, long def) { return this.preferences.getLong(key, def); }

	protected float get(String key, float def) { return this.preferences.getFloat(key, def); }

	protected double get(String key, double def) { return this.preferences.getDouble(key, def); }

	protected String get(String key, String def) { return this.preferences.get(key, def); }

	// Setter byte[], boolean, int, long, float, double, String
	protected void set(String key, String value)
	{ this.preferences.put(key, value); }

	protected void set(String key, int value) { this.preferences.putInt(key, value); }

	protected void set(String key, long value) { this.preferences.putLong(key, value); }

	protected void set(String key, float value) { this.preferences.putFloat(key, value); }

	protected void set(String key, double value) { this.preferences.putDouble(key, value); }

	protected void set(String key, boolean value) { this.preferences.putBoolean(key, value); }

	protected void set(String key, byte[] value) { this.preferences.putByteArray(key, value); }
}
