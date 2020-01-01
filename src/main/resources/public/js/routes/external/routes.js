fitme.config(function ($stateProvider) {

    $stateProvider
        .state('fitme', {
            abstract: true,
            templateUrl: 'views/common/layouts/simple.html',
            data: {pageTitle: 'Fitme'}
        })

        .state('fitme.404', {
            url: '/404',
            templateUrl: 'views/fitme/404.html',
            data: {pageTitle: '404'}
        })

})
