<?php

	date_default_timezone_set('Asia/Seoul');
	header('Content-type: application/json');
	

	// response object
	class Result {
		
	}
	
	
	class Response {
		
	}
	
	class Section {
		
	}

	class Article {
		
	}

	// get URI PATH
	$uri = $_SERVER['REQUEST_URI'];

	$delimiter = "apiserver.php";
	$splited_uri_arr = explode($delimiter, $uri);

	$host = "http://api.news.naver.com";
	$new_url = $host.$splited_uri_arr[1];
	
	$url_info = parse_url($new_url);
	$resource_arr = explode("next/", $url_info["path"]);
	$query = $url_info["query"];

	
// 	http://api.news.naver.com/main/export/v2/outside/next/rankingArticlesRealtime.nhn?sectionId=100&rankingType=hit
// 	http://api.news.naver.com/main/export/v2/outside/next/rankingArticles.nhn?sectionId=100&rankingType=popular_day&datehour=2014113017
// 	http://api.news.naver.com/main/export/v2/outside/next/rankingArticles.nhn?sectionId=100&rankingType=popular_day&datehour=20141129
// 	http://api.news.naver.com/main/export/v2/outside/next/articles.nhn?datehour=20140124103
// 	http://api.news.naver.com/main/export/v2/outside/next/read.nhn?officeId=073&articleId=0002377584
// 	http://api.news.naver.com/main/export/v2/outside/next/hotissues.nhn
// 	http://api.news.naver.com/main/export/v2/outside/next/hotissueArticles.nhn?componentId=887553
	
	// prepare Result
	$result = new Result();

	// 첫번째가 root context임
	switch($resource_arr[1]) {
	case "rankingArticlesRealtime.nhn" :
		$result->name = "rankingArticelsRealtime";
		break;
		
	case "rankingArticles.nhn" :
		$result->name = "rankingArticels";
		break;
		
	case "articles.nhn" :
		$result->name = "articles";
		break;
		
	case "read.nhn" :
		$result->name = "read";
		break;
		
	case "hotissues.nhn" :
		$result->name = "hotissues";
		break;
		
	case "hotissueArticles.nhn" :
		$result->name = "hotissueArticles";
		break;
	
	default :
		$result->name = "failure";
	}
	

	// convert to json and response
	$json = json_encode($result);
// 	$json = html_entity_decode($rson);
// 	echo iconv('euc-kr', 'UTF-8', json_encode($response, JSON_UNESCAPED_UNICODE));
// 	echo json_encode($response, JSON_UNESCAPED_UNICODE);
// 	$json = json_encode($response);
	
// 	echo iconv('Unicode', 'UTF-8', $json);

	
	
	
?>