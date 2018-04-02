fitme.config(function($stateProvider, $urlRouterProvider) {

  $urlRouterProvider.otherwise('/login');

  $stateProvider
      .state('app', {
        abstract: true,
        templateUrl: 'views/common/layouts/full.html',
        data : { pageTitle: 'Home' }

      })
      .state('app.dashboard', {
        url: '/dashboard',
        templateUrl: 'views/main.html',
        controller: 'dashboardController'
      })
      .state('app.users', {
        url: '/users',
        templateUrl: 'views/main.html',
        controller: 'usersController'
      })
      .state('app.routines', {
        url: '/routines',
        templateUrl: 'views/main.html',
        controller: 'routinesController'
      })
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
