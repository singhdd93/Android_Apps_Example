package org.michenux.yourappidea.model;

public class SlidingMenuItem {

	private String label;

	private String icon;

	public SlidingMenuItem(String label, String icon) {
		super();
		this.label = label;
		this.icon = icon;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
}
