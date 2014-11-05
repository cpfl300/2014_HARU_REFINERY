<?php
	
	// response object
	class Response {
		
	}
	class Info {

	}

	// get URI PATH
	$uri = $_SERVER['REQUEST_URI'];

	$delimiter = "newsApiServer.php";
	$splited_uri_arr = explode($delimiter, $uri);

	$host = "http://newsapi.naver.com";
	$new_url = $host.$splited_uri_arr[1];
	
	$url_info = parse_url($new_url);
	$path_arr = explode("/", $url_info["path"]);
	$query = $url_info["query"];


	// prepare response
	$response = new Response();
	//$info = new Info();

	// 첫번째가 root context임
	switch($path_arr[1]) {
	case "" :
		$response->test = "hardlink-root";
		break;
	case "test" :
		$response->test = "success";
		break;
	default :
		$response->test = "failure";
	}

	// convert to json and response
	header('Content-type: application/json');
	echo json_encode($response);

?>