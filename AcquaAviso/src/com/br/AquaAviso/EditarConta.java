package com.br.AquaAviso;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.renderscript.Type;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class EditarConta extends Activity{
	EditText edtSenhaAntiga,edtNovaSenha,edtNome, edtEmail,edtSenha;
	Button btVoltar,btOk;
	ContaAtiva conta=MainActivity.usuario;
	String url=MainActivity.url;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.menuinicial);	
		Intent intent = getIntent();
		int opcao;
		opcao=intent.getIntExtra("opcao", -1);
		Log.i("ExibirRelatos","paramertro: "+opcao);
		
		switch(opcao){
			case 0:
				criarModificarPerfil();
				break;
			case 1:
				criarModificarSenha();
				break;
			case 2:
				excluirConta();
				break;
			default:
				finish();
				break;
						
		}
	}
	

	public void onBackPressed(){
        super.onBackPressed();
        startActivity(new Intent(EditarConta.this,MenuInicial.class));
		 finish();
    }

	private void criarModificarSenha() {
				LinearLayout linearLayout = new LinearLayout(this);
		        linearLayout.setOrientation(LinearLayout.VERTICAL);
		        linearLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		        
		        
		        TextView title = new TextView(this);
		        title.setText("Modificar Senha");
		        title.setTextSize(16);
		        title.setTypeface(Typeface.DEFAULT_BOLD);
		        title.setGravity(Gravity.CENTER_HORIZONTAL);
		        TextView tvNome = new TextView(this);
		        tvNome.setText("Senha antiga:");
		        tvNome.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
		        
		        edtSenhaAntiga = new EditText(this);
		        edtSenhaAntiga.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		        edtSenhaAntiga.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		        
		        
		        TextView textNovaSenha = new TextView(this);
		        textNovaSenha.setText("Nova senha:");
		        textNovaSenha.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
		        
		        
		        edtNovaSenha= new EditText(this);
		        edtNovaSenha.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		        edtNovaSenha.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		        
		        
		        LinearLayout buttons = new LinearLayout(this);
		        buttons.setOrientation(LinearLayout.HORIZONTAL);
		        buttons.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		        
		        btOk = new Button(this);
		        btOk.setText("Ok");
		        btOk.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
		        btOk.setOnClickListener( new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						String senhaAntiga=edtSenhaAntiga.getText().toString();
						String novaSenha=edtNovaSenha.getText().toString();
						
						String vazio="";
						 if (senhaAntiga.equals("")) 
						    	vazio="Campo senha atual não foi informado.\n";
						 if (novaSenha.equals(""))
						    	vazio+="Campo nova senha não foi informado.\n";
						 if (novaSenha.equals("") || senhaAntiga.equals(""))
								mensagem("Erro.:", ""+vazio);
						 else{
								String resposta="";
								try{
									resposta=verificarLoginExiste(conta.getNome(),edtSenhaAntiga.getText().toString());
									//resposta=ConexaoHttpClient.executarHttpPost(url+"/logar.php",parametrosPost).toString();
									Log.i("MainActivity","respostaLogin = "+resposta);
									resposta = resposta.replaceAll("\\s+", "");
									if(resposta.equals("1")){
											 AlertDialog.Builder confirmarExclusao = new AlertDialog.Builder(EditarConta.this);
											 confirmarExclusao.setTitle("Alterar a senha?");
											 confirmarExclusao.setMessage("Deseja realmente alterar a senha?\nSua nova senha será: "+edtNovaSenha.getText().toString());
											 confirmarExclusao.setPositiveButton("SIM", new DialogInterface.OnClickListener() {		
												public void onClick(DialogInterface dialog, int which) {
													String resposta2="";
													try{
														resposta2=AtualizarSenha(conta.getId(),edtNovaSenha.getText().toString());
														resposta2 = resposta2.replaceAll("\\s+", "");
														if(resposta2.equals("1")){
															Toast.makeText(EditarConta.this, "Senha alterada!!!",Toast.LENGTH_LONG).show();	
															mensagemFinal("Aviso", "Você será desconectado para poder atualizar as informações!");
														}
														else{
															mensagem("Erro", "Erro Conexão, tente novamente!");
														}
													}catch(Exception e){
														mensagem("Resposta", "Erro Conexão: "+e.getMessage().toString());
													}
													
												}
											 });
											 confirmarExclusao.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {		
													public void onClick(DialogInterface dialog, int which) {
													   	dialog.cancel();
														
													}
											 });
											 confirmarExclusao.show();
										
										
									}
									else
										mensagem("Resposta", "Senha invalida, senha atual não alterada! ");
							
									 
								}catch(Exception e){
									mensagem("Resposta", "Erro Conexão: "+e.getMessage().toString());
								}
						
						 }
						
					}
				});
		        
		        
		        //btGravar.setGravity(Gravity.RIGHT);
		        btVoltar = new Button(this);
		        btVoltar.setText("Voltar");
		        btVoltar.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
		        
		        btVoltar.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						 Intent intent = new Intent(EditarConta.this, MenuInicial.class); 
						 startActivity(intent);
						 finish();
					}
				});
		        
		        buttons.addView(btOk);
		        buttons.addView(btVoltar);
		        
		        linearLayout.addView(title);
		        linearLayout.addView(tvNome);
		        linearLayout.addView(edtSenhaAntiga);
		        linearLayout.addView(textNovaSenha);
		        linearLayout.addView(edtNovaSenha);
		        linearLayout.addView(buttons);
		        setContentView(linearLayout);
	}

	private void criarModificarPerfil() {
				LinearLayout linearLayout = new LinearLayout(this);
		        linearLayout.setOrientation(LinearLayout.VERTICAL);
		        linearLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		        
		        
		        TextView title = new TextView(this);
		        title.setText("Editar Perfil");
		        title.setTextSize(16);
		        title.setTypeface(Typeface.DEFAULT_BOLD);
		        title.setGravity(Gravity.CENTER_HORIZONTAL);
		        TextView tvNome = new TextView(this);
		        tvNome.setText("Nome:");
		        tvNome.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
		        
		        edtNome = new EditText(this);
		        edtNome.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		        edtNome.setText(conta.getNome());
		        
		        TextView textEmail = new TextView(this);
		        textEmail.setText("E-mail:");
		        textEmail.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
		        
		        edtEmail = new EditText(this);
		        edtEmail.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		        edtEmail.setText(conta.getEmail());
		        
		        TextView textSenha = new TextView(this);
		        textSenha.setText("Senha:(requesito de segurança):");
		        textSenha.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
		        
		        edtSenha = new EditText(this);
		        edtSenha.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		        edtSenha.setHint("Senha da conta");
		        edtSenha.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		        LinearLayout buttons = new LinearLayout(this);
		        buttons.setOrientation(LinearLayout.HORIZONTAL);
		        buttons.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		        
		        Button btOk = new Button(this);
		        btOk.setText("Ok");
		        btOk.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
		        btOk.setOnClickListener( new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						String novoNome=edtNome.getText().toString();
						String novoEmail=edtEmail.getText().toString();
						String confirmaSenha=edtSenha.getText().toString();
						
						String vazio="";
						 if (novoNome.equals("")) 
						    	vazio="Campo nome não foi informado.\n";
						 if (novoEmail.equals(""))
						    	vazio+="Campo email não foi informado.\n";
						 if (confirmaSenha.equals(""))
						    	vazio+="Campo senha não foi informado.\n";
						 if (novoNome.equals("") || novoEmail.equals("") || confirmaSenha.equals("") )
								mensagem("Erro.:", ""+vazio);
						 else{
								String resposta="";
								if(novoNome.equalsIgnoreCase(conta.getNome()))
									resposta="2";
								else{
									resposta=verificarNomeDisponivel(novoNome);
									resposta = resposta.replaceAll("\\s+", "");
								}
								if(resposta.equals("2")){//usei 2 só nessa função
									try{
											resposta="";
											resposta=verificarLoginExiste(conta.getNome(),confirmaSenha);
											Log.i("EditarConta","respostaLogin = "+resposta);
											resposta = resposta.replaceAll("\\s+", "");
											if(resposta.equals("1")){
													 AlertDialog.Builder confirmarExclusao = new AlertDialog.Builder(EditarConta.this);
													 confirmarExclusao.setTitle("Editar");
													 confirmarExclusao.setMessage("Deseja realmente editar esses dados?\nSeu novo nome de usuário será: "+edtNome.getText().toString()+"\nSeu e-mail sera:\n"+edtEmail.getText().toString());
													 confirmarExclusao.setPositiveButton("SIM", new DialogInterface.OnClickListener() {		
														public void onClick(DialogInterface dialog, int which) {
															String resposta2="";
															try{
																resposta2=AtualizarPerfil(edtNome.getText().toString(),edtEmail.getText().toString(),conta.getId());
																Log.i("EditarConta","respostaLogin = "+resposta2);
																resposta2 = resposta2.replaceAll("\\s+", "");
																if(resposta2.equals("1")){
																	Toast.makeText(EditarConta.this, "Perfil alterada!!!",Toast.LENGTH_LONG).show();	
																	mensagemFinal("Aviso", "Você será desconectado para poder atualizar as informações!");
																}
																else{
																	mensagem("Erro", "Erro Conexão, tente novamente!");
																}
															}catch(Exception e){
																mensagem("Resposta", "Erro Conexão: "+e.getMessage().toString());
															}
															
														}
		
													 });
													 confirmarExclusao.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {		
															public void onClick(DialogInterface dialog, int which) {
															   	dialog.cancel();
																
															}
													 });
													 confirmarExclusao.show();
											}
											else
												mensagem("Resposta", "Senha invalida!!! ");
								
										 
										}catch(Exception e){
											mensagem("Resposta", "Erro Conexão: "+e.getMessage().toString());
										}
								}//if(resposta==2)
								else
									mensagem("Resposta", "Nome de usuário não desponível, escolha outro!");
								
						 }//else
						
						
					}
				});
		        
		        
		        //btGravar.setGravity(Gravity.RIGHT);
		        btVoltar = new Button(this);
		        btVoltar.setText("Voltar");
		        btVoltar.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
		        
		        btVoltar.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						 Intent intent = new Intent(EditarConta.this, MenuInicial.class); 
						 startActivity(intent);
						 finish();
						
					}
				});
		        
		        buttons.addView(btOk);
		        buttons.addView(btVoltar);
		        
		        linearLayout.addView(title);
		        linearLayout.addView(tvNome);
		        linearLayout.addView(edtNome);
		        linearLayout.addView(textEmail);
		        linearLayout.addView(edtEmail);
		        linearLayout.addView(textSenha);
		        linearLayout.addView(edtSenha);
		        linearLayout.addView(buttons);
		        setContentView(linearLayout);
		
	}
	
	private void excluirConta() {
		LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
        
		TextView textSenha = new TextView(this);
        textSenha.setText("Senha(requesito de segurança):");
        textSenha.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
        
        edtSenha = new EditText(this);
        edtSenha.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
        edtSenha.setHint("Senha da conta");
        
        edtSenha.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        
        Button btOk = new Button(this);
        btOk.setText("Ok");
        btOk.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
        btOk.setOnClickListener( new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					
					String senhaAtual=edtSenha.getText().toString();
					 if (senhaAtual.equals("")) 
					    	mensagem("Erro:.", "Campo nome não pode estar vazio.\n");
					 else{
							String resposta="";
							try{
									resposta=verificarLoginExiste(conta.getNome(),senhaAtual);
									resposta = resposta.replaceAll("\\s+", "");
									if(resposta.equals("1")){
											 AlertDialog.Builder confirmarExclusao = new AlertDialog.Builder(EditarConta.this);
											 confirmarExclusao.setTitle("Editar");
											 confirmarExclusao.setMessage("Deseja realmente excluir essa conta?");
											 confirmarExclusao.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
												
												@Override
												public void onClick(DialogInterface dialog, int which) {
													//ArrayList<NameValuePair> parametrosPost2=new ArrayList<NameValuePair>();
													
													//parametrosPost2.add(new BasicNameValuePair("idconta",conta.getId()));
													String resposta="";
													try{
														resposta=excluirContaUsuario(conta.getId());
														resposta = resposta.replaceAll("\\s+", "");
														if(resposta.equals("1")){	
															mensagemFinal("Aviso", "Perfil excluído!");
														}else
															mensagem("Erro","Erro conexão!!!");
													}catch(Exception e){
														mensagem("Resposta", "Erro Conexão: "+e.getMessage().toString());
													}
												}

												
											 });
											 confirmarExclusao.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {		
													public void onClick(DialogInterface dialog, int which) {
													   	dialog.cancel();
														
													}
											 });
											 confirmarExclusao.show();
									}
									else{
										mensagem("Erro", "Senha errada!");		 
											 }
							}catch(Exception e){
								mensagem("Resposta", "Erro Conexão: "+e.getMessage().toString());
							}
					 }
					
				}
			});
        
        btVoltar = new Button(this);
        btVoltar.setText("Voltar");
        btVoltar.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
        
        btVoltar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 Intent intent = new Intent(EditarConta.this, MenuInicial.class); 
				 startActivity(intent);
				 finish();
				
			}
		});
		
        
        LinearLayout buttons = new LinearLayout(this);
        buttons.setOrientation(LinearLayout.HORIZONTAL);
        buttons.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		buttons.addView(btOk);
		buttons.addView(btVoltar);
		
        linearLayout.addView(textSenha);
        linearLayout.addView(edtSenha);
        linearLayout.addView(buttons);
        
        
       
        setContentView(linearLayout);
		
	}
	
	
