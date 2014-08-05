package com.br.AquaAviso;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class CadastrarRelato  extends Activity{
	String url=MainActivity.url;
	
	EditText edtLocal, edtEstado, edtRelato;
	Spinner spinnerTipoDeOcorrencia;
	ContaAtiva conta=MainActivity.usuario;
	String local, estado, relato, tipoDeOcorrencia, data;
	private ProgressDialog progress;
	LatLng pontosDoLocal;
	
	
	List<String> estados = Arrays.asList("AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PR",
			"PB", "PA", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SE", "SP", "TO","al", "ap", "am", "ba", "ce", "df", "es", "go", "ma", "mt", "ms", "mg", "pr",
			"pb", "pa", "pe", "pi", "rj", "rn", "rs", "ro", "rr", "sc", "se", "sp", "to"); 
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Log.i("CadastrarRelato","Cadastrar: passei aqui 1!!");
		setContentView(R.layout.cadastrar);
		//Log.i("CadastrarRelato","Cadastrar: passei aqui 2!!");
		
		Button but02=(Button) findViewById(R.id.buttonCadastrarEnviar);
		but02.setOnClickListener(new View.OnClickListener() {
		
			@Override
			public void onClick(View v) {
				edtLocal=(EditText) findViewById(R.id.editTextCadastrar_Local);
				edtEstado=(EditText) findViewById(R.id.editTextCadastrar_Estado);
				edtRelato=(EditText) findViewById(R.id.editTextCadastrarRelato);
				spinnerTipoDeOcorrencia=(Spinner) findViewById(R.id.spinnerCadastrar);
				
				local=edtLocal.getText().toString().toUpperCase();
				estado=edtEstado.getText().toString().toUpperCase();
				relato=edtRelato.getText().toString();
				tipoDeOcorrencia=spinnerTipoDeOcorrencia.getSelectedItem().toString();
			//	Log.i("CadastrarRelato","Cadastrar: passei aqui 4!!");
				//boolean verf=true;
				//verificar condições para postagem
				String vazio="";
				 if (local.equals("")) 
				    	vazio="Local não foi informado.\n";
				 if (estado.equals(""))
				    	vazio+="Estado não foi informado.\n";
				 if (relato.equals("")) 
				    	vazio+="Relato não foi informado.\n";
				 if (tipoDeOcorrencia.equals("Todos"))
					 vazio+="Escolha um tipo de relato.\n";
				 if (local.equals("") || estado.equals("") || relato.equals("") || tipoDeOcorrencia.equals("Todos"))
						mensagem("Erro.:", ""+vazio,1);
				 else if(!estados.contains(estado))
					 	mensagem("Erro.:", "Digite um estado Válido.\n"+estado+" não existe!",1);
				 else{
						
				        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");  
				        data=sdf.format( new Date( System.currentTimeMillis() ) );
					
					
						
						String resposta = "";
						try {
							resposta=getLatLng();
							
							if (resposta.equals("1"))
								 //startActivity(new Intent(CadastroUsuario.this,MenuPrincipal.class));
								mensagem("Gravação.:", "Comentário Salvo com Sucesso", 0);
							else
								mensagem("Erro", "Não foi possíve gravar o Comentário... ", 1);
						}
						catch(Exception erro)
						{
							Log.i("erro", "erro = "+erro);
							mensagem("Erro", "Erro ao gravar: "+erro, 1);
						}
					
					
					}
				
						
				
			}

			
		});
			
		
	}
	
	
	
	private String cadastrarComentarioServe() {
		
		String s = "";
		AsyncTask<String,String,String> tread;
		 
		try {
			Log.i("CadastrarRelato","LATLNG = "+pontosDoLocal.longitude+ " "+pontosDoLocal.latitude);
			tread=new Conexao(CadastrarRelato.this).execute(url+"/cadastrarComentario.php","20","idusuario",conta.getId(),"nome",conta.getNome(),"email",conta.getEmail(),"tipo",tipoDeOcorrencia,"local",local,"estado",estado,"relato",relato,"data",data,"latitude",String.valueOf(pontosDoLocal.latitude),"longitude",String.valueOf(pontosDoLocal.longitude));
			s = tread.get();
		    Log.i("CadastrarRelato","respostaLogin 2 = "+s);
		} catch (Exception e) {
		    s="0";
		}
		 return s;
	}
	
	
