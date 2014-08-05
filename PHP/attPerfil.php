<?php
  header("Access-Control-Allow-Origin: *");
  //$novonome=$_GET['novonome'];
  //$novoemail=$_GET['novoemail'];
  //$idconta=$_GET['idconta'];
  $novonome=$_POST['novonome'];
  $novoemail=$_POST['novoemail'];
  $idconta=$_POST['idconta'];

   $host="mysql14.000webhost.com"; // Host name
     $username="a3780406_Leo";; // Mysql username
     $password="..."; // Mysql password
     $db_name="a3780406_BD"; // Database name

  mysql_connect($host, $username, $password)or die("cannot connect");//criei uma variavel e fiz a conexao com servidor (server, usuario e senha)
  mysql_select_db($db_name) or die("cannot select DB");//seleciona o banco de dados(android) da conexao(server)



  $sql = "UPDATE conta SET nome = '$novonome',email='$novoemail' WHERE id = $idconta";
  $resultado = mysql_query($sql) or die ("Erro .:" . mysql_error());

   if($resultado)
		  echo "1";
   else
          echo "0";
   // close MySQL connection
   mysql_close();
   exit;
?>
