fitme.config(function($stateProvider) {

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
        templateUrl: 'views/forms.html',
        controller: 'usersController'
      })
      .state('app.routines', {
        url: '/routines',
        templateUrl: 'views/widgets.html',
        controller: 'routinesController'
      })

})
