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
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class CopyOfMainActivity extends Activity {
	public static ContaAtiva usuario=new ContaAtiva();
	private String nome, senha;
	private Button but01,but02;
	private EditText edt01, edt02;
	private CheckBox check;
	public static final String url="http://aquaaviso.besaba.com/aquaaviso";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		File arquivoLido = getFileStreamPath("dados.txt");
		Log.i("MainActivity","resposta passei aqui 1");
			if (arquivoLido.exists()) {
					lerUsuarioDoArquivo();
					//Log.i("MainActivity","dados gravados user = "+usuario.toString());
					startActivity(new Intent(CopyOfMainActivity.this, MenuInicial.class));
					finish();
			} else{
					setContentView(R.layout.login);
					//Log.i("MainActivity","resposta passei aqui 2");
					but01 = (Button)findViewById(R.id.butaoMenuInicial_Continuar);//mapeamento java xml
					but01.setOnClickListener(new View.OnClickListener() {
					@Override
						public void onClick(View v) {
								//Log.i("MainActivity","resposta passei aqui 3 oncliecd");
								edt01 = (EditText)findViewById(R.id.editTextMenuInicial_Nome);
								//Log.i("MainActivity","resposta passei aqui 3 oncliecd");
								edt02 = (EditText)findViewById(R.id.editTextMenuInicialSenha_Usuario);
								//Log.i("MainActivity","resposta passei aqui 3 oncliecd");
								nome=edt01.getText().toString();
								senha=edt02.getText().toString();
								//Log.i("MainActivity","resposta passei aqui 3 oncliecd");
								String vazio="";
								 if (nome.equals("")) 
								    	vazio="Campo nome não pode estar vazio.\n";
								 if (senha.equals(""))
								    	vazio+="Campo senha não pode estar vazio\n.";
								 if (nome.equals("") || senha.equals(""))
										mensagem("Erro.:", ""+vazio);
								 else{
									 Log.i("MainActivity","resposta passei aqui 5 oncliecd");
										ArrayList<NameValuePair> parametrosPost=new ArrayList<NameValuePair>();
										parametrosPost.add(new BasicNameValuePair("nome",nome)); 
										parametrosPost.add(new BasicNameValuePair("senha",senha));
										String resposta;
										// Log.i("MainActivity","resposta passei aqui 6 oncliecd");
										try{
											Log.i("MainActivity","resposta passei aqui 7 oncliecd");
											resposta=ConexaoHttpClient.executarHttpPost(url+"/logar.php",parametrosPost).toString();
											//resposta=ConexaoHttpClient.executarHttpGet(url+"/logar.php?nome="+nome+"&senha="+senha).toString();
											//Log.i("MainActivity","conectou 4");
											//Log.i("MainActivity","respostaLogin = "+resposta);
											resposta = resposta.replaceAll("\\s+", "");//remover espaços
											if(resposta.equals("1")){//login deu certo
																	check = (CheckBox)findViewById(R.id.checkBoxMenu_Inicial);
																	if(check.isChecked()){//lembrar está selecionado
																						resposta=ConexaoHttpClient.executarHttpPost(url+"/consultarUsuario.php",parametrosPost).toString();
																						resposta = resposta.replaceAll("\\s+", "");//remover espaços
																						FileOutputStream arquivoGravar = openFileOutput("dados.txt", MODE_PRIVATE);
																						arquivoGravar.write(resposta.getBytes());
																						arquivoGravar.close();
																						lerUsuarioDoArquivo();
																						Log.i("MainActivity","dados gravados user = "+usuario.toString());
													
																						
																						startActivity(new Intent(CopyOfMainActivity.this, MenuInicial.class));
																						finish();
																						
																						
																	}else{//lembrar não esta seleconado
																			StringTokenizer st; 
																			resposta=ConexaoHttpClient.executarHttpPost(url+"/consultarUsuario.php",parametrosPost).toString();
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
																		
																		startActivity(new Intent(CopyOfMainActivity.this, MenuInicial.class));
																		finish();
																	}
												
											
											}else//não logou
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
							startActivity(new Intent(CopyOfMainActivity.this, CadastroConta.class));					
						}
					});
				}
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
public void mensagem(String titulo, String texto){
		
		AlertDialog.Builder msg = new AlertDialog.Builder(CopyOfMainActivity.this);
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
