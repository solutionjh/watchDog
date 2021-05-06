<%@ page language="java" pageEncoding="UTF-8"
    contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!doctype html>
<html lang="ko">
<head>
	<meta charset="UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
	<meta name="description" content="WatchDog 실행 화면" />
	
	<title>WatchDog 이상감지</title>
	
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
                            <a class="nav-link collapsed active text-truncate" href="#submenu1" data-toggle="collapse" data-target="#submenu1">
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
                                                    <a class="nav-link active py-1" href="/makeproperty">
                                                        <i class="fas fa-keyboard"></i> 데이터 레이아웃 파일 생성
                                                        <span class="sr-only">(current)</span>
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

            <main role="main" class="col-md-10 ml-sm-auto col-lg-10 px-4" id="predMain">
	            <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
	                <h1 class="h2">데이터 레이아웃 파일 생성</h1>
	                <div class="btn-group">
	                    <button type="button" class="btn btn-primary" id="btnExecutePredict" data-loading-text="실행중...">저장</button>
	                </div>
	            </div>
				
				<form class="was-validated">
				<div class="mb-1">신규 생성 여부 선택</div>
					<!-- <div class="mb-1">(데이터 파일이 fixed layout(고정길이) 이면 Y, CSV 등의 delimiter(구분자)를 사용하는 파일이면 N으로 입력합니다)</div> -->
					<div class="custom-control custom-radio">
				    	<input type="radio" class="custom-control-input" id="isNewY" name="checkNewRadio" checked="checked">
				    	<label class="custom-control-label font-weight-bold" for="isNewY" id="isNewYLabel">신규 생성</label>
				  	</div>
				  	<div class="custom-control custom-radio mb-4">
				    	<input type="radio" class="custom-control-input" id="isNewN" name="checkNewRadio">
				    	<label class="custom-control-label" for="isNewN" id="isNewNLabel">수정</label>
				  	</div>
				  	
					<div class="mb-1" id="projectNameTitle">프로젝트명 입력(필수)</div>
		            <div class="d-flex mb-4" id="projectNameDiv">
		            	<input type="text" class="form-inline form-control" id="projectName" 
		            		placeholder="데이터 레이아웃을 저장할 이름을 지정합니다. (EX - NICE정기배치)" required/>
		            </div>
		            
		            <div class="mb-1" id="propNameTitle">레이아웃 파일 선택</div>
		            <div class="dropdown mb-4" id="propNameDiv">
		            	<button type="button"
							class="btn btn-sm btn-outline-danger dropdown-toggle px-4 py-2"
							id="propertyNameDropdown" data-toggle="dropdown"
							aria-haspopup="true" aria-expanded="false" required>*.properties</button>
		            	<div class="dropdown-menu" id="propertyNameDropdownMenu" aria-labelledby="propertyNameDropdown" style="max-height: 300px; overflow-y: auto"></div>
		            </div>
		            
					<div class="mb-1">데이터 파일 종류 선택(필수)</div>
					<!-- <div class="mb-1">(데이터 파일이 fixed layout(고정길이) 이면 Y, CSV 등의 delimiter(구분자)를 사용하는 파일이면 N으로 입력합니다)</div> -->
					<div class="custom-control custom-radio">
				    	<input type="radio" class="custom-control-input" id="fixedLayoutY" name="makePropRadio">
				    	<label class="custom-control-label" for="fixedLayoutY" id="fixedLayoutYLabel" >고정길이(Fixed Length) 파일</label>
				  	</div>
				  	<div class="custom-control custom-radio mb-4">
				    	<input type="radio" class="custom-control-input" id="fixedLayoutN" name="makePropRadio" checked="checked">
				    	<label class="custom-control-label font-weight-bold" for="fixedLayoutN" id="fixedLayoutNLabel">구분자(delimiter)로 구분된 파일</label>
				  	</div>
					
					<div class="mb-1">항목 정보(헤더) 입력(필수)</div>
		            <div class="d-flex mb-4">
		            	<input type="text" class="form-inline form-control" id="header"
		            		placeholder="데이터 파일의 항목명을 순서대로 ,로 구분하여 입력합니다. (EX - SEQ,DATE,NAME,SCORE1,SCORE2,GRADE1,GRADE2) ※ 구분자는 ,로 입력" required/>
		            </div>
		            
		            <div class="mb-1" id="columnLengthTitle">항목 길이 정보 입력(필수)</div>
		            <div class="d-flex mb-4" id="columnLengthArea">
		            	<input type="text" class="form-inline form-control" id="columnLength"
		            		placeholder="fixed length(고정길이) 파일일 경우 각 항목의 길이를 header 순서와 같게 입력합니다 . (EX - 1,2,8,10,10,5,3,5) ※ 구분자는, 로 입력" required/>
		            </div>
		            
		            <div class="mb-1" id="delimiterTitle">구분자(delimiter) 입력(필수)</div>
		            <div class="d-flex mb-4" id="delimiterArea">
		            	<input type="text" class="form-inline form-control" id="delimiter"
		            		placeholder="데이터 파일의 delimiter(구분자)가 무엇인지 입력합니다. (EX - ,)" required/>
		            </div>
		            
		            <div class="mb-1">헤더 라인 수 입력(필수)</div>
		            <div class="d-flex mb-4">
		            	<input type="text" class="form-inline form-control" id="headerToSkip" 
		            		placeholder="헤더 라인 수를 입력합니다. 데이터 파일의 헤더가 1줄 있을 경우 1 입력, 없으면 0을 입력합니다. (EX - 1)" required/>
		            </div>
		            
		            <div class="mb-1">트레일러 라인 수 입력(필수)</div>
		            <div class="d-flex mb-4">
		            	<input type="text" class="form-inline form-control" id="footerToSkip" 
		            		placeholder="트레일러 라인 수를 입력합니다. 데이터 파일의 트레일러가 1줄 있을 경우 1 입력, 없으면 0을 입력합니다. (EX - 1)" required/>
		            </div>
		            
		            <div class="mb-1">분포 이상 탐지 항목 입력</div>
		            <div class="d-flex mb-4">
		            	<input type="text" class="form-inline form-control" id="ratioGrade" 
		            		placeholder="카이제곱 검정을 수행할 항목들을 , 로 구분하여 입력합니다. 스코어 추출시 등급에 해당하는 항목을 입력합니다. (EX - GRADE1,GRADE2)"/>
		            </div>
		            
		            <div class="mb-1">모니터링 제외 항목 입력(Key 에 해당하는 항목)</div>
		            <div class="d-flex mb-4">
		            	<input type="text" class="form-inline form-control" id="keyColumn" 
		            		placeholder="모형에서 사용하지 않을 항목들을 ,로 구분하여 입력합니다. key에 해당하는 항목, 문자형 항목을 입력합니다. (EX - SEQ,DATE,NAME)"/>
		            </div>
		            
		            <div class="mb-1">모니터링 포함 항목 입력(숫자형일 경우에만 모형에 사용)</div>
		            <div class="d-flex mb-4">
		            	<input type="text" class="form-inline form-control" id="requiredColumn" 
		            		placeholder="모형에서 반드시 사용할 항목을 입력합니다. 입력하지 않더라도 숫자형 항목은 사용하게 됩니다. (EX - GRADE1,SCORE1,GRADE2,SCORE2) ※ 구분자는 ,로 입력"/>
		            </div>
		            
		            <div class="mb-1">이상 판단 기준 입력</div>
		            <div class="d-flex mb-4">
		            	<input type="text" class="form-inline form-control" id="threshold" 
		            		placeholder="오토 인코더의 psi, 오토 인코더의 항목 변화율, 카이제곱 통계량의 기준값을 차례대로 ,로 구분하여 입력합니다. 해당 값보다 크면 화면에서 이상으로 표시합니다. (EX - 1,50,250)"/>
		            </div>
		            
	            </form>
	            
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
	    var isNewYN = "Y";
	    var propertyName = "";
		var projectName = "";
		var fixedLayoutYN = "N";
		var header = "";
		var columnLength = "";
		var delimiter = "";
		var headerToSkip = "";
		var footerToSkip = "";
		var ratioGrade = "";
		var threshold = "";
		var keyColumn = "";
		var requiredColumn = "";
		
		
		/* ----- 함수 ----- */
		
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
				success : function(propertyNameList) {
					var listName = propertyNameList;
					// 프로젝트를 드롭다운 메뉴 하위 리스트에 추가
					$.each(listName, function(index) {
						var newPropertyName = '<a class="dropdown-item" href="#">'+listName[index].replace(/^.*[\\\/]/, "")+'</a>'
						$(newPropertyName).appendTo("#propertyNameDropdownMenu");
					});
					// 프로젝트 선택 이벤트 처리
					$("#propertyNameDropdownMenu a").click(function(){
						propertyName = $(this).text();
						$(this).parents(".dropdown").find(".dropdown-toggle").html(propertyName);
						$("#propertyNameDropdown").removeClass("btn-outline-danger");
						$("#propertyNameDropdown").addClass("btn-outline-success");
						
						loadProperty(propertyName);
					});
				},
				error : function(request, status, error) {
					alert("레이아웃 리스트를 불러오던 중, 오류가 발생하였습니다.\n\n"+"code:"+request.status+"\n"+"message:"+request.responseText);
				}
			});
		}

	
		// 데이터 이상감지 실행 함수
		var executeMakeProp = function() {
			var formData = {
				"projectName" : projectName,
				"fixedLayoutYN" : fixedLayoutYN,
				"header" : header,
				"columnLength" : columnLength,
				"delimiter" : delimiter,
				"headerToSkip" : headerToSkip,
				"footerToSkip" : footerToSkip,
				"ratiograde" : ratioGrade,
				"threshold" : threshold,
				"keyColumn" : keyColumn,
				"requiredColumn" : requiredColumn
			}
			
			$.ajax({
				type : "put",
				url : "/anomaly/makeproperty",
				data : JSON.stringify(formData),
				contentType : "application/json",
				async : false,
				cache: false,
				success : function(data) {
					alert("레이아웃 파일 생성이 완료되었습니다.\n");
					
					
				},
				error : function(request, status, error) {
					alert("레이아웃 파일 생성 중, 오류가 발생하였습니다.\n\n"+"code:"+request.status+"\n"+"message:"+request.responseText);
				},
				complete: function() {
					// 로딩 UI 숨기기
					$("#pageLoading").hide();
				}
			});
		}
		
	    
		/* ----- 이벤트 처리 ----- */
		
		// 항목별 이상탐지 결과 산출 여부
		$("#fixedLayoutYLabel").on("click", function() {
			// 항목별 이상탐지 결과 산출 여부 값 세팅
			fixedLayoutYN = "Y";
			$(this).addClass("font-weight-bold");
			$("#fixedLayoutNLabel").removeClass("font-weight-bold");
			setProjectUI();
		});
		
		$("#fixedLayoutNLabel").on("click", function() {
			// 항목별 이상탐지 결과 산출 여부 값 세팅
			fixedLayoutYN = "N";
			$(this).addClass("font-weight-bold");
			$("#fixedLayoutYLabel").removeClass("font-weight-bold");
			setProjectUI();
		});
		
		$("#isNewYLabel").on("click", function() {
			// 항목별 이상탐지 결과 산출 여부 값 세팅
			isNewYN = "Y";
			$(this).addClass("font-weight-bold");
			$("#isNewNLabel").removeClass("font-weight-bold");
			setIsNewUI();
		});
		
		$("#isNewNLabel").on("click", function() {
			// 항목별 이상탐지 결과 산출 여부 값 세팅
			isNewYN = "N";
			$(this).addClass("font-weight-bold");
			$("#isNewYLabel").removeClass("font-weight-bold");
			setIsNewUI();
		});
		
		
		// 고정길이 파일 여부에 따라 columnLength, 을 입력받을지 선택받을지 UI 변경
		var setProjectUI = function(){
			if(fixedLayoutYN == "Y"){
				$("#delimiterTitle").hide();
				$("#delimiter").hide();
				$("#delimiter").val("");
				delimiter = "";
				$("#delimiterArea").removeClass("mb-4");
				$("#delimiterArea").addClass("mb-0");
				
				$("#columnLengthTitle").show();
				$("#columnLength").show();
				$("#columnLengthArea").removeClass("mb-0");
				$("#columnLengthArea").addClass("mb-4");
				
				document.getElementById("columnLength").setAttribute('required', "");
				document.getElementById("delimiter").removeAttribute('required');
				
			} else if(fixedLayoutYN == "N"){
				$("#columnLengthTitle").hide();
				$("#columnLength").hide();
				$("#columnLength").val("");
				columnLength = "";
				$("#columnLengthArea").removeClass("mb-4");
				$("#columnLengthArea").addClass("mb-0");
				
				$("#delimiterTitle").show();
				$("#delimiter").show();
				$("#delimiterArea").removeClass("mb-0");
				$("#delimiterArea").addClass("mb-4");
				
				document.getElementById("delimiter").setAttribute('required',"");
				document.getElementById("columnLength").removeAttribute('required');
				
			}
			
		}
		
		var setIsNewUI = function(){
			if(isNewYN == "Y"){
				$("#propNameTitle").hide();
				$("#propNameDiv").hide();
				$("#propertyNameDropdown").val("*.properties");
				$("#propertyNameDropdown").text("*.properties");
				$("#propertyNameDropdown").removeClass("btn-outline-success");
				$("#propertyNameDropdown").addClass("btn-outline-danger");
				propertyName = "";
				$("#propNameDiv").removeClass("mb-4");
				$("#propNameDiv").addClass("mb-0");
				document.getElementById("propertyNameDropdown").removeAttribute('required');
				
				$("#projectNameTitle").show();
				$("#projectNameDiv").show();
				$("#projectName").show();
				$("#projectNameDiv").removeClass("mb-0");
				$("#projectNameDiv").addClass("mb-4");
				document.getElementById("projectName").setAttribute('required', "");
				
			} else if(isNewYN == "N"){
				$("#projectNameTitle").hide();
				$("#projectNameDiv").hide();
				$("#projectName").hide();
				$("#projectName").val("");
				projectName = "";
				$("#projectNameDiv").removeClass("mb-4");
				$("#projectNameDiv").addClass("mb-0");
				document.getElementById("projectName").removeAttribute('required');
				
				$("#propNameTitle").show();
				$("#propNameDiv").show();
				$("#propNameDiv").removeClass("mb-0");
				$("#propNameDiv").addClass("mb-4");
				document.getElementById("propertyNameDropdown").setAttribute('required', "");
			}
		}
		
		// 불러온 property 데이터 값 세팅
		var loadProperty = function(propertyName){
			$.ajax({
				type : "GET",
				url : "<c:url value='/anomaly/loadproperty/" + propertyName +"/' />",
				dataType : "json",
				cache : false,
				success : function(responseData) {
					setUIValues(responseData);
				    
				},
				error : function(request, status, error) {
					alert('code:'+request.status+'\n'+'message:'+request.responseText+'\n'+'error:'+error);
				}
			})
		}
		
		var setUIValues = function(data) {
			// fixedlayoutyn
			fixedLayoutYN = data.fixedLayoutYN;
			if(fixedLayoutYN == "Y"){
				document.getElementById("fixedLayoutN").removeAttribute('checked');
				document.getElementById("fixedLayoutY").setAttribute('checked', "checked");
				$("#fixedLayoutY").addClass("font-weight-bold");
				$("#fixedLayoutN").removeClass("font-weight-bold");
			}else if(fixedLayoutYN == "N"){
				document.getElementById("fixedLayoutY").removeAttribute('checked');
				document.getElementById("fixedLayoutN").setAttribute('checked', "checked");
				$("#fixedLayoutN").addClass("font-weight-bold");
				$("#fixedLayoutY").removeClass("font-weight-bold");
			}
			
			setProjectUI();
			
			// header
			header = data.header;
			$("#header").val(header);
			
			// columnlength
			columnLength = data.columnLength;
			$("#columnLength").val(columnLength);
			
			// delimiter
			delimiter = data.delimiter;
			$("#delimiter").val(delimiter);
			
			// headertoskip
			headerToSkip = data.headerToSkip;
			$("#headerToSkip").val(headerToSkip);
			
			// footertoskip
			footerToSkip = data.footerToSkip;
			$("#footerToSkip").val(footerToSkip);
			
			// keycolumn
			keyColumn = data.keyColumn;
			$("#keyColumn").val(keyColumn);
			
			// ratiograde
			ratioGrade = data.ratiograde;
			$("#ratioGrade").val(ratioGrade);
			
			// requiredcolumn
			requiredColumn = data.requiredColumn;
			$("#requiredColumn").val(requiredColumn);
			
			// threshold
			threshold = data.threshold;
			$("#threshold").val(threshold);
		}
		
		// 실행
		$("#btnExecutePredict").on("click", function() {
			if(isNewYN == "Y"){
				projectName = $("#projectName").val();
			} else if(isNewYN == "N"){
				projectName = propertyName.split(".properties")[0];
				// 수정 여부를 최종 확인.				
				if (confirm(projectName + ".properties를 수정 하시겠습니까?") == false){
					//취소 시 화면 새로고침보다는 해당 화면을 그래로 두는게 좋을 듯 함.
					//location.reload();
					return false;
				}
			}
			
			header = $("#header").val();
			columnLength = $("#columnLength").val();
			delimiter = $("#delimiter").val();
			headerToSkip = $("#headerToSkip").val();
			footerToSkip = $("#footerToSkip").val();
			ratioGrade = $("#ratioGrade").val();
			threshold = $("#threshold").val();
			keyColumn = $("#keyColumn").val();
			requiredColumn = $("#requiredColumn").val();
			
			if(projectName.trim().length == 0 || fixedLayoutYN.trim().length == 0 || header.trim().length == 0
				|| headerToSkip.trim().length == 0 || footerToSkip.trim().length == 0
				|| (fixedLayoutYN == "Y" && columnLength.trim().length == 0)
				|| (fixedLayoutYN == "N" && delimiter.trim().length == 0)) {
				
				if(projectName.trim().length == 0){
					$("#projectName").val("");
				}
				if(fixedLayoutYN.length == 0){
					$("#fixedLayoutYN").val("");
				}
				if(header.trim().length == 0){
					$("#header").val("");
				}
				if(fixedLayoutYN == "Y" && columnLength.trim().length == 0){
					$("#columnLength").val("");
				}
				if(fixedLayoutYN == "N" && delimiter.trim().length == 0){
					$("#delimiter").val("");
				}
				if(headerToSkip.trim().length == 0){
					$("#headerToSkip").val("");
				}
				if(footerToSkip.trim().length == 0){
					$("#footerToSkip").val("");
				}
				if(ratioGrade.trim().length == 0){
					$("#ratioGrade").val("");
				}
				if(keyColumn.trim().length == 0){
					$("#keyColumn").val("");
				}
				if(requiredColumn.trim().length == 0){
					$("#requiredColumn").val("");
				}
				if(threshold.trim().length == 0){
					$("#threshold").val("");
				}
				
				alert("빨간색으로 표시된 필수정보를 모두 입력해주세요.\n")
				
			}
			else{
				// 로딩 UI 보이기
		        $("#pageLoading").show();
			
				// 로딩 이미지를 보이게 하기위한 딜레이
				setTimeout(executeMakeProp, 1000);
			}
	    });
		
		
	    $(document).ready(function() {
	    	// 프로젝트 리스트 가져오기
	    	getFileList("properties", ".*\\.properties");
	    	setIsNewUI();
	    	setProjectUI();
	    });

	</script>
</body>
</html>