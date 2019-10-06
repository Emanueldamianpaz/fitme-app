fitme.controller('usersController', function ($rootScope, $scope, UsersService, MessageNotification, $filter) {

    $rootScope.stateCurrent = "users";

    $scope.userList = [{
        email: null,
        gender: null,
        id: null,
        lastName: null,
        name: null,
        nickname: null,
        picture: null,
        role: null,
        userInfo: {
            id: null, weight: null, height: null, currentFat: null,
            exerciseSession: [],
            frecuencyExercise: null,
            goal: null,
            height: null,
            id: null,
            weight: null,
            userRoutine: null
        }
    }]
    UsersService.getListUsersInfo().then(function (response) {
        $scope.userList = response.data;
    });
})

