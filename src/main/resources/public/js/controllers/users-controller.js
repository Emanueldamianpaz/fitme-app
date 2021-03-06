fitme.controller('usersController', function ($rootScope, $scope, UsersService, UserRoutinesService,
                                              RoutineTemplatesService, TrainingService, MessageNotification, $filter) {

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
    $scope.coachTip = [''];

    $scope.refreshData = function () {
        UsersService.getListUsers()
            .then(response => $scope.userList = response.data);
    }

    $scope.showDetailUser = function (user) {
        $scope.setUserSelected(user);
        UsersService.getUser($scope.userSelected.id).then(function (response) {
            $scope.userSelectedDetail = response.data;
            $scope.getUserInfoParsed();
            $scope.getPercentGoalCompleted();
            $scope.renderProgressChart();
        })
    };

    $scope.showUserExperiences = function (user) {
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
        $scope.setUserSelected(user);
        UsersService.getUser($scope.userSelected.id).then(function (response) {
            $scope.userSelectedDetail = response.data;
        })
    };

    $scope.showRoutinesForAssign = function (user) {
        $scope.routinesToAdd = [];

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
        $scope.setUserSelected(user);
        UsersService.getUser($scope.userSelected.id).then(function (response) {
            $scope.userSelectedDetail = response.data;

            RoutineTemplatesService.getRoutinesTemplates()
                .then(rr => {
                    $scope.routineList = rr.data.filter(function (el) {
                        return !$scope.userSelectedDetail.userRoutines.map(x => x.routineTemplate.id).find(r => r == el.id)
                    });
                })
        })

    }

    $scope.addRoutinesToUser = function () {
        let routinesIDList = {
            routine_template_ids: $scope.routinesToAdd.map(x => x.id)
        }

        UserRoutinesService.addUserRoutine($scope.userSelected.id, routinesIDList)
            .then(x => UsersService.getUser($scope.userSelected.id).then(function (response) {
                $scope.refreshAssign(response);
            }))
    }

    $scope.deassignRoutine = function (id) {
        if (confirm("¿Está seguro de desasignar esta rutina?")) {
            UserRoutinesService.deleteUserRoutine($scope.userSelected.id, id).then(x => {
                console.log(x);
                UsersService.getUser($scope.userSelected.id).then(function (response) {
                    $scope.refreshAssign(response);
                })
            })
        }
    }

    $scope.refreshAssign = function (response) {
        $scope.userSelectedDetail = response.data;
        $scope.routinesToAdd = [];
        RoutineTemplatesService.getRoutinesTemplates()
            .then(rr => {
                $scope.routineList = rr.data.filter(function (el) {
                    return !$scope.userSelectedDetail.userRoutines.map(x => x.routineTemplate.id).find(r => r == el.id)
                });
            })
    }
    $scope.sendTip = function (userRoutineId, userExperienceId) {
        let coachTip = {
            coachTip: $scope.coachTip[userExperienceId]
        }

        UserRoutinesService.setCoachTip($scope.userSelectedDetail.id, userRoutineId, userExperienceId, coachTip)
            .then(x => console.log(x))
    };

    $scope.refreshData();

    // ---------------------------------------------------------------------------- Interno para manejo de la UI

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

    $scope.getTotalParsed = function (training) {

        let totalCalories = 0;
        let totalMeters = 0;
        training.nutritionSessions.map(n => totalCalories = totalCalories + n.calories);
        training.runningSessions.map(r => totalMeters = totalMeters + r.runningSession.distanceCovered);

        return {
            date: training.date,
            calories: (Math.round(totalCalories * 100) / 100),
            meters: (Math.round(totalMeters * 100) / 100),
        }
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

    $scope.setUserSelected = function (user) {
        $scope.userSelected = Object.create(user);
    };

    $scope.addRoutineToUser = function () {
        var routineItem = $scope.routineToAdd.object;
        var existsInArray = false;

        $scope.routinesToAdd.map(r => r == routineItem ? existsInArray = true : existsInArray = false);


        if (!existsInArray) {

            if (routineItem.mealNutritions.length < 1 && routineItem.workoutExercises.length < 1) {
                if (confirm("La rutina seleccionada no posee dietas y ejercicios, ¿desea asignarla de todas formas?")) {
                    $scope.routinesToAdd.push(routineItem);
                }
            } else if (routineItem.mealNutritions.length == 0) {
                if (confirm("La rutina seleccionada no posee dietas, ¿desea asignarla de todas formas?")) {
                    $scope.routinesToAdd.push(routineItem);
                }
            } else if (routineItem.workoutExercises.length == 0) {
                if (confirm("La rutina seleccionada no posee ejercicios, ¿desea asignarla de todas formas?")) {
                    $scope.routinesToAdd.push(routineItem);
                }
            } else {
                $scope.routinesToAdd.push(routineItem);
            }
            console.log(routineItem);

        }
    }

    $scope.removeRoutine = function (routineId) {
        $scope.routinesToAdd = $scope.routinesToAdd.filter(nut => nut.id != routineId);
    }


    $scope.renderProgressChart = function () {

        let total = $scope.userSelectedDetail.userInfo.trainingSession
            .map(training => $scope.getTotalParsed(training))

        let labels = total.map(x => x.date)
        let optionsRunning = {
            responsive: true,
            title: {
                display: true,
                text: 'Running'
            },
            tooltips: {
                mode: 'index',
                intersect: true
            },
            annotation: {
                annotations: [{
                    type: 'line',
                    mode: 'horizontal',
                    scaleID: 'y-axis-0',
                    value: 5,
                    borderColor: 'rgb(75, 192, 192)',
                    borderWidth: 4,
                }]
            }
        }
        let optionsNutrition = {
            responsive: true,
            title: {
                display: true,
                text: 'Nutricion'
            },
            tooltips: {
                mode: 'index',
                intersect: true
            },
            annotation: {
                annotations: [{
                    type: 'line',
                    mode: 'horizontal',
                    scaleID: 'y-axis-0',
                    value: 5,
                    borderColor: 'rgb(75, 192, 192)',
                    borderWidth: 4,
                }]
            }
        }

        new Chart(document.getElementById('progressRunning'), {
            type: 'line',
            data: {
                labels: labels,
                datasets: [{
                    label: 'Metros recorridos',
                    borderColor: 'rgb(54, 162, 235)',
                    borderWidth: 2,
                    fill: false,
                    data: total.map(x => x.meters)
                }]
            },
            options: optionsRunning
        });

        new Chart(document.getElementById('progressNutrition'), {
            type: 'line',
            data: {
                labels: labels,
                datasets: [{
                    label: 'Calorias ingeridas',
                    borderColor: 'rgb(54, 162, 235)',
                    borderWidth: 2,
                    fill: false,
                    data: total.map(x => x.calories)
                }]
            },
            options: optionsNutrition
        });
    };


})

