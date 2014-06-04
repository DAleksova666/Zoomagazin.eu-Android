<?php
$response = array();

require_once '/home/playspor/public_html/zoomagazin.eu/android/DataBaseConnect.php';

$db = new DB_CONNECT();

if (isset($_GET['pet'])) {
    $pet = $_GET['pet'];
}else{
	$pet = '1';
}
switch ($pet) {
    case "1":
        $categoryId = '167';
        break;
    case "2":
        $categoryId = '195';
        break;
    case "3":
        $categoryId = '169';
        break;
	case "4":
        $categoryId = '168';
        break;
	case "5":
        $categoryId = '31';
        break;
	case "6":
        $categoryId = '295';
        break;
	default:
		$categoryId = '167';
		break;
}

if (isset($_GET['type'])) {
    $typeProd = $_GET['type'];
}else{
	$typeProd = '1';
}

switch ($typeProd) {
    case "1":
		$sql = mysql_query("select * from CatalogueProducts where `deleted`=0 and `giftOnly`=0 and NOW() < newProductDate AND mainCategory in ($categoryId) and `notAvailable`=0 order by `newProductAdded` desc, `newProductDate` desc limit 50") or die(mysql_error());
        break;
    case "2":
		$sql = mysql_query("select * from CatalogueProducts where `deleted`=0 and `giftOnly`=0 and `onSalePrice`<>0 AND mainCategory in ($categoryId) order by `id` asc limit 50") or die(mysql_error());
        break;
    case "3":
		$sql = mysql_query("select * from CatalogueProducts where (`promoPrice`!=0 or `promoCategoryPrice`<>0 or `promoManufacturerPrice`<>0 or `pseudoPromotion`=1  ) AND mainCategory in ($categoryId) order by `id` asc limit 50") or die(mysql_error()); 
        break;
	default:
		$sql = mysql_query("select * from CatalogueProducts where `deleted`=0 and `giftOnly`=0 and NOW() < newProductDate AND mainCategory in ($categoryId) and `notAvailable`=0 order by `newProductAdded` desc, `newProductDate` desc limit 50") or die(mysql_error());
		break;
}

if (mysql_num_rows($sql) > 0) {

    $response["products"] = array();

    while ($row = mysql_fetch_array($sql)) {
        $product = array();
		$product["id"] = $row["id"];
        $product["nameBg"] = strip_tags($row["nameBg"]);
		$product["descriptionBg"] = str_replace("&nbsp;", "",strip_tags($row["descriptionBg"]));
		if($row["onSalePrice"] != 0){
			$product["price"] = $row["onSalePrice"];
		}elseif ($row["promoPrice"] != 0) {
			$product["price"] = $row["promoPrice"];
		}elseif($row["promoCategoryPrice"] != 0){
			$product["price"] = $row["promoCategoryPrice"];
		}elseif($row["promoManufacturerPrice"] != 0){
			$product["price"] = $row["promoManufacturerPrice"];
		}else{
			$product["price"] = $row["price"];
		}
		$productId = $row["id"];
		$sql2 = mysql_query("select * from CatalogueProductsPictures where `productId` = $productId limit 1") or die(mysql_error());
		if (mysql_num_rows($sql2) > 0) {
			while ($row2 = mysql_fetch_array($sql2)) {
				$product["picture"] = $row2["picture"];
			}
		}

        array_push($response["products"], $product);
    }

    $response["success"] = 1;

    echo json_encode($response);
} else {

    $response["success"] = 0;

    echo json_encode($response);
}
?>