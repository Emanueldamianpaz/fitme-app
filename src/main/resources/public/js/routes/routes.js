fitme.config(function ($urlRouterProvider, $httpProvider, $locationProvider) {
    $urlRouterProvider.otherwise('/404');

    $httpProvider.interceptors.push('responseHandler');

    $locationProvider.html5Mode({enabled: true, rewriteLinks: false});

})
