<?php

$url = 'https://lirarate.org/wp-json/lirarate/v2/rates?currency=LBP&_ver=t2022431';
$json = file_get_contents($url);
$jo = json_decode($json);
$length = count($jo->sell);
$updated_sell = intval($jo->sell[$length - 1][1]);

?>
