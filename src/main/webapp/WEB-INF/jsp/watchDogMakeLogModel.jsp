<%@ page language="java" pageEncoding="UTF-8"
    contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!doctype html>
<html lang="ko">
<head>
	<meta charset="UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
	<meta name="description" content="WatchDog 실행 화면" />
	
	<title>WatchDog Log 모형 생성</title>
	
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
                            <a class="nav-link collapsed text-truncate" href="#submenu1" data-toggle="collapse" data-target="#submenu1">
                                <i class="fas fa-database"></i>
                                <span class="d-none d-sm-inline">배치 파일 이상감지</span>
                            </a>
                            <div class="collapse" id="submenu1" aria-expanded="false">
                                <ul class="flex-column pl-4 nav">
                                    <li class="nav-item">
                                        <a class="nav-link collapsed" href="#submenu1-1" data-toggle="collapse" data-target="#submenu1-1">
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
	                <h1 class="h2">Json Log 모형 생성</h1>
	                <div class="btn-group">
	                    <button type="button" class="btn btn-primary" id="btnExecuteMakeLogModel" data-loading-text="실행중...">실행</button>
	                </div>
	            </div>
				
				<form class="was-validated">
					<div class="mb-1">대상 패키지 입력</div>
			        <div class="d-flex mb-4">
			           	<input type="text" class="form-inline form-control" id="packageId" 
			           		placeholder="ex) PKG_0900100_100" required/>
			        </div>
			            			        
			        <div class="mb-1">대상 스냅샷 입력</div>
			        <div class="d-flex mb-4">
			          	<input type="text" class="form-inline form-control" id="snapshotId" 
			           		placeholder="ex) 20200101" required/>
			        </div>
		            
		            <div class="mb-1">대상 적용일시 입력</div>
		            <div class="d-flex mb-4">
		            <input type="text" class="form-control" id="applyDtim" 
		            	placeholder="ex) 20200101000000" required>
		            </div>
		            
		            <div class="mb-1">학습 데이터 시작일 입력</div>
		            <div class="d-flex mb-4">
		            <input type="text" class="form-control" id="fromDtim" 
		            	placeholder="ex) 2020-01-01" required>
		            </div>
		            
		            <div class="mb-1">학습 데이터 종료일 입력</div>
		            <div class="d-flex mb-4">
		            <input type="text" class="form-control" id="toDtim" 
		            	placeholder="ex) 2020-01-02" required>
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
		
		var packageId = "";
		var snapshotId = "";
		var applyDtim = "";
		var fromDtim = "";
		var toDtim = "";
		
		
		/* ----- 함수 ----- */
		// CSV 생성 실행 함수
		var executeMakeLogModel = function() {
			var formData = {
				"pkgId" : packageId,
				"snstId" : snapshotId,
				"aplyDtim" : applyDtim,
				"fromDtim" : fromDtim,
				"toDtim" : toDtim
			}
			
			$.ajax({
				type : "put",
				url : "/anomaly/trainlogmodel",
				data : JSON.stringify(formData),
				contentType : "application/json",
				async : false,
				cache : false,
				success : function(data) {
					alert("로그 모형 개발이 완료되었습니다.\n");
				},
				error : function(request, status, error) {
					alert("로그 모형 개발 중, 오류가 발생하였습니다.\n\n"+"code:"+request.status+"\n"+"message:"+request.responseText);
				},
				complete: function() {
					// 로딩 UI 숨기기
					$("#pageLoading").hide();
				}
			});
		}

		
		/* ----- 이벤트 처리 ----- */
		
		$("#packageId").on("change", function(){
	    	packageId = $(this).val();
	    	
			$("#packageId").val(packageId);
		});
		
		$("#snapshotId").on("change", function(){
	    	snapshotId = $(this).val();
	    	
			$("#snapshotId").val(snapshotId);
		});
		
		$("#applyDtim").on("change", function(){
	    	applyDtim = $(this).val();
	    	
			$("#applyDtim").val(applyDtim);
		});
		
		$("#fromDtim").on("change", function(){
	    	fromDtim = $(this).val();
	    	
			$("#fromDtim").val(fromDtim);
		});
		
		$("#toDtim").on("change", function(){
	    	toDtim = $(this).val();
	    	
			$("#toDtim").val(toDtim);
		});
		
		// 실행
	    $('#btnExecuteMakeLogModel').on('click', function() {
			packageId = $("#packageId").val();
			snapshotId = $("#snapshotId").val();
			applyDtim = $("#applyDtim").val();
			fromDtim = $("#fromDtim").val();
			toDtim = $("#toDtim").val();
						
			if(packageId.trim().length == 0 || snapshotId.trim().length == 0 
					|| applyDtim.trim().length == 0){
				
				// 공백 문자로만 경로가 적혀있을 때, 텍스트 박스를 리셋하여 다시 빨간색으로 표시되게끔 처리 
				if(packageId.trim().length == 0){
					$("#packageId").val("");
				}
				if(snapshotId.trim().length == 0){
					$("#snapshotId").val("");
				}
				if(applyDtim.trim().length == 0){
					$("#applyDtim").val("");
				}
				if(fromDtim.trim().length == 0){
					$("#fromDtim").val("");
				}
				if(toDtim.trim().length == 0){
					$("#toDtim").val("");
				}
				
				alert("빨간색으로 표시된 필수정보를 모두 입력해주세요.\n")
			}
			else{
				// 로딩 UI 보이기
		        $("#pageLoading").show();
			
				// 로딩 이미지를 보이게 하기위한 딜레이
				setTimeout(executeMakeLogModel, 1000);
			}	
		});
		
		
	    $(document).ready(function() {

	    });

	</script>
</body>
</html>