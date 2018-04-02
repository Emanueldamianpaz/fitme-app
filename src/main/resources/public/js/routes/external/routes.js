fitme.config(function($stateProvider) {

  $stateProvider
      .state('fitme', {
        abstract: true,
        templateUrl: 'views/common/layouts/simple.html',
        data : { pageTitle: 'Home' }
      })
      .state('fitme.login', {
        url: '/login',
        templateUrl: 'views/pages/login.html',
        data : { pageTitle: 'Login' }
      })
      .state('fitme.register', {
        url: '/register',
        templateUrl: 'views/pages/register.html',
        data : { pageTitle: 'Registrarse' }
      })
      .state('fitme.404', {
        url: '/404',
        templateUrl: 'views/pages/404.html',
        data : { pageTitle: '404' }
      })
      .state('fitme.500', {
        url: '/500',
        templateUrl: 'views/pages/500.html',
        data : { pageTitle: '500' }

      })

})
