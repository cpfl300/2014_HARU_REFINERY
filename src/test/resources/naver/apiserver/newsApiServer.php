<?php
	date_default_timezone_set('Asia/Seoul');

	// response object
	class Response {
		
	}
	class Info {

	}
	
	class Article {
		
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
		$response->name = "root";
		break;
		
	case "article" :
		$articles = array();
		for ($i=0; $i<100; $i++) {
			$now = new DateTime();
			
			$article = new Article();
			$article->hotissue = "hotissue".(string)$i;
			$article->subject = "subject".(string)$i;
			$article->journal = "journal".(string)$i;
			$article->section = "section".(string)$i;
			$article->date = $now->format('Y-m-d H:i:s');
			$article->content = "content".(string)$i;
			
			$articles[$i] = $article;
		}
		
		
		$response->articles = $articles;
		break;	
	
	default :
		$response->name = "failure";
	}

	// convert to json and response
	header('Content-type: application/json');
	echo json_encode($response);

?>