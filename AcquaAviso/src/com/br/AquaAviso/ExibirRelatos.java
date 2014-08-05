package com.br.AquaAviso;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;






import java.util.StringTokenizer;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;















import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class ExibirRelatos extends Activity {
	String url=MainActivity.url;
	public ArrayList<Comentario> listaComentario=new ArrayList<Comentario>();
	Button but01;
	TextView titulo,afogamento,tubarao,atropelamento,assalto,outros, positivo;
	ArrayList <Button> listaCurti, listaNcurti;
	TextView t;
	Spinner tipo; 
	String opt="";
	boolean inicioTela=true;
	ContaAtiva conta=MainActivity.usuario;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exibir);
		
		listaCurti=new ArrayList<Button>();
		listaNcurti=new ArrayList<Button>();
		
		//Intent intent = getIntent();  
		//Bundle params = intent.getExtras(); 
		Log.i("ExibirRelatos","Passei aqui!");
		
		//String[] r = params.getStringArray("array");
		Intent intent = getIntent();
		String estado,local;
		estado=intent.getStringExtra("estado");
		local=intent.getStringExtra("local");
		
		titulo=(TextView) findViewById(R.id.textViewExibirLocal);
		Log.i("ExibirRelatos","Passei aqui 1!");
		titulo.setText(local+"/"+estado);
		Log.i("ExibirRelatos","Passei aqui 2!");
		String resposta;
		
		try{
			resposta=cometariosDoLocal(local,estado);
			LerComentarios(resposta);
			Log.i("ExibirRelatos","Passei aqui 3!");
			
			
			afogamento=(TextView) findViewById(R.id.textViewExibirAfogamento);
			tubarao=(TextView) findViewById(R.id.textViewExibirTubarao);
			atropelamento=(TextView) findViewById(R.id.textViewExibirAtropelamento);
			assalto=(TextView) findViewById(R.id.textViewExibirAssalto);
			outros=(TextView) findViewById(R.id.textViewExibirOutros);
			positivo=(TextView) findViewById(R.id.textViewExibirPositivo);
			int contAfogamento=0,contTubarao=0,contAtropelamento=0,contAssalto=0,contOutros=0,contPositivo=0;
			for(Comentario c:listaComentario){
				if(c.getTipo().equals("Afogamento"))
					contAfogamento++;
				else if(c.getTipo().equals("Tubarão"))
					contTubarao++;
				else if(c.getTipo().equals("Atropelamento"))
					contAtropelamento++;
				else if(c.getTipo().equals("Assalto/Arrastão"))
					contAssalto++;
				else if(c.getTipo().equals("Outros"))
					contOutros++;
				else if(c.getTipo().equals("Relato Positivo"))
					contPositivo++;
				
			}
			afogamento.setText(contAfogamento+" Afogamento");
			tubarao.setText(contTubarao+" Tubarão");
			atropelamento.setText(contAtropelamento+ " Atropelamento");
			assalto.setText(contAssalto+" Assalto/Arrastão");
			outros.setText(contOutros+" Outros");
			positivo.setText(contPositivo+" Relato positivo");
			Log.i("ExibirRelatos","Passei aqui 4!");
			//colocarMsgTela(0);
			Log.i("ExibirRelatos","Passei aqui 5!");
		}catch(Exception e){
			mensagem("Resposta", "Erro ConexÃ£o: "+e.getMessage().toString());
	}
	
	
	tipo = (Spinner)findViewById(R.id.spinnerExibir);
	tipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {//opções do spineer para imprimri na tela i=0 primeiro codigo (todos) do sppiner
	    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {   
			opt=tipo.getSelectedItem().toString();
			Log.i("ExibirRelatos","Passei aqui 6! "+opt);
			if(i==0 && inicioTela==true){
				Log.i("ExibirRelatos","Passei aqui 7!");
				colocarMsgTela(0);
				inicioTela=false;
			}
			else if(i==0 && inicioTela==false){
				colocarMsgTela(1);
				Log.i("ExibirRelatos","Passei aqui 8!");
			}
			else{
				colocarMsgTela(2);
				Log.i("ExibirRelatos","Passei aqui 9!");
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			
		}
});
	
	
}

