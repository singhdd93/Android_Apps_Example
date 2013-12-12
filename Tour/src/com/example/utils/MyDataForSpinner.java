package com.example.utils;
public class MyDataForSpinner 
	{
		public MyDataForSpinner( String spinnerText, String value ) {
			this.spinnerText = spinnerText;
			this.value = value;
		}

		public String getSpinnerText() {
			return spinnerText;
		}

		public String getValue() {
			return value;
		}

		public String toString() {
			return spinnerText;
		}

		String spinnerText;
		String value;
	}