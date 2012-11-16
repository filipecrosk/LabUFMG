package com.primeiroestilo.labufmg;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.primeiroestilo.database.AnimaisAdapter;
import com.primeiroestilo.database.TourosAdapter;
import com.primeiroestilo.database.EventosAdapter;

public class AddEvento extends Activity {
	private AnimaisAdapter dbHelper;
	private TourosAdapter dbTouros;
	private EventosAdapter dbEventos;
	private AutoCompleteTextView AutBusca, TourosBusca;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_eventos);
		final int clienteId = getIntent().getIntExtra("cliente", -1);
		Log.d("id do cliente:", Integer.toString(clienteId));
		
		//Cancelar o cadastro e voltar
		Button BtCancel = (Button) findViewById(R.id.btCancelar);
		BtCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	dbHelper.close();
            	dbTouros.close();
                finish();
            }
        });
		
		/******* autocompletar com nomes de produtos ***************/
		dbHelper = new AnimaisAdapter(this);
		dbHelper.open();
		
		AutBusca = (AutoCompleteTextView) findViewById(R.id.NumAnimal);
		
		List<String> list = new ArrayList<String>();
	    Cursor autocompletar = dbHelper.fetchAllAnimais(clienteId);	
	    if (autocompletar.getCount() > 0){
	    	if  (autocompletar.moveToFirst()) {
	    		do {
	    			list.add(autocompletar.getString(0));
	    		}while (autocompletar.moveToNext());
	    	}
	    }
	    	
	    ArrayAdapter<String> Autadapter = new ArrayAdapter<String>(this,R.layout.autocomplete_item, list);
	    	
	    AutBusca.setThreshold(3);
	    AutBusca.setAdapter(Autadapter);
		
		AutBusca.setOnKeyListener(new OnKeyListener()
	    {
	        public boolean onKey(View v, int keyCode, KeyEvent event)
	        {
	            if ((event.getAction() == KeyEvent.ACTION_DOWN)
	                    && (keyCode == KeyEvent.KEYCODE_ENTER))
	            {
	            	String NumAnimal = AutBusca.getText().toString();
	            	//showToast( NumAnimal );
	            	
	                return true;
	            }
	            return false;
	        }
	    });
		
		/******* autocompletar com nomes de produtos ***************/
		
		/******* autocompletar com nomes de touros ***************/
		dbTouros = new TourosAdapter(this);
		dbTouros.open();
		
		TourosBusca = (AutoCompleteTextView) findViewById(R.id.Touro);
		
		List<String> listTouros = new ArrayList<String>();
	    Cursor autocompletarTouros = dbTouros.fetchAllTouros(clienteId);	
	    if (autocompletarTouros.getCount() > 0){
	    	if  (autocompletarTouros.moveToFirst()) {
	    		do {
	    			listTouros.add(autocompletarTouros.getString(0));
	    		}while (autocompletarTouros.moveToNext());
	    	}
	    }
	    	
	    ArrayAdapter<String> TourosAdapter = new ArrayAdapter<String>(this,R.layout.autocomplete_item, listTouros);
	    	
	    TourosBusca.setThreshold(3);
	    TourosBusca.setAdapter(TourosAdapter);
		
		TourosBusca.setOnKeyListener(new OnKeyListener()
	    {
	        public boolean onKey(View v, int keyCode, KeyEvent event)
	        {
	            if ((event.getAction() == KeyEvent.ACTION_DOWN)
	                    && (keyCode == KeyEvent.KEYCODE_ENTER))
	            {
	            	String NumAnimal = TourosBusca.getText().toString();
	            	//showToast( NumAnimal );
	            	
	                return true;
	            }
	            return false;
	        }
	    });
		
		/******* autocompletar com nomes de touros ***************/
		
		
		/****** spinner codigo de lactacao *******/
		// Array of choices
		String colors[] = {"11-aborto","12-amamentando","13-cetose","14-cio","15-corpo estranho", "16-deslocamento de abomaso","17-diarréia","18-em exposição","19-febre do leite","20-ferimento do úbere","21-lactação induzida","22-mastite","23-metrite",
				"24-nervosismo","25-ordenha perdida","26-outros ferimentos","27-outros problemas de saúde","28-podridão no casco","29-amostra de leite perdida","-30recém parida","31-sem apetite","32-timpanismo","33-tratado com ocitocina",
				"40-baixa produção","41-baixa gordura","42-causa desconhecida","43-deslocamento de abomaso","44-esgotamento do úbere e problemas na ordenha","45-febre do leite","46-ferimento","47-idade avançada","48-mastite","49-ordenha lenta","50-outras causas",
				"60-choque elétrico, raio","61-deslocamento do abomaso","62-doença","63-intoxicação",
				"70-para rebanho dentro do estado","71-para rebanho fora do estado","72-em leilão"};
		//falta termina do 51 em diante, do 63 em diante
		
		// Selection of the spinner
		Spinner spinner = (Spinner) findViewById(R.id.SpinLactacao);

		// Application of the Array to the Spinner
		ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, colors);
		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down vieww
		spinner.setAdapter(spinnerArrayAdapter);
		
		/****** spinner codigo de EVENTO *******/
		// Array of choices
		String eventos[] = {"1-Em lactação","2-Parto","3-Secagem","4-Cobertura","5-Descarte", "8-Aborto"};
		
		Spinner spinEvento = (Spinner) findViewById(R.id.SpinEvento);

		ArrayAdapter<String> spinnerArrayEvento = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, eventos);
		spinnerArrayEvento.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down vieww
		spinEvento.setAdapter(spinnerArrayEvento);
		
		//******************************************************************/
		//Salvar os itens
		//******************************************************************/
		final String thisNome = AutBusca.getText().toString();
    	final String thisCnpj = spinEvento.getSelectedItem().toString();
		
		Button BtSalvar = (Button) findViewById(R.id.btSalvar);
		BtSalvar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	
            	
            	//add
    			/*long id = dbEventos.create(thisNome, thisCnpj);
    			if (id > 0) {
	    			Toast.makeText(getApplicationContext(), "Evento cadastrado com sucesso.",
	    			          Toast.LENGTH_SHORT).show();
	    			dbHelper.close();
	    			finish();
	    		} else {
	    			Toast.makeText(getApplicationContext(), "Falha no cadastro.",
	    			          Toast.LENGTH_SHORT).show();
	    		}*/
            	Toast.makeText(getApplicationContext(), "Evento cadastrado.",
  			          Toast.LENGTH_SHORT).show();
    			
            	dbHelper.close();
            	dbTouros.close();
                finish();
            }
        });
	}
	
	//método executado quando o usuário clica no botão voltar do aparelho
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        //fecha a conexão com o Banco de dados
        dbTouros.close();
        dbHelper.close();        
    }
}
