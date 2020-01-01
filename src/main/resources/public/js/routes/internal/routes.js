fitme.config(function ($stateProvider) {

    $stateProvider
        .state('app', {
            abstract: true,
            templateUrl: 'views/common/layouts/full.html',
            data: {pageTitle: 'Home'}
        })

        .state('app.dashboard', {
            url: '/dashboard',
            templateUrl: 'views/app/dashboard.html',
            controller: 'dashboardController'
        })

        .state('app.routines', {
            url: '/routines',
            templateUrl: 'views/app/routines/routines.html',
            controller: 'routinesController'
        })

        .state('app.users', {
            url: '/users',
            templateUrl: 'views/app/users/users.html',
            controller: 'usersController'
        })

        .state('app.exercises', {
            url: '/exercises',
            templateUrl: 'views/app/exercises/exercises.html',
            controller: 'exercisesController'
        })

        .state('app.nutritions', {
            url: '/nutritions',
            templateUrl: 'views/app/nutritions/nutritions.html',
            controller: 'nutritionsController'
        })


})
