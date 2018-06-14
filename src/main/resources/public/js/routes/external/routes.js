fitme.config(function ($stateProvider) {

    $stateProvider
        .state('fitme', {
            abstract: true,
            templateUrl: 'views/common/layouts/simple.html',
            data: {pageTitle: 'Fitme'}
        })
        .state('fitme.login', {
            url: '/login',
            templateUrl: 'views/fitme/login.html',
            data: {pageTitle: 'Login'}
        })
        .state('fitme.register', {
            url: '/register',
            templateUrl: 'views/fitme/register.html',
            data: {pageTitle: 'Registrarse'}
        })
        .state('fitme.404', {
            url: '/404',
            templateUrl: 'views/fitme/404.html',
            data: {pageTitle: '404'}
        })
        .state('fitme.500', {
            url: '/500',
            templateUrl: 'views/fitme/500.html',
            data: {pageTitle: '500'}

        })

})