private String verificarLoginExiste(String nome, String senha) {
		String s = "";
		AsyncTask<String,String,String> tread;
		 
		try {
			tread=new Conexao(EditarConta.this).execute(url+"/logar.php","4","nome",nome,"senha",senha);
		    s = tread.get();
		    Log.i("MainActivity","respostaLogin 2 = "+s);
		} catch (Exception e) {
		    s="0";
		}
		 return s;
		}


private String AtualizarSenha(String idConta, String novaSenha) {
	
	String s = "";
	AsyncTask<String,String,String> tread;
	 
	try {
		tread=new Conexao(EditarConta.this).execute(url+"/attSenha.php","4","idconta",idConta,"senhanova",novaSenha);
	    s = tread.get();
	    Log.i("EditarConta","respostaLogin 2 = "+s);
	} catch (Exception e) {
	    s="0";
	}
	 return s;
}


private String AtualizarPerfil( String novonome, String novoemail, String idconta) {
	// TODO Auto-generated method stub
	String s = "";
	AsyncTask<String,String,String> tread;
	 
	try {
		tread=new Conexao(EditarConta.this).execute(url+"/attPerfil.php","6","novonome",novonome,"novoemail",novoemail,"idconta",idconta);
		s = tread.get();
	    Log.i("EditarConta","respostaLogin 2 = "+s);
	} catch (Exception e) {
	    s="0";
	}
	 return s;
}

