package com.br.AquaAviso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class Conexao extends AsyncTask<String, String, String>{
	private Context context;
	private ProgressDialog progress;
	public static final int HTTP_TIMEOUT= 30 * 1000;//precisar passar um parametro de tempo
	private static HttpClient httpClient;
	
	public Conexao(){
		this.context = null;
	}
	
	public Conexao(Context c){
	this.context = c;
	}
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		progress = new ProgressDialog(context);
		progress.setMessage("Carregando...");
		progress.show();
	}
	
	
	@Override
	protected String doInBackground(String... url) {
		publishProgress("Carregando...");
		String resultado;
		ArrayList<NameValuePair> parametrosPost=new ArrayList<NameValuePair>();
		int totalParametros=Integer.parseInt(url[1]);
		int indeceParametro=2;//todo parametro começa no indice 2 do array url
		while(indeceParametro<(totalParametros+2)){
			parametrosPost.add(new BasicNameValuePair(url[indeceParametro],url[indeceParametro+1]));
			indeceParametro+=2;
		}
		try {
			Log.i("Conexao url",url[0]);
			resultado=ConexaoHttpClient.executarHttpPost(url[0],parametrosPost).toString();
			Log.i("Conexao","resposta= "+resultado);
		} catch (Exception e) {
			resultado="-1";
		}
		return resultado;
	}

	
	protected void onPostExecute(String resultado) {
		//ti.depoisDownload(params);
		super.onPostExecute(resultado);
		progress.dismiss();
		
	}

}
