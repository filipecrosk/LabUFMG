package com.primeiroestilo.labufmg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu extends Activity {
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
        
        Button bt1 = (Button) findViewById(R.id.btClientes);
        bt1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), Clientes.class);
                startActivityForResult(myIntent, 0);
            }

        });
        
        
        Button bt2 = (Button) findViewById(R.id.btImportar);
        bt2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), Importar.class);
                startActivityForResult(myIntent, 0);
            }

        });
        
        
        Button bt3 = (Button) findViewById(R.id.btExportar);
        bt3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), Exportar.class);
                startActivityForResult(myIntent, 0);
            }

        });
	}
}