public void colocarMsgTela(int filtro){
	
	LinearLayout ll = (LinearLayout) findViewById(R.id.Linear);
	ll.removeAllViews();
	Button  n;
	Button n2;
	int cont=0;
	if(filtro==0){
				for(Comentario c:listaComentario){
					 
					LinearLayout linha = new LinearLayout(ExibirRelatos.this);
			        linha.setOrientation(LinearLayout.HORIZONTAL);
			        linha.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
			
					 t=new TextView(ExibirRelatos.this);
					 t.setTypeface(Typeface.DEFAULT_BOLD);
					 t.setText(c.getNome());//nome
					 t.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
					 t.setTextColor(Color.BLACK);
			         
					 linha.addView(t);
					 
					 n=new Button(ExibirRelatos.this);//bottun curtir
					 n.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
					 n.setId(cont);
					 n.setTextColor(Color.rgb(0, 139, 69));
					 n.setText(c.getCurtida());
					 n.setBackgroundColor(Color.TRANSPARENT);
					 n.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.curti, 0 );
					 listaCurti.add(n);
					// n.setTextSize(3);
					 
					 linha.addView(n);
					 
					 listaCurti.get(cont).setOnClickListener(new View.OnClickListener() {//ação do bittun curtir
							@Override
							public void onClick(View v) {
								String resposta;								
								try{
										resposta=verificarCurtida(listaComentario.get(v.getId()).getId(),conta.getId());
										resposta = resposta.replaceAll("\\s+", "");//remover espaços
										if(resposta.equals("1")){//verifica se a msg já fo curtida(classificada)
												//mensagem("Curti", "ID: "+listaComentario.get(v.getId()).getNome());
												int x= Integer.parseInt(listaComentario.get(v.getId()).getCurtida());
												x++;//+ 1 curtida
												resposta="";
												try{
														resposta=attCurtidaServe(listaComentario.get(v.getId()).getId(),Integer.toString(x),conta.getId());
														resposta = resposta.replaceAll("\\s+", "");//remover espaços
														if(resposta.equals("1")){
															listaCurti.get(v.getId()).setText(Integer.toString(x));//atualizar a view
															listaComentario.get(v.getId()).setCurtida(Integer.toString(x));//atualiza a lista de comentario										
														}
														else
															mensagem("Resposta", "Erro: Curtida não computada, tente novamente!");
												
													}catch(Exception e){
														mensagem("Resposta", "Tente novamente(erro conexão): "+e.getMessage().toString());
													}
										}else
											mensagem("Resposta", "Você já classificou está mensagem!!");
											
									}catch(Exception e){
										mensagem("Resposta", "Tente novamente(erro conexão): "+e.getMessage().toString());
									}
								}
							
						});
					 						 
			
					 n2=new Button(ExibirRelatos.this);//button de não curtir
					 n2.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
					 n2.setId(cont);
					 
					 
					 n2.setTextColor(Color.RED);
					 n2.setText(c.getNcurtida());
					 n2.setBackgroundColor(Color.TRANSPARENT);
					 n2.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.ncurti, 0 );
					 // n.setTextSize(3);
					 linha.addView(n2);
					 listaNcurti.add(n2);
					 listaNcurti.get(cont).setOnClickListener(new View.OnClickListener() {
						 @Override
							public void onClick(View v) {
								String resposta;								
								try{
										resposta=verificarCurtida(listaComentario.get(v.getId()).getId(),conta.getId());
										resposta = resposta.replaceAll("\\s+", "");//remover espaços
										if(resposta.equals("1")){
												int x= Integer.parseInt(listaComentario.get(v.getId()).getNcurtida());
												x++;//+ 1 curtida
												resposta="";
												try{
														resposta=attNCurtidaServe(listaComentario.get(v.getId()).getId(), Integer.toString(x), conta.getId());
														//resposta=ConexaoHttpClient.executarHttpPost(url+"/attNcurtida.php",parametrosPost2).toString();
														resposta = resposta.replaceAll("\\s+", "");//remover espaços
														if(resposta.equals("1")){
															listaNcurti.get(v.getId()).setText(Integer.toString(x));
															listaComentario.get(v.getId()).setNcurtida(Integer.toString(x));//atualiza a lista de comentario										
														}
														else
															mensagem("Resposta", "Erro: Curtida não computada, tente novamente!");
												
													}catch(Exception e){
														mensagem("Resposta", "Tente novamente(erro conexão): "+e.getMessage().toString());
													}
										}else
											mensagem("Resposta", "você já classificou está mensagem!!");
											
									}catch(Exception e){
										mensagem("Resposta", "Tente novamente(erro conexão): "+e.getMessage().toString());
									}
								}
						});
			       
					 ll.addView(linha);//add primera linha inserida na teble
					 
					 linha = new LinearLayout(ExibirRelatos.this);//segund linha
					 linha.setOrientation(LinearLayout.HORIZONTAL);
				     linha.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
				     //linha.setGravity(Gravity.LEFT);	
				     
				     t=new TextView(ExibirRelatos.this);
					 t.setTypeface(Typeface.DEFAULT_BOLD);
					 t.setText(c.getTipo()+" - "+c.getLocal());//tipo
					 t.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
					 t.setTextColor(Color.BLACK);
					 
					 linha.addView(t);			 
					
					 
					 ll.addView(linha);//add segunda linha, depois fazer os botÃµes
					 
					 linha = new LinearLayout(ExibirRelatos.this);//TerÃ§eira linha
					 t=new TextView(ExibirRelatos.this);
					 t.setText(c.getRelato());//Relato
					 t.setTextColor(Color.BLACK);
					 
					 linha.addView(t);
					 ll.addView(linha);//add TerÃ§eita linha Relatos
					 
					 linha = new LinearLayout(ExibirRelatos.this);//Quarta linha
					 linha.setOrientation(LinearLayout.HORIZONTAL);
				     linha.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
					 linha.setGravity(Gravity.RIGHT);
					 t=new TextView(ExibirRelatos.this);
					 t.setText(c.getData());//Data
					 linha.addView(t);
					 ll.addView(linha);//add Quarta linha Relatos
					 
					 
					 linha = new LinearLayout(ExibirRelatos.this);//Quarta linha
					 linha.setOrientation(LinearLayout.HORIZONTAL);
					 linha.setGravity(Gravity.CENTER_HORIZONTAL);
				     linha.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
				     t=new TextView(ExibirRelatos.this);
				     t.setTextColor(Color.rgb(205, 201, 201));
					 t.setText("______________________________________________");//Relato
				     
					 linha.addView(t);
					 ll.addView(linha);//add Quarta linha Relatos
					 
					 cont++;
				}
	}else if(filtro==1){
		
		for(int i=0;i<listaCurti.size();i++){
			ViewGroup layout = (ViewGroup) listaCurti.get(i).getParent();
			ViewGroup layout2 = (ViewGroup) listaNcurti.get(i).getParent();
			if(null!=layout) //for safety only  as you are doing onClick
				layout.removeView(listaCurti.get(i));
			
			if(null!=layout2) //for safety only  as you are doing onClick
				layout.removeView(listaNcurti.get(i));
		}
		
		for(Comentario c:listaComentario){				
				 LinearLayout linha = new LinearLayout(ExibirRelatos.this);
		         linha.setOrientation(LinearLayout.HORIZONTAL);
		         linha.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		
				 t=new TextView(ExibirRelatos.this);
				 t.setTypeface(Typeface.DEFAULT_BOLD);
				 t.setText(c.getNome());//nome
				 t.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
				 t.setTextColor(Color.BLACK);
		         
				 linha.addView(t);
				 
				n=listaCurti.get(cont);
				/* n.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
				 n.setId(cont);
				 n.setTextColor(Color.rgb(0, 139, 69));
				 n.setText(c.getCurtida());
				 n.setBackgroundColor(Color.TRANSPARENT);
				 n.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.curti, 0 );
				 listaCurti.add(n);
				// n.setTextSize(3);*/
				 
				 linha.addView(n);
				 
				 						 
		
				 n2=listaNcurti.get(cont);
				/* n2.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
				 n2.setId(cont);
				 
				 
				 n2.setTextColor(Color.RED);
				 n2.setText(c.getNcurtida());
				 n2.setBackgroundColor(Color.TRANSPARENT);
				 n2.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.ncurti, 0 );
				 // n.setTextSize(3);*/
				 linha.addView(n2);
				 /*listaNcurti.add(n2);
				 listaNcurti.get(cont).setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							mensagem("NÃ£o Curti", "ID: "+listaComentario.get(v.getId()).getNome());
							
						}
					});*/
		       
				 ll.addView(linha);//add primera linha inserida na teble
				 
				 linha = new LinearLayout(ExibirRelatos.this);//segunda linha
				 linha.setOrientation(LinearLayout.HORIZONTAL);
			     linha.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
			     //linha.setGravity(Gravity.LEFT);	
			     
			     t=new TextView(ExibirRelatos.this);
				 t.setTypeface(Typeface.DEFAULT_BOLD);
				 t.setText(c.getTipo()+" - "+c.getLocal());//tipo
				 t.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
				 t.setTextColor(Color.BLACK);
				 
				 linha.addView(t);			 
				
				 
				 ll.addView(linha);//add segunda linha, depois fazer os botÃµes
				 
				 linha = new LinearLayout(ExibirRelatos.this);//TerÃ§eira linha
				 t=new TextView(ExibirRelatos.this);
				 t.setText(c.getRelato());//Relato
				 t.setTextColor(Color.BLACK);
				 
				 linha.addView(t);
				 ll.addView(linha);//add TerÃ§eita linha Relatos
				 
				 linha = new LinearLayout(ExibirRelatos.this);//Quarta linha
				 linha.setOrientation(LinearLayout.HORIZONTAL);
			     linha.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
				 linha.setGravity(Gravity.RIGHT);
				 t=new TextView(ExibirRelatos.this);
				 t.setText(c.getData());//Data
				 linha.addView(t);
				 ll.addView(linha);//add Quarta linha Relatos
				 
				 
				 linha = new LinearLayout(ExibirRelatos.this);//Quarta linha
				 linha.setOrientation(LinearLayout.HORIZONTAL);
				 linha.setGravity(Gravity.CENTER_HORIZONTAL);
			     linha.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
			     t=new TextView(ExibirRelatos.this);
			     t.setTextColor(Color.rgb(205, 201, 201));
				 t.setText("______________________________________________");//Relato
			     
				 linha.addView(t);
				 ll.addView(linha);//add Quarta linha Relatos
				 cont++;
		}		
		
		
		
	}else{
		for(int i=0;i<listaCurti.size();i++){
			ViewGroup layout = (ViewGroup) listaCurti.get(i).getParent();
			ViewGroup layout2 = (ViewGroup) listaNcurti.get(i).getParent();
			if(null!=layout) //for safety only  as you are doing onClick
				layout.removeView(listaCurti.get(i));
			
			if(null!=layout2) //for safety only  as you are doing onClick
				layout.removeView(listaNcurti.get(i));
		}
		for(Comentario c:listaComentario){
			if(c.getTipo().equals(opt)){
				 Log.i("ExibirRelatos","Passei aqui 10!");
				 LinearLayout linha = new LinearLayout(ExibirRelatos.this);
		         linha.setOrientation(LinearLayout.HORIZONTAL);
		         linha.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		
				 t=new TextView(ExibirRelatos.this);
				 t.setTypeface(Typeface.DEFAULT_BOLD);
				 t.setText(c.getNome());//nome
				 t.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
				 t.setTextColor(Color.BLACK);
		         
				 linha.addView(t);
				
				 n=listaCurti.get(cont);
				 /*Log.i("ExibirRelatos","Passei aqui 11!");
				 n.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
				 n.setId(cont);
				 n.setTextColor(Color.rgb(0, 139, 69));
				 n.setText(c.getCurtida());
				 n.setBackgroundColor(Color.TRANSPARENT);
				 n.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.curti, 0 );
				 //listaCurti.add(n);
				// n.setTextSize(3);*/
				 
				 linha.addView(n);
				 Log.i("ExibirRelatos","Passei aqui 12!");
				
				 						 
				 n2=listaNcurti.get(cont);
				/* n2.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
				 n2.setId(cont);
				 
				 
				 n2.setTextColor(Color.RED);
				 n2.setText(c.getNcurtida());
				 n2.setBackgroundColor(Color.TRANSPARENT);
				 n2.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.ncurti, 0 );
				 // n.setTextSize(3);*/
				 linha.addView(n2);
				 /*listaNcurti.add(n2);
				 listaNcurti.get(cont).setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							mensagem("NÃ£o Curti", "ID: "+listaComentario.get(v.getId()).getNome());
							
						}
					});*/
		       
				 ll.addView(linha);//add primera linha inserida na teble
				 
				 linha = new LinearLayout(ExibirRelatos.this);//segund linha
				 linha.setOrientation(LinearLayout.HORIZONTAL);
			     linha.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
			     //linha.setGravity(Gravity.LEFT);	
			     
			     t=new TextView(ExibirRelatos.this);
				 t.setTypeface(Typeface.DEFAULT_BOLD);
				 t.setText(c.getTipo()+" - "+c.getLocal());//tipo
				 t.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
				 t.setTextColor(Color.BLACK);
				 
				 linha.addView(t);			 
				
				 
				 ll.addView(linha);//add segunda linha, depois fazer os botÃµes
				 
				 linha = new LinearLayout(ExibirRelatos.this);//TerÃ§eira linha
				 t=new TextView(ExibirRelatos.this);
				 t.setText(c.getRelato());//Relato
				 t.setTextColor(Color.BLACK);
				 
				 linha.addView(t);
				 ll.addView(linha);//add TerÃ§eita linha Relatos
				 
				 linha = new LinearLayout(ExibirRelatos.this);//Quarta linha
				 linha.setOrientation(LinearLayout.HORIZONTAL);
			     linha.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
				 linha.setGravity(Gravity.RIGHT);
				 t=new TextView(ExibirRelatos.this);
				 t.setText(c.getData());//Data
				 linha.addView(t);
				 ll.addView(linha);//add Quarta linha Relatos
				 
				 
				 linha = new LinearLayout(ExibirRelatos.this);//Quarta linha
				 linha.setOrientation(LinearLayout.HORIZONTAL);
				 linha.setGravity(Gravity.CENTER_HORIZONTAL);
			     linha.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
			     t=new TextView(ExibirRelatos.this);
			     t.setTextColor(Color.rgb(205, 201, 201));
				 t.setText("______________________________________________");//Relato
			     
				 linha.addView(t);
				 ll.addView(linha);//add Quarta linha Relatos				
			}
		cont++;
		}		
	}
}


