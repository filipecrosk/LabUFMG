package com.primeiroestilo.database;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/**
 * This class creates the relation with the SQLite Database Helper
 * through which queries can be SQL called. 		
 * @author Andrei
 *
 */
public class DatabaseHelper extends SQLiteOpenHelper {
	// The database name and version
	private static final int DB_VERSION = 1;

	//The Android's default system path of your application database.
    private static String DB_PATH = "/data/data/com.primeiroestilo.labufmg/databases/";
    private static String DB_NAME = "labufmg.sqlite";
    private SQLiteDatabase myDataBase;
    private final Context myContext;
 
	/**
	 * Database Helper constructor. 
	 * @param context
	 */
	public DatabaseHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		this.myContext = context;
		
		 boolean dbexist = checkDataBase();
		 if(dbexist)
		 {
			 //System.out.println("Database exists");
			 openDataBase(); 
		 }
		 else
		 {
		     System.out.println("Database doesn't exist");
		     try {
				createDataBase();
			} catch (IOException e) {
				throw new Error("Error copying database");
			}
		 }

	}
	/**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase() throws IOException{
    	boolean dbExist = checkDataBase();
    	if(dbExist){
    		//do nothing - database already exist
    	}else{
    		//By calling this method and empty database will be created into the default system path
               //of your application so we are gonna be able to overwrite that database with our database.
        	this.getReadableDatabase();
        	try {
    			copyDataBase();
    		} catch (IOException e) {
        		throw new Error("Error copying database");
        	}
    	}
    }
	
    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){
    	//SQLiteDatabase checkDB = null;
    	boolean checkDB = false;
    	try{
    		String myPath = DB_PATH + DB_NAME;
    		//checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    		File dbfile = new File(myPath);
    	    checkDB = dbfile.exists();
    	}catch(SQLiteException e){
    		//database does't exist yet.
    	}
 
    	/*if(checkDB != null){
    		checkDB.close();
    	}
 
    	return checkDB != null ? true : false;*/
    	return checkDB;
    }
 
    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{
 
    	//Open your local db as the input stream
    	InputStream myInput = myContext.getAssets().open(DB_NAME);
 
    	// Path to the just created empty db
    	String outFileName = DB_PATH + DB_NAME;
 
    	//Open the empty db as the output stream
    	OutputStream myOutput = new FileOutputStream(outFileName);
 
    	//transfer bytes from the inputfile to the outputfile
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}
 
    	//Close the streams
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();
 
    }
 
    public void openDataBase() throws SQLException{
    	//Open the database
        String myPath = DB_PATH + DB_NAME;
    	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
 
    }
 
    @Override
	public synchronized void close() {
 
    	    if(myDataBase != null)
    		    myDataBase.close();
    	    super.close();
 
	}
 
	@Override
	public void onCreate(SQLiteDatabase db) {
 
	}
 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (newVersion > oldVersion) {
		    db.beginTransaction();
		 
		    boolean success = true;
		    for (int i = oldVersion ; i < newVersion ; ++i) {
		      int nextVersion = i + 1;
		      switch (nextVersion) {
		      	case 2:
		          success = upgradeToVersion15(db);
		          break;
		      }
		 
		      if (!success) {
		        break;
		      }
		    }
		 
		    if (success) {
		      db.setTransactionSuccessful();
		    }
		    db.endTransaction();
		  } else {
		    //clearDatabase(db)
		    onCreate(db);
		  }
	}
	
	public boolean upgradeToVersion4(SQLiteDatabase db){
		try {
			//db.execSQL("CREATE TABLE android_metadata (_id INTEGER PRIMARY KEY AUTOINCREMENT, data_atu DATE)");
			//db.execSQL("INSERT INTO android_metadata (_id,data_atu) VALUES (1,datetime('NOW'))");
			return true;
		} catch (SQLiteException e) {
			return false;
		}
	}
	
	public boolean upgradeToVersion15(SQLiteDatabase db){
		try {
			//myContext.deleteDatabase(DB_NAME);
			//onCreate(db);
			//db.execSQL("CREATE TABLE android_metadata (_id INTEGER PRIMARY KEY AUTOINCREMENT, data_atu DATE)");
			//db.execSQL("UPDATE android_metadata SET data_atu=datetime('NOW')");
			//db.execSQL("ALTER TABLE produtos ADD COLUMN nome2 TEXT");
			Log.d("Atualizacao", "novos itens inseridos");
			return true;
		} catch (SQLiteException e) {
			return false;
		}
	}
 
    // Add your public helper methods to access and get content from the database.
    // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
    // to you to create adapters for your views.    
}
