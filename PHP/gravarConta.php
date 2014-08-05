<?php
   header("Access-Control-Allow-Origin: *");
  //$usuario=$_GET['nome'];
  //$email=$_GET['email'];
  //$senha=$_GET['senha'];
  $usuario=$_POST['nome'];
  $email=$_POST['email'];
  $senha=$_POST['senha'];


     $host="mysql14.000webhost.com"; // Host name
     $username="a3780406_Leo";; // Mysql username
     $password="120330Leo"; // Mysql password
     $db_name="a3780406_BD"; // Database name


   mysql_connect($host, $username, $password)or die("cannot connect");//criei uma variavel e fiz a conexao com servidor (server, usuario e senha)
   mysql_select_db($db_name) or die("cannot select DB");//seleciona o banco de dados(android) da conexao(server)

  $sql = "insert into conta (nome, email,senha) values ('$usuario','$email', '$senha')";
  
  $resultado = mysql_query($sql) or die ("Erro .:" . mysql_error());
  if($resultado)
		  echo "1";
   else
          echo "0";

// close MySQL connection
   mysql_close();
   exit;
?>
