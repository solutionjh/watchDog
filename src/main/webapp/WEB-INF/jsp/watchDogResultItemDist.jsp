<%@ page language="java" pageEncoding="UTF-8"
    contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!doctype html>
<html lang="ko">
<head>
	<meta charset="UTF-8" />
	<meta name="viewport"
	    content="width=device-width, initial-scale=1, shrink-to-fit=no" />
	<meta name="description" content="WatchDog 항목 분포 이상 조회 화면" />
	
	<title>WatchDog 항목 분포 이상 조회</title>
	
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
                                                    <a class="nav-link active py-1" href="/resultitemdist">
                                                        <i class="fas fa-poll-h"></i> 항목 분포 변화 조회
                                                        <span class="sr-only">(current)</span>
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
	                
	                <h1 class="h2">WatchDog 항목 분포 차이</h1>
	                
	                <!-- 2020.03.27 kwb 드롭다운 메뉴가 잘려보이지 않도록, 페이지를 왔다갔다 이동시에 드롭다운 메뉴의 위치가 고정되어 보이도록 처리 -->
					<div align="right">
		                <span class="dropdown" style="margin-right:100px;">
		                    <button type="button"
		                        class="btn btn-sm btn-outline-secondary dropdown-toggle"
		                        type="button" id="projectNameItemDropdown" data-toggle="dropdown"
		                        aria-haspopup="true" aria-expanded="false">프로젝트 선택</button>
		                    <div class="dropdown-menu dropdown-menu-right" id="projectNameItemDropdownMenu"
		                        aria-labelledby="projectNameItemDropdown" style="max-height: 400px; overflow-y: auto"></div>
		                </span>
		                <span class="dropdown" style="margin-right:100px;">
		                    <button type="button"
		                        class="btn btn-sm btn-outline-secondary dropdown-toggle"
		                        type="button" id="regdtimDropdown" data-toggle="dropdown"
		                        aria-haspopup="true" aria-expanded="false">수행일시 선택</button>
		                    <div class="dropdown-menu dropdown-menu-right" id="regdtimDropdownMenu"
		                        aria-labelledby="regdtimDropdown" style="max-height: 400px; overflow-y: auto"></div>
		                </span>
		                <span class="dropdown">
		                    <button type="button"
		                        class="btn btn-sm btn-outline-secondary dropdown-toggle"
		                        type="button" id="fieldNameDropdown" data-toggle="dropdown"
		                        aria-haspopup="true" aria-expanded="false">항목 선택</button>
		                    <div class="dropdown-menu dropdown-menu-right" id="fieldNameDropdownMenu"
		                        aria-labelledby="fieldNameDropdown" style="max-height: 400px; overflow-y: auto"></div>
		                </span>
		            </div>
	            </div>
	
	            <table id="resultitemdisttable" data-toggle="resultitemdisttable"
	                data-content-type="application/json" 
	                data-sortable="true" data-detail-view="true" data-show-export="false" data-sortable="false"
	                data-click-to-select="true" data-pagination="true" data-show-footer="false"
	                data-theadClasses="thead-light" data-pagination="true" data-page-list="[10,25,50,all]"
	                data-side-pagination="client"
	                data-detail-formatter="detailFormatter" data-response-handler="responseHandler">
	            </table>
            </main>
        </div>
    </div>

    <!-- js 파일 적용 -->
    <script src="/webjars/jquery/dist/jquery.min.js"></script>
    <script src="/webjars/bootstrap/4.3.1/dist/js/bootstrap.bundle.js"></script>
    <script src="/webjars/bootstrap-table/dist/bootstrap-table.min.js"></script>
    <script src="/webjars/bootstrap-table/dist/locale/bootstrap-table-ko-KR.min.js"></script>
    <script src="/webjars/bootstrap-table/dist/extensions/export/bootstrap-table-export.min.js"></script>
    <script src="/webjars/font-awesome/5.12.0/js/fontawesome.min.js"></script>
    
    <!-- 테이블 세팅 관련 -->
    <script type="text/javascript">
		var projectName="";
	    var regdtim="";
	    var fieldName = "";
	
	    var $table = $('#resultitemdisttable');
	    var $remove = $('#resultremove');
	
	    var fn_setprojectNameItemDropdown = function(url, target) {
	        $.ajax({
	            type : "GET",
	            url : url, 
	            dataType : "json",
	            cache : false,
	            success : function(projectNameList) {
	                var listName = projectNameList;
	                $.each(listName, function(index) {
	                    var newProjectName = '<a class="dropdown-item" href="#">'+listName[index]+'</a>';
	                    $(newProjectName).appendTo('#projectNameItemDropdownMenu');
	                });
	                
	                $("#projectNameItemDropdownMenu a").click(function(){
	                    projectName = $(this).text();
	                    $(this).parents('.dropdown').find('.dropdown-toggle').html(projectName);
	                    fn_setRegdtimDropdown("<c:url value='/resultitemdist/" + projectName + "/regdtim/' />", $('#regdtimDropdown'))
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
	                    $table.bootstrapTable('refreshOptions', {
	                        url: '/resultitemdist/'+projectName+'/'+regdtim,
	                        onLoadError: function(status){
	                        	alert("[STATUS "+status+"]"+"항목 분포 변화 조회에 실패했습니다");
	                        }
	                    })
	                    fn_setFieldNameDropdown("<c:url value='/resultitemdist/fieldname/" + projectName + "/" + regdtim +"/' />");
	                });
	            },
	            error : function(request, status, error) {
					alert('code:'+request.status+'\n'+'message:'+request.responseText+'\n'+'error:'+error);
				}
	        });
	    };

		var fn_setFieldNameDropdown = function(url){

			$.ajax({
				type : "GET",
				url : url,
				dataType : "json",
				cache : false,
				success : function(fieldList) {
					$("#fieldNameDropdownMenu").empty();
			    	$("#fieldNameDropdown").text('항목 선택');
			    	fieldName = "";
					
					var listName = fieldList
			    	$.each(listName, function(index){
				    	var newFieldName = '<a class="dropdown-item" href="#">'+fieldList[index]+'</a>';
				    	$(newFieldName).appendTo('#fieldNameDropdownMenu');
				    })

				    $('#fieldNameDropdownMenu a').click(function(){
					    fieldName = $(this).text();
						$(this).parents('.dropdown').find('.dropdown-toggle').html(fieldName);
						$table.bootstrapTable('refreshOptions', {
							url: '/resultitemdist/'+projectName+'/'+regdtim+'/'+fieldName,
							onLoadError: function(status){
	                        	alert("[STATUS "+status+"]"+"항목 분포 변화 조회에 실패했습니다");
	                        }
						})
						
					})
				},
				error : function(request, status, error) {
					alert('code:'+request.status+'\n'+'message:'+request.responseText+'\n'+'error:'+error);
				}
			})	
		}
		
	    function responseHandler(res) {
	        $.each(res.rows, function (i, row) {
	            row.state = $.inArray(row.registerDtim, selections) !== -1
	        })
	        return res
	    }
	
	    function detailFormatter(index, row) {
	        var html = []
	        $.each(row, function (key, value) {
	            html.push('<p><b>' + key + ':</b> ' + value + '</p>')
	        })
	        return html.join('')
	    }
	
	
	    window.operateEvents = {
	        'click .remove': function (e, value, row, index) {
	            $table.bootstrapTable('remove', {
	                field: 'registerDtim',
	                values: [row.registerDtim]
	            })
	        }
	    }
	
	    function initTable() {
	    	
	        $table.bootstrapTable('destroy').bootstrapTable({
	            height: 700,
	            locale: 'ko-KR',
	            columns: [
	            {
	              field: 'state',
	              checkbox: true,
	              align: 'center',
	              valign: 'middle'
	            }, {
	              title: '항목코드',
	              field: 'fieldName',
	              align: 'center',
	              sortable: true,
	              valign: 'middle'
	            }, {
	                title: '항목 값',
	                field: 'itemValue',
	                align: 'center',
	                sortable: true,
	                valign: 'middle'
	            }, {
	              title: '개발시점 분포(%)',
	              field: 'devProbability',
	              align: 'right',
	              sortable: true,
	              valign: 'middle'
	            }, {
	              title: '선택시점 분포(%)',
	              field: 'nowProbability',
	              align: 'right',
	              sortable: true,
	              valign: 'middle'
	            }, {
	                title: '분포 차이(%)',
	                field: 'diff',
	                align: 'right',
	                sortable: true,
	                valign: 'middle'
	            }],
	        })
	      }
	
	    $(document).ready(function() {
	        fn_setprojectNameItemDropdown("<c:url value='/resultitemdist/projectnames' />", $('projectNameItemDropdown'));
	        initTable();
	    });
	</script>
</body>
</html>