private String getLatLng() {
	
			String resposta="", resposta2="";
	
			AsyncTask<String,String,String> asyncTask = new AsyncTask<String,String,String>(){
			
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				progress = new ProgressDialog(CadastrarRelato.this);
				progress.setMessage("Carregando...");
				progress.show();
			}
			
			@Override
			protected String doInBackground(String... params) {
				progress.setMessage("Carregando coordenada...");
				JSONObject jsonObject=getLocationInfo(local+", "+estado+", Brasil");
				if(jsonObject==null)
					return "0";
				else{
					 pontosDoLocal=getGeoPoint(jsonObject);
					 return "1";
				}
			}
			
			protected void onPostExecute(String resultado) {
				//ti.depoisDownload(params);
				super.onPostExecute(resultado);
				progress.dismiss();
				if(resultado.equals("1")){
					String resposta2 = cadastrarComentarioServe();
				}
			}
			
		}.execute("");
		
		try {
			resposta = asyncTask.get();
		} catch (InterruptedException e) {
			resposta = "0";
		} catch (ExecutionException e) {
			resposta = "0";
		}
		
		if(resposta.equals("0") || resposta2.equals("0")){
			return "0";
		}
		else
			return "1";
		
		
	}
	
	
	public static JSONObject getLocationInfo(String address) {

		HttpGet httpGet=null;
		try {
			httpGet = new HttpGet("https://maps.googleapis.com/maps/api/place/textsearch/json?query="+URLEncoder.encode(address,"UTF-8")+"&sensor=false&key=AIzaSyAu_EGLN9CYjmqPDjz4h0WgQYgdzRVu5L4");		
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}
		HttpClient client = new DefaultHttpClient();
		HttpResponse response;
		StringBuilder stringBuilder = new StringBuilder();

		try {
			response = client.execute(httpGet);
			HttpEntity entity = response.getEntity();
			InputStream stream = entity.getContent();
			int b;
			while ((b = stream.read()) != -1) {
				stringBuilder.append((char) b);
			}
		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		}

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = new JSONObject(stringBuilder.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return null;
		}

		return jsonObject;
	}

	public static LatLng getGeoPoint(JSONObject jsonObject) {

		Double lon = new Double(0);
		Double lat = new Double(0);
		LatLng latandlng=null;
		try {

			lon = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
				.getJSONObject("geometry").getJSONObject("location")
				.getDouble("lng");

			lat = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
				.getJSONObject("geometry").getJSONObject("location")
				.getDouble("lat");
			latandlng=new LatLng(lat,lon);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return latandlng;

	}
	
	
	public void mensagem(String titulo, String texto, int x){
		
		if(x==0){
			AlertDialog.Builder msg = new AlertDialog.Builder(CadastrarRelato.this);
			msg.setTitle(titulo);
			msg.setMessage(texto);
			msg.setNegativeButton("Ok", new DialogInterface.OnClickListener() {		
			public void onClick(DialogInterface dialog, int which) {
				local=local.toUpperCase();
				estado=estado.toUpperCase();
				Intent intent = new Intent(CadastrarRelato.this, ExibirRelatos.class);
                intent.putExtra("local", local);
                intent.putExtra("estado", estado);
                startActivity(intent);
				
				finish();	
				}
			});
			msg.show();
		}
		else{
			AlertDialog.Builder msg = new AlertDialog.Builder(CadastrarRelato.this);
			msg.setTitle(titulo);
			msg.setMessage(texto);
			msg.setNegativeButton("Ok", null);
			msg.show();
		}
	}
	
	
	

}
