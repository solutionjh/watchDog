<%@ page language="java" pageEncoding="UTF-8"
    contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!doctype html>
<html lang="ko">
<head>
	<meta charset="UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
	<meta name="description" content="WatchDog 실행 화면" />
	
	<title>WatchDog 이상감지(복수)</title>
	
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
                                                    <a class="nav-link active py-1" href="/predictmulti">
                                                        <i class="fas fa-search-plus"></i> 데이터 이상감지(복수)
                                                        <span class="sr-only">(current)</span>
                                                    </a>
                                                </li>
                                            </ul>
                                        </div>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link text-truncate" href="#submenu1-2" data-toggle="collapse" data-target="#submenu1-2">
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

            <main role="main" class="col-md-10 ml-sm-auto col-lg-10 px-4">
	            <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
	                <h1 class="h2">데이터 이상감지(복수)</h1>
	                <div class="btn-group">
	                    <button type="button" class="btn btn-primary" id="btnExecutePredictMulti" data-loading-text="실행중...">실행</button>
	                </div>
	            </div>
				
				<!-- 2020.04.03 kwb 필수 입력필드 UI 적용을 위해 추가 -->
				<form class="was-validated">
					<table id="predictTable" class=" table order-list">
					    <thead>
					        <tr>
					        	<th>No.</th>
					        	<th>데이터 파일</th>
					        	<th>레이아웃 파일</th>
					        	<th>프로젝트</th>
					        	<th>항목별 결과 산출여부</th>
					        </tr>
					    </thead>
					    <tbody>
					        <tr>
					        	<td>1</td>
					            <td style="width: 30%">
					            	<!-- 2020.04.01 kwb 파일경로를 직접 입력할 수 있도록 변경 / 커스터마이징한 파일선택 버튼으로 교체 후, 텍스트창 우측에 위치시킴 -->
						            <div class="d-flex">
							            <input type="text" class="form-inline form-control" id="inputDataFilePath0" 
							            	placeholder="*.csv, *.txt, *.gz 등   (EX - D:/watchdog/data/test.csv)   ※ 구분자, 띄어쓰기 주의" 
							            	oninput="setInputDataFile(0)" required/>
							            
							           	<div class="btnSelectFile ml-1" id="btnSelectDataFile0" style="display:none">
							            	<input type="file" id="inputDataFile0" onchange="changeInputDataFilePath(0)"/>
							            	<label for="inputDataFile0">파일선택</label>
						            	</div>
				            		</div>
					            </td>
					            <td style="width: 30%">
					            	<!-- 2020.04.01 kwb 파일경로를 직접 입력할 수 있도록 변경 / 커스터마이징한 파일선택 버튼으로 교체 후, 텍스트창 우측에 위치시킴 -->
							    	<div class="d-flex">
							            <input type="text" class="form-inline form-control" id="inputLayoutFilePath0" 
							            	placeholder="*.properties   (EX - D:/watchdog/layout/test.properties)   ※ 구분자, 띄어쓰기 주의" 
							            	oninput="setInputLayoutFile(0)" required/>
							            
							           	<div class="btnSelectFile ml-1" id="btnSelectLayoutFile0" style="display:none">
							            	<input type="file" id="inputLayoutFile0" onchange="changeInputLayoutFilePath(0)"/>
							            	<label for="inputLayoutFile0">파일선택</label>
						            	</div>
				            		</div>
					            </td>
					            <td style="width: 25%">
					            	<!-- 2020.04.01 kwb 필수 입력필드처럼 보이도록 'btn-outline-danger' 클래스명 추가 -->
									<div class="dropdown">
							        	<button type="button"
							            	class="btn btn-sm btn-outline-danger dropdown-toggle px-4 py-2" 
							            	id="projectNameDropdown0" data-toggle="dropdown"
							            	aria-haspopup="true" aria-expanded="false" required>*.adnn</button>
							        	<div class="dropdown-menu" id="projectNameDropdownMenu0" aria-labelledby="projectNameDropdown0" style="max-height: 250px; overflow-y: auto"></div>
		            					
		            					<!-- 2020.04.10 kwb 기준정보가 생성되지 않은 프로젝트를 선택했을 때를 위한 경고문구 추가 -->
		            					<div class="alertText mt-1" id="alertTextMetaIsNotExisted0"> 해당 프로젝트의 기준정보가 생성되어있지 않습니다. </div>
							    	</div>            	
					            </td>
					            <td style="width: 15%">
						        	<div class="custom-control custom-radio">
									  	<input type="radio" class="custom-control-input" id="columnCalculateY0" name="customRadio0" checked="checked">
									   	<label class="custom-control-label font-weight-bold" for="columnCalculateY0" id="columnCalculateYLabel0" onclick="setColumnCalculateY(0)">파일 및 항목 이상여부 산출</label>
									</div>
									<div class="custom-control custom-radio">
									  	<input type="radio" class="custom-control-input" id="columnCalculateN0" name="customRadio0">
									   	<label class="custom-control-label" for="columnCalculateN0" id="columnCalculateNLabel0" onclick="setColumnCalculateN(0)">파일 이상여부 산출</label>
									</div>
					            </td>
					            <td>
					            	<a class="deleteRow"></a>
					            </td>
					        </tr>
					    </tbody>
					    <tfoot>
					        <tr>
					            <td colspan="5" style="text-align: center;">
					            	<i class="fas fa-plus-circle fa-2x" id="addrow" style="cursor: pointer;"></i>
					            </td>
					        </tr>
					        <tr>
					        </tr>
					    </tfoot>
					</table>
				</form>
				
				<!-- 2020.04.03 kwb watchDogPredict.jsp와 맞추기위해 필드제목 추가 -->
				<div class="mb-1" id="titleResult" style="display: none">실행 결과</div>
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
    
		var listName = "";
		var projectName = "";
		var tableData = [];
		
		
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
			
			// 브라우저에 따라 각 행의 UI를 변경
			setBrowserUI(0);
		}
		
		// 2020.04.03 kwb 브라우저에 따라 각 행의 UI를 변경하는 함수
		var setBrowserUI = function(rowNum){
			// 브라우저가 IE일 때만 처리
			if(browser == "ie"){
				// '파일선택' 버튼 보이기 
				$("#btnSelectDataFile" + rowNum).show();
				$("#btnSelectLayoutFile" + rowNum).show();
			}
		}
		
		var setColumnCalculateY = function(rownum) {
			tableData[rownum].columnCalculateYN = "Y";
			$('#columnCalculateYLabel'+rownum).addClass('font-weight-bold');
			$('#columnCalculateNLabel'+rownum).removeClass('font-weight-bold');
		}
	
		var setColumnCalculateN = function(rownum) {
			tableData[rownum].columnCalculateYN = "N";
			$('#columnCalculateNLabel'+rownum).addClass('font-weight-bold');
			$('#columnCalculateYLabel'+rownum).removeClass('font-weight-bold');
		}
		
		// 2020.04.03 kwb 파일선택 버튼을 이용할 경우(IE), 텍스트창에 선택한 파일의 경로를 입력해주고 데이터 파일의 경로를 변수에 세팅해주는 함수
		var changeInputDataFilePath = function(rownum){
	    	var filePath = $("#inputDataFile"+rownum).val();
	    	filePath = filePath.replace(/\\/g, "/");
			
	    	$("#inputDataFilePath"+rownum).val(filePath);
	    	tableData[rownum].inputDataFile = filePath;
	    }
		
		// 2020.04.03 kwb 텍스트창에 경로를 직접 입력할 경우, 데이터 파일의 경로를 변수에 세팅해주는 함수
		var setInputDataFile = function(rownum){
	    	var filePath = $("#inputDataFilePath"+rownum).val();
	    	tableData[rownum].inputDataFile = filePath.replace(/\\/g, "/");
	    }
	
		// 2020.04.03 kwb 파일선택 버튼을 이용할 경우(IE), 텍스트창에 선택한 파일의 경로를 입력해주고 레이아웃 파일의 경로를변수에 세팅해주는 함수
	    var changeInputLayoutFilePath = function(rownum){
	    	var filePath = $("#inputLayoutFile"+rownum).val();
	    	filePath = filePath.replace(/\\/g, "/");
	    	
	    	$("#inputLayoutFilePath"+rownum).val(filePath);
	    	
	    	tableData[rownum].inputLayoutFile = filePath;
	    }
	    
	 	// 2020.04.03 kwb 텍스트창에 경로를 직접 입력할 경우, 레이아웃 파일의 경로를 변수에 세팅해주는 함수
	    var setInputLayoutFile = function(rownum){
	    	var filePath = $("#inputLayoutFilePath"+rownum).val();
	    	tableData[rownum].inputLayoutFile = filePath.replace(/\\/g, "/");
	    }
		
	 	// 프로젝트 파일(*.adnn) 리스트 가져와서 드롭다운 메뉴에 추가해주는 함수
		var getFileList = function() {
			var filePath = "model"; 
			var filePattern = ".*\\.adnn";
			
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
					listName = projectNameList;
				},
				error : function(data, status, err) {
					alert(err);
				}
			});
		};
	
		var dropdownClick = function(rownum){
			getFileList();
			
			$.each(listName, function(index) {
				var newProjectName = '<a class="dropdown-item" href="#">'+listName[index].replace(/^.*[\\\/]/, '')+'</a>';			
				$(newProjectName).appendTo('#projectNameDropdownMenu'+rownum);
			});
			
			$("#projectNameDropdown"+rownum).click(function() {
				$("#projectNameDropdownMenu"+rownum).empty();
				dropdownClick(rownum);
			});
			$("#projectNameDropdownMenu"+rownum+" a").click(function(){
				projectName = $(this).text();
				$(this).parents('.dropdown').find('.dropdown-toggle').html(projectName);
				tableData[rownum].projectName = projectName;

				// 해당 프로젝트의 기준정보가 생성되어 있는지 체크
				checkMeta(projectName, rownum);
			});
		}

		// 2020.04.10 kwb 해당 프로젝트의 기준정보가 생성되어 있는지 체크하는 함수
		var checkMeta = function(project, rowNum) {
			var formData = {
				"projectName" : project
			}
				
			$.ajax({
				type : "GET",
				url : "/anomaly/checkMeta",
				data : formData,
				dataType : "json",
				cache: false,
				success : function(isExistedMeta) {
					if(isExistedMeta){
						// 2020.04.10 kwb 선택한 프로젝트의 기준정보가 존재하면, 드롭다운 메뉴의 테마색을 초록색으로 변경하고 경고문구를 숨김
						$("#projectNameDropdown"+rowNum).removeClass("btn-outline-danger");
						$("#projectNameDropdown"+rowNum).addClass("btn-outline-success");
						$("#alertTextMetaIsNotExisted"+rowNum).hide();
					}
					else{
						// 2020.04.10 kwb 선택한 프로젝트의 기준정보가 존재하지 않으면, 드롭다운 메뉴의 테마색을 빨간색으로 변경하고 경고문구를 보여줌
						$("#projectNameDropdown"+rowNum).removeClass("btn-outline-success");
						$("#projectNameDropdown"+rowNum).addClass("btn-outline-danger");
						$("#alertTextMetaIsNotExisted"+rowNum).show();

						// 2020.04.10 kwb 기준정보가 생성되지 않은 프로젝트를 선택한 상태로 실행 버튼을 눌렀을 때, 실행을 막기위하여 projectName(전역변수) 초기화
						// 실행 버튼 클릭 이벤트를 처리하는 부분($('#btnExecutePredictMulti').on('click', function() {~})에서 해당 값을 체크하고 있음
						tableData[rowNum].projectName = "";
					}
				},
				error : function(request, status, error) {
					alert("기준정보 생성 여부를 체크하던 중, 오류가 발생하였습니다.\n\n"+"code:"+request.status+"\n"+"message:"+request.responseText);
				}
			});
		}
		
		var addNewRow = function() {
			var counter = 1;
			var rowCnt = 2;
			
			getFileList();
			
		    $("#addrow").on("click", function () {
		    	
		        var newRow = $("<tr>");
		        var cols = "";
		        
				// No.
				cols += '<td>'+rowCnt+'</td>';
				// 데이터 파일
				cols += '<td><div class="d-flex"><input type="text" class="form-inline form-control" id="inputDataFilePath'+counter+'" placeholder="*.csv, *.txt, *.gz 등   (EX - D:/watchdog/data/test.csv)   ※ 구분자, 띄어쓰기 주의" oninput="setInputDataFile('+counter+')" required/><div class="btnSelectFile ml-1" id="btnSelectDataFile'+counter+'" style="display:none"><input type="file" id="inputDataFile'+counter+'" onchange="changeInputDataFilePath('+counter+')"/><label for="inputDataFile'+counter+'">파일선택</label></div></div></td>';
				// 레이아웃 파일
				cols += '<td><div class="d-flex"><input type="text" class="form-inline form-control" id="inputLayoutFilePath'+counter+'" placeholder="*.properties   (EX - D:/watchdog/layout/test.properties)   ※ 구분자, 띄어쓰기 주의" oninput="setInputLayoutFile('+counter+')" required/><div class="btnSelectFile ml-1" id="btnSelectLayoutFile'+counter+'" style="display:none"><input type="file" id="inputLayoutFile'+counter+'" onchange="changeInputLayoutFilePath('+counter+')"/><label for="inputLayoutFile'+counter+'">파일선택</label></div></div></td>';
				// 프로젝트
				cols += '<td><div class="dropdown"><button type="button" class="btn btn-sm btn-outline-danger dropdown-toggle px-4 py-2" id="projectNameDropdown'+counter+'" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" required>*.adnn</button><div class="dropdown-menu" id="projectNameDropdownMenu'+counter+'" aria-labelledby="projectNameDropdown'+counter+'" style="max-height: 250px; overflow-y: auto"></div><div class="alertText mt-1" id="alertTextMetaIsNotExisted'+counter+'"> 해당 프로젝트의 기준정보가 생성되어있지 않습니다. </div></div></td>';
				// 항목별 결과
				cols += '<td><div class="custom-control custom-radio"><input type="radio" class="custom-control-input" id="columnCalculateY'+counter+'" name="customRadio'+counter+'" checked="checked"><label class="custom-control-label font-weight-bold" for="columnCalculateY'+counter+'" id="columnCalculateYLabel'+counter+'" onclick="setColumnCalculateY('+counter+')">파일 및 항목 이상여부 산출</label></div><div class="custom-control custom-radio"><input type="radio" class="custom-control-input" id="columnCalculateN'+counter+'" name="customRadio'+counter+'"><label class="custom-control-label" for="columnCalculateN'+counter+'" id="columnCalculateNLabel'+counter+'" onclick="setColumnCalculateN('+counter+')">파일 이상여부 산출</label></div></td>';
				// 행 삭제 버튼
				cols += '<td><input type="button" class="ibtnDel btn btn-sm btn-danger" value="X"></td>';
				
		        newRow.append(cols);
		        $("table.order-list").append(newRow);
				
		        dropdownClick(counter);
		        tableData[counter] = {
					"columnCalculateYN" : "Y",	//2020.04.09 kwb 별도로 선택을 하지 않으면 기본적으로 Y
					"inputDataFile" : "",
					"inputLayoutFile" : "",
					"projectName" : ""
				};
		        
		     	// 브라우저에 따라 각 행의 UI를 변경
		        setBrowserUI(counter);
		        
		        counter++;
		        rowCnt++;
		    });
	
	
		    $("table.order-list").on("click", ".ibtnDel", function (event) {
			    //alert($(this).closest("tr").prevAll().length);
			    // delete data 
			    var counterIdx = $(this).closest("tr").find('.custom-control-input')[0].id.replace("columnCalculateY","");
				delete tableData[counterIdx]
				
		        $(this).closest("tr").remove();       
		        //counter -= 1
		        rowCnt -= 1;
		    });
		}
		
		// 데이터 이상탐지(복수) 실행 함수
		var executePredictMulti = function() {
			
			$.ajax({
				type : "put",
				url : "/anomaly/predictmulti",
				data : JSON.stringify(tableData),
				contentType : "application/json",
				async : false,
				cache: false,
				beforeSend: function() {
				},
				success : function(data) {
					alert("이상탐지(복수)가 완료되었습니다.\n");
					
					// 실행결과 필드 제목 보이기
					$("#titleResult").show();
					// 실행 결과를 간단히 테이블로 만들어 보여줌
					appendResultTable(data);
				},
				error : function(request, status, error) {
					alert("이상탐지 중, 오류가 발생하였습니다.\n\n"+"code:"+request.status+"\n"+"message:"+request.responseText);
				},
				complete: function() {
					// 로딩 UI 숨기기
					$("#pageLoading").hide();
				}
			})
		}
		
		// 실행결과를 테이블로 만들어 페이지 하단에 추가해주는 함수
		var appendResultTable = function(resultData) {
			
			// 2020.03.27 kty remove existing table
			var elem = document.getElementById('resultTable');
			if (elem != null){
				elem.parentNode.removeChild(elem);
			}
			
			var resultList = JSON.parse(resultData);
			var tableHead = '<table class="table table-striped table-borderless" id="resultTable"><caption>PSI 이상 기준: 1 / 항목 이상 기준: 변동률 50%</caption><thead><tr><th scope="col">No.</th><th scope="col">프로젝트명</th><th scope="col">파일명</th><th scope="col">파일 건수</th><th scope="col">PSI</th><th scope="col">이상 항목 건수</th><th scope="col">카이제곱 이상 항목 수</th></tr></thead><tbody id="resultTbody"></tbody></table>';
			$('main').append(tableHead);
			//$("#resultTbody").empty();
			var predRow = "";
			resultList.forEach(function (resultRow, index){
				predRow = "<tr>";
				predRow += '<td>'+(index+1)+'</td>';
				predRow += '<td>'+resultRow.projectName+'</td>';
				predRow += '<td>'+resultRow.fileName+'</td>';
				predRow += '<td>'+resultRow.fileCount+'</td>';
				predRow += '<td>'+resultRow.psi+'</td>';
				predRow += '<td>'+resultRow.car+'</td>';
				predRow += '<td>'+resultRow.chiSqCnt+'</td>';
				predRow += "</tr>";
				
				$("#resultTbody").append(predRow);
			});
			
			$('#resultTable td:nth-child(5)').each(function() {
				var resultVal = $(this).text();
				if(resultVal >= 1){
					$(this).css('backgroundColor', '#f76e6e')
				} 
			});
	
			$('#resultTable td:nth-child(6)').each(function() {
				var resultVal = $(this).text();
				if(resultVal > 0){
					$(this).css('backgroundColor', '#f76e6e')
				} 
			});
	
			$('#resultTable td:nth-child(7)').each(function() {
				var resultVal = $(this).text();
				if(resultVal > 0){
					$(this).css('backgroundColor', '#f76e6e')
				} 
			});
		};
		
		
		/* ----- 이벤트 처리 ----- */
	    
		$("#projectNameDropdown0").click(function() {
			$("#projectNameDropdownMenu0").empty();
			dropdownClick(0);
		});
		
		$('#btnExecutePredictMulti').on('click', function() {
			
			/* 2020.04.03 kwb 비어있는 정보가 있으면, 실행하지 않고 alert 처리 */
			
			// 정보가 비어있는지 여부
			var isEmpty = false;
			// for문을 돌면서 각 행에 비어있는 정보가 하나라도 있으면 isEmpty = true
			for(var rowNum=0; rowNum<tableData.length; rowNum++){
				if(tableData[rowNum].columnCalculateYN.trim().length == 0 || tableData[rowNum].inputDataFile.trim().length == 0
						|| tableData[rowNum].inputLayoutFile.trim().length == 0 || tableData[rowNum].projectName.trim().length == 0){
					
					isEmpty = true;
					
					// 공백 문자로만 경로가 적혀있을 때, 텍스트 박스를 리셋하여 다시 빨간색으로 표시되게끔 처리 
					if(tableData[rowNum].inputDataFile.trim().length == 0){
						$("#inputDataFilePath"+rowNum).val("");
					}
					if(tableData[rowNum].inputLayoutFile.trim().length == 0){
						$("#inputLayoutFilePath"+rowNum).val("");
					}
				}
			}
			// 비어있는 정보가 있으면 alert을 띄우고, 없으면 실행
			if(isEmpty){
				alert("빨간색으로 표시된 필수정보를 모두 입력해주세요.\n")
			}
			else{
				// 로딩 UI 보이기
		        $("#pageLoading").show();
			
				// 로딩 이미지를 보이게 하기위한 딜레이
				setTimeout(executePredictMulti, 1000);
			}
		});
		
		
	    $(document).ready(function() {
	    	// 2020.03.30 kwb 브라우저를 체크하여 브라우저에 따라 다른 UI를 제공하도록 처리
	    	checkBrowser();
	    	
	    	tableData.push({
				"columnCalculateYN" : "Y",	//2020.04.09 kwb 별도로 선택을 하지 않으면 기본적으로 Y
				"inputDataFile" : "",
				"inputLayoutFile" : "",
				"projectName" : ""
			});
	    	addNewRow();
	    });
	    
	</script>
</body>
</html>