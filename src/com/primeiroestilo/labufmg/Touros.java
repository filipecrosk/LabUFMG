package com.primeiroestilo.labufmg;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import com.primeiroestilo.database.TourosAdapter;

public class Touros extends Activity {
	public static String ListadeProdutos;
	private SimpleCursorAdapter dataSource;
	private TourosAdapter dbHelper;
	private EditText filterText = null;
	
	ListView listView;
	Cursor Touros;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.touros);
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
		Button BtAnimais = (Button) findViewById(R.id.btAnimais);
		BtAnimais.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), Animais.class);
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
		
		dbHelper = new TourosAdapter(this);
        dbHelper.open();
        
        Cursor Touros = dbHelper.fetchAllTouros(clienteId);
		startManagingCursor(Touros);
		
    	if (Touros.getCount() > 0 ){
            //cria cursor que será exibido na tela, nele serão exibidos 
            //todos os contatos cadastrados
            dataSource = new SimpleCursorAdapter(this, R.layout.row_touros, Touros, 
            		new String[] {"_id","nome","_id"}, new int[] { R.id.TextId, R.id.TextNome });
           
            //listView.setOnItemClickListener(onItemClick_List);
            
            //relaciona o dataSource ao próprio listview
            listView.setAdapter(dataSource);
            dataSource.setFilterQueryProvider(new FilterQueryProvider() {

                 public Cursor runQuery(CharSequence constraint) {
                    String partialValue = constraint.toString();
                    return dbHelper.TourosFetchBusca(clienteId,partialValue);

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

    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //filterText.removeTextChangedListener(filterTextWatcher);
    }
}