public boolean LerComentarios(String msg) throws UnsupportedEncodingException{
StringTokenizer st;
st = new StringTokenizer(msg, "#");
int cont=0;
listaComentario=new ArrayList<Comentario>();
Comentario comentario=new Comentario();
while (st.hasMoreTokens()) {
	if(cont==0)
		comentario.setId(st.nextToken());
	else if(cont==1)
		comentario.setIdUsuario(st.nextToken());
	else if(cont==2)
		comentario.setNome(st.nextToken());
	else if(cont==3)
		comentario.setEmail(st.nextToken());
	else if(cont==4)
		comentario.setTipo(st.nextToken());
	else if(cont==5)
		comentario.setLocal(st.nextToken());
	else if(cont==6)
		comentario.setEstado(st.nextToken());
	else if(cont==7)
		comentario.setRelato(new String(st.nextToken().getBytes("UTF-8")));
	else if(cont==8)
		comentario.setCurtida(st.nextToken());
	else if(cont==9)
		comentario.setNcurtida(st.nextToken());
	else if(cont==10)
		comentario.setData(st.nextToken());
	cont++;
	if(cont==11){
		listaComentario.add(comentario);
		cont=0;
		comentario=new Comentario();
	}
   // tokens.add(st.nextToken());  
}

return true;	
}
public void atualizarCurtida(ArrayList<NameValuePair> parametros){

String respostaRetornada = null;
Log.i("Alterar", "vai entrar no try");
try {
	respostaRetornada = ConexaoHttpClient.executarHttpPost(url+"", parametros);					
	String resposta = respostaRetornada.toString();
	Log.i("alterar", "resposta = "+resposta);
	resposta = resposta.replaceAll("\\s+", "");
	if (resposta.equals("1"));
	else
		mensagem("Erro", "Seu voto nÃ£o foi computado!!!");
}
catch(Exception erro)
{
	Log.i("erro", "erro = "+erro);
	//Toast.makeText(CadastroUsuario.this, "Erro.: "+erro, Toast.LENGTH_LONG).show();
	mensagem("Erro", "Erro ao computar seu voto(Tente de novo): "+erro);
}
}


