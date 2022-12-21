<?php



$servername = "localhost";
$username = "root";
$password = "";
$db = "quotedb";

$conn = new mysqli($servername, $username, $password, $db);

if($conn->connect_error){
	die("connection failed: ".$conn->connect_error);
}

$q_id = $_POST['quote_id'];

$query = "DELETE FROM quote WHERE id = '$q_id'";

$result = $conn->query($query);

if($result){
	$response = array("status"=>"1", "message"=>"Quote DELETED successfully");
}
else{
	$response = array("status"=>"0", "message"=>"Quote not DELETED successfully");
}
echo json_encode($response);







?>