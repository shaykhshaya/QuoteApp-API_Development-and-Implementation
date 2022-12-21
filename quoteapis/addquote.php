<?php

$servername = "localhost";
$username = "root";
$password = "";
$db = "quotedb";

$conn = new mysqli($servername, $username, $password, $db);

if($conn->connect_error){
	die("connection failed: ".$conn->connect_error);
}

$QUOTE = $_POST['quote'];
$QUOTE_NAME = $_POST['quote_name'];


$query = "INSERT INTO quote(quote_data,quote_name)VALUE('$QUOTE','$QUOTE_NAME')";

$result = $conn->query($query);

if($result==1){
	$response = array("status"=>"1", "message"=>"Quote successfully inserted");
}
else{
	$response = array("status"=>"0", "message"=>"Quote not successfullt inserted");
}
echo json_encode($response);
?>