private String cometariosDoLocal(String local, String estado) {
	String s = "";
	AsyncTask<String,String,String> tread;
	 
	try {
		tread=new Conexao(ExibirRelatos.this).execute(url+"/consultarLocal.php","4","local",local,"estado",estado);
		s = tread.get();
	    Log.i("ExibirRelatos","LOCAL: "+local+" ESTADO: "+estado);
	    Log.i("ExeirRelatos","respostaLogin 2 = "+s);
	} catch (Exception e) {
	    s="0";
	}
	 return s;
}

private String verificarCurtida(String idcomentario, String idconta) {
	/*resposta=ConexaoHttpClient.executarHttpPost(url+"/verificaCurtida.php",parametrosPost).toString();

	ArrayList<NameValuePair> parametrosPost=new ArrayList<NameValuePair>();
	parametrosPost.add(new BasicNameValuePair("idcomentario",listaComentario.get(v.getId()).getId())); 
	parametrosPost.add(new BasicNameValuePair("idconta",conta.getId()));*/
	
	String s = "";
	AsyncTask<String,String,String> tread;
	 
	try {
		tread=new Conexao(ExibirRelatos.this).execute(url+"/verificaCurtida.php","4","idcomentario",idcomentario,"idconta",idconta);
		s = tread.get();
	    Log.i("ExeirRelatos","respostaLogin 2 = "+s);
	} catch (Exception e) {
	    s="0";
	}
	 return s;
}

