<?php
$response = array();

require_once '/home/playspor/public_html/zoomagazin.eu/android/DataBaseConnect2.php';

$db = new DB_CONNECT();


if (isset($_GET['date'])) {
    $date = $_GET['date'];
}else{
	$date = date("y-m-d"); 
}

$result = mysql_query("select * from `News` where `date`>='$date' order by `date` desc limit 20") or die(mysql_error());

if (mysql_num_rows($result) > 0) {

    $response["news"] = array();

    while ($row = mysql_fetch_array($result)) {

        $news = array();
		$news["id"] = $row["id"];
        $news["nameBg"] = strip_tags($row["nameBg"]);
		$news["date"] = strip_tags($row["date"]);
		
		array_push($response["news"], $news);
    }

    $response["success"] = 1;

    echo json_encode($response);
} else {

    $response["success"] = 0;

    echo json_encode($response);
}
?>