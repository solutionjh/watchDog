<%@ page language="java" pageEncoding="UTF-8"
    contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!doctype html>
<html lang="ko">
<head>
	<meta charset="UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
	<meta name="description" content="WatchDog 실행 화면" />
	
	<title>WatchDog 모형개발 및 기준정보 생성</title>
	
	<!-- External CSS -->
	<link rel="stylesheet" href="/webjars/bootstrap/4.3.1/dist/css/bootstrap.min.css" />
	<link rel="stylesheet" href="/webjars/bootstrap-table/dist/bootstrap-table.min.css" />
	<link rel="stylesheet" href="/webjars/font-awesome/5.12.0/css/fontawesome.min.css" />
	
	<!-- Custom CSS -->
	<link rel="stylesheet" href="main.css" />
	<link rel="stylesheet" href="not-main.css" />
	<link rel="stylesheet" href="font.css" />
</head>
<body>
    <nav class="navbar navbar-dark fixed-top bg-dark flex-md-nowrap p-0 shadow">
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
                                        <a class="nav-link active text-truncate" href="#submenu1-1" data-toggle="collapse" data-target="#submenu1-1">
                                            <i class="fas fa-play"></i> 이상탐지 실행
                                        </a>
                                        <div class="collapse show" id="submenu1-1" aria-expanded="false">
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
                                                    <a class="nav-link active py-1" href="/makemodel">
                                                        <i class="fas fa-laptop-code"></i> 모형개발 및 기준정보 생성
                                                        <span class="sr-only">(current)</span>
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
                                        <a class="nav-link collapsed text-truncate" href="#submenu1-2" data-toggle="collapse" data-target="#submenu1-2">
                                            <i class="fas fa-chalkboard-teacher"></i>
                                            <span class="d-none d-sm-inline">결과 조회</span>
                                        </a>
                                        <div class="collapse" id="submenu1-2" aria-expanded="false">
                                            <ul class="flex-column pl-4 nav">
                                                <li class="nav-item">
                                                    <a class="nav-link py-1" href="/result">
                                                        <i class="fas fa-table"></i> 수행 결과 조회
                                                    </a>
                                                </li>
                                                <li class="nav-item">
                                                    <a class="nav-link py-1" href="/resultgraph">
                                                        <i class="fas fa-chart-line"></i> 수행 결과 조회(그래프)
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
                                        <a class="nav-link collapsed text-truncate" href="#submenu2-2" data-toggle="collapse" data-target="#submenu2-2">
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
	                <h1 class="h2">모형학습 및 기준정보 생성</h1>
	                <div class="btn-group">
	                    <button type="button" class="btn btn-primary" id="btnExecuteMakeModel" data-loading-text="실행중...">실행</button>
	                </div>
	            </div>
				
				<form class="was-validated">
					<div class="mb-1">모형 학습 여부 선택</div>
					<div class="custom-control custom-radio">
				    	<input type="radio" class="custom-control-input" id="makeModelY" name="makeModelRadio" checked="checked">
				    	<label class="custom-control-label font-weight-bold" for="makeModelY" id="makeModelYLabel" >모형 생성 및 기준정보 생성</label>
				  	</div>
				  	<div class="custom-control custom-radio mb-4">
				    	<input type="radio" class="custom-control-input" id="makeModelN" name="makeModelRadio">
				    	<label class="custom-control-label" for="makeModelN" id="makeModelNLabel">기준정보 생성</label>
				  	</div>
				
					<!-- 2020.04.01 kwb 파일경로를 직접 입력할 수 있도록 변경 / 커스터마이징한 파일선택 버튼으로 교체 후, 텍스트창 우측에 위치시킴 -->
					<div class="mb-1" id="inputAreaTitle">데이터 파일 선택</div>
		            <div class="d-flex mb-4">
		            	<input type="text" class="form-inline form-control" id="inputDataFilePath" 
		            		placeholder="*.csv   (EX - D:/watchdog/data/test.csv)   ※ 구분자, 띄어쓰기 주의" required/>
		            
		            	<div class="btnSelectFile ml-1" id="btnSelectDataFile" style="display:none">
		            		<input type="file" id="inputDataFile"/>
		            		<label for="inputDataFile">파일선택</label>
		            	</div>
		            </div>
		            
		            <!-- 2020.04.01 kwb 파일경로를 직접 입력할 수 있도록 변경 / 커스터마이징한 파일선택 버튼으로 교체 후, 텍스트창 우측에 위치시킴 -->
		            <div class="mb-1" id="layoutAreaTitle">레이아웃 파일 선택</div>
		            <div class="d-flex mb-4">
		            	<input type="text" class="form-inline form-control" id="inputLayoutFilePath" 
		            		placeholder="*.properties   (EX - D:/watchdog/layout/test.properties)   ※ 구분자, 띄어쓰기 주의" required/>
		            
		            	<div class="btnSelectFile ml-1" id="btnSelectLayoutFile" style="display:none">
		            		<input type="file" id="inputLayoutFile"/>
		            		<label for="inputLayoutFile">파일선택</label>
		            	</div>
		            </div>
		            
		            <!-- 2020.04.01 kwb 필수 입력필드처럼 보이도록 'btn-outline-danger' 클래스명 추가 -->
		            <div class="mb-1" id="projectAreaTitle">프로젝트 선택</div>
		            <div class="dropdown mb-4" id="projectDropdownArea">
		            	<button type="button"
							class="btn btn-sm btn-outline-danger dropdown-toggle px-4 py-2"
							id="projectNameDropdown" data-toggle="dropdown"
							aria-haspopup="true" aria-expanded="false" required>*.adnn</button>
		            	<div class="dropdown-menu" id="projectNameDropdownMenu" aria-labelledby="projectNameDropdown" style="max-height: 300px; overflow-y: auto"></div>
		            </div>
		            <div class="d-flex mb-4" id="projectTextInputArea">
		            	<input type="text" class="form-inline form-control" id="projectNameInput" 
		            		placeholder="입력하신 프로젝트명으로 model 폴더에  .adnn 파일이 저장됩니다. 기존 파일이 있으면 백업됩니다." required/>
		            </div>
		            
		            <div class="mb-1">항목별 이상탐지 결과 산출여부</div>
					<div class="custom-control custom-radio">
				    	<input type="radio" class="custom-control-input" id="columnCalculateY" name="customRadio" checked="checked">
				    	<label class="custom-control-label font-weight-bold" for="columnCalculateY" id="columnCalculateYLabel" >파일 및 항목 이상여부 산출</label>
				  	</div>
				  	<div class="custom-control custom-radio mb-4">
				    	<input type="radio" class="custom-control-input" id="columnCalculateN" name="customRadio">
				    	<label class="custom-control-label" for="columnCalculateN" id="columnCalculateNLabel">파일 이상여부 산출</label>
				  	</div>
            	</form>
            	<div class="mb-1" id="titleResult" style="display: none">모형 생성 결과</div>
            </main>
        </div>
    </div>

    <!-- 2020.04.01 kwb 로딩 이미지, 배경 변경 -->
    <div class="backgroundLoading" id='pageLoading'>
		<div class="iconLoading">
			<span style="color: Dodgerblue"><i class="fas fa-spinner fa-spin fa-5x"></i></span>
		</div>
	</div>

	<!-- js 파일 적용 -->
    <script src="/webjars/jquery/dist/jquery.min.js"></script>
    <script src="/webjars/bootstrap/4.3.1/dist/js/bootstrap.bundle.js"></script>
    <script src="/webjars/bootstrap/4.3.1/dist/js/bootstrap.min.js"></script>
    <script src="/webjars/bootstrap-table/dist/bootstrap-table.min.js"></script>
    <script src="/webjars/bootstrap-table/dist/locale/bootstrap-table-ko-KR.min.js"></script>
    <script src="/webjars/bootstrap-table/dist/extensions/export/bootstrap-table-export.min.js"></script>
    <script src="/webjars/font-awesome/5.12.0/js/fontawesome.min.js"></script>

    <script type="text/javascript">
		
    	/* ----- 변수 ----- */
    	
    	// 기준정보 생성을 위해 PredictController.java에 넘겨줘야할 정보들
    	var columnCalculateYN = "Y";	// 항목별 이상탐지 결과 산출여부	//2020.04.09 kwb 별도로 선택을 하지 않으면 기본적으로 Y
		var inputDataFilePath = "";	// 데이터 파일 경로
		var inputLayoutFilePath = "";	// 레이아웃 파일 경로
		var projectName = "";	// 프로젝트 파일 이름
		var makeModelYN = "Y";
		
		var browser = ""; // 현재 브라우저
		
		
		/* ----- 함수 ----- */
		
		// 2020.03.30 kwb 브라우저를 구분해주는 함수
		var checkBrowser = function(){
			// 브라우저 및 버전을 구하기 위한 변수들
			var agent = navigator.userAgent.toLowerCase();
			var name = navigator.appName;
			
			// MS 계열 브라우저를 구분
			if(name === "Microsoft Internet Explorer" || agent.indexOf("trident") > -1 || agent.indexOf("edge/") > -1) {
				browser = "ie";
				/* if(name === "Microsoft Internet Explorer") { // IE old version (IE 10 or Lower)
					agent = /msie ([0-9]{1,}[\.0-9]{0,})/.exec(agent);
					browser += parseInt(agent[1]);
				} else { // IE 11+
					if(agent.indexOf("trident") > -1) { // IE 11 
						browser += 11;
					} else if(agent.indexOf("edge/") > -1) { // Edge
						browser = "edge";
					}
				} */
			}
			else if(agent.indexOf("safari") > -1) { // Chrome or Safari
				if(agent.indexOf("opr") > -1) { // Opera
					browser = "opera";
				} else if(agent.indexOf("chrome") > -1) { // Chrome
					browser = "chrome";
				} else { // Safari
					browser = "safari";
				}
			} else if(agent.indexOf("firefox") > -1) { // Firefox
				browser = "firefox";
			}
			
			// 브라우저에 따라 UI 변경
			setBrowserUI();
		}
		
		// 2020.03.30 kwb 브라우저에 따라 UI를 변경하는 함수
		var setBrowserUI = function(){
			// 브라우저가 IE일 때만 처리
			if(browser == "ie"){
				// '파일선택' 버튼 보이기 
				$("#btnSelectDataFile").show();
				$("#btnSelectLayoutFile").show();
			}
		}
		
		// 프로젝트 파일(*.adnn) 리스트 가져와서 드롭다운 메뉴에 추가해주는 함수
		var getFileList = function(filePath, filePattern) {
			var formData = {
				"dirname" : filePath,
				"pattern" : filePattern
			}
			
			$.ajax({
				type : "GET",
				url : "/anomaly/getfiles",
				data : formData,
				dataType : "json",
				cache: false,
				success : function(projectNameList) {
					var listName = projectNameList;
					// 프로젝트를 드롭다운 메뉴 하위 리스트에 추가
					$.each(listName, function(index) {
						var newProjectName = '<a class="dropdown-item" href="#">'+listName[index].replace(/^.*[\\\/]/, "")+'</a>'
						$(newProjectName).appendTo("#projectNameDropdownMenu");
					});
					// 프로젝트 선택 이벤트 처리
					$("#projectNameDropdownMenu a").click(function(){
						projectName = $(this).text();
						$(this).parents(".dropdown").find(".dropdown-toggle").html(projectName);
						
						// 2020.04.01 kwb 프로젝트를 선택한 후엔 드롭다운 메뉴 버튼에 성공(초록색 테마) CSS 적용
						$("#projectNameDropdown").removeClass("btn-outline-danger");
						$("#projectNameDropdown").addClass("btn-outline-success");
					});
				},
				error : function(request, status, error) {
					alert("프로젝트를 불러오던 중, 오류가 발생하였습니다.\n\n"+"code:"+request.status+"\n"+"message:"+request.responseText);
				}
			});
		};
		
		// 모형학습 여부에 따라 projectname 을 입력받을지 선택받을지 UI 변경
		var setProjectNameUI = function(){
			if(makeModelYN == "Y"){
				// 프로젝트명 입력하는 부분 출력 및 프로젝트 선택 삭제
				$("#projectDropdownArea").hide();
				$("#projectNameInput").show();
				$("#projectTextInputArea").removeClass("mb-0");
				$("#projectTextInputArea").addClass("mb-4");
				document.getElementById("projectAreaTitle").innerHTML = "프로젝트명 입력";
				document.getElementById("projectNameInput").setAttribute('required', "");
				document.getElementById("projectNameDropdown").removeAttribute('required');
				//document.getElementById("").setAttribute();
				//document.getElementById("inputAreaTitle").innerHTML = "데이터 파일 선택(CSV파일)";
			} else if(makeModelYN == "N"){
				// 프로젝트명 입력하는 부분 삭제 및 프로젝트 선택 추가
				$("#projectDropdownArea").show();
				$("#projectNameInput").hide();
				$("#projectTextInputArea").removeClass("mb-4");
				$("#projectTextInputArea").addClass("mb-0");
				document.getElementById("projectAreaTitle").innerHTML = "프로젝트 선택";
				document.getElementById("projectNameDropdown").setAttribute('required',"");
				document.getElementById("projectNameInput").removeAttribute('required');
				//document.getElementById("").setAttribute();
				//document.getElementById("inputAreaTitle").innerHTML = "데이터 파일 선택";
			}
			
		}
		
		// 기준정보 생성 실행 항수
		var executeMakeModel = function() {
			var formData = {
				"makeModelYN" : makeModelYN,
				"columnCalculateYN" : columnCalculateYN,
				"inputDataFile" : inputDataFilePath,
				"inputLayoutFile" : inputLayoutFilePath,
				"projectName" : projectName
			}
				
			$.ajax({
				type : "put",
				url : "/anomaly/makemodel",
				data : JSON.stringify(formData),
				contentType : "application/json",
				async : false,
				cache: false,
				success : function(data) {
					makeResultTable(data);
					alert("모형 개발이 완료되었습니다.\n");
					// 실행결과 필드 제목 보이기
					$("#titleResult").show();
				},
				error : function(request, status, error) {
					alert("모형을 생성하던 중, 오류가 발생하였습니다.\n\n"+"code:"+request.status+"\n"+"message:"+request.responseText);
				},
				complete: function() {
					// 로딩 UI 숨기기
					$("#pageLoading").hide();
				}
			});	
		}
		
		
		var makeResultTable = function(resultData){
			
			var elem = document.getElementById('mdlRsltTbl');
			if (elem != null){
				elem.parentNode.removeChild(elem);
			}
			
			var resultList = JSON.parse(resultData);
			
			var tableHead = "";
	    	tableHead += '<table class="table table-striped table-borderless" style="table-layout: fixed" id="mdlRsltTbl"><colgroup><col width="10%"/><col width="10%"/><col width="10%"/><col width="70%"/></colgroup><caption>* 상수값 항목, 값이 없는 항목을 제외한 숫자형 항목이 자동으로 선택되었습니다.</caption><thead><tr><th scope="col">모형파일명</th><th scope="col">전체항목수</th><th scope="col">선택항목수</th><th scope="col">선택항목</th></tr></thead><tbody id="mdlRsltTbody"></tbody></table>';
	    	$('main').append(tableHead);
	    	
			var rsltRow = "<tr>";
			rsltRow += '<td>'+resultList.projectName+'</td>';
			rsltRow += '<td>'+resultList.entireColNum+'</td>';
			rsltRow += '<td>'+resultList.selectedColNum+'</td>';
			rsltRow += '<td style="overflow: auto">'+resultList.selectedCol+'</td>';
			rsltRow += "</tr>";
			
			$("#mdlRsltTbody").empty();
			$("#mdlRsltTbody").append(rsltRow);
			
		}
		
		/* ----- 이벤트 처리 ----- */
		
		// 모형 학습 여부 선택
		$("#makeModelYLabel").on("click", function(){
			makeModelYN = "Y";
			$(this).addClass("font-weight-bold");
			$("#makeModelNLabel").removeClass("font-weight-bold");
			setProjectNameUI();
			
		});
		
		$("#makeModelNLabel").on("click", function(){
			makeModelYN = "N";
			$(this).addClass("font-weight-bold");
			$("#makeModelYLabel").removeClass("font-weight-bold");
			setProjectNameUI();
			
		});
		
		// 항목별 이상탐지 결과 산출 여부
		$("#columnCalculateYLabel").on("click", function() {
			// 항목별 이상탐지 결과 산출 여부 값 세팅
			columnCalculateYN = "Y";
			$(this).addClass("font-weight-bold");
			$("#columnCalculateNLabel").removeClass("font-weight-bold");
		});
		$("#columnCalculateNLabel").on("click", function() {
			// 항목별 이상탐지 결과 산출 여부 값 세팅
			columnCalculateYN = "N";
			$(this).addClass("font-weight-bold");
			$("#columnCalculateYLabel").removeClass("font-weight-bold");
		});
		
		// 데이터 파일 선택 (파일선택 버튼 - IE)
	    $("#inputDataFile").on("change", function(){
	    	var filePath = $(this).val();
	    	// 구분자 지환 ( \\ -> / )
	    	filePath = filePath.replace(/\\/g, "/");
	    	// 선택한 파일의 경로를 <input type =text>에 세팅
			$("#inputDataFilePath").val(filePath);
		});
		
		// 레이아웃 파일 선택 (파일선택 버튼 - IE)
		$("#inputLayoutFile").on("change", function(){
			var filePath = $(this).val();
	    	// 구분자 지환 ( \\ -> / )
	    	filePath = filePath.replace(/\\/g, "/");
			$("#inputLayoutFilePath").val(filePath);
		});
		
		$("#projectNameInput").on("change", function(){
			projectName = $(this).val();
			
		});
		
		// 실행
		$("#btnExecuteMakeModel").on("click", function() {
			// 데이터 파일 경로 값 세팅
			inputDataFilePath = $("#inputDataFilePath").val();
			// 레이아웃 파일 경로 값 세팅 
			inputLayoutFilePath = $("#inputLayoutFilePath").val();
						
			// 2020.04.01 kwb 비어있는 정보가 있으면, 실행하지 않고 alert 처리
			if(columnCalculateYN.trim().length == 0 || inputDataFilePath.trim().length == 0
					|| inputLayoutFilePath.trim().length == 0 || projectName.trim().length == 0){
				
				// 공백 문자로만 경로가 적혀있을 때, 텍스트 박스를 리셋하여 다시 빨간색으로 표시되게끔 처리 
				if(inputDataFilePath.trim().length == 0){
					$("#inputDataFilePath").val("");
				}
				if(inputLayoutFilePath.trim().length == 0){
					$("#inputLayoutFilePath").val("");
				}
				
				alert("빨간색으로 표시된 필수정보를 모두 입력해주세요.\n")
			}
			else{
				// 로딩 UI 보이기
		        $("#pageLoading").show();
			
				// 로딩 이미지를 보이게 하기위한 딜레이
				setTimeout(executeMakeModel, 1000);
			}
	    });
		
		
	    $(document).ready(function() {
	    	// 2020.03.30 kwb 브라우저를 체크하여 브라우저에 따라 다른 UI를 제공하도록 처리
	    	checkBrowser();
	    	// 프로젝트 리스트 가져오기
	    	getFileList("model", ".*\\.adnn");
	    	// 모형개발 및 기준정보 생성 화면 초기화
	    	setProjectNameUI();
	    });
		
	</script>
</body>
</html>