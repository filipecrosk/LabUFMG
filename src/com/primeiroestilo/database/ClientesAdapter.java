package com.primeiroestilo.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ClientesAdapter {
	private Context context;
	public SQLiteDatabase database;
	private DatabaseHelper dbHelper;
	
	public ClientesAdapter(Context context) {
		this.context = context;
	}
	
	public ClientesAdapter open() throws SQLException {
		dbHelper = new DatabaseHelper(context);
		database = dbHelper.getWritableDatabase();
		return this;
	}
	
	public void close() {
		dbHelper.close();
	}
	
	/**
	 * Retorna todos os clientes
	 * @return
	 */
	public Cursor fetchAllClientes() {
		return database.rawQuery("SELECT _id, proprietario, propriedade, numero_ordenhas, strftime('%d/%m/%Y', data_controle) AS the_data  FROM propriedades ORDER BY proprietario ASC", null);
	}
	

	/**
	 * Retorna clientes pela Letra.
	 * @return
	 */
	public Cursor ClientesFetchByLetra(String Letra) {
		return database.rawQuery("SELECT _id, proprietario, propriedade, numero_ordenhas, strftime('%d/%m/%Y', data_controle) AS the_data  FROM propriedades WHERE proprietario LIKE '"+ Letra +"%' ORDER BY proprietario ASC", null);
	}
	

	
	/**
	 * Retorna clientes pela Busca.
	 * @return
	 */
	public Cursor ClientesFetchBusca(String busca) {
		return database.rawQuery("SELECT _id, proprietario, propriedade, numero_ordenhas, strftime('%d/%m/%Y', data_controle) AS the_data  FROM propriedades " +
				" WHERE proprietario LIKE '%"+ busca +"%' OR _id LIKE '%"+ busca +"%'  ORDER BY proprietario ASC", null);
	}
	
	/**
	 * Retrieves the details of a specific cliente.
	 * @return
	 */
	public Cursor ClienteFindById(int id) {
		Cursor myCursor = database.rawQuery("SELECT _id, proprietario, propriedade, numero_ordenhas, strftime('%d/%m/%Y', data_controle) AS the_data  FROM propriedades WHERE _id = '"+ id +"'", null);
		
		if (myCursor != null) {
			myCursor.moveToFirst();
		}
		
		return myCursor;
	}
}