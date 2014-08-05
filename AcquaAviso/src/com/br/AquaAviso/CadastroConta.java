package com.br.AquaAviso;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CadastroConta extends Activity {
	EditText edt01,edt02,edt03,edt04;
	Button bt01,bt02;
	ContaAtiva conta=MainActivity.usuario;
	String url=MainActivity.url;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cadastroconta);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		 
		bt01=(Button)findViewById(R.id.buttonContaCriar);
		bt01.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				edt01=(EditText)findViewById(R.id.editTextContaNome);
				edt02=(EditText)findViewById(R.id.editTextContaEmail);
				edt03=(EditText)findViewById(R.id.editTextContaSenha);
				edt04=(EditText)findViewById(R.id.editTextContaRepitaSenha);
				String nome,email,senha,repitasenha;
				nome=edt01.getText().toString();
				email=edt02.getText().toString();
				senha=edt03.getText().toString();
				repitasenha=edt04.getText().toString();
				
				String resposta="";
				if(nome.equals(""))
					resposta+="Nome em branco.\n";
				if(email.equals(""))
					resposta+="email em branco\n";
				if(senha.equals("") || repitasenha.equals(""))
					resposta+="Senha em branco\n";
				if(!senha.equals(repitasenha))
					resposta+="Senhas diferentes, digite a mesma senha nos dois campos";
				if(resposta.equals("")){
					resposta="";
					resposta =verificarNomeDisponivel(nome);
					resposta = resposta.replaceAll("\\s+", "");
					if(resposta.equals("2")){//só aqui eu usei 2
							resposta="";
							try{
									resposta =cadastrarContaUsuario(nome,email,senha);
									resposta = resposta.replaceAll("\\s+", "");
									if(resposta.equals("1")){
											
										 AlertDialog.Builder confirmarCadastro = new AlertDialog.Builder(CadastroConta.this);
										 confirmarCadastro.setTitle("Cadastro");
										 confirmarCadastro.setMessage("Conta criada com sucesso!! ");
										 confirmarCadastro.setPositiveButton("Ok", new DialogInterface.OnClickListener() {		
											public void onClick(DialogInterface dialog, int which) {
												finish();
											}
										 });
										 confirmarCadastro.show();
									}else if(resposta.equals("-1"))
										mensagem("Resposta", "Servidor temporariamente fora do ar, tente mais tarde!");
									else
										mensagem("Resposta", "Erro ao cadastrar, tente mais tarde!");
							}catch(Exception e){
								mensagem("Resposta", "Erro Conexão: "+e.getMessage().toString());
							}
					}else
						mensagem("Resposta", "Nome de usuário não desponível, escolha outro!");
				}	
				else
					mensagem("Erro", "Corriga os erro:\n"+resposta);
					
			}
			
		});
		
		
		
	}
	
private String cadastrarContaUsuario(String nome, String email, String senha) {
		String s = "";
		AsyncTask<String,String,String> tread;
		try {
			tread=new Conexao(CadastroConta.this).execute(url+"/gravarConta.php","6","nome",nome,"email",email,"senha",senha);
			// tread = new RequestTask().execute(uri);
		    s = tread.get();
		    Log.i("MainActivity","respostaLogin 2 = "+s);
		} catch (Exception e) {
		    s="0";
		}
		return s;
	}
	
private String verificarNomeDisponivel(String nome) {
	String s = "";
	AsyncTask<String,String,String> tread;
	try {
		tread=new Conexao(CadastroConta.this).execute(url+"/verificarNomeDisponivel.php","2","nome",nome);
		// tread = new RequestTask().execute(uri);
	    s = tread.get();
	    Log.i("MainActivity","respostaLogin 2 = "+s);
	} catch (Exception e) {
	    s="0";
	}
	return s;
}

public void mensagem(String titulo, String texto){
		
		AlertDialog.Builder msg = new AlertDialog.Builder(CadastroConta.this);
		msg.setTitle(titulo);
		msg.setMessage(texto);
		msg.setNegativeButton("Ok", null);
		msg.show();
	}
}
