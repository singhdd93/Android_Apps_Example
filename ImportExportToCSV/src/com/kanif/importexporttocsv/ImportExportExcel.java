package com.kanif.importexporttocsv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.util.Log;

public class ImportExportExcel {
	DatabaseHandler dbHandler;

	Context context;

	protected static final File DATABASE_DIRECTORY = new File(
			Environment.getExternalStorageDirectory(), "ImportExport");

	protected static final File IMPORT_ITEM_FILE = new File(DATABASE_DIRECTORY,
			"ImportExcel.csv");

	public ImportExportExcel(Context context) {
		this.context = context;
	}

	public Boolean exportDataToCSV(String outFileName) {

		Log.e("excel", "in exportDatabasecsv()");
		Boolean returnCode = false;

		String csvHeader = "";
		String csvValues = "";

		try {

			dbHandler = new DatabaseHandler(context);
			dbHandler.open();

			if (!DATABASE_DIRECTORY.exists()) {
				DATABASE_DIRECTORY.mkdirs();
			}
			Log.e("export fun:file name", outFileName);
			File outFile = new File(DATABASE_DIRECTORY, outFileName);
			FileWriter fileWriter = new FileWriter(outFile);
			Log.e("after FileWriter :file name", outFile.toString());
			BufferedWriter out = new BufferedWriter(fileWriter);
			
			Cursor cursor = dbHandler.getUserInfo();
			// Log.e("excel", "cursor col count" + cursor.getCount());

			int col_count = cursor.getColumnCount();
			Log.e("col count", ""+col_count);
			csvHeader += "\"" + "Id" + "\",";
			csvHeader += "\"" + "Name" + "\",";
			csvHeader += "\"" + "Age" + "\",";
			
			csvHeader += "\n";

			if (cursor != null) {
				out.write(csvHeader);
				while (!cursor.isAfterLast()) {
					// csvValues = Long.toString(cursor.getLong(0)) + ",";

					csvValues = cursor.getString(0) + ","; //id
					csvValues += cursor.getString(1) + ","; // name
					csvValues += cursor.getString(2) + ",\n"; // age
					

					out.write(csvValues);
					cursor.moveToNext();
				}
				// Log.e("excel", "csvValues are:-  " + csvValues);

			}
			out.close();
			cursor.close();
			returnCode = true;
		} catch (Exception e) {
			returnCode = false;
			Log.e("Exception", e.getMessage());
		}

		dbHandler.close();
		return returnCode;
	}

	public void importDataFromCSV() {
		int i = 0;

		boolean flag_is_header = false;

		File file = new File(IMPORT_ITEM_FILE.getPath());
		if (file.exists()) {
		}
		BufferedReader bufRdr = null;
		try {
			bufRdr = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String line = null;

		try {
			while ((line = bufRdr.readLine()) != null) {

				String[] insertValues = line.split(",");
				if (flag_is_header) {

					dbHandler = new DatabaseHandler(context);
					dbHandler.open();
					long row=dbHandler.insertUserInfo(insertValues[1],
							insertValues[2]);
					Log.e("no. of rows inserted", ""+row);

				} else {
					flag_is_header = true;
				}
			}
			dbHandler.close();
			bufRdr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	
}
