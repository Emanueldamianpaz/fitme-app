fitme.config(function ($urlRouterProvider, $rootScope) {

    $rootScope.stateCurrent = "Login"
    $urlRouterProvider.otherwise('/login');

})
