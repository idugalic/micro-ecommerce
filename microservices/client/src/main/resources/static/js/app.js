angular.module('sso', [ 'ngRoute', 'ngResource' ]).config(
		function($routeProvider) {

			$routeProvider.otherwise('/');
			$routeProvider.when('/', {
				templateUrl : 'home.html',
				controller : 'home'
			}).when('/dashboard', {
				templateUrl : 'dashboard.html',
				controller : 'dashboard'
			}).when('/orders', {
				templateUrl : 'orders.html',
				controller : 'orders'
			}).when('/orders2', {
				templateUrl : 'orders.html',
				controller : 'orders2'
			}).when('/hal', {
				templateUrl : 'browser/index.html'
				
			}).when('/product', {
				templateUrl : 'productDetail.html',
				controller : 'product'
			});

		}).controller('navigation', function($scope, $http, $window, $route) {
	$scope.tab = function(route) {
		return $route.current && route === $route.current.controller;
	};
	if (!$scope.user) {
		$http.get('/dashboard/user').success(function(data) {
			$scope.user = data;
			$scope.authenticated = true;
		}).error(function() {
			$scope.authenticated = false;
		});
	}
	$scope.logout = function() {
		$http.post('/dashboard/logout', {}).success(function() {
			delete $scope.user;
			$scope.authenticated = false;
			// Force reload of home page to reset all state after logout
			$window.location.hash = '';
		});
	};
}).controller('home', function() {
}).controller('dashboard', function($scope, $resource) {

	$resource('/dashboard/message', {}).get({}, function(data) {
		$scope.message = data.message;
	}, function() {
		$scope.message = '';
	});

}).controller('orders', function($scope, $resource) {

	$resource('/dashboard/api/orders', {}).get({}, function(data) {
		$scope.message = data;
	}, function() {
		$scope.message = 'test';
	});

}).controller('orders2', function($scope, $resource) {

	$resource('/dashboard/api/orders/orders', {}).get({}, function(data) {
		$scope.message = data;
	}, function() {
		$scope.message = 'test';
	});

}).controller('product', function($scope, $resource) {

	$resource('/dashboard/api/product/1', {}).get({}, function(data) {
		$scope.message = data;
	}, function() {
		$scope.message = 'test product';
	});

});