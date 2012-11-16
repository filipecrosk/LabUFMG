package com.primeiroestilo.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class UsuariosAdapter {
	private Context context;
	public SQLiteDatabase database;
	private DatabaseHelper dbHelper;
	
	public UsuariosAdapter(Context context) {
		this.context = context;
	}
	
	public UsuariosAdapter open() throws SQLException {
		dbHelper = new DatabaseHelper(context);
		database = dbHelper.getWritableDatabase();
		return this;
	}
	
	public void close() {
		dbHelper.close();
	}
	
	/**
	 * Retorna todos os Usuarios
	 * @return
	 */
	public Cursor fetchAllUsuarios() {
		return database.rawQuery("SELECT id, nome, senha" +
				" FROM usuarios ORDER BY nome ASC", null);
	}

	
	/**
	 * Retorna Usuarios pela Busca.
	 * @return
	 */
	public Cursor UsuariosFetchBusca(String busca) {
		return database.rawQuery("SELECT id, nome, senha" +
				" FROM usuarios WHERE nome LIKE '%"+ busca +"%'  ORDER BY nome ASC", null);
	}
	
	/**
	 * Retrieves the details of a specific Animal.
	 * @return
	 */
	public Cursor UsuarioFindById(int id) {
		Cursor myCursor = database.rawQuery("SELECT id, nome, senha FROM usuarios" +
				" WHERE id = '"+ id +"'", null);
		
		if (myCursor != null) {
			myCursor.moveToFirst();
		}
		
		return myCursor;
	}
}