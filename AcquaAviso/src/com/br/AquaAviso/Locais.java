package com.br.AquaAviso;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;



























import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.CancelableCallback;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Locais extends FragmentActivity{
	String url=MainActivity.url;
	ArrayList<DadosLocais> listaDados;
	//ArrayList<PontosMarker> listasPontosOcorrencia;
	LatLng temp;
	private ProgressDialog progress;
	
	private SupportMapFragment mapFrag;
	private GoogleMap map;
	private Marker marker;
	
	TextView text;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.locais);
		String resposta;
		GoogleMapOptions options = new GoogleMapOptions();
		options.zOrderOnTop(true);
		mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment1);
		
		
		
		resposta=carregarDados();
		if(!resposta.equals("0")){
			//lerDados(resposta);
			lerDadosMontarMapa(resposta);
			//String aux = montarMapa(resposta);
			//map = mapFrag.getMap();
		//	configMap();
			/*text = (TextView)findViewById(R.id.textLocais);
			String teste="";
			for(DadosLocais d:listaDados){
				teste+=d.getLocal()+", "+d.getEstado()+"\n";
				}
			text.setText(teste);*/
			
			
			
		}else{//ver aqui depois
			mensagem("Resposta","Não foi encontrado nenhum relato neste local!!!");
		}
		
	}


	private String carregarDados() {
		String s = "";
		AsyncTask<String,String,String> tread;
		 
		try {
			tread=new Conexao(Locais.this).execute(url+"/carregarDadosLocais.php","0");
			s = tread.get();
		    Log.i("ExeirRelatos","respostaLogin 2 = "+s);
		} catch (Exception e) {
		    s="0";
		}
		 return s;
	}

	private void lerDadosMontarMapa(String msg) {
		
		new AsyncTask<String,String,String>(){
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				progress = new ProgressDialog(Locais.this);
				progress.setMessage("Carregando...");
				progress.show();
			}
			
			@Override
			protected String doInBackground(String... params) {
					progress.setMessage("Carregando...");
					
					StringTokenizer st;
					st = new StringTokenizer(params[0], "#");
					int cont=0;
					LatLng latLng;
					String aux="";
					listaDados=new ArrayList<DadosLocais>();
					DadosLocais dados=new DadosLocais();
					while (st.hasMoreTokens()) {
						if(cont==0)
							dados.setLocal(st.nextToken());
						else if(cont==1)
							dados.setEstado(st.nextToken());
						else if(cont==2)
							aux=st.nextToken();
						else if(cont==3){
							latLng= new LatLng(Double.parseDouble(aux),Double.parseDouble(st.nextToken()));
							dados.setCoordenada(latLng);
						}
						
						cont++;
						if(cont==4){
							listaDados.add(dados);
							cont=0;
							dados=new DadosLocais();
						}
					   // tokens.add(st.nextToken());  
					}
					 Log.i("ExeirRelatos","Passei aquiii1");
				return "";
			}
			
			protected void onPostExecute(String resultado) {
				//ti.depoisDownload(params);
				super.onPostExecute(resultado);
				progress.dismiss();
				
				 Log.i("ExeirRelatos","Passei aquiii2");
				configMap();
				
			}
			
		}.execute(msg);
	
	}
	
public void mensagem(String titulo, String texto){
		
		AlertDialog.Builder msg = new AlertDialog.Builder(Locais.this);
		msg.setTitle(titulo);
		msg.setMessage(texto);
		msg.setNegativeButton("Ok", null);
		msg.show();
	}

