package com.br.AquaAviso;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Conta extends ListActivity{
	 String [] listaUsuarios=new String[]{"Perfil","Senha","Excluir Conta","Voltar","Sair"};
		
	 int posicao=0;
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setTitle("Editar");
			ArrayAdapter<String> aaCursos = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaUsuarios);
	        setListAdapter(aaCursos);
	       
	 }
	 
	 protected void onListItemClick(ListView l, View v, int position, long id)
	 {
		 super.onListItemClick(l, v, position, id);
		// Object objetoSelecionado = this.getListAdapter().getItem(position);
		// String nomeCurso = objetoSelecionado.toString();
		 switch(position){
		 case 0:
			 passarTela(0);
			 break;
		 case 1:
			 passarTela(1);
			 break;
		 case 2:
			 passarTela(2);
			 break;
		 case 3:
			 startActivity(new Intent(Conta.this,MenuInicial.class));
			 finish();
			 break;
		 default:
			 deleteFile("dados.txt");
			 startActivity(new Intent(Conta.this, MainActivity.class));
			 finish();
			
			 break;
		 }

	 }
	 
	 public void passarTela(int identificador){
		 Intent intent = new Intent(Conta.this, EditarConta.class);
         intent.putExtra("opcao", identificador);
         startActivity(intent);
         finish();
	 }
	 
	 public void onBackPressed(){
	        super.onBackPressed();
	        startActivity(new Intent(Conta.this, MenuInicial.class));
			 finish();
	    }
	 
	 
	 
	 public void mensagem(String titulo, String texto){
			
			AlertDialog.Builder msg = new AlertDialog.Builder(Conta.this);
			msg.setTitle(titulo);
			msg.setMessage(texto);
			msg.setNegativeButton("Ok", null);
			msg.show();
		}
}
	 
