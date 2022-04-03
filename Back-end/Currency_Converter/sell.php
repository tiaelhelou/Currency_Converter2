<?php

$url = 'https://lirarate.org/wp-json/lirarate/v2/rates?currency=LBP&_ver=t2022431'; // url to gt from  it updated rate (Note that url keeps on changing 																						   everyday (ver= : changes only))
$json = file_get_contents($url); // Geting content of the url

$jo = json_decode($json); // Decoding JSON 

$length = count($jo->sell); // Getting the length of the array 

$updated_sell = intval($jo->sell[$length - 1][1]); // Getting updated rate of the sell which is the last element in the array

?>
