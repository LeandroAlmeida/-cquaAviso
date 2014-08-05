<?php
  header("Access-Control-Allow-Origin: *");
  $idusuario=$_POST['idusuario'];
  $nome  =$_POST['nome'];
  $email  =$_POST['email'];
  $tipo  =$_POST['tipo'];
  $local  =$_POST['local'];
  $estado  =$_POST['estado'];
  $relato  =$_POST['relato'];
  $data  =$_POST['data'];
  $latitude  =$_POST['latitude'];
  $longitude  =$_POST['longitude'];


    $host="mysql14.000webhost.com"; // Host name
     $username="a3780406_Leo";; // Mysql username
     $password="120330Leo"; // Mysql password
     $db_name="a3780406_BD"; // Database name

  mysql_connect($host, $username, $password)or die("cannot connect");//criei uma variavel e fiz a conexao com servidor (server, usuario e senha)
  mysql_select_db($db_name) or die("cannot select DB");//seleciona o banco de dados(android) da conexao(server)

  $sql = "INSERT INTO comentarios (id, idUsuario, nome, email, tipo, local, estado, relato, curtida, ncurtida, data,latidude,longitude) VALUES (NULL, $idusuario, '$nome', '$email', '$tipo', '$local', '$estado', '$relato', 0, 0, '$data','$latitude','$longitude')";

  $resultado = mysql_query($sql) or die ("Erro .:" . mysql_error());
  if($resultado)
		  echo "1";
   else
          echo "0";
   // close MySQL connection
   mysql_close();
?>