private String excluirContaUsuario(String idconta) {

	String s = "";
	AsyncTask<String,String,String> tread;
	 
	try {
		tread=new Conexao(EditarConta.this).execute(url+"/excluirConta.php?idconta="+idconta);
		tread=new Conexao(EditarConta.this).execute(url+"/excluirConta.php","2","idconta",idconta);
	    s = tread.get();
	    Log.i("EditarConta","respostaLogin 2 = "+s);
	} catch (Exception e) {
	    s="0";
	}
	 return s;
}

private String verificarNomeDisponivel(String nome) {
	String s = "";
	AsyncTask<String,String,String> tread;
	try {
		tread=new Conexao(EditarConta.this).execute(url+"/verificarNomeDisponivel.php","2","nome",nome);
		// tread = new RequestTask().execute(uri);
	    s = tread.get();
	    Log.i("MainActivity","respostaLogin 2 = "+s);
	} catch (Exception e) {
	    s="0";
	}
	return s;
}
	
public void mensagem(String titulo, String texto){
		
		AlertDialog.Builder msg = new AlertDialog.Builder(EditarConta.this);
		msg.setTitle(titulo);
		msg.setMessage(texto);
		msg.setNegativeButton("Ok", null);
		msg.show();
		
	}
	
	
	
public void mensagemFinal(String titulo, String texto){
		
		AlertDialog.Builder msg = new AlertDialog.Builder(EditarConta.this);
		msg.setTitle(titulo);
		msg.setMessage(texto);
		msg.setNegativeButton("Ok", new DialogInterface.OnClickListener() {		
		public void onClick(DialogInterface dialog, int which) {
			deleteFile("dados.txt");
			startActivity(new Intent(EditarConta.this, MainActivity.class));
			finish();	
			}
		});
		msg.show();
		
	}

}
