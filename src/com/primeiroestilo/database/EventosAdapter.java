package com.primeiroestilo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class EventosAdapter {
	private Context context;
	public SQLiteDatabase database;
	private DatabaseHelper dbHelper;
	
	public EventosAdapter(Context context) {
		this.context = context;
	}
	
	public EventosAdapter open() throws SQLException {
		dbHelper = new DatabaseHelper(context);
		database = dbHelper.getWritableDatabase();
		return this;
	}
	
	public void close() {
		dbHelper.close();
	}
	
	/**
	 * Retorna todos os Eventos
	 * @return
	 */
	public Cursor fetchAllEventos(int cliente) {
		return database.rawQuery("SELECT _id, cliente, propriedade, animal, cod_evento1, strftime('%d/%m/%Y', data) AS data_1, cod_evento2, strftime('%d/%m/%Y', data2) AS data_2" +
				" FROM eventos WHERE cliente='"+ cliente +"' ORDER BY data DESC", null);
	}
	

	/**
	 * Retorna Eventos pelo Animal.
	 * @return
	 */
	public Cursor EventosFetchByAnimal(int cliente, String Animal) {
		return database.rawQuery("SELECT _id, cliente, propriedade, animal, cod_evento1, strftime('%d/%m/%Y', data) AS data_1, cod_evento2, strftime('%d/%m/%Y', data2) AS data_2" +
				" FROM eventos WHERE cliente='"+ cliente +"' AND animal = '"+ Animal +"' ORDER BY data DESC", null);
	}
	

	
	/**
	 * Retorna Eventos pelo COD EVENTO.
	 * @return
	 */
	public Cursor EventosFetchBusca(int cliente, String codigo) {
		return database.rawQuery("SELECT _id, cliente, propriedade, animal, cod_evento1, strftime('%d/%m/%Y', data) AS data_1, cod_evento2, strftime('%d/%m/%Y', data2) AS data_2" +
				" FROM eventos WHERE cliente='"+ cliente +"' AND ( cod_evento1 = '"+ codigo +"' OR cod_evento2 = '"+ codigo +"')  ORDER BY data DESC", null);
	}
	
	/**
	 * Retrieves the details of a specific Evento.
	 * @return
	 */
	public Cursor EventoFindById(int cliente, int id) {
		Cursor myCursor = database.rawQuery("SELECT _id, cliente, propriedade, animal, cod_evento1, strftime('%d/%m/%Y', data) AS data_1, cod_evento2, strftime('%d/%m/%Y', data2) AS data_2  FROM eventos" +
				" WHERE cliente='"+ cliente +"' AND  _id = '"+ id +"'", null);
		
		if (myCursor != null) {
			myCursor.moveToFirst();
		}
		
		return myCursor;
	}
	
	
	/**
	 * Creates the user name and password.
	 * 
	 * @param username The username.
	 * @param password The password.
	 * @return
	 */
	public long create(String nome, String cnpj) {
		ContentValues initialValues = createUserTableContentValues(nome, cnpj);
		return database.insert("eventos", null, initialValues);
	}
	
	/**
	 * Stores the username and password upon creation of new login details.
	 * @param username The user name.
	 * @param password The password.
	 * @return The entered values. 
	 */
	private ContentValues createUserTableContentValues(String nome, String cnpj) {
		ContentValues values = new ContentValues();
		values.put("animal", nome);
		values.put("cod_evento1", cnpj);
		return values;
	}
}