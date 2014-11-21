<?php

	date_default_timezone_set('Asia/Seoul');
	header('Content-type: text/html; charset=utf8');
// 	header('Content-type: application/json');
	
	$con = mysqli_connect("localhost","root","","harutestapiserver");
	
	// Check connection
	if (mysqli_connect_errno()) {
		echo "Failed to connect to MySQL: " . mysqli_connect_error();
	}
	
	// news
	mysql_query("set session character_set_connection=utf8;");
	mysql_query("set session character_set_results=utf8;");
	mysql_query("set session character_set_client=utf8;");
	
	// journals
	$result = mysqli_query($con,"select * from journals");
	$journals = array();
	while($row = mysqli_fetch_array($result)){
		array_push($journals, $row[name]);
	}
	$journal_count = count($journals);
	
	// major_sections
	$result = mysqli_query($con,"select * from major_sections");
	$sections = array();
	while($row = mysqli_fetch_array($result)) {
		$section = new Section();
		$section->name = $row[name];
		$section->minors = array();
		
		$sub_result = mysqli_query($con,"SELECT minor.name AS name FROM minor_sections AS minor JOIN major_sections AS major ON minor.major_sections_id = major.id WHERE major.name = \"".$section->name."\"");
		while($sub_row = mysqli_fetch_array($sub_result)) {
			array_push($section->minors, $sub_row[name]);
		}
		
		$section->hotissues = array();
		
		array_push($sections, $section);
	}
	
	// hotissues
	$result = mysqli_query($con, "select * from hotissues");
	$boundaries = array(12, 26, 42, 60, 69, 77, 119);
	$i = 0;
	$count = 0;
	while($row = mysqli_fetch_array($result)) {
		$boundary = $boundaries[$i];
		$cur_section = $sections[$i];
		
		array_push($cur_section->hotissues, $row[name]);
		if ($boundary <= $count) {
			$i++;
		}
		$count++;
	}
	

	// response object
	class Response {
		
	}
	
	class Section {
		
	}

	class Article {
		
	}

	// get URI PATH
	$uri = $_SERVER['REQUEST_URI'];

	$delimiter = "newNewsApiServer.php";
	$splited_uri_arr = explode($delimiter, $uri);

	$host = "http://newsapi.naver.com";
	$new_url = $host.$splited_uri_arr[1];
	
	$url_info = parse_url($new_url);
	$path_arr = explode("/", $url_info["path"]);
	$query = $url_info["query"];


	// prepare response
	$response = new Response();
	//$info = new Info();
// 	echo $path_arr[1];

	// 첫번째가 root context임
	switch($path_arr[1]) {
	case "" :
		$response->name = "root";
		break;
		
	case "article" :
		$articles = array();
		
		for ($i=0; $i<count($sections); $i++) {
			$cur_section = $sections[$i];
			$cur_hotissues = $cur_section->hotissues;
// 			print_r($cur_hotissues);
			$cur_hotissues_count = count($cur_hotissues);
			$randNum = rand($cur_hotissues_count*5, $cur_hotissues_count*10);
// 			echo $cur_hotissues_count;
// 			echo "  ";
// 			echo $randNum;
// 			echo "  ";

			for ($j=0; $j<$randNum; $j++) {
				$now = new DateTime();
				$journalRandNum = rand(0, $journal_count-1);
				$hitsRandNum = rand(3000, 100000);
				$article = new Article();
				$minors_count = count($cur_section->minors);
				
				$article->hotissue = $cur_hotissues[$j%$cur_hotissues_count];
				$article->title = "title".(string)$j;
				$article->journal = $journals[$journalRandNum];
				$article->section = $cur_section->minors[$j%$minors_count];
				$article->date = $now->format('Y-m-d H:i:s');
				$article->content = "content".(string)$j;
				$article->hits = $hitsRandNum;
				$article->completed_reading_count = rand($hitsRandNum*0.2, $hitsRandNum*0.7);
				
				array_push($articles, $article);
			}
		}
		
		$response->responseArticles = $articles;
		break;	
	
	default :
		$response->name = "failure";
	}
	

	// convert to json and response
// 	$json = json_encode($response);
// 	$json = html_entity_decode($rson);
// 	echo iconv('euc-kr', 'UTF-8', json_encode($response, JSON_UNESCAPED_UNICODE));
	echo json_encode($response, JSON_UNESCAPED_UNICODE);
// 	$json = json_encode($response);
	
// 	echo iconv('Unicode', 'UTF-8', $json);

	
	
	
?>