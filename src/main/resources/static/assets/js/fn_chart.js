	var fn_PieChart = function (param) {
		if (pieChart != null) {
			pieChart.destroy();
		}
		
		var pieChart = document.getElementById(param.target);
		var myChart = echarts.init(pieChart);
		var option;
	
		option = {
			title : {
				text : param.title
			},
			color: ['#e63756', '#49b857'],
			tooltip: {
		          trigger: 'item',
		          padding: [7, 10],
		          backgroundColor: utils.getGrays()['100'],
		          borderColor: utils.getGrays()['300'],
		          textStyle: {
		            color: utils.getColors().dark
		          },
		          borderWidth: 1,
		          transitionDuration: 0,
		          formatter: function formatter(params) {
		              return "<strong>".concat(params.data.name, ":</strong> ").concat(params.value, param.formatterTxt);
		            }
		        },
	        position: function position(pos, params, dom, rect, size) {
	            return getPosition(pos, params, dom, rect, size);
	          },
	
			legend : {
				data : param.name,
				bottom: 98,
			    left: 'center',
				itemWidth : 8,
				itemHeight : 10,
				borderRadius : 0,
				icon : 'circle',
				inactiveColor : utils.getGrays()['400'],
				textStyle : {
					color : utils.getGrays()['700']
				}
			},
	
			toolbox : {
				feature : {
					saveAsImage : {
						title : 'Save'
					}
				}
			},
			
			series: [{
		          type: 'pie',
		          radius: param.radius,
		          left: 0,
			      right: 0,
			      top: 30,
			      bottom: 0,
		          avoidLabelOverlap: false,
		          hoverAnimation: false,
		          itemStyle: {
		            borderWidth: 2,
		            borderColor: utils.getColor('card-bg')
		          },
		          label: {
		            normal: {
		              show: false,
		              position: 'center',
		              textStyle: {
		                fontSize: '20',
		                fontWeight: '500',
		                color: utils.getGrays()['700']
		              }
		            },
		            emphasis: {
		              show: false
		            }
		          },
		          labelLine: {
		            normal: {
		              show: false
		            }
		          },
		          data: param.data
			        
		        }]
		};
	
		option && myChart.setOption(option);
	}		
	
	
	//항목별 일치성 예제
	function fn_HorizontalBarChart(param) {
		
		//var data = [10, 20, 30, 40, 50, 90];
		var dataArray = []
		
		$.each(param.data, function(i) {				
			var dataObj ={}				
			if (this > 80){
				dataObj.value = param.data[i];
				dataObj.itemStyle = {color: '#e63756'};
			}else{
				dataObj.value = param.data[i];
				dataObj.itemStyle = {color: '#49b857'};
			}
			
			dataArray.push(dataObj);
		});
	
		
		if (itemMatchChart != null) {
			itemMatchChart.destroy();
		}
		
		var itemMatchChart = document.getElementById('itemMatchChart');
		var myChart = echarts.init(itemMatchChart);
		var option;
		
		option = {
				title: {
			        text: param.title,
			        show: true
			    },
			    color : [ utils.getColors().primary ],
			    tooltip : {
					trigger : 'item',
					padding : [ 7, 10 ],
					backgroundColor : utils.getGrays()['100'],
					borderColor : utils.getGrays()['300'],
					textStyle : {
						color : utils.getColors().dark
					},
					borderWidth : 1,
					transitionDuration : 0 ,
					formatter: function formatter(params) {
			              return "<strong>".concat(params.name, ":</strong> ").concat(params.value, "%");
		            }
				},
									
				legend : {
					left : 'center',
					itemWidth : 10,
					itemHeight : 10,
					borderRadius : 0,
					icon : 'circle',
					inactiveColor : utils.getGrays()['400'],
					textStyle : {
						color : utils.getGrays()['700']
					}
				},
				toolbox : {
					feature : {
						saveAsImage : {
							title : 'Save'
						}
					}
				},
			    grid: {
			        left: '3%',
			        right: '4%',
			        bottom: '3%',
			        containLabel: true
			    },
			    xAxis: {
			        type: 'value',
			        axisTick : false,
			        axisLabel : {
						color : utils.getGrays()['400'],
						textStyle : {
							fontSize : 10
						}
					},
					boundaryGap : true
			    },
			    yAxis: {
			        type: 'category',
					axisLabel : {
						color : utils.getGrays()['400'],
						textStyle : {
							fontSize : 10
						}
					},
			        data: ['A00001', 'A00002', 'A00003', 'A00004', 'A00005', 'A00006']
			    },
			    series: [
			       
			        {
						type : 'bar',
						data: dataArray,
						barWidth : '60%',
						barGap : '30%',
						label : {
							normal : {
								show : true
							}
						},
						z : 10
			        }
			    ],
		};
	
		option && myChart.setOption(option);
	}
			
	
	
	function fn_PsiChart(data) {	
		if (psiLineChart != null) {
			psiLineChart.destroy();
		}
	
		var dataSize = data.dtims.length
		var thresholdValue = 1.0;
		var thresholdArray = new Array(dataSize);
		for (var i = 0; i < dataSize; i++) {
			thresholdArray[i] = thresholdValue;
		}
	
		var psiLineChart = document.getElementById('psiChart');
		var myChart = echarts.init(psiLineChart);
		var option;
	
		option = {
			title : {
				text : data.title
			},
			color : [ '#49b857', utils.getColors().warning ],
			tooltip : {
				trigger : 'axis',
				padding : [ 7, 10 ],
				backgroundColor : utils.getGrays()['100'],
				borderColor : utils.getGrays()['300'],
				textStyle : {
					color : utils.getColors().dark
				},
				borderWidth : 1,
				transitionDuration : 0,
				position : function position(pos, params, dom, rect, size) {
					return getPosition(pos, params, dom, rect, size);
				}
			},
	
			legend : {
				data : [ 'PSI', 'Threshold' ],
				left : 'center',
				itemWidth : 10,
				itemHeight : 10,
				borderRadius : 0,
				icon : 'circle',
				inactiveColor : utils.getGrays()['400'],
				textStyle : {
					color : utils.getGrays()['700']
				}
			},
			 grid: {
			        left: '3%',
			        right: '4%',
			        bottom: '3%',
			        containLabel: true
			    },
	
			toolbox : {
				feature : {
					saveAsImage : {
						title : 'Save'
					}
				}
			},
			xAxis : {
				type : 'category',
				data : data.dtims,
				boundaryGap : false,
				axisPointer : {
					lineStyle : {
						color : utils.getGrays()['300'],
						type : 'dashed'
					}
				},
				splitLine : {
					show : false
				},
				axisLine : {
					lineStyle : {
						color : utils.rgbaColor('#000', 0.01),
						type : 'dashed'
					}
				},
				axisTick : {
					show : false
				},
				axisLabel : {
					color : utils.getGrays()['400'],
					margin : 15,
					rotate : 45,
					textStyle : {
						fontSize : 10
					}
				}
			},
			yAxis : {
				type : 'value',
				axisPointer : {
					show : false
				},
				splitLine : {
					lineStyle : {
						color : utils.getGrays()['300'],
						type : 'dashed'
					}
				},
				boundaryGap : false,
				axisLabel : {
					show : true,
					color : utils.getGrays()['400'],
					margin : 15,
					textStyle : {
						fontSize : 10
					}
				},
				axisTick : {
					show : false
				},
				axisLine : {
					show : false
				}
			},
			series : [
					{
						name : 'PSI',
						type : 'line',
						data : data.psis,
						lineStyle : {
							color :  '#49b857'
						},
						itemStyle : {
							borderColor : '#49b857',
							borderWidth : 2
						},
						showSymbol : false,
						smooth : true,
						hoverAnimation : true,
						areaStyle : {
							color : {
								type : 'linear',
								x : 0,
								y : 0,
								x2 : 0,
								y2 : 1,
								colorStops : [
										{
											offset : 0,
											color : '#9cd2a2'
										},
										{
											offset : 1,
											color : '#d2ecd7'
										} ]
							}
						}
					}, {
						name : 'Threshold',
						type : 'line',
						data : thresholdArray,
						lineStyle : {
							color : utils.getColors().warning
						},
						itemStyle : {
							borderColor : utils.getColors().warning,
							borderWidth : 2
						},
						showSymbol : false,
						smooth : false,
						hoverAnimation : true
					}
	
			]
		};
	
		option && myChart.setOption(option);
	}
	
	function fn_LineChart(data) {	
		if (dataCntLineChart != null) {
			dataCntLineChart.destroy();
		}
	
		var dataCntLineChart = document.getElementById(data.target);
		var myChart = echarts.init(dataCntLineChart);
		var option;
	
		option = {
			title : {
				text : data.title
			},
			color : [ utils.getColors().primary],
			tooltip : {
				trigger : 'axis',
				padding : [ 7, 10 ],
				backgroundColor : utils.getGrays()['100'],
				borderColor : utils.getGrays()['300'],
				textStyle : {
					color : utils.getColors().dark
				},
				borderWidth : 1,
				transitionDuration : 0,
				position : function position(pos, params, dom, rect, size) {
					return getPosition(pos, params, dom, rect, size);
				}
			},
	
			/*legend : {
				data : [data.name],
				left : 'center',
				itemWidth : 10,
				itemHeight : 10,
				borderRadius : 0,
				icon : 'circle',
				inactiveColor : utils.getGrays()['400'],
				textStyle : {
					color : utils.getGrays()['700']
				}
			},*/
			 grid: {
			        left: '3%',
			        right: '4%',
			        bottom: '3%',
			        containLabel: true
			    },
	
			toolbox : {
				feature : {
					saveAsImage : {
						title : 'Save'
					}
				}
			},
			xAxis : {
				type : 'category',
				data : data.xData,
				boundaryGap : false,
				axisPointer : {
					lineStyle : {
						color : utils.getGrays()['300'],
						type : 'dashed'
					}
				},
				splitLine : {
					show : false
				},
				axisLine : {
					lineStyle : {
						color : utils.rgbaColor('#000', 0.01),
						type : 'dashed'
					}
				},
				axisTick : {
					show : false
				},
				axisLabel : {
					color : utils.getGrays()['400'],
					margin : 15,
					rotate : 45,
					textStyle : {
						fontSize : 10
					}
				}
			},
			yAxis : {
				type : 'value',
				axisPointer : {
					show : false
				},
				splitLine : {
					lineStyle : {
						color : utils.getGrays()['300'],
						type : 'dashed'
					}
				},
				boundaryGap : false,
				axisLabel : {
					show : true,
					color : utils.getGrays()['400'],
					margin : 15,
					textStyle : {
						fontSize : 10
					}
				},
				axisTick : {
					show : false
				},
				axisLine : {
					show : false
				}
			},
			series : [
					{
						name : data.name,
						type : 'line',
						data : data.yData,
						lineStyle : {
							color : data.color
						},
						itemStyle : {
							borderColor : data.color,
							borderWidth : 2
						},
						showSymbol : true,
						symbolSize : 10,
						smooth : true,
						hoverAnimation : true,
						areaStyle : {
							color : {
								type : 'linear',
								x : 0,
								y : 0,
								x2 : 0,
								y2 : 1,
								colorStops : [
										{
											offset : 0,
											color : utils.rgbaColor(data.color,
													0.2)
										},
										{
											offset : 1,
											color : utils
													.rgbaColor(data.color,
															0)
										} ]
							}
						}
					}
	
			]
		};
	
		option && myChart.setOption(option);
	}
	
	function fn_BarChart(param) {
	
		if (itemBarChart != null) {
			itemBarChart.destroy();
		}
	
		var itemBarChart = document.getElementById(param.target);
		var myChart = echarts.init(itemBarChart);
		var option;
	
		option = {
			title : {
				text : param.title
			},
			color : [ utils.getColors().primary ],
			tooltip : {
				trigger : 'item',
				padding : [ 7, 10 ],
				backgroundColor : utils.getGrays()['100'],
				borderColor : utils.getGrays()['300'],
				textStyle : {
					color : utils.getColors().dark
				},
				borderWidth : 1,
				transitionDuration : 0,
				position : function position(pos, params, dom, rect, size) {
					return getPosition(pos, params, dom, rect, size);
				}
			},
	
			/*legend : {
				data : [ param.name ],
				left : 'center',
				itemWidth : 10,
				itemHeight : 10,
				borderRadius : 0,
				icon : 'circle',
				inactiveColor : utils.getGrays()['400'],
				textStyle : {
					color : utils.getGrays()['700']
				}
			},*/
			 grid: {
			        left: '3%',
			        right: '4%',
			        bottom: '3%',
			        containLabel: true
			    },
			toolbox : {
				feature : {
					saveAsImage : {
						title : 'Save'
					}
				}
			},
			xAxis : {
				type : 'category',
				data : param.xData,
				axisLabel : {
					color : utils.getGrays()['400'],
					rotate : 45,
					textStyle : {
						fontSize : 10
					}
				},
				axisLine : {
					lineStyle : {
						color : utils.getGrays()['300'],
						type : 'dashed'
					}
				},
				axisTick : false,
				boundaryGap : true
	
			},
			yAxis : {
				axisPointer : {
					type : 'none'
				},
				axisTick : 'none',
				splitLine : {
					lineStyle : {
						color : utils.getGrays()['300'],
						type : 'dashed'
					}
				},
				axisLine : {
					show : false
				},
				axisLabel : {
					color : utils.getGrays()['400'],
					//rotate : 45,
					textStyle : {
						fontSize : 10
					}
				}
			},
			series : [ {
				name : param.name,
				type : 'bar',
				data : param.yData,
				type : 'bar',
				barWidth : '12%',
				barGap : '30%',
				label : {
					normal : {
						show : false
					}
				},
				z : 10,
				itemStyle : {
					normal : {
						barBorderRadius : [ 10, 10, 0, 0 ],
						color : param.barColor
					}
				}
			}, {
				type : 'bar',
				barWidth : '12%',
				barGap : '30%',
				label : {
					normal : {
						show : false
					}
				},
				itemStyle : {
					normal : {
						barBorderRadius : [ 4, 4, 0, 0 ],
						color : utils.getGrays()[300]
					}
				}
			} ],
		};
	
		option && myChart.setOption(option);
	}
	
	function fn_boxplot(param){
		
		if (boxplotChart != null) {
			boxplotChart.destroy();
		}
	
		var boxplotChart = document.getElementById(param.target);
		var myChart = echarts.init(boxplotChart);
		var option;
								
		option = {
		    title: [
		        {
		            text: param.title,
		            left: 'center',
		            show:false
		        }
		    ],
		    tooltip: {
		        trigger: 'item',  //item axis
		        axisPointer: {
		            type: 'shadow'
		        }
		    },
		    grid: {
		        top: '2%',
		        left: '2%',
		        right: '2%',
		        bottom: '10%'
		    },
		    xAxis: {
		    	type : 'category',	
		    	data: param.name,
				boundaryGap : true,
				axisPointer : {
					lineStyle : {
						color : utils.getGrays()['300'],
						type : 'dashed'
					}
				},
				splitLine : {
					show : false
				},
				axisLine : {
					lineStyle : {
						color : utils.rgbaColor('#000', 0.01),
						type : 'line'
					}
				},
				axisTick : {
					show : false
				},
				axisLabel : {
					color : utils.getGrays()['500'],
					textStyle : {
						fontSize : 10
					}
				}
		    },
		    
		    
		    yAxis: {
		        type: 'value',
		        name: param.yAxisNm,
		        splitArea: {
		            show: true
		        } ,
				axisPointer : {
					show : false
				},
				splitLine : {
					lineStyle : {
						color : utils.getGrays()['300'],
						type : 'line'
					},
					show : true
				},
				boundaryGap : false,
				axisLabel : {
					show : true,
					color : utils.getGrays()['500'],
					textStyle : {
						fontSize : 10
					}
				},
					axisTick : {
					show : false
				},
				 axisLine : {
					lineStyle : {
						color : utils.getGrays()['300'],
						type : 'line'							
					},
					show : true
				}  
		    },
		    series: [
		        {
		            type: 'boxplot',
		            data: param.boxData,
		            tooltip: {
		            	backgroundColor : utils.getGrays()['50'],
		            	borderColor : utils.getColors().primary,
						textStyle : {
							color : utils.getColors().dark
						},
						formatter:  boxplot_formatter
		            }
		        },
		        {
		        	name: 'outlier',
		            type: 'scatter',
		            data: param.outliers,
					itemStyle : {
						normal : {
							color :  utils.getColors().danger
						}
					},
		        }
		    ]
		};
	
		option && myChart.setOption(option);
		
	}
	