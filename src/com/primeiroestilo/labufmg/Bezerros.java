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
import com.primeiroestilo.database.BezerrosAdapter;

public class Bezerros extends Activity {
	public static String ListadeProdutos;
	private SimpleCursorAdapter dataSource;
	private BezerrosAdapter dbHelper;
	private EditText filterText = null;
	
	ListView listView;
	Cursor Bezerros;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bezerros);
		final int clienteId = getIntent().getIntExtra("cliente", -1);
		Log.d("id do cliente:", Integer.toString(clienteId));
		
		Button BtMenu = (Button) findViewById(R.id.btMenu);
		BtMenu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), Menu.class);
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
		
		dbHelper = new BezerrosAdapter(this);
        dbHelper.open();
        
        Cursor Bezerros = dbHelper.fetchAllBezerros(clienteId);
		startManagingCursor(Bezerros);
		
    	if (Bezerros.getCount() > 0 ){
            //cria cursor que será exibido na tela, nele serão exibidos 
            //todos os contatos cadastrados
            dataSource = new SimpleCursorAdapter(this, R.layout.row_bezerros, Bezerros, 
            		new String[] {"_id","sexo","tamanho","peso"}, new int[] { R.id.TextNome, R.id.TextSexo, R.id.TextPeso });
           
            //listView.setOnItemClickListener(onItemClick_List);
            
            //relaciona o dataSource ao próprio listview
            listView.setAdapter(dataSource);
            dataSource.setFilterQueryProvider(new FilterQueryProvider() {

                 public Cursor runQuery(CharSequence constraint) {
                    String partialValue = constraint.toString();
                    return dbHelper.BezerrosFetchBusca(clienteId,partialValue);

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