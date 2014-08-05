<?php
     header("Access-Control-Allow-Origin: *");
     $idcomentario =$_POST['idcomentario'];
     $idconta =$_POST['idconta'];


        $host="mysql14.000webhost.com"; // Host name
     $username="a3780406_Leo";; // Mysql username
     $password="120330Leo"; // Mysql password
     $db_name="a3780406_BD"; // Database name


     mysql_connect($host, $username, $password)or die("cannot connect");//criei uma variavel e fiz a conexao com servidor (server, usuario e senha)
     mysql_select_db($db_name) or die("cannot select DB");//seleciona o banco de dados(android) da conexao(server)

     $sql="select * from jacurtida where idcomentario= '$idcomentario' and  idconta= '$idconta'";//consulta sql
     $resultado = mysql_query($sql) or die("Erro:.". mysql_error());//passo uma string da consulta

     if(mysql_num_rows($resultado) > 0)
                                  echo '0';
     else
        echo '1';
        
     // close MySQL connection
     mysql_close();
?>
