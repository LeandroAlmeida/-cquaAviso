package com.br.AquaAviso;



import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;

public class MenuInicial extends Activity {
	private Button but01,but02,but03, but04;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menuinicial);	
		
		but01 = (Button)findViewById(R.id.buttonMenuVisualizar);
		but01.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MenuInicial.this, Visualizar.class); 
				 startActivity(intent);
			}
		});
		
		but02 = (Button)findViewById(R.id.buttonMenuCadastrar);
		//but02.setLayoutParams((new LayoutParams(but01.getLayoutParams().width, LayoutParams.WRAP_CONTENT)));
		
		but02.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MenuInicial.this, CadastrarRelato.class); 
				startActivity(intent);
				
			}
		});
		
		
		but03 = (Button)findViewById(R.id.buttonMenuSobre);
		but03.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mensagem("Sobre", "Aplicativo desenvolvido para compor a nota da disciplina de Projeto de Interface do PPgSC - UFRN\nDesenvolvedor:\nLEANDRO DE ALMEIDA MELO\nAgradecimentos:\nLEONARDO CUNHA DE MIRANDA\nCÍCERO ALVES DA SILVA\nFLADSON THIAGO OLIVEIRA GOMES\nMARIO ANDRADE VIEIRA DE MELO NETO\nLUCAS DE ALMEIDA MARCIANO\nJUVANE NUNES MARCIANO");
			}
		});
		
		but04 = (Button)findViewById(R.id.buttonMenuConta);
		but04.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//Log.i("MenuInicial","passei aqui na conta button");
				startActivity(new Intent(MenuInicial.this,Conta.class));		
				finish();
			}
		});
	
	}

	
	
public void mensagem(String titulo, String texto){
		
		AlertDialog.Builder msg = new AlertDialog.Builder(MenuInicial.this);
		msg.setTitle(titulo);
		msg.setMessage(texto);
		msg.setNegativeButton("Ok", null);
		msg.show();
	}
}
