package com.primeiroestilo.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class AnimaisAdapter {
	private Context context;
	public SQLiteDatabase database;
	private DatabaseHelper dbHelper;
	
	public AnimaisAdapter(Context context) {
		this.context = context;
	}
	
	public AnimaisAdapter open() throws SQLException {
		dbHelper = new DatabaseHelper(context);
		database = dbHelper.getWritableDatabase();
		return this;
	}
	
	public void close() {
		dbHelper.close();
	}
	
	/**
	 * Retorna todos os Animais
	 * @return
	 */
	public Cursor fetchAllAnimais(int cliente) {
		return database.rawQuery("SELECT _id, cliente, propriedade, strftime('%d/%m/%Y', nascimento) AS data_nascimento, strftime('%d/%m/%Y', ultimo_parto) AS data_parto, ordem_entrada, apelido, leite_primeira, leite_segunda, leite_terceira, ocorrencia, lote" +
				" FROM animais WHERE cliente='"+ cliente +"' ORDER BY nascimento DESC", null);
	}
	

	/**
	 * Retorna Animais pela Letra.
	 * @return
	 */
	public Cursor AnimaisFetchByLetra(int cliente, String Letra) {
		return database.rawQuery("SELECT _id, cliente, propriedade, strftime('%d/%m/%Y', nascimento) AS data_nascimento, strftime('%d/%m/%Y', ultimo_parto) AS data_parto, ordem_entrada, apelido, leite_primeira, leite_segunda, leite_terceira, ocorrencia, lote" +
				" FROM animais WHERE cliente='"+ cliente +"' AND apelido LIKE '"+ Letra +"%' ORDER BY nascimento DESC", null);
	}
	

	
	/**
	 * Retorna Animais pela Busca.
	 * @return
	 */
	public Cursor AnimaisFetchBusca(int cliente, String busca) {
		return database.rawQuery("SELECT _id, cliente, propriedade, strftime('%d/%m/%Y', nascimento) AS data_nascimento, strftime('%d/%m/%Y', ultimo_parto) AS data_parto, ordem_entrada, apelido, leite_primeira, leite_segunda, leite_terceira, ocorrencia, lote" +
				" FROM animais WHERE cliente='"+ cliente +"' AND apelido LIKE '%"+ busca +"%'  ORDER BY nascimento DESC", null);
	}
	
	/**
	 * Retrieves the details of a specific Animal.
	 * @return
	 */
	public Cursor AnimalFindById(int cliente, int id) {
		Cursor myCursor = database.rawQuery("SELECT _id, cliente, propriedade, strftime('%d/%m/%Y', nascimento) AS data_nascimento, strftime('%d/%m/%Y', ultimo_parto) AS data_parto, ordem_entrada, apelido, leite_primeira, leite_segunda, leite_terceira, ocorrencia, lote  FROM animais" +
				" WHERE cliente='"+ cliente +"' AND  reb = '"+ id +"'", null);
		
		if (myCursor != null) {
			myCursor.moveToFirst();
		}
		
		return myCursor;
	}
}