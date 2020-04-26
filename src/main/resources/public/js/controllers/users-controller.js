fitme.controller('usersController', function ($rootScope, $scope, UsersService, UserRoutinesService, TrainingService, MessageNotification, $filter) {

    $rootScope.stateCurrent = "users";

    $scope.userList = [];
    $scope.routineList = [];

    $scope.routineToAdd = {object: {}};
    $scope.routinesToAdd = [];

    $scope.userSelected = {};
    $scope.userSelectedDetail = {
        "id": "",
        "userInfo": {
            "id": "",
            "initialWeight": 0,
            "height": 0,
            "currentFat": 0,
            "frecuencyExercise": "",
            "userGoal": {
                "type": "",
                "goalFat": 0
            },
            "trainingSession": []
        },
        "userRoutines": [],
        "name": "",
        "lastName": "",
        "email": "",
        "picture": "",
        "nickname": "",
        "genre": null,
        "role": ""
    };


    $scope.userTip = {id: '', message: ''};

    $scope.refreshData = function () {
        UsersService.getListUsers().then(function (response) {
            $scope.userList = response.data;
        });
    }

    $scope.showDetailUser = function (user) {
        $scope.setUserSelected(user);
        UsersService.getUser($scope.userSelected.id).then(function (response) {
            $scope.userSelectedDetail = response.data;
            $scope.getUserInfoParsed();
            $scope.getPercentGoalCompleted();
        })
    }

    $scope.getPercentGoalCompleted = function () {
        let parsed = {
            initial: $scope.userSelectedDetail.userInfo.initialWeight,
            current: $scope.userSelectedDetail.userInfo.currentFat,
            goal: $scope.userSelectedDetail.userInfo.userGoal.goalFat,
            completed: 0
        };

        parsed.completed = ((parsed.initial - parsed.current) * 100) / (parsed.initial - parsed.goal)

        parsed.completed = parsed.completed < 0 ? 0 : parsed.completed;
        parsed.completed = parsed.completed > 100 ? 100 : parsed.completed;

        return parsed

    };

    $scope.getUserInfoParsed = function () {
        let goalInfo = null;

        if ($scope.userSelectedDetail.userInfo.userGoal) {
            goalInfo = {
                type: $filter('translate')(`goal-type.${$scope.userSelectedDetail.userInfo.userGoal.type}`),
                goalFat: $scope.userSelectedDetail.userInfo.userGoal.goalFat
            }
        } else {
            goalInfo = {
                type: 'n/a',
                goalFat: 'n/a'
            }
        }
        return {
            initialWeight: $scope.userSelectedDetail.userInfo.initialWeight ? `${$scope.userSelectedDetail.userInfo.initialWeight}kg.` : 'n/a',
            height: $scope.userSelectedDetail.userInfo.height ? `${$scope.userSelectedDetail.userInfo.height}m.` : 'n/a',
            currentFat: $scope.userSelectedDetail.userInfo.currentFat ? `${$scope.userSelectedDetail.userInfo.currentFat}kg.` : 'n/a',
            goal: goalInfo
        }
    }

    $scope.setUserSelected = function (user) {
        $scope.userSelected = Object.create(user);
    };

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

    $scope.refreshData();

    $scope.getTotalParsed = function (training) {

        let totalCalories = 0;
        let totalMeters = 0;
        training.nutritionSessions.map(n => totalCalories = totalCalories + n.calories);
        training.runningSessions.map(r => totalMeters = totalMeters + r.runningSession.distanceCovered);

        return {
            calories: (Math.round(totalCalories * 100) / 100),
            meters: (Math.round(totalMeters * 100) / 100),
        }
    }

})