public void configMap(){
	
	map = mapFrag.getMap();
	map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
	 Log.i("ExeirRelatos","Passei aquiii2.3");
	LatLng latLng;
	
	LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
	Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	if (location != null) {
	   latLng= new LatLng(location.getLatitude(),location.getLongitude());//-7.120034,-34.876211
	}
	else
		latLng= new LatLng(-7.120034,-34.876211);//-7.120034,-34.876211
	
	
	 Log.i("ExeirRelatos","Passei aquiii3");
	CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(8).bearing(0).tilt(0).build();
	CameraUpdate update = CameraUpdateFactory.newCameraPosition(cameraPosition);
	
	//map.moveCamera(update);
	map.animateCamera(update, 3000, new CancelableCallback(){
		@Override
		public void onCancel() {
			Log.i("LocaisMoveCameraCancel", "CancelableCallback.onCancel()");
		}

		@Override
		public void onFinish() {
			Log.i("LocaisMoveCameraOk", "CancelableCallback.onFinish()");
		}
	});
	
	// MARKERS
//	getLocation("Rua Vergueiro","SP");
	 Log.i("ExeirRelatos","Passei aquiii4");
		for(DadosLocais d:listaDados){
				customAddMarker(d.getCoordenada(),d.getLocal(),d.getEstado());
		}
		
		map.setInfoWindowAdapter(new InfoWindowAdapter(){

			@Override
			public View getInfoContents(Marker marker) {//ALTERAA MSGEM DO BALAÃO
				LinearLayout ll = new LinearLayout(Locais.this);
				TextView tv = new TextView(Locais.this);
				tv.setText(Html.fromHtml("<b><font color=\"black\">Local:</font></b> <i> <font color=\"black\">"+marker.getTitle()+" | "+marker.getSnippet()+"</font></i> <br /> <font color=\"red\">Clique aqui para realizar uma consulta!</font>"));
				return tv;
			}

			@Override
			public View getInfoWindow(Marker marker) {//altera o balao
				/*LinearLayout ll = new LinearLayout(MainActivity.this);
				ll.setPadding(20, 20, 20, 20);
				ll.setBackgroundColor(Color.GREEN);
				
				TextView tv = new TextView(MainActivity.this);
				tv.setText(Html.fromHtml("<b><font color=\"#ffffff\">"+marker.getTitle()+":</font></b> "+marker.getSnippet()));
				ll.addView(tv);
				
				Button bt = new Button(MainActivity.this);
				bt.setText("Botão");
				bt.setBackgroundColor(Color.RED);
				bt.setOnClickListener(new Button.OnClickListener(){

					@Override
					public void onClick(View v) {
						Log.i("Script", "Botão clicado");
					}
					
				});
				
				ll.addView(bt);*/
				
				return null;//não quero alrerar o balão
			}
			
		});
	
	
	// EVENTSPara o marcador andar com
	/*map.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
			@Override
			public void onCameraChange(CameraPosition cameraPosition) {
				//Log.i("Locais",cameraPosition.target.latitude+ ", "+cameraPosition.target.longitude);
				
				if(marker != null){
					marker.remove();
				}
				//customAddMarker(new LatLng(cameraPosition.target.latitude, cameraPosition.target.longitude), "1: Marcador Alterado", "O Marcador foi reposicionado");
				
			}
		});*/
		//clicar adicionar um marcador
		map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
			@Override
			public void onMapClick(LatLng latLng) {
				Log.i("Script", "setOnMapClickListener()");
				if(marker != null){
					marker.remove();
				}
				Log.i("Locais",latLng.latitude+", "+ latLng.longitude);
				//customAddMarker(new LatLng(latLng.latitude, latLng.longitude), "2: Marcador Alterado", "O Marcador foi reposicionado");
				
			}
		});
		
		map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
			@Override
			public boolean onMarkerClick(Marker marker) {
				Log.i("Script", "3: Marker: "+marker.getTitle());
				return false;
			}
		});
		
		map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
			@Override
			public void onInfoWindowClick(Marker marker) {
				Log.i("Script", "4: Marker: "+marker.getTitle());
				Intent intent = new Intent(Locais.this, ExibirRelatos.class);
                intent.putExtra("local", marker.getTitle());
                intent.putExtra("estado", marker.getSnippet());
                startActivity(intent);
				
			//	Toast.makeText(Locais.this, "Voce clicou para realizar uma consulta!", Toast.LENGTH_LONG).show();
			}
		});
}


public void customAddMarker(LatLng latLng, String title, String snippet){
	MarkerOptions options = new MarkerOptions();
	options.position(latLng).title(title).snippet(snippet);//.draggable(true)/caso queira que ele seja arrastavel
	options.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin));
	
	marker = map.addMarker(options);
}
/*
public LatLng getLocation(String local, String estado){
	Geocoder gc = new Geocoder(Locais.this, new Locale("pt","br"));
	List<Address> addressList;
	Log.i("Locais", "Passei aqui ");
	//addressList = gc.getFromLocation(list.get(list.size() - 1).latitude, list.get(list.size() - 1).longitude, 1);
	//addressList = gc.getFromLocationName(local+", "+ estado+", brasil", 1);
	LatLng ll= getGeoPoint(getLocationInfo(local+", "+ estado+", Brasil"));
/*
	String address = "Rua: "+addressList.get(0).getThoroughfare()+"\n";
	address += "Cidade: "+addressList.get(0).getSubAdminArea()+"\n";
	address += "Estado: "+addressList.get(0).getAdminArea()+"\n";
	address += "País: "+addressList.get(0).getCountryName();
	Log.i("Locais", "Passei aqui 2");
	LatLng ll;
	if(addressList==null || addressList.size()==0){
		Log.i("Locais", "Passei aqui 6");
		//Toast.makeText(MainActivity.this, "Local: "+address, Toast.LENGTH_LONG).show();
		//Toast.makeText(Locais.this, "LatLng: "+ll, Toast.LENGTH_LONG).show();
		return null;
		
	}
	Log.i("Locais", "Passei aqui 3");
	Log.i("Locais", "Passei aqui 4"+ll);
	//Log.i("Locais", "Passei aqui 5"+addressList.get(0).getLongitude());
	//ll = new LatLng(addressList.get(0).getLatitude(), addressList.get(0).getLongitude());
	return ll;
}

public static JSONObject getLocationInfo(String address) {

	HttpGet httpGet=null;
	try {
		httpGet = new HttpGet("http://maps.googleapis.com/maps/api/geocode/json?address=" +URLEncoder.encode(address,"UTF-8")+ "&sensor=false");
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
		e.printStackTrace();
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

}*/
/*private String montarMapa(String resposta) {

new AsyncTask<String,String,String>(){
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		progress = new ProgressDialog(Locais.this);
		progress.setMessage("Carregando...");
		progress.show();
	}
	
	@Override
	protected String doInBackground(String... params) {
		progress.setMessage("Carregando...");
		
		//String resposta=getMarketString();
		

		return null;
	}
	
	protected void onPostExecute(String resultado) {
		//ti.depoisDownload(params);
		super.onPostExecute(resultado);
		progress.dismiss();
		GoogleMapOptions options = new GoogleMapOptions();
		options.zOrderOnTop(true);
		mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment1);
		map = mapFrag.getMap();
		configMap();
		
	}
	
}.execute(resposta);


return "";
}

private String getMarketString(){

/*for(DadosLocais d:listaDados){
	LatLng l=getLocation(d.getLocal(),d.getEstado());
	if(l!=null){
		Log.i("Script", "Local: "+d.getLocal()+"pontos "+l);
		PontosMarker p= new PontosMarker(l,d.getLocal(),d.getEstado());
		listasPontosOcorrencia.add(p);
	}
}
return "";
}*/


}
