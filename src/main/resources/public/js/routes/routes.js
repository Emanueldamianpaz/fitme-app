fitme.config(function ($urlRouterProvider, $httpProvider, $locationProvider) {
    $urlRouterProvider.otherwise('/login');

    $httpProvider.interceptors.push('responseHandler');

    $locationProvider.html5Mode({enabled: true, rewriteLinks: false});

})
