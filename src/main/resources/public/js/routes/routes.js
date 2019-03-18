fitme.config(function ($urlRouterProvider, $httpProvider, $locationProvider) {
    $urlRouterProvider.otherwise('/dashboard');

    $httpProvider.interceptors.push('responseHandler');

    $locationProvider.html5Mode({enabled: true, rewriteLinks: false});

})
