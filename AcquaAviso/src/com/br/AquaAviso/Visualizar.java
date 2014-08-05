package com.br.AquaAviso;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Visualizar extends Activity {
	String url= MainActivity.url;
	private Button but01, but02, but03;
	private EditText edt01,edt02;
	String local,estado;
	
	List<String> estados = Arrays.asList("AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PR",
			"PB", "PA", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SE", "SP", "TO", "al", "ap", "am", "ba", "ce", "df", "es", "go", "ma", "mt", "ms", "mg", "pr",
			"pb", "pa", "pe", "pi", "rj", "rn", "rs", "ro", "rr", "sc", "se", "sp", "to"); 
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.visualizar);
		
		but01 = (Button)findViewById(R.id.buttonMenuVizualizarOk);
		but01.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				// TODO Auto-generated method stub
				//setContentView(R.layout.terceira);
				Log.i("Visualizar","A1");
				edt01 = (EditText)findViewById(R.id.editTextCadastrar_Local);
				Log.i("Visualizar","A2");
				edt02 = (EditText)findViewById(R.id.editTextCadastrar_Estado);
				Log.i("Visualizar","A3");
				local=edt01.getText().toString();
				Log.i("Visualizar","A4");
				estado=edt02.getText().toString();
				
				String vazio="";
				 if (estado.equals(""))
				    	vazio+="Campo estado não pode estar vazio\n.";
				 if (estado.equals(""))
						mensagem("Erro.:", ""+vazio);
				 else if(!estados.contains(estado))
					 mensagem("Erro.:", "Digite um estado Válido.\n"+estado+" não existe!");
				  else
				  {
					  String resposta;
					  	try{
							resposta=VerificarLocal(local, estado);
							resposta = resposta.replaceAll("\\s+", "");//remover espaços
							if(resposta.equals("1")){
								local=local.toUpperCase();
								estado=estado.toUpperCase();
								Intent intent = new Intent(Visualizar.this, ExibirRelatos.class);
				                intent.putExtra("local", local);
				                intent.putExtra("estado", estado);
				                startActivity(intent);
				                
								
							}else
								mensagem("Resposta","Não foi encontrado nenhum relato neste local!!!");
							
						}catch(Exception e){
							mensagem("Resposta", "Erro Conexão: "+e.getMessage().toString());
						}
				  }
			}
		});
		
		but02 = (Button)findViewById(R.id.buttonMenuVizualizarLocais);
		but02.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Visualizar.this, Locais.class); 
				startActivity(intent);
			}
		});
		
			
	}
	
	
	
private String VerificarLocal(String local, String estado) {
	String s = "";
	AsyncTask<String,String,String> tread;
	 
	try {
		tread=new Conexao(Visualizar.this).execute(url+"/verificarLocal.php","4","local",local,"estado",estado);
		s = tread.get();
	    Log.i("Visualizar","LOCAL: "+local+" ESTADO: "+estado);
	    Log.i("Visualizar","respostaLogin 2 = "+s);
	} catch (Exception e) {
	    s="0";
	}
	 return s;
	}
	
public void mensagem(String titulo, String texto){
		
		AlertDialog.Builder msg = new AlertDialog.Builder(Visualizar.this);
		msg.setTitle(titulo);
		msg.setMessage(texto);
		msg.setNegativeButton("Ok", null);
		msg.show();
	}
	
}
