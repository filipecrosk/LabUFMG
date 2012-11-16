package com.primeiroestilo.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class TourosAdapter {
	private Context context;
	public SQLiteDatabase database;
	private DatabaseHelper dbHelper;
	
	public TourosAdapter(Context context) {
		this.context = context;
	}
	
	public TourosAdapter open() throws SQLException {
		dbHelper = new DatabaseHelper(context);
		database = dbHelper.getWritableDatabase();
		return this;
	}
	
	public void close() {
		dbHelper.close();
	}
	
	/**
	 * Retorna todos os Touros
	 * @return
	 */
	public Cursor fetchAllTouros(int cliente) {
		return database.rawQuery("SELECT _id, cliente, propriedade, nome" +
				" FROM touros WHERE cliente='"+ cliente +"' ORDER BY nome ASC", null);
	}

	
	/**
	 * Retorna Touros pela Busca.
	 * @return
	 */
	public Cursor TourosFetchBusca(int cliente, String busca) {
		return database.rawQuery("SELECT _id, cliente, propriedade, nome" +
				" FROM touros WHERE cliente='"+ cliente +"' AND nome LIKE '%"+ busca +"%'  ORDER BY nome ASC", null);
	}
	
	/**
	 * Retrieves the details of a specific Animal.
	 * @return
	 */
	public Cursor TouroFindById(int cliente, int id) {
		Cursor myCursor = database.rawQuery("SELECT _id, cliente, propriedade, nome FROM touros" +
				" WHERE cliente='"+ cliente +"' AND  _id = '"+ id +"'", null);
		
		if (myCursor != null) {
			myCursor.moveToFirst();
		}
		
		return myCursor;
	}
}