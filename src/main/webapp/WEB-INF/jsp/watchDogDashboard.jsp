<%@ page language="java" pageEncoding="UTF-8"
         contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <meta name="description" content="WatchDog 실행 화면"/>

    <title>WatchDog 대시보드</title>

    <!-- External CSS -->
    <link rel="stylesheet" href="/webjars/bootstrap/4.3.1/dist/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="/webjars/bootstrap-table/dist/bootstrap-table.min.css"/>
    <link rel="stylesheet" href="/webjars/chart.js/dist/Chart.min.css"/>
    <link rel="stylesheet" href="/webjars/font-awesome/5.12.0/css/fontawesome.min.css"/>

    <!-- Custom CSS -->
    <link rel="stylesheet" href="main.css"/>
    <link rel="stylesheet" href="not-main.css"/>
    <link rel="stylesheet" href="font.css"/>
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
                        <a class="nav-link active text-truncate" href="#submenu1" data-toggle="collapse"
                           data-target="#submenu1">
                            <i class="fas fa-database"></i>
                            <span class="d-none d-sm-inline">배치 파일 이상감지</span>
                        </a>
                        <div class="collapse show" id="submenu1" aria-expanded="false">
                            <ul class="flex-column pl-4 nav">
                                <li class="nav-item">
                                    <a class="nav-link text-truncate" href="#submenu1-1" data-toggle="collapse"
                                       data-target="#submenu1-1">
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
                                                    <i class="fas fa-file-csv"></i> CSV 만들기
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
                                    <a class="nav-link active text-truncate" href="#submenu1-2" data-toggle="collapse"
                                       data-target="#submenu1-2">
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
                                                <a class="nav-link active py-1" href="/dashboard">
                                                    <i class="fas fa-tachometer-alt"></i> 대시보드
                                                    <span class="sr-only">(current)</span>
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
                        <a class="nav-link collapsed text-truncate" href="#submenu2" data-toggle="collapse"
                           data-target="#submenu2">
                            <i class="far fa-server"></i>
                            <span class="d-none d-sm-inline">Online 이상감지</span>
                        </a>
                        <div class="collapse" id="submenu2" aria-expanded="false">
                            <ul class="flex-column pl-4 nav">
                                <li class="nav-item">
                                    <a class="nav-link collapsed text-truncate" href="#submenu2-2"
                                       data-toggle="collapse" data-target="#submenu2-2">
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
                <h1 class="h2">WatchDog 대시보드</h1>

                <div></div>
                <div><span id="recentExecute">최근 실행 시간</span></div>
                <div class="btn-group">
                    <div class="dropdown-menu dropdown-menu-right" id="projectDropdownMenu"
                         style="max-height: 400px; overflow-y: auto"></div>
                    <button type="button" class="btn btn-outline-secondary">프로젝트명</button>
                    <button type="button" class="btn btn-secondary dropdown-toggle dropdown-toggle-split"
                            data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <span class="sr-only">프로젝트 선택</span>
                    </button>

                </div>

                <div class="btn-group">
                    <button type="button" class="btn btn-outline-secondary">기간 선택</button>
                    <button type="button" class="btn btn-secondary dropdown-toggle dropdown-toggle-split"
                            data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <span class="sr-only" style="margin-right:100px;">날짜 선택</span>
                    </button>
                    <div class="dropdown-menu dropdown-menu-right" id="recentDropdownMenu">
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
                <canvas id="dashDoughnut" width="350" height="350" style="max-width: 350px; float:left;"></canvas>
                <canvas id="dashStackedBar" width="350" height="350" style="max-width: 350px; float:left;"></canvas>
                <canvas id="dashItemBar" width="350" height="350" style="max-width: 350px; float:left;"></canvas>
                <canvas id="dashPsiLine" width="350" height="350" style="max-width: 450px; float:left;"></canvas>
                <canvas id="dashChisqBar" width="350" height="350" style="max-width: 450px; float:left;"></canvas>
                <canvas id="dashDataCntBar" width="350" height="350" style="max-width: 450px; float:left;"></canvas>
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
    var mainDoughnutChart = null;
    var mainStackedBarChart = null;
    var itemBarChart = null;
    var psiLineChart = null;
    var chisqBarChart = null;
    var dataCntBarChart = null;
    var recentExecute = "";
    var projectName = "";
    var recentPeriod = "전체";

    var fn_changePathVar = function () {
        if (recentPeriod == "최근 1일") {
            recentPeriod = "oneday";
        } else if (recentPeriod == "최근 1주") {
            recentPeriod = "oneweek";
        } else if (recentPeriod == "최근 1개월") {
            recentPeriod = "onemonth";
        } else if (recentPeriod == "최근 3개월") {
            recentPeriod = "onequarter";
        } else if (recentPeriod == "최근 6개월") {
            recentPeriod = "onehalf";
        } else if (recentPeriod == "최근 1년") {
            recentPeriod = "oneyear";
        } else if (recentPeriod == "전체") {
            recentPeriod = "all";
        }
    }

    var fn_setProjectNameDropdown = function (url) {
        $.ajax({
            type: "GET",
            url: url,
            dataType: "json",
            cache: false,
            success: function (projectNameList) {
                var listName = projectNameList;
                $.each(listName, function (index) {
                    var newProjectName = '<a class="dropdown-item" href="#">' + listName[index] + '</a>';
                    $(newProjectName).appendTo('#projectDropdownMenu');
                });

                $("#projectDropdownMenu a").click(function () {
                    projectName = $(this).text();
                    $(this).parents('.btn-group').find('.btn-outline-secondary').html(projectName);
                    $(this).parents('.dropdown').find('.dropdown-toggle').html(projectName);
                    console.log("project dropdown clicked");
                    fn_setRecentDtim("<c:url value='/main/regdtim/" + projectName + "' />");
                    fn_changePathVar();
                    fn_setCountGraphs("<c:url value='/main/anomalycnt/" + projectName + "/" + recentPeriod + "' />");
                    fn_setStatGraphs("<c:url value='/main/stat/"+ projectName + "/" + recentPeriod + "' />");
                });


            },
            error: function (request, status, error) {
                alert('code:' + request.status + '\n' + 'message:' + request.responseText + '\n' + 'error:' + error);
            }
        });
    };

    $("#recentDropdownMenu a").click(function () {
        recentPeriod = $(this).text();
        $(this).parents('.btn-group').find('.btn-outline-secondary').html(recentPeriod);

        console.log("recent dropdown clicked");
        fn_changePathVar();
        fn_setCountGraphs("<c:url value='/main/anomalycnt/"+ projectName + "/" + recentPeriod + "' />");
        fn_setStatGraphs("<c:url value='/main/stat/"+ projectName + "/" + recentPeriod + "' />");
    });

    var fn_setRecentDtim = function (url) {
        $.ajax({
            type: "GET",
            url: url,
            dataType: "json",
            cache: false,
            success: function (recentRegDtims) {
                console.log("SET RECENT DROPDOWN " + url + "/" + recentRegDtims[0]);
                recentExecute = recentRegDtims[0];
                var recentTimeText = document.getElementById('recentExecute');
                recentTimeText.innerHTML = '최근실행시간: ' + recentExecute;
            },
            error: function (request, status, error) {
                alert('code:' + request.status + '\n' + 'message:' + request.responseText + '\n' + 'error:' + error);
            }
        });
    }

    var fn_setCountGraphs = function (url) {
        console.log("Count Graph : " + url + "+" + recentPeriod + "+" + projectName);
        $.ajax({
            type: "GET",
            url: url,
            dataType: "json",
            cache: false,
            success: function (anomalyCounts) {
                var totalData = {"anomalyCnt": 0, "normalCnt": 0};
                var dtimData = {"dtims": [], "anomalyCnts": [], "normalCnts": []};
                anomalyCounts.forEach(function (row, index) {
                    totalData.anomalyCnt += parseInt(row.anomalyCnt);
                    totalData.normalCnt += parseInt(row.normalCnt);

                    dtimData.dtims.push(row.dtim);
                    dtimData.anomalyCnts.push(row.anomalyCnt);
                    dtimData.normalCnts.push(row.normalCnt);
                });

                fn_setDoughnutGraph(totalData);
                fn_setStackedBarChart(dtimData);

            },
            error: function (request, status, error) {
                alert('code:' + request.status + '\n' + 'message:' + request.responseText + '\n' + 'error:' + error);
            }

        })
    }

    // Any of the following formats may be used
    //var ctx = document.getElementById('myChart');
    //var ctx = document.getElementById('myChart').getContext('2d');
    //var ctx = $('#myChart');
    //var ctx = 'myChart';

    var fn_setDoughnutGraph = function (data) {
        if (mainDoughnutChart != null) {
            mainDoughnutChart.destroy();
        }
        var ctxDn = document.getElementById('dashDoughnut');
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
                    text: "전체 파일 이상감지 결과",
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
                        left: 50,
                        right: 50,
                        top: 10,
                        bottom: 50
                    }
                }
            }
        });
    }

    var fn_setStackedBarChart = function (data) {
        if (mainStackedBarChart != null) {
            mainStackedBarChart.destroy();
        }
        var ctxSb = document.getElementById('dashStackedBar');
        mainStackedBarChart = new Chart(ctxSb, {
            type: "bar",
            data: {
                labels: data.dtims,
                datasets: [
                    {
                        label: '정상',
                        data: data.normalCnts,
                        backgroundColor: 'rgba(54, 162, 235, 0.2)',
                        borderColor: 'rgba(54, 162, 235, 1)',
                        borderWidth: 1
                    },

                    {
                        label: '이상',
                        data: data.anomalyCnts,
                        backgroundColor: 'rgba(255, 99, 132, 0.2)',
                        borderColor: 'rgba(255, 99, 132, 1)',
                        borderWidth: 1
                    }]
            },
            options: {
                title: {
                    display: true,
                    text: "일별 파일 이상감지 건수",
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
                    xAxes: [{stacked: true}],
                    yAxes: [{stacked: true, ticks: {beginAtZero: true}}]
                }
            }
        });
    }

    var fn_setStatGraphs = function (url) {
        console.log("stat : " + url + "+" + recentPeriod + "+" + projectName);
        $.ajax({
            type: "GET",
            url: url,
            dataType: "json",
            cache: false,
            success: function (predData) {
                var statData = {"dtims": [], "psis": [], "cars": [], "chisqcnts": [], "datacnts": []};
                predData.forEach(function (row, index) {

                    statData.dtims.push(row.dtim);
                    statData.psis.push(parseFloat(row.psi));
                    statData.cars.push(parseInt(row.car));
                    statData.chisqcnts.push(parseInt(row.chisqcnt));
                    statData.datacnts.push(parseInt(row.inputDataCnt));
                });

                fn_setItemBarChart(statData);
                fn_setPsiLineChart(statData);
                fn_setChisqBarChart(statData);
                fn_setDataCntBarChart(statData);


            },
            error: function (request, status, error) {
                alert('code:' + request.status + '\n' + 'message:' + request.responseText + '\n' + 'error:' + error);
            }

        })
    }
    var fn_setItemBarChart = function (data) {
        if (itemBarChart != null) {
            itemBarChart.destroy();
        }

        var ctxB = document.getElementById('dashItemBar');
        itemBarChart = new Chart(ctxB, {
            type: "bar",
            data: {
                labels: data.dtims,
                datasets: [
                    {
                        label: '항목수',
                        data: data.cars,
                        backgroundColor: 'rgba(255, 99, 132, 0.2)',
                        borderColor: 'rgba(255, 99, 132, 1)',
                        borderWidth: 1
                    }

                ]
            },

            options: {
                title: {
                    display: true,
                    text: "일별 이상항목 수",
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
                    xAxes: [{}],
                    yAxes: [{ticks: {beginAtZero: true}}]
                }
            }

        })

    }

    var fn_setPsiLineChart = function (data) {
        if (psiLineChart != null) {
            psiLineChart.destroy();
        }

        var dataSize = data.dtims.length
        var thresholdValue = 1.0;
        var thresholdArray = new Array(dataSize);
        for (var i = 0; i < dataSize; i++) {
            thresholdArray[i] = thresholdValue;
        }

        var ctxPL = document.getElementById('dashPsiLine');
        psiLineChart = new Chart(ctxPL, {
            type: "line",
            data: {
                labels: data.dtims,
                datasets: [
                    {
                        label: 'PSI',
                        data: data.psis,
                        backgroundColor: 'rgba(255, 99, 132, 0.2)',
                        borderColor: 'rgba(54, 162, 235, 1)',
                        fill: false,
                    },
                    {
                        label: 'Threshold',
                        data: thresholdArray,
                        backgroundColor: 'rgba(54, 162, 235, 0.2)',
                        borderColor: 'rgba(255, 99, 132, 1)',
                        fill: false,
                        pointStyle: 'line'
                    }

                ]
            },

            options: {
                title: {
                    display: true,
                    text: "PSI 기준 파일 변화도",
                    fontSize: 17,
                    fontStyle: 'bold',
                    padding: 10
                },
                legend: {
                    display: true,
                    padding: 10,
                    //position:'bottom',
                    labels: {
                        filter: function (legendItem, data) {
                            return legendItem.index != 1
                        }
                    }
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
                    xAxes: [{}],
                    yAxes: [{ticks: {beginAtZero: true}}]
                },
                elements: {
                    line: {
                        tension: 0
                    }
                }
            }

        })
    }

    var fn_setChisqBarChart = function (data) {
        if (chisqBarChart != null) {
            chisqBarChart.destroy();
        }

        var dataSize = data.dtims.length
        var thresholdValue = 250.0;
        var thresholdArray = new Array(dataSize);
        for (var i = 0; i < dataSize; i++) {
            thresholdArray[i] = thresholdValue;
        }

        var ctxCB = document.getElementById('dashChisqBar');
        chisqBarChart = new Chart(ctxCB, {
            type: "bar",
            data: {
                labels: data.dtims,
                datasets: [
                    {
                        label: '항목 수',
                        data: data.chisqcnts,
                        backgroundColor: 'rgba(54, 162, 235, 0.2)',
                        borderColor: 'rgba(54, 162, 235, 1)',
                        borderWidth: 1
                    },
                ]
            },

            options: {
                title: {
                    display: true,
                    text: "카이제곱 기준 이상 항목 수",
                    fontSize: 17,
                    fontStyle: 'bold',
                    padding: 10
                },
                legend: {
                    display: true,
                    padding: 10,
                    position: 'bottom',
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
                    xAxes: [{}],
                    yAxes: [{ticks: {beginAtZero: true}}]
                },

            }

        })
    }

    var fn_setDataCntBarChart = function (data) {
        if (dataCntBarChart != null) {
            dataCntBarChart.destroy();
        }

        var dataSize = data.dtims.length

        var ctxDCB = document.getElementById('dashDataCntBar');
        dataCntBarChart = new Chart(ctxDCB, {
            type: "bar",
            data: {
                labels: data.dtims,
                datasets: [
                    {
                        label: '건수',
                        data: data.datacnts,
                        backgroundColor: 'rgba(54, 162, 235, 0.2)',
                        borderColor: 'rgba(54, 162, 235, 1)',
                        borderWidth: 1
                    },
                ]
            },

            options: {
                title: {
                    display: true,
                    text: "데이터 파일 건수",
                    fontSize: 17,
                    fontStyle: 'bold',
                    padding: 10
                },
                legend: {
                    display: true,
                    padding: 10,
                    position: 'bottom',
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
                    xAxes: [{}],
                    yAxes: [{ticks: {beginAtZero: true}}]
                },

            }

        })
    }

    $(document).ready(function () {
        fn_setProjectNameDropdown("<c:url value='/main/projectnames' />");
        fn_changePathVar();
        fn_setCountGraphs("<c:url value='/main/anomalycnt/"+ projectName + "/" + recentPeriod + "' />");
        fn_setStatGraphs("<c:url value='/main/stat/"+ projectName + "/" + recentPeriod + "' />");

    });
</script>
</body>
</html>