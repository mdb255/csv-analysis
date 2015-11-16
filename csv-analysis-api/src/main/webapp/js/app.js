'use strict';

// Declare config module
angular.module('appConfig', [])
	.constant('appConfig',
  {
    'apiUrl': 'http://localhost:8090/csv-analysis'
  });

//Declare app module
angular.module('analysisApp', ['appConfig', 'nvd3']);

// Single controller
angular
	.module('analysisApp')
	.controller('ChartCtrl', ChartCtrl);

ChartCtrl.$inject = ['$scope', '$log', '$http', '$window', 'appConfig'];

function ChartCtrl($scope, $log, $http, $window, appConfig) {
	initialize();
	
	// Exposed methods
	$scope.loadChartData = loadChartData;
	$scope.refreshSources = refreshSources;
	
	function initialize() {
		// Set default values
		$scope.availableSourceFilenames = refreshSources();
		$scope.selectedFilename = "";
		
		$scope.chartOptions = {
            chart: {
                type: 'multiBarChart',
                height: 450,
                width: 800,
                margin : {
                    top: 20,
                    right: 20,
                    bottom: 60,
                    left: 60
                },
                clipEdge: true,
                transitionDuration: 500,
                stacked: true,
                xAxis: {
                    axisLabel: 'Country',
                    showMaxMin: false,
                },
                yAxis: {
                    axisLabel: 'Population (m)',
                    axisLabelDistance: -10,
                    tickFormat: function(d){
                        return d3.format(',f')(d);
                    }
                }
            }
        };
		
		$scope.chartConfig = {
			deepWatchData: true
		}
	}

    function loadChartData() {
    	if ($scope.selectedFilename !== "") {
	    	$log.debug("Loaded chart data from " + $scope.selectedFilename);
	    	
	    	$http.get(appConfig.apiUrl + "/csvData?sourceFilename=" + encodeURIComponent($scope.selectedFilename)).
		        success(function(data) {
		        	$log.debug("Got csvData: " + JSON.stringify(data));
		        	
		        	// Chart grouping configuration
		        	var fieldsByLabel = {
		    				"Under 25": "under25",
		    				"25-50": "between25and50",
		    				"50+": "over50",
		        		};
		        	var indexField = "country";
		        	
		        	/* 2 different ways to group the chart data */
		        	
		        	// E.g., by age group
		//        	$scope.chartData = buildDirectChartData(data, fieldsByLabel, indexField);
		        	// E.g., by country
		        	$scope.chartData = buildTransposedChartData(data, fieldsByLabel, indexField);
		        }).
		        error(function(data, status) {
		        	var errMsg = "Error retrieving csv data";
		        	$window.alert(errMsg);
		        	$log.debug(errMsg);
		        });
    	}
	}
    
    function refreshSources() {
    	$http.get(appConfig.apiUrl + "/dataSources").
	        success(function(data) {
	        	$log.debug("Got dataSources: " + JSON.stringify(data));
	        	$scope.availableSourceFilenames = data;
	        }).
	        error(function(data, status) {
	        	var errMsg = "Error getting data sources";
	        	$window.alert(errMsg);
	        	$log.debug(errMsg);
	        });
	}
    
    // Build chart data, directly from CSV data
    function buildDirectChartData(csvData, fieldsByLabel, fieldToSplitBy) {
    	var chartData = _.map(csvData, function(cd) {
    		return {
                key: cd[fieldToSplitBy],
//                color: "#00CCAA",
                values: _.map(_.pairs(fieldsByLabel), function(labelAndField) {
                	return { x: labelAndField[0], y: cd[labelAndField[1]] };
                })
            }
    	});
    	
    	$log.debug(JSON.stringify(chartData));
    	
    	return chartData;
    }
    
    function buildTransposedChartData(csvData, fieldsByLabel, fieldToGroupBy) {
    	var fields = _.values(fieldsByLabel);
    	
    	var transposedData = _.map(fields, function(f) {
    		var result = {};
    		result.categoryToSplitBy = f;
    		
    		_.each(csvData, function(cd) {
    			result[cd[fieldToGroupBy]] = cd[f];
    		});
    		return result;
    	});
    	
    	var chartData = transposedData.map(function(td) {
    		var groupedData = _.pairs(_.omit(td, ["categoryToSplitBy"]));
    		
    		return {
                key: _.findKey(fieldsByLabel, function(f) { return f === td.categoryToSplitBy; }),
                values: _.map(groupedData, function(gd) {
                	return { x: gd[0], y: gd[1] };
                })
            };
    	});
    	
    	$log.debug(JSON.stringify(transposedData));
    	$log.debug(JSON.stringify(chartData));
    	
    	return chartData;
    }
}
