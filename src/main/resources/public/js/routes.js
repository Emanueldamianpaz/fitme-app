fitme.config(function($stateProvider, $urlRouterProvider) {

  $urlRouterProvider.otherwise('/login');

  $stateProvider
      .state('app', {
        abstract: true,
        templateUrl: 'views/common/layouts/full.html',
      })
      .state('app.main', {
        url: '/dashboard',
        templateUrl: 'views/main.html',
      })
      .state('fitme', {
        abstract: true,
        templateUrl: 'views/common/layouts/simple.html',
      })
      .state('fitme.login', {
        url: '/login',
        templateUrl: 'views/pages/login.html'
      })
      .state('fitme.register', {
        url: '/register',
        templateUrl: 'views/pages/register.html'
      })
      .state('fitme.404', {
        url: '/404',
        templateUrl: 'views/pages/404.html'
      })
      .state('fitme.500', {
        url: '/500',
        templateUrl: 'views/pages/500.html'
      })

})

fitme.run(function($rootScope, $state, $stateParams) {
  $rootScope.$on('$stateChangeSuccess',function(){
    document.body.scrollTop = document.documentElement.scrollTop = 0;
  });
  $rootScope.$state = $state;
  return $rootScope.$stateParams = $stateParams;
})