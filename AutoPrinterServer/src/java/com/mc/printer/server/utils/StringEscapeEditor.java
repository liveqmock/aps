package com.mc.printer.server.utils;

import java.beans.PropertyEditorSupport;

public class StringEscapeEditor extends PropertyEditorSupport {

	public StringEscapeEditor() {
		super();
	}

	@Override
	public void setAsText(String text) {
		if (text == null) {
			setValue(null);
		} else {
			text = text.replaceAll("<", "＄1�7").replaceAll(">", "＄1�7");
			setValue(text);
		}
	}

	@Override
	public String getAsText() {
		Object value = getValue();
		return value != null ? value.toString() : "";
	}
}