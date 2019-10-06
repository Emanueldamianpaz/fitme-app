fitme.controller('usersController', function ($rootScope, $scope, UsersService, MessageNotification, $filter) {

    $rootScope.stateCurrent = "users";

    UsersService.getExercises().then(function (response) {
        $scope.exerciseList = response.data;
    });
})

