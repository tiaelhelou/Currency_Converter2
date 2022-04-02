<?php

include('ConnecttoDb\my_db.php');

$query = $mysqli->prepare("SELECT count(id) FROM rates;");
$query->execute();
$result = $query->get_result();
$row = mysqli_fetch_row($result);
$counter = $row[0]; 


$query = $mysqli->prepare("SELECT buy_rate FROM rates WHERE id = $counter;");
$query->execute();
$result = $query->get_result();

$buy = [];

while($rates = $result->fetch_assoc()){
    $buy[] = $rates;
}


$query = $mysqli->prepare("SELECT sell_rate FROM rates WHERE id = $counter;");
$query->execute();
$result = $query->get_result();

$sell = [];

while($rates = $result->fetch_assoc()){
    $sell[] = $rates;
}


$json_buy = json_encode($buy);
echo $json_buy;

$json_sell = json_encode($sell);
echo $json_sell;

?>