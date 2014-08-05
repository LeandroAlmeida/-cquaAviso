package com.br.AquaAviso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

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
import android.widget.CheckBox;
import android.widget.EditText;

public class MainActivity extends Activity {
	public static ContaAtiva usuario=new ContaAtiva();
	private String nome, senha;
	private Button but01,but02;
	private EditText edt01, edt02;
	private CheckBox check;
	String resposta;
	public static final String url="http://aquaaviso.webege.com/aquaaviso";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		File arquivoLido = getFileStreamPath("dados.txt");
			if (arquivoLido.exists()) {
					lerUsuarioDoArquivo();
					startActivity(new Intent(MainActivity.this, MenuInicial.class));
					finish();
			} else{
					setContentView(R.layout.login);
					but01 = (Button)findViewById(R.id.butaoMenuInicial_Continuar);//mapeamento java xml
					but01.setOnClickListener(new View.OnClickListener() {
					@Override
						public void onClick(View v) {
								edt01 = (EditText)findViewById(R.id.editTextMenuInicial_Nome);
								edt02 = (EditText)findViewById(R.id.editTextMenuInicialSenha_Usuario);
								nome=edt01.getText().toString();
								senha=edt02.getText().toString();
								String vazio="";
								 if (nome.equals("")) 
								    	vazio="Nome de usuário não foi informado.\n";
								 if (senha.equals(""))
								    	vazio+="Snha não foi informado.\n.";
								 if (nome.equals("") || senha.equals(""))
										mensagem("Erro.:", ""+vazio);
								 else{
									 try{
										 	resposta=verificarLoginExiste(nome,senha);
										 	
											//Log.i("MainActivity","respostaLogin = "+resposta);
											resposta = resposta.replaceAll("\\s+", "");//remover espaços
											if(resposta.equals("1")){//login deu certo
																	check = (CheckBox)findViewById(R.id.checkBoxMenu_Inicial);
																	if(check.isChecked()){//lembrar está selecionado
																						resposta=consultarUsuario(nome,senha);
																						resposta = resposta.replaceAll("\\s+", "");//remover espaços
																						FileOutputStream arquivoGravar = openFileOutput("dados.txt", MODE_PRIVATE);
																						arquivoGravar.write(resposta.getBytes());
																						arquivoGravar.close();
																						lerUsuarioDoArquivo();
																						Log.i("MainActivity","dados gravados user = "+usuario.toString());
																						startActivity(new Intent(MainActivity.this, MenuInicial.class));
																						finish();
																	}else{//lembrar não esta seleconado
																			StringTokenizer st; 
																			resposta=consultarUsuario(nome,senha);
																			resposta = resposta.replaceAll("\\s+", "");//remover espaços
																			st = new StringTokenizer(resposta, "#");
																			int cont =0;
																			while (st.hasMoreTokens()) {
																				if(cont==0)
																					usuario.setId(st.nextToken());
																				else if(cont==1)
																					usuario.setNome(st.nextToken());
																				else if(cont==2)
																					usuario.setEmail(st.nextToken());
																				else if(cont==3)
																					usuario.setSenha(st.nextToken());
																				cont++;
																			}
																		   // tokens.add(st.nextToken());  
																		
																		startActivity(new Intent(MainActivity.this, MenuInicial.class));
																		finish();
																	}
											
											}else if (resposta.equals("-1"))//não logou colocar -1 depois
												mensagem("Resposta", "Servidor temporariamente fora do ar, tente mais tarde!");
											else
												mensagem("Resposta", "Nome de usuario ou senha errada, tente de novo!!!");
										}catch(Exception e){
												mensagem("Resposta", "Erro Conexão: "+e.getMessage().toString());
										}
								
								 }
							
							}
					
					});
					but02 = (Button)findViewById(R.id.butaoMenuInicialNovo_Usuario);
					but02.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							startActivity(new Intent(MainActivity.this, CadastroConta.class));					
						}
					});
				}
		
		
	}
	
	
private String consultarUsuario(String nome, String senha) {//VER DEPOIS
	String s = "";
	AsyncTask<String,String,String> tread;
	try {
		tread=new Conexao(MainActivity.this).execute(url+"/consultarUsuario.php","4", "nome",nome,"senha",senha);
	   // tread = new RequestTask().execute(uri);
	    s = tread.get();
	    Log.i("MainActivity","respostaLogin 2 = "+s);
	} catch (Exception e) {
	    s="0";
	}
	return s;
	}	
	
	
	
private String verificarLoginExiste(String nome, String senha) {
	String s = "";
	AsyncTask<String,String,String> tread;
	try {
		tread=new Conexao(MainActivity.this).execute(url+"/logar.php","4","nome",nome,"senha",senha);
	    s = tread.get();
	    Log.i("MainActivity","respostaLogin 2 = "+s);
	} catch (Exception e) {
	    s="0";
	}
	 return s;
	}
	
public void mensagem(String titulo, String texto){
		
		AlertDialog.Builder msg = new AlertDialog.Builder(MainActivity.this);
		msg.setTitle(titulo);
		msg.setMessage(texto);
		msg.setNegativeButton("Ok", null);
		msg.show();
	}

public void lerUsuarioDoArquivo(){
		StringTokenizer st; 
		try {
			FileInputStream arquivoLi = openFileInput("dados.txt");
			int tamanhoArquivo = arquivoLi.available();
			byte dadosBytesLidos[] = new byte[tamanhoArquivo];
			arquivoLi.read(dadosBytesLidos);
			String textLido = new String(dadosBytesLidos);
			st = new StringTokenizer(textLido, "#");
			int cont=0;
			while (st.hasMoreTokens()) {
				if(cont==0)
					usuario.setId(st.nextToken());
				else if(cont==1)
					usuario.setNome(st.nextToken());
				else if(cont==2)
					usuario.setEmail(st.nextToken());
				else if(cont==3)
					usuario.setSenha(st.nextToken());
				cont++;
			   // tokens.add(st.nextToken());  
			}
			arquivoLi.close();		
		} catch (FileNotFoundException erro) {
			mensagem("Arquivo não encontrado", "" + erro);
		} catch (IOException erro) {
			mensagem("Erro de entrada e Saida", "" + erro);
	}

		
}


}
