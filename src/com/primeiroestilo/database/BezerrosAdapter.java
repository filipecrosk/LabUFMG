package com.primeiroestilo.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class BezerrosAdapter {
	private Context context;
	public SQLiteDatabase database;
	private DatabaseHelper dbHelper;
	
	public BezerrosAdapter(Context context) {
		this.context = context;
	}
	
	public BezerrosAdapter open() throws SQLException {
		dbHelper = new DatabaseHelper(context);
		database = dbHelper.getWritableDatabase();
		return this;
	}
	
	public void close() {
		dbHelper.close();
	}
	
	/**
	 * Retorna todos os Bezerros
	 * @return
	 */
	public Cursor fetchAllBezerros(int cliente) {
		return database.rawQuery("SELECT _id, cliente, propriedade, sexo, tamanho, facilidade, condicao, peso" +
				" FROM bezerros WHERE cliente='"+ cliente +"' ORDER BY _id DESC", null);
	}

	
	/**
	 * Retorna Bezerros pela Busca.
	 * @return
	 */
	public Cursor BezerrosFetchBusca(int cliente, String busca) {
		return database.rawQuery("SELECT _id, cliente, propriedade, sexo, tamanho, facilidade, condicao, peso" +
				" FROM bezerros WHERE cliente='"+ cliente +"' AND sexo LIKE '%"+ busca +"%'  ORDER BY _id DESC", null);
	}
	
	/**
	 * Retrieves the details of a specific Animal.
	 * @return
	 */
	public Cursor BezerroFindById(int cliente, int id) {
		Cursor myCursor = database.rawQuery("SELECT _id, cliente, propriedade, sexo, tamanho, facilidade, condicao, peso FROM bezerros" +
				" WHERE cliente='"+ cliente +"' AND  _id = '"+ id +"'", null);
		
		if (myCursor != null) {
			myCursor.moveToFirst();
		}
		
		return myCursor;
	}
}