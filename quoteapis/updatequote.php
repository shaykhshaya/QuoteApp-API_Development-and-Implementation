<?php



$servername = "localhost";
$username = "root";
$password = "";
$db = "quotedb";

$conn = new mysqli($servername, $username, $password, $db);

if($conn->connect_error){
	die("connection failed: ".$conn->connect_error);
}

$QUOTE = $_POST['quote_data'];
$ID = $_POST['id'];

$query = "UPDATE quote SET quote_data = '$QUOTE' WHERE id = '$ID'";

$result = $conn->query($query);

if($result){
	$response = array("status"=>"1", "message"=>"Quote UPDATED successfully");
}
else{
	$response = array("status"=>"0", "message"=>"Quote not UPDATED successfully");
}
echo json_encode($response);







?>