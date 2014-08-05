package com.br.AquaAviso;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class ConexaoHttpClient {
	public static final int HTTP_TIMEOUT= 30 * 1000;//precisar passar um parametro de tempo
	private static HttpClient httpClient;
	
	
	
	private static HttpClient getHttpClient(){//metodo que retorna a conexão
		if(httpClient == null){//
			httpClient = new DefaultHttpClient();//inicializar
			final HttpParams httpParams = httpClient.getParams();//pega os parametro da conexao
			HttpConnectionParams.setConnectionTimeout(httpParams, HTTP_TIMEOUT);//setar o tempo max da conexao
			HttpConnectionParams.setSoTimeout(httpParams, HTTP_TIMEOUT);//pranmetros para tempo de conexao
			ConnManagerParams.setTimeout(httpParams, HTTP_TIMEOUT);//tembem setado tempo de conexao para o gerenciador de conexao
		}
		return httpClient;
		
	}
	
	//para o metodo post
	public static String executarHttpPost(String url, ArrayList<NameValuePair> parametrosPost) throws Exception {//passa a url e os parametros que irão ser enviados para PHP
		BufferedReader bufferedReader=null;//inicialmente com anda depois iremos povoar
		String resultado;
		try{
			
			HttpClient Client = getHttpClient();//pegar a conexao
			HttpPost Post=new HttpPost(url);//inicia com a url para em seguida passar os parametros
			UrlEncodedFormEntity formatarParametros = new UrlEncodedFormEntity(parametrosPost, "UTF-8");//colocando os parametros no formato
			Post.setEntity(formatarParametros);//passando os parametros para post
			HttpResponse httpResponse = Client.execute(Post);
			bufferedReader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));//ele que vai receber esses dados de entrada e inputstremreade é responsavel, por receber dados
			StringBuffer stringBuffer = new StringBuffer("");//vai receber as informações
			String line="";
			String LS= System.getProperty("line.separator");//separa as linhas(\s)
			while((line=bufferedReader.readLine()) != null){//percorre linha por linha
				stringBuffer.append(line+LS);	//adicionando stringBuffer
			}
			bufferedReader.close();//fecha leitor
			resultado=stringBuffer.toString();//passa para uma String o resultado
			return resultado;
		}finally{
			if(bufferedReader != null){
				try{
					bufferedReader.close();
				}catch(IOException e){
					e.printStackTrace();
				}	
			}
		}
	}
		
		//para o metodo gets
		public static String executarHttpGet(String url) throws Exception {//passa a url e os parametros que irão ser enviados para PHP
		
			BufferedReader bufferedReader=null;//inicialmente com anda depois iremos povoar
			String resultado;
			try{
				
				HttpClient Client = getHttpClient();//pegar a conexao
				HttpGet Get=new HttpGet(url);//inicia com a url para em seguida passar os parametros
				Get.setURI(new URI(url));//passando os parametros para get é url
				HttpResponse httpResponse = Client.execute(Get);
				bufferedReader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));//ele que vai receber esses dados de entrada e inputstremreade é responsavel, por receber dados
				StringBuffer stringBuffer = new StringBuffer("");//vai receber as informações
				String line="";
				String LS= System.getProperty("line.separator");//separa as linhas(\s)
				while((line=bufferedReader.readLine()) != null){//percorre linha por linha
					stringBuffer.append(line+LS);	//adicionando stringBuffer
				}
				bufferedReader.close();//fecha leitor
				resultado=stringBuffer.toString();//passa para uma String o resultado
				return resultado;
			}finally{
				if(bufferedReader != null){
					try{
						bufferedReader.close();
					}catch(IOException e){
						e.printStackTrace();
					}	
				}
			}
		
		
	}
	
}


