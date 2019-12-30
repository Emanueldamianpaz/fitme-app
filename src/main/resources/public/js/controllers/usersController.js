fitme.controller('usersController', function ($rootScope, $scope, UsersService, RoutinesService, MessageNotification, $filter) {

    $rootScope.stateCurrent = "users";

    $scope.userList = [];
    $scope.routineList = [];

    $scope.routineToAdd = {object: {}};
    $scope.routinesToAdd = [];

    $scope.userSelected = {};
    $scope.userSelectedDetail = {};
    $scope.userTip = {id: '', message: ''};

    UsersService.getListUsersInfo().then(function (response) {
        $scope.userList = response.data;
    });

    RoutinesService.getRoutinesLight().then(function (response) {
        $scope.routineList = response.data;
    });

    $scope.setUserSelected = function (user) {
        $scope.userSelected = Object.create(user);
    };

    $scope.changeSelectedItem = function () {
        console.log($scope.routineToAdd);
    }

    $scope.addRoutineToUser = function () {
        var routineItem = $scope.routineToAdd.object;
        console.log(routineItem);

        var existsInArray = false;

        $scope.routinesToAdd.map(function (routine) {
            if (routine == routineItem) {
                existsInArray = true;
            }
        });

        if (!existsInArray) {
            $scope.routinesToAdd.push(routineItem);
            console.log(routineItem);

        }
    }

    $scope.removeRoutine = function (routineId) {
        $scope.routinesToAdd = $scope.routinesToAdd.filter(function (nut) {
            return nut.id != routineId
        });
    }

    $scope.getDetailUser = function () {
        UsersService.getDetailUser($scope.userSelected.id).then(function (response) {
            $scope.userSelectedDetail = response.data;
        })
    }

    $scope.addRoutinesToUser = function () {

        var routinesIDList = $scope.routinesToAdd.map(function (x) {
            return x.id
        });

        UsersService.setRoutines($scope.userSelected.id, routinesIDList).then(function (response) {
            console.log("funciono");
        })
    }

    $scope.sendTip = function () {
        $scope.userTip.id = $scope.userSelected.id;

        UsersService.sendMessage($scope.userTip.message, $scope.userTip.id)

    };


})

