<?php
     header("Access-Control-Allow-Origin: *");
     $local =$_POST['local'];
     $estado =$_POST['estado'];
     
       $host="mysql14.000webhost.com"; // Host name
     $username="a3780406_Leo";; // Mysql username
     $password="120330Leo"; // Mysql password
     $db_name="a3780406_BD"; // Database name

     mysql_connect($host, $username, $password)or die("cannot connect");//criei uma variavel e fiz a conexao com servidor (server, usuario e senha)
     mysql_select_db($db_name) or die("cannot select DB");//seleciona o banco de dados(android) da conexao(server)

     $sql="select * from comentarios where local= '%$local%' and estado= '$estado' ORDER BY id  DESC";//consulta sql
     $resultado = mysql_query($sql) or die("Erro:.". mysql_error());//passo uma string da consulta

     while($linha = mysql_fetch_object($resultado)){
                  echo $linha->id."#".$linha->idUsuario."#".$linha->nome."#".$linha->email."#".$linha->tipo."#".$linha->local."#".$linha->estado."#".$linha->relato."#".$linha->curtida."#".$linha->ncurtida."#".$linha->data."#";
      }
     // close MySQL connection
     mysql_close();
?>