private String attCurtidaServe(String idcomentario, String Numerocurtida, String idconta) {
	
	/*resposta=ConexaoHttpClient.executarHttpPost(url+"/attCurtida.php",parametrosPost2).toString();
	ArrayList<NameValuePair> parametrosPost2=new ArrayList<NameValuePair>();
	parametrosPost2.add(new BasicNameValuePair("idcomentario",listaComentario.get(v.getId()).getId())); 
	parametrosPost2.add(new BasicNameValuePair("curtida",Integer.toString(x)));	
	parametrosPost2.add(new BasicNameValuePair("idconta",conta.getId()));*/
	
	String s = "";
	AsyncTask<String,String,String> tread;
	 
	try {
		tread=new Conexao(ExibirRelatos.this).execute(url+"/attCurtida.php","6","idcomentario",idcomentario,"curtida",Numerocurtida,"idconta",idconta);
		s = tread.get();
	    Log.i("ExeirRelatos","respostaLogin 2 = "+s);
	} catch (Exception e) {
	    s="0";
	}
	 return s;
}

private String attNCurtidaServe(String idcomentario, String NumeroNcurtida, String idconta) {
	
	/*resposta=ConexaoHttpClient.executarHttpPost(url+"/attNcurtida.php",parametrosPost2).toString();
	ArrayList<NameValuePair> parametrosPost2=new ArrayList<NameValuePair>();
	parametrosPost2.add(new BasicNameValuePair("idcomentario",listaComentario.get(v.getId()).getId())); 
	parametrosPost2.add(new BasicNameValuePair("ncurtida",Integer.toString(x)));	
	parametrosPost2.add(new BasicNameValuePair("idconta",conta.getId()));*/
	
	String s = "";
	AsyncTask<String,String,String> tread;
	 
	try {
		tread=new Conexao(ExibirRelatos.this).execute(url+"/attNcurtida.php","6","idcomentario",idcomentario,"ncurtida",NumeroNcurtida,"idconta",idconta);
		s = tread.get();
	    Log.i("ExeirRelatos","respostaLogin 2 = "+s);
	} catch (Exception e) {
	    s="0";
	}
	 return s;
}



public void mensagem(String titulo, String texto){

AlertDialog.Builder msg = new AlertDialog.Builder(ExibirRelatos.this);
msg.setTitle(titulo);
msg.setMessage(texto);
msg.setNegativeButton("Ok", null);
msg.show();
}


	
}
