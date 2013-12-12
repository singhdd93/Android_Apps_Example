package com.kanif.importexporttocsv;

import java.io.File;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class ImportExportActivity extends Activity implements OnClickListener{

	DatabaseHandler databaseConnector;
	Context context;
	Button importBtn,exportBtn;
	ImportExportExcel importExportExcel;
	
	
	String exportFileName="ExportExcel.csv";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_import_export);
		
		initializeUI();
	}

	public void initializeUI()
	{
		context=this;
		databaseConnector=new DatabaseHandler(context);
		importExportExcel=new ImportExportExcel(context);
		importBtn=(Button) findViewById(R.id.importBtn);
		exportBtn=(Button) findViewById(R.id.export);
		
		importBtn.setOnClickListener(this);
		exportBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.importBtn:
			importExportExcel.importDataFromCSV();
			Toast.makeText(context, "File imported successfully", 1).show();
			break;

		case R.id.export:
			try {
				importExportExcel.exportDataToCSV(exportFileName);
				Toast.makeText(context, "File exported successfully", 1).show();
			} catch (Exception e) {
				Toast.makeText(context, "Please,Import data first", 1).show();
			
			}
			break;
		default:
			break;
		}
	}

}
