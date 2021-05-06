<%@ page language="java" pageEncoding="UTF-8"
    contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport"
        content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <meta name="description" content="WatchDog 실행 화면" />
    
    <title>WatchDog 데이터 파일 이상감지</title>
    
    <!-- External CSS -->
    <link rel="stylesheet" href="/webjars/bootstrap/4.3.1/dist/css/bootstrap.min.css" />
    <link rel="stylesheet" href="/webjars/bootstrap-table/dist/bootstrap-table.min.css" />
    <link rel="stylesheet" href="/webjars/chart.js/dist/Chart.min.css" />
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
                            <a class="nav-link active text-truncate" href="/main">
                                <i class="fas fa-home"></i>
                                <span class="d-none d-sm-inline">Home</span>
                                <span class="sr-only">(current)</span>
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
                <h1 class="h2">WatchDog 데이터 파일 이상감지 현황</h1>
                <div> </div>
                <div> <span id="recentExecute">최근 실행 시간</span></div>
                <div class="btn-group">
                  <button type="button" class="btn btn-outline-secondary">기간 선택</button>
                  <button type="button" class="btn btn-secondary dropdown-toggle dropdown-toggle-split" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <span class="sr-only">날짜 선택</span>
                  </button>
                  <div class="dropdown-menu" id="recentDropdownMenu">
                    <a class="dropdown-item" href="#">최근 1일</a>
                    <a class="dropdown-item" href="#">최근 1주</a>
                    <a class="dropdown-item" href="#">최근 1개월</a>
                    <a class="dropdown-item" href="#">최근 3개월</a>
                    <a class="dropdown-item" href="#">최근 6개월</a>
                    <a class="dropdown-item" href="#">최근 1년</a>
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item" href="#">전체</a>
                  </div>
                </div>
            </div>
             
            <div>
                <canvas id="mainDoughnut" width="400" height="400" style="max-width: 400px; float:left;"></canvas>
                <canvas id="mainStackedBar" width="400" height="400" style="max-width: 600px; float:left;"></canvas>
            </div>
            <div>
                
            </div>
            </main>
        </div>
    </div>

    <script src="/webjars/jquery/dist/jquery.min.js"></script>
    <script src="/webjars/bootstrap/4.3.1/dist/js/bootstrap.bundle.js"></script>
    <script src="/webjars/bootstrap-table/dist/bootstrap-table.min.js"></script>
    <script src="/webjars/bootstrap-table/dist/locale/bootstrap-table-ko-KR.min.js"></script>
    <script src="/webjars/bootstrap-table/dist/extensions/export/bootstrap-table-export.min.js"></script>
    <script src="/webjars/font-awesome/5.12.0/js/fontawesome.min.js"></script>
    <script src="/webjars/bootstrap/4.3.1/dist/js/bootstrap.min.js"></script>
    <script src="/webjars/chart.js/dist/Chart.min.js"></script>
    
    <script type="text/javascript">
        var recentPeriod = "all";
        var mainDoughnutChart = null;
        var mainStackedBarChart = null;
        var recentExecute = "";
        
        $("#recentDropdownMenu a").click(function(){
            recentPeriod = $(this).text();
            $(this).parents('.btn-group').find('.btn-outline-secondary').html(recentPeriod);
            
            fn_changePathVar();
            fn_setGraphs("<c:url value='/main/anomalycnt/" + recentPeriod + "' />");
        });

        var fn_changePathVar = function(){
            if(recentPeriod == "최근 1일"){
                recentPeriod = "oneday";
            }else if(recentPeriod == "최근 1주"){
                recentPeriod = "oneweek";
            }else if(recentPeriod == "최근 1개월"){
                recentPeriod = "onemonth";
            }else if(recentPeriod == "최근 3개월"){
                recentPeriod = "onequarter";
            }else if(recentPeriod == "최근 6개월"){
                recentPeriod = "onehalf";
            }else if(recentPeriod == "최근 1년"){
                recentPeriod = "oneyear";
            }else if(recentPeriod == "전체"){
                recentPeriod = "all";
            }
        }
        
        var fn_setRecentDtim = function(url){
            $.ajax({
                type:"GET",
                url:url,
                dataType:"json",
                cache : false,
                success:function(recentRegDtims){
                    recentExecute = recentRegDtims[0];
                    var recentTimeText = document.getElementById('recentExecute');
                    recentTimeText.innerHTML = '최근실행시간: '+recentExecute;
                },
                error:function(request, status, error){
                    alert('code:'+request.status+'\n'+'message:'+request.responseText+'\n'+'error:'+error);
                }
            });
        }
        
        var fn_setGraphs = function(url){
            $.ajax({
                type: "GET",
                url: url,
                dataType: "json",
                cache : false,
                success: function(anomalyCounts){
                    var totalData = {"anomalyCnt":0, "normalCnt":0};
                    var dtimData = {"dtims":[], "anomalyCnts":[], "normalCnts":[]};
                    anomalyCounts.forEach(function(row, index){
                        totalData.anomalyCnt += parseInt(row.anomalyCnt);
                        totalData.normalCnt += parseInt(row.normalCnt);
                        
                        dtimData.dtims.push(row.dtim);
                        dtimData.anomalyCnts.push(row.anomalyCnt);
                        dtimData.normalCnts.push(row.normalCnt);
                    });
                    //recentExecute = dtimData.regDtims[anomalyCounts.length-1];

                    //var recentTimeText = document.getElementById('recentExecute');
                    //recentTimeText.innerHTML = '최근실행시간: '+recentExecute;
                    
                    fn_setDoughnutGraph(totalData);
                    fn_setStackedBarChart(dtimData);

                },
                error:function(request, status, error){
                    alert('code:'+request.status+'\n'+'message:'+request.responseText+'\n'+'error:'+error);
                }
                
            })
        }
    
        // Any of the following formats may be used
        //var ctx = document.getElementById('myChart');
        //var ctx = document.getElementById('myChart').getContext('2d');
        //var ctx = $('#myChart');
        //var ctx = 'myChart';
        
        var fn_setDoughnutGraph = function(data){
            if(mainDoughnutChart != null){
                mainDoughnutChart.destroy();
            }
            
            var ctxDn = document.getElementById('mainDoughnut').getContext('2d');
            // And for a doughnut chart
            mainDoughnutChart = new Chart(ctxDn, {
                type: 'doughnut',
                data: {
                    labels: ['정상파일', '이상파일'],
                    datasets: [{
                        label: '# of Executions',
                        data: [data.normalCnt, data.anomalyCnt],
                        backgroundColor: [
                            'rgba(54, 162, 235, 0.2)',
                            'rgba(255, 99, 132, 0.2)'
                        ],
                        borderColor: [
                            'rgba(54, 162, 235, 1)',
                            'rgba(255, 99, 132, 1)'
                        ],
                        borderWidth: 1
                    }]
                },
                options: {
                    title: {
                        display: true,
                        text: "전체 파일 이상탐지 결과",
                        fontSize: 17,
                        fontStyle: 'bold',
                        padding: 10
                    },
                    legend: {
                        display: true,
                        padding: 30
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
        }
        
        var fn_setStackedBarChart = function(data){
            if(mainStackedBarChart != null){
                mainStackedBarChart.destroy();
            }
            var ctxSb = document.getElementById('mainStackedBar').getContext('2d');
            mainStackedBarChart = new Chart(ctxSb,{
                    type:"bar",
                    data: {
                        labels: data.dtims,
                        datasets: [
                            {
                                label: '정상',
                                data: data.normalCnts,
                                backgroundColor: '#D6E9C6'
                            },
                            
                            {
                                label: '이상',
                                data: data.anomalyCnts,
                                backgroundColor: '#EBCCD1'
                        }]
                    },
                    options: {
                        title: {
                            display: true,
                            text: "일별 파일 이상탐지 건수",
                            fontSize: 17,
                            fontStyle: 'bold',
                            padding: 10
                        },
                        legend: {
                            display: true,
                            padding: 10
                        },
                        layout: {
                            padding: {
                                left: 30,
                                right: 30,
                                top: 10,
                                bottom: 10
                            }
                        },
                        responsive: false,
                        scales: {
                            xAxes: [{ stacked: true }],
                            yAxes: [{ stacked: true, ticks: {beginAtZero: true} }]
                        }
                    }
                });
        }
    
         
        
        $(document).ready(function() {
            fn_setRecentDtim("<c:url value='/main/" + "regdtim" + "' />");
            fn_setGraphs("<c:url value='/main/anomalycnt/" + recentPeriod + "' />");
        });
    </script>
</body>
</html>