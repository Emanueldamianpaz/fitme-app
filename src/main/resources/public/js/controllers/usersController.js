fitme.controller('usersController', function ($rootScope, $scope, UsersService, MessageNotification, $filter) {

    $rootScope.stateCurrent = "users";

    $scope.userList = [];
    $scope.userSelected = {};
    $scope.userTip = {id: '', message: ''};

    UsersService.getListUsersInfo().then(function (response) {
        $scope.userList = response.data;
    });

    $scope.setUserSelected = function (user) {
        $scope.userSelected = Object.create(user);
    };

    $scope.sendTip = function () {
        $scope.userTip.id = $scope.userSelected.id;

        // TODO
        // UsersService.sendMessage(message, id)

    };


})

