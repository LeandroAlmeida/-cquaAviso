<?php
     //header("Content-type: application/javascript");
     header("Access-Control-Allow-Origin: *");
     //$nome =$_GET['nome'];//pega o usuario da url   get mostra a url
     //$senha =$_GET['senha'];  //pega a senha da url
     $nome =$_POST['nome'];
     $senha =$_POST['senha'];
     $host="mysql14.000webhost.com"; // Host name
     $username="a3780406_Leo";; // Mysql username
     $password="120330Leo"; // Mysql password
     $db_name="a3780406_BD"; // Database name


     mysql_connect($host, $username, $password)or die("cannot connect");//criei uma variavel e fiz a conexao com servidor (server, usuario e senha)
     mysql_select_db($db_name) or die("cannot select DB");//seleciona o banco de dados(android) da conexao(server)
     $sql="select * from conta where nome= '$nome' and senha= '$senha'";//consulta sql
     $resultado = mysql_query($sql) or die("Erro:.". mysql_error());//passo uma string da consulta
     
     if(mysql_num_rows($resultado) > 0)
                                  echo '1';
     else
        echo '0';
     // close MySQL connection
     mysql_close();
     exit;
?>

