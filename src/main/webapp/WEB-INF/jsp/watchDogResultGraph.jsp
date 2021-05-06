<!-- 
2020.03.18 kwb
- 프로젝트와 수행일시를 선택하였을 때, Chart.js의 scatter chart를 사용하여 mse값을 x축, ratio값을 y축으로 하는 라인 그래프를 출력하는 화면 구현
- 기존의 watchDogResult.jsp, watchDogResultItem.jsp 코드 활용
-->

<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!doctype html>
<html lang="ko">
<head>
	<meta charset="UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
	<meta name="description" content="WatchDog 결과 조회 화면" />
	
	<title>WatchDog 결과 조회</title>
	
	<!-- External CSS -->
	<link rel="stylesheet" href="/webjars/bootstrap/4.3.1/dist/css/bootstrap.min.css" />
	<link rel="stylesheet" href="/webjars/chart.js/dist/Chart.min.css" />
	<link rel="stylesheet" href="/webjars/font-awesome/5.12.0/css/fontawesome.min.css" />
	
	<!-- Custom CSS -->
	<link rel="stylesheet" href="main.css" />
	<link rel="stylesheet" href="not-main.css" />
	<link rel="stylesheet" href="font.css" />
</head>

<body>
	<nav
		class="navbar navbar-dark fixed-top bg-dark flex-md-nowrap p-0 shadow">
		<a class="navbar-brand col-sm-3 col-md-2 mr-0" href="#">NICE평가정보</a>
	</nav>
	<div class="container-fluid">
		<div class="row">
			<nav class="col-md-2 d-none d-md-block bg-light sidebar">
                <div class="sidebar-sticky">
                    <ul class="nav flex-column flex-nowrap overflow-hidden">
                        <li class="nav-item">
                        	<a class="nav-link text-truncate" href="/main">
                        		<i class="fas fa-home"></i>
                        		<span class="d-none d-sm-inline">Home</span>
                        	</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link active text-truncate" href="#submenu1" data-toggle="collapse" data-target="#submenu1">
                                <i class="fas fa-database"></i>
                                <span class="d-none d-sm-inline">배치 파일 이상감지</span>
                            </a>
                            <div class="collapse show" id="submenu1" aria-expanded="false">
                                <ul class="flex-column pl-4 nav">
                                    <li class="nav-item">
                                        <a class="nav-link text-truncate" href="#submenu1-1" data-toggle="collapse" data-target="#submenu1-1">
                                            <i class="fas fa-play"></i> 이상탐지 실행
                                        </a>
                                        <div class="collapse" id="submenu1-1" aria-expanded="false">
                                            <ul class="flex-column pl-4 nav">
                                                <li class="nav-item">
                                                    <a class="nav-link py-1" href="/makeproperty">
                                                        <i class="fas fa-keyboard"></i> 데이터 레이아웃 파일 생성
                                                    </a>
                                                </li>
                                                <li class="nav-item">
                                                    <a class="nav-link py-1" href="/makecsv">
                                                        <i class="fas fa-file-csv"></i>  CSV 만들기
                                                    </a>
                                                </li>
                                                <li class="nav-item">
                                                    <a class="nav-link py-1" href="/makemodel">
                                                        <i class="fas fa-laptop-code"></i> 모형개발 및 기준정보 생성
                                                    </a>
                                                </li>
                                                <li class="nav-item">
                                                    <a class="nav-link py-1" href="/predict">
                                                        <i class="fas fa-search"></i> 데이터 이상감지
                                                    </a>
                                                </li>
                                                <li class="nav-item">
                                                    <a class="nav-link py-1" href="/predictmulti">
                                                        <i class="fas fa-search-plus"></i> 데이터 이상감지(복수)
                                                    </a>
                                                </li>
                                            </ul>
                                        </div>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link active text-truncate" href="#submenu1-2" data-toggle="collapse" data-target="#submenu1-2">
                                            <i class="fas fa-chalkboard-teacher"></i>
                                            <span class="d-none d-sm-inline">결과 조회</span>
                                        </a>
                                        <div class="collapse show" id="submenu1-2" aria-expanded="false">
                                            <ul class="flex-column pl-4 nav">
                                                <li class="nav-item">
                                                    <a class="nav-link py-1" href="/result">
                                                        <i class="fas fa-table"></i> 수행 결과 조회
                                                    </a>
                                                </li>
                                                <li class="nav-item">
                                                    <a class="nav-link active py-1" href="/resultgraph">
                                                        <i class="fas fa-chart-line"></i> 수행 결과 조회(그래프)
                                                        <span class="sr-only">(current)</span>
                                                    </a>
                                                </li>
                                                <li class="nav-item">
                                                    <a class="nav-link py-1" href="/resultitem"> 
                                                        <i class="fas fa-table"></i> 항목 이상 결과 조회
                                                    </a>
                                                </li>
                                                <li class="nav-item">
                                                    <a class="nav-link py-1" href="/resultitemdist">
                                                        <i class="fas fa-poll-h"></i> 항목 분포 변화 조회
                                                    </a>
                                                </li>
                                                <li class="nav-item">
                                                    <a class="nav-link py-1" href="/dashboard">
                                                        <i class="fas fa-tachometer-alt"></i> 대시보드
                                                    </a>
                                                </li>
                                                <li class="nav-item">
                                                    <a class="nav-link py-1" href="/deletedata">
                                                        <i class="fas fa-folder-minus"></i> 데이터 삭제
                                                    </a>
                                                </li>
                                             </ul>
                                        </div>
                                    </li>
                                </ul>
                            </div>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link collapsed text-truncate" href="#submenu2" data-toggle="collapse" data-target="#submenu2">
                                <i class="far fa-server"></i>
                                <span class="d-none d-sm-inline">Online 이상감지</span>
                            </a>
                            <div class="collapse" id="submenu2" aria-expanded="false">
                                <ul class="flex-column pl-4 nav">
                                    <li class="nav-item">
                                        <a class="nav-link text-truncate" href="#submenu2-2" data-toggle="collapse" data-target="#submenu2-2">
                                            <i class="fas fa-chalkboard"></i>
                                            <span class="d-none d-sm-inline">Online 결과 조회</span>
                                        </a>
                                        <div class="collapse" id="submenu2-2" aria-expanded="true">
                                            <ul class="flex-column pl-4 nav">
                                                <li class="nav-item">
                                                    <a class="nav-link py-1" href="/online/result">
                                                        <i class="fas fa-table"></i> Online 수행 결과 조회 
                                                    </a>
                                                </li>
                                                <li class="nav-item">
                                                    <a class="nav-link py-1" href="/online/rowresult">
                                                        <i class="fas fa-poll"></i> 이상 확률 분포 조회
                                                    </a>
                                                </li>
                                                <li class="nav-item">
                                                    <a class="nav-link py-1" href="/online/rowdist"> 
                                                        <i class="fas fa-chart-bar"></i> 이상 확률 분포 그래프
                                                    </a>
                                                </li>
                                                <li class="nav-item">
                                                    <a class="nav-link py-1" href="/online/itemresult"> 
                                                        <i class="fas fa-table"></i> 항목 이상 확률 조회
                                                    </a>
                                                </li>
                                             </ul>
                                        </div>
                                    </li>
                                </ul>
                            </div> 
                        </li>
                    </ul>
                </div>
            </nav>

			<main role="main" class="col-md-10 ml-sm-auto col-lg-10 px-4">
				<div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
					
					<h1 class="h2">WatchDog 수행 결과(그래프)</h1>
					
					<!-- 2020.03.27 kwb 드롭다운 메뉴가 잘려보이지 않도록, 페이지를 왔다갔다 이동시에 드롭다운 메뉴의 위치가 고정되어 보이도록 처리 -->
					<div align="right">
						<span class="dropdown" style="margin-right:100px;">
							<button type="button"
								class="btn btn-sm btn-outline-secondary dropdown-toggle"
								type="button" id="projectNameDropdown" data-toggle="dropdown"
								aria-haspopup="true" aria-expanded="false">프로젝트 선택</button>
							<div class="dropdown-menu dropdown-menu-right" id="projectNameDropdownMenu" aria-labelledby="projectNameDropdown" style="max-height: 400px; overflow-y: auto"></div>
						</span>
						<span class="dropdown">
							<button type="button"
								class="btn btn-sm btn-outline-secondary dropdown-toggle"
								type="button" id="regdtimDropdown" data-toggle="dropdown"
								aria-haspopup="true" aria-expanded="false">수행일시 선택</button>
							<div class="dropdown-menu dropdown-menu-right" id="regdtimDropdownMenu" aria-labelledby="regdtimDropdown" style="max-height: 400px; overflow-y: auto"></div>
						</span>
					</div>
				</div>
	
				<canvas id="resultGraph"></canvas>
			</main>
		</div>
	</div>

	<!-- js 파일 적용 -->
	<script src="/webjars/jquery/dist/jquery.min.js"></script>
	<script src="/webjars/bootstrap/4.3.1/dist/js/bootstrap.bundle.js"></script>
	<script src="/webjars/chart.js/dist/Chart.min.js"></script>
	<script src="/webjars/font-awesome/5.12.0/js/fontawesome.min.js"></script>
    
    <!-- 그래프 세팅 관련 -->
	<script>
		var projectName="";
		var regdtim="";
		var chart = null;

		var fn_setProjectNameDropdown = function(url, target) {			    
			$.ajax({
				type : "GET",
				url : url,
				dataType : "json",
				cache : false,
				success : function(projectNameList) {
					var listName = projectNameList;
					$.each(listName, function(index) {
						var newProjectName = '<a class="dropdown-item" href="#">'+listName[index]+'</a>';
						$(newProjectName).appendTo('#projectNameDropdownMenu');
					});
		                
					$("#projectNameDropdownMenu a").click(function(){
						projectName = $(this).text();
						$(this).parents('.dropdown').find('.dropdown-toggle').html(projectName);
						fn_setRegdtimDropdown("<c:url value='/resultgraph/" + projectName + "/regdtim' />", $('#regdtimDropdown'))
					});
				},
				error : function(request, status, error) {
					alert('code:'+request.status+'\n'+'message:'+request.responseText+'\n'+'error:'+error);
				}
			});
		};
		    
		//2019-12-20 hjy 프로젝트 선택 창 변경시 수행일시가 Reset이 안되고 계속 Add되는 현상 조치를 위한 함수
		function initRegdtimDropdown() {
			$("#regdtimDropdownMenu").empty();
			$("#regdtimDropdown").text('수행일시 선택');
		}
		    
		var fn_setRegdtimDropdown = function(url, target) {
			$.ajax({
				type : "GET",
				url : url, 
				dataType : "json",
				cache : false,
				success : function(regdtimList) {
		            	
					initRegdtimDropdown();
			            	
					var listName = regdtimList;
					$.each(listName, function(index) {
						var newRegdtim = '<a class="dropdown-item" href="#">'+listName[index]+'</a>';
						$(newRegdtim).appendTo('#regdtimDropdownMenu');
					});
			                
					$("#regdtimDropdownMenu a").click(function(){
						regdtim = $(this).text();
						$(this).parents('.dropdown').find('.dropdown-toggle').html(regdtim);
						fn_setResultGraph("<c:url value='/resultgraph/" + projectName + "/" + regdtim + "' />")
					});
				},
				error : function(request, status, error) {
					alert('code:'+request.status+'\n'+'message:'+request.responseText+'\n'+'error:'+error);
				}
			});
		};

		// 선택한 프로젝트와 수행일시에 해당하는 결과를 라인 그래프(scatter chart)로 출력
		var fn_setResultGraph = function(url, target) {
			$.ajax({
				type : "GET",
				url : url, 
				dataType : "json",
				cache : false,
				success : function(resultGraphPoints) {
					/* 
					2020.04.07 kwb
					- X축 기준으로 '0.000 ~ 최대 mse값'에 대해서 0.001 간격으로 모든 점을 찍도록 변경
					- 기존에는 DB에 저장되어 있는 mse값과 ratio값만 각각 x좌표, y좌표로 사용해서 점을 찍었음
					*/
				    
					if(chart != null){
						chart.destroy();
					}
					var lengthDevMseNRatioList = resultGraphPoints.devMseNRatioList.length;	// 개발(기준)시점의 오브젝트(mse, ratio) 개수
					var lengthNowMseNRatioList = resultGraphPoints.nowMseNRatioList.length; // 현재시점의 오브젝트(mse, ratio) 개수
					var maxMseDev = parseFloat(resultGraphPoints.devMseNRatioList[lengthDevMseNRatioList-1].mse);	// 개발(기준)시점의 최대 mse값
					var maxMSeNow = parseFloat(resultGraphPoints.nowMseNRatioList[lengthNowMseNRatioList-1].mse);	// 현재시점의 최대 mse값
					var maxMse = maxMseDev > maxMSeNow ? maxMseDev : maxMSeNow;	// 개발시점과 현재시점 중 가장 큰 mse값

					var pointsDev = [];	// 개발(기준)시점 좌표 리스트
					var devIndex=0;	// devMseNRatioList의 인덱스로 사용

					var pointsNow = [];	// 현재시점 좌표 리스트
					var nowIndex=0; // devMseNRatioList의 인덱스로 사용
					
					// 개발(기준)시점의 mse값과 ratio값을 좌표값으로 변환해서 리스트에 저장
					for (var x = 0.000; x.toFixed(3) <= maxMse; x = x + 0.001){
						// 'x=x+0.001'과 같은 연산을 진행하다보면 '0.003000000001'과 같은 식으로 오류가 발생하는 경우가 있어서 '.toFixed(3)'를 통해 소수점 셋째 자리까지만 사용
												
						// x좌표값과 동일한 mse값이 있으면, y좌표로 해당 mse값의 ratio값을 사용
						if(x.toFixed(3) == parseFloat(resultGraphPoints.devMseNRatioList[devIndex].mse)){
							pointsDev.push({x: String(x.toFixed(3)), y: resultGraphPoints.devMseNRatioList[devIndex].ratio});
							// 인덱스가 리스트의 최대 인덱스를 넘지않도록 처리 
							if(devIndex + 1 < lengthDevMseNRatioList){
								devIndex++;
							}
						}
						// x좌표값과 동일한 mse값이 없으면, y좌표로 0을 사용 
						else{
							pointsDev.push({x: String(x.toFixed(3)), y: String(0)});
						}
					}
					
					// 현재시점의 mse값과 ratio값을 좌표값으로 변환해서 리스트에 저장
					for (var x = 0.000; x.toFixed(3) <= maxMse; x = x + 0.001){
						// 'x=x+0.001'과 같은 연산을 진행하다보면 '0.003000000001'과 같은 식으로 오류가 발생하는 경우가 있어서 '.toFixed(3)'를 통해 소수점 셋째 자리까지만 사용
						
						// x좌표값과 동일한 mse값이 있으면 y좌표로 해당 mse값의 ratio값을 사용
						if(x.toFixed(3) == parseFloat(resultGraphPoints.nowMseNRatioList[nowIndex].mse)){
							pointsNow.push({x: String(x.toFixed(3)), y: resultGraphPoints.nowMseNRatioList[nowIndex].ratio});
							// 인덱스가 리스트의 최대 인덱스를 넘지않도록 처리 
							if(nowIndex + 1 < lengthNowMseNRatioList){
								nowIndex++;
							}
						}
						// x좌표값과 동일한 mse값이 없으면, y좌표로 0을 사용
						else{
							pointsNow.push({x: String(x.toFixed(3)), y: String(0)});
						}
					}

					// 차트 출력
					var ctx = document.getElementById('resultGraph').getContext('2d'); 
					chart = new Chart(ctx, { 
						// 차트 종류
						type: 'scatter',
						// 차트 데이터 
						data: {
							datasets: [
								{ 
									label: '기준',
									//데이터
									data: pointsDev,
									//배경
		    						backgroundColor: 'transparent', 
									//라인
		    						showLine: true,
		    						borderColor: 'rgb(54, 162, 235)', //blue
		    						borderWidth: 3,
		    						//포인트
		    						pointHoverRadius: 10
		    					},
		    					{
		    						label: '현재',
		    						//데이터
		    						data: pointsNow,
		    						//배경
		    						backgroundColor: 'transparent', 
									//라인
		    						showLine: true,
		    						borderColor: 'rgb(255, 99, 132)', //red
		    						borderWidth: 3,
		    						//포인트
		    						pointHoverRadius: 10
		    					}
		    				] 
		    			},
		    			// 옵션 
		    			options: {
			    			title: { 
			    				display: true,
								text: '프로젝트 : ' + projectName + " / 수행일시 : " + regdtim, 
								fontSize: 20,
								fontStyle: 'bold',
								padding: 30
		    				},
		    				legend: { 
		    					display: true,
		    					padding: 30
							}, 
							scales: {
								xAxes: [{
									scaleLabel: {
										display: true,
										labelString: 'MeanSquaredError',
										fontSize: 15,
										fontStyle: 'bold'
									},
									ticks: {
										beginAtZero: true
									}
								}],
								yAxes: [{
									scaleLabel: {
										display: true,
										labelString: 'MSE Ratio(%)',
										fontSize: 15,
										fontStyle: 'bold'
									},
									ticks: {
										beginAtZero: true
									}
								}]
							},
							layout: {
								padding: {
									left: 50,
									right: 50,
									top: 10,
									bottom: 50
								}
							}
						}
					});
				},
				error : function(request, status, error) {
					alert('code:'+request.status+'\n'+'message:'+request.responseText+'\n'+'error:'+error);
				}
			});
		};
		    
		$(document).ready(function() {
			fn_setProjectNameDropdown("<c:url value='/resultgraph/projectnames' />", $('projectNameDropdown'));
		});	
	</script>
</body>
</html>