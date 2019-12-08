fitme.controller('usersController', function ($rootScope, $scope, UsersService, MessageNotification, $filter) {

    $rootScope.stateCurrent = "users";

    $scope.userList = [];
    $scope.userSelected = {};
    $scope.userSelectedDetail = {};
    $scope.userTip = {id: '', message: ''};

    UsersService.getListUsersInfo().then(function (response) {
        $scope.userList = response.data;
    });

    $scope.setUserSelected = function (user) {
        $scope.userSelected = Object.create(user);
    };

    $scope.getDetailUser = function () {
        UsersService.getDetailUser($scope.userSelected.id).then(function (response) {
            $scope.userSelectedDetail = response.data;
        })
    }

    $scope.sendTip = function () {
        $scope.userTip.id = $scope.userSelected.id;

        UsersService.sendMessage($scope.userTip.message, $scope.userTip.id)

    };


})

