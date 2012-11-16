package com.primeiroestilo.labufmg;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import com.primeiroestilo.database.AnimaisAdapter;

public class Animais extends Activity {
	public static String ListadeProdutos;
	private SimpleCursorAdapter dataSource;
	private AnimaisAdapter dbHelper;
	private EditText filterText = null;
	
	ListView listView;
	Cursor Animais;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.animais);
		final int clienteId = getIntent().getIntExtra("cliente", -1);
		Log.d("id do cliente:", Integer.toString(clienteId));
		
		Button BtMenu = (Button) findViewById(R.id.btMenu);
		BtMenu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), Menu.class);
                startActivityForResult(myIntent, 0);
            }
        });
		Button BtBezerros = (Button) findViewById(R.id.btBezerros);
		BtBezerros.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), Bezerros.class);
                myIntent.putExtra("cliente", clienteId);
                startActivityForResult(myIntent, 0);
            }
        });
		Button BtTouros = (Button) findViewById(R.id.btTouros);
		BtTouros.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), Touros.class);
                myIntent.putExtra("cliente", clienteId);
                startActivityForResult(myIntent, 0);
            }
        });
		Button BtEventos = (Button) findViewById(R.id.btEventos);
		BtEventos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), Eventos.class);
                myIntent.putExtra("cliente", clienteId);
                startActivityForResult(myIntent, 0);
            }
        });
		
		filterText = (EditText) findViewById(R.id.search_box);
	    
		listView = (ListView) findViewById(R.id.lista);
		listView.setTextFilterEnabled(true);
		
		dbHelper = new AnimaisAdapter(this);
        dbHelper.open();
        
        Cursor Animais = dbHelper.fetchAllAnimais(clienteId);
		startManagingCursor(Animais);
		
    	if (Animais.getCount() > 0 ){
            //cria cursor que será exibido na tela, nele serão exibidos 
            //todos os contatos cadastrados
            dataSource = new SimpleCursorAdapter(this, R.layout.row_animais, Animais, 
            		new String[] {"_id","data_nascimento","data_parto","_id"}, new int[] { R.id.TextNome, R.id.TextNascimento, R.id.TextParto });
           
            //listView.setOnItemClickListener(onItemClick_List);
            
            //relaciona o dataSource ao próprio listview
            listView.setAdapter(dataSource);
            dataSource.setFilterQueryProvider(new FilterQueryProvider() {

                 public Cursor runQuery(CharSequence constraint) {
                    String partialValue = constraint.toString();
                    return dbHelper.AnimaisFetchBusca(clienteId,partialValue);

                }
            });
            
            /**
             * Enabling Search Filter
             * */
            filterText.addTextChangedListener(new TextWatcher() {
     
                @Override
                public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                    // When user changed the Text
                	 dataSource.getFilter().filter(cs.toString());
                }
     
                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                        int arg3) {
                    // TODO Auto-generated method stub
     
                }
     
                @Override
                public void afterTextChanged(Editable arg0) {
                    // TODO Auto-generated method stub
                }
            });
    	} else{
            Toast.makeText(this, "Nenhum registro encontrado", Toast.LENGTH_SHORT).show(); 
        }
	}
	
	/*OnItemClickListener onItemClick_List = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> l, View view, int position, long index) {
	
			Cursor c = ((SimpleCursorAdapter)l.getAdapter()).getCursor();
			c.moveToPosition(position);
	
		    int cliId = c.getInt(1);
		    String cliProp = c.getString(2);
		    String cliNome = c.getString(0);
		    
		    Intent myIntent = new Intent(view.getContext(), Animais.class);
		    myIntent.putExtra("cliente", cliId);
		    myIntent.putExtra("propriedade", cliProp);
		    myIntent.putExtra("proprietario", cliNome);
		    startActivityForResult(myIntent, 0);
		}
	};*/
    
	@Override
    protected void onDestroy() {
        super.onDestroy();
        //filterText.removeTextChangedListener(filterTextWatcher);
        
        dbHelper.close();
    }
}