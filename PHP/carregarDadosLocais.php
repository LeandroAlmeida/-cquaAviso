<?php
     header("Access-Control-Allow-Origin: *");

     $host="mysql14.000webhost.com"; // Host name
     $username="a3780406_Leo";; // Mysql username
     $password="..."; // Mysql password
     $db_name="a3780406_BD"; // Database name

     mysql_connect($host, $username, $password)or die("cannot connect");//criei uma variavel e fiz a conexao com servidor (server, usuario e senha)
     mysql_select_db($db_name) or die("cannot select DB");//seleciona o banco de dados(android) da conexao(server)


     $sql = "SELECT local, estado, latitude, longitude FROM `comentarios` GROUP BY local";//consulta sql
     $resultado = mysql_query($sql) or die("Erro:.". mysql_error());//passo uma string da consulta

     $linha = mysql_fetch_object($resultado);
       while($linha = mysql_fetch_object($resultado)){
                    echo $linha->local."#".$linha->estado."#".$linha->latitude."#".$linha->longitude."#";
      }
     // close MySQL connection
     mysql_close();
     exit;
?>
