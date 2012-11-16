package com.primeiroestilo.labufmg;

import android.app.Activity;
import android.os.Bundle;
import java.io.*;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;

public class Teste extends Activity {
	// GUI controls
	EditText txtData;
	Button btnWriteSDFile;
	Button btnReadSDFile;
	Button btnClearScreen;
	Button btnClose;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.teste);
	// bind GUI elements with local controls
	txtData = (EditText) findViewById(R.id.txtData);
	txtData.setHint("Enter some lines of data here...");

	btnWriteSDFile = (Button) findViewById(R.id.btnWriteSDFile);
	btnWriteSDFile.setOnClickListener(new OnClickListener() {

	public void onClick(View v) {
		// write on SD card file data in the text box
		try {
			File myFile = new File("/sdcard/mysdfile.txt");
			myFile.createNewFile();
			FileOutputStream fOut = new FileOutputStream(myFile);
			OutputStreamWriter myOutWriter = 
									new OutputStreamWriter(fOut);
			myOutWriter.append(txtData.getText());
			myOutWriter.close();
			fOut.close();
			Toast.makeText(getBaseContext(),
					"Done writing SD 'mysdfile.txt'",
					Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			Toast.makeText(getBaseContext(), e.getMessage(),
					Toast.LENGTH_SHORT).show();
		}
	}// onClick
	}); // btnWriteSDFile

		btnReadSDFile = (Button) findViewById(R.id.btnReadSDFile);
		btnReadSDFile.setOnClickListener(new OnClickListener() {

		public void onClick(View v) {
			// write on SD card file data in the text box
		try {
			File myFile = new File("/sdcard/mysdfile.txt");
			FileInputStream fIn = new FileInputStream(myFile);
			BufferedReader myReader = new BufferedReader(
					new InputStreamReader(fIn));
			String aDataRow = "";
			String aBuffer = "";
			while ((aDataRow = myReader.readLine()) != null) {
				aBuffer += aDataRow + "\n";
			}
			txtData.setText(aBuffer);
			myReader.close();
			Toast.makeText(getBaseContext(),
					"Done reading SD 'mysdfile.txt'",
					Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			Toast.makeText(getBaseContext(), e.getMessage(),
					Toast.LENGTH_SHORT).show();
		}
		}// onClick
		}); // btnReadSDFile

		btnClearScreen = (Button) findViewById(R.id.btnClearScreen);
		btnClearScreen.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// clear text box
				txtData.setText("");
			}
		}); // btnClearScreen

		btnClose = (Button) findViewById(R.id.btnClose);
		btnClose.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// clear text box
				finish();
			}
		}); // btnClose

	}// onCreate

}// AndSDcard