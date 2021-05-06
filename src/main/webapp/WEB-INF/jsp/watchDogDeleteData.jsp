<%@ page language="java" pageEncoding="UTF-8"
    contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!doctype html>
<html lang="ko">
<head>
	<meta charset="UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
	<meta name="description" content="WatchDog 실행 화면" />
	
	<title>WatchDog 데이터 삭제</title>
	
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
                                                    <a class="nav-link active py-1" href="/deletedata">
                                                        <i class="fas fa-folder-minus"></i> 데이터 삭제
                                                        <span class="sr-only">(current)</span>
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

            <main role="main" class="col-md-10 ml-sm-auto col-lg-10 px-4" id="predMain">
	            <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
	                <h1 class="h2">프로젝트 데이터 삭제</h1>
	                <div class="btn-group">
	                    <button type="button" class="btn btn-primary" id="btnExecuteDelete" data-loading-text="실행중...">실행</button>
	                </div>
	            </div>
				
				<form class="was-validated">
		            <div class="mb-1">데이터를 삭제할 프로젝트 선택</div>
		            <div class="dropdown mb-4">
		            	<button type="button"
							class="btn btn-sm btn-outline-danger dropdown-toggle px-4 py-2"
							id="projectNameDropdown" data-toggle="dropdown"
							aria-haspopup="true" aria-expanded="false" required>*.adnn</button>
		            	<div class="dropdown-menu" id="projectNameDropdownMenu" aria-labelledby="projectNameDropdown" style="max-height: 300px; overflow-y: auto"></div>
		            	
		            	<!-- 
		            	<div class="mb-1">항목별 이상탐지 결과 산출여부</div>
						<div class="custom-control custom-radio">
					    	<input type="radio" class="custom-control-input" id="deleteBatchY" name="customRadio" checked="checked">
					    	<label class="custom-control-label font-weight-bold" for="deleteBatchY" id="deleteBatchYLabel">기타 임시 산출물 삭제</label>
					  	</div>
					  	<div class="custom-control custom-radio mb-4">
					    	<input type="radio" class="custom-control-input" id="deleteBatchN" name="customRadio">
					    	<label class="custom-control-label" for="deleteBatchN" id="deleteBatchNLabel">기타 임시 산출물 삭제하지 않음</label>
					  	</div>
					  	 -->
		            </div>
		            
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

		var projectName = "";	// 프로젝트 파일 이름
		var deleteBatchYN = ""; // 배치 테이블 데이터 삭제 여부
		
		var fn_setProjectNameDropdown = function(url) {
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
	                    
	                    $("#projectNameDropdown").removeClass("btn-outline-danger");
						$("#projectNameDropdown").addClass("btn-outline-success");
	                });
	            },
	            error : function(request, status, error) {
					alert('code:'+request.status+'\n'+'message:'+request.responseText+'\n'+'error:'+error);
				}
	        });
	    };

	
		// 데이터 이상감지 실행 함수
		var executeDelete = function() {
			alert(projectName);
			$.ajax({
				type : "GET",
				url : "<c:url value='/main/deleteproject/" + projectName + "' />",
				dataType : "json",
				cache: false,
				success : function(data) {
					alert("프로젝트 데이터 삭제가 완료되었습니다.\n");
				},
				error : function(request, status, error) {
					alert("프로젝트 데이터 삭제 중, 오류가 발생하였습니다.\n\n"+"code:"+request.status+"\n"+"message:"+request.responseText);
				},
				complete: function() {
					// 로딩 UI 숨기기
					$("#pageLoading").hide();
				}
			});
		}
		
		$("#deleteBatchYLabel").on("click", function() {
			// 항목별 이상탐지 결과 산출 여부 값 세팅
			deleteBatchYN = "Y";
			$(this).addClass("font-weight-bold");
			$("#deleteBatchNLabel").removeClass("font-weight-bold");
		});
		$("#deleteBatchNLabel").on("click", function() {
			// 항목별 이상탐지 결과 산출 여부 값 세팅
			deleteBatchYN = "N";
			$(this).addClass("font-weight-bold");
			$("#deleteBatchYLabel").removeClass("font-weight-bold");
		});
		
		// 실행
		$("#btnExecuteDelete").on("click", function() {
			
			if(projectName.trim().length == 0){
				alert("빨간색으로 표시된 필수정보를 모두 입력해주세요.\n")
			}
			else{
				// 로딩 UI 보이기
		        $("#pageLoading").show();
			
				// 로딩 이미지를 보이게 하기위한 딜레이
				setTimeout(executeDelete, 1000);
			}
	    });
		
		
	    $(document).ready(function() {
	    	fn_setProjectNameDropdown("<c:url value='/main/allprojectnames' />");
	    });

	</script>
</body>
</html>