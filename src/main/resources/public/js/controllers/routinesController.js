fitme.controller('routinesController', function ($rootScope, $scope, RoutinesService, ExercisesService, NutritionsService, RoutineTemplatesService, MessageNotification, $filter) {

    $rootScope.stateCurrent = "routines";

    $scope.routineSelected = {};

    $scope.routineEdit = {exercises: [], nutritions: []};
    $scope.routineAdd = {exercises: [], nutritions: []};
    $scope.routineModelAdd = {
        name: '',
        description: '',
        routineTemplate: {
            exercises: [],
            nutritions: []
        }
    };
    $scope.routineModelEdit = {
        name: '',
        description: '',
        routineTemplate: {
            exercises: [],
            nutritions: []
        }
    };


    ExercisesService.getExercises().then(function (response) {
        $scope.exerciseList = response.data;
    });
    NutritionsService.getNutritions().then(function (response) {
        $scope.nutritionList = response.data;
    });
    RoutinesService.getRoutines().then(function (response) {
        $scope.routineList = response.data;
    });

    RoutineTemplatesService.getRoutinesTemplates().then(function (response) {
        $scope.routineTemplateList = response.data;
    })

    $scope.setRoutineSelected = function (routine) {
        $scope.routineSelected = Object.create(routine);
        $scope.routineModelEdit = Object.create(routine);
    };

    $scope.selectRoutineTemplate = function (type) {
        if (type == 'edit') {
            if ($scope.routineEdit.routineTemplate != 'new') {
                $scope.routineEdit.routineTemplateSelected = JSON.parse($scope.routineEdit.routineTemplate);
            } else {
                $scope.routineEdit.routineTemplateSelected = null;
            }
        } else {
            if ($scope.routineAdd.routineTemplate != 'new') {
                $scope.routineAdd.routineTemplateSelected = JSON.parse($scope.routineAdd.routineTemplate);
            } else {
                $scope.routineAdd.routineTemplateSelected = null;
            }
        }
    };

    $scope.renderAverageScoring = function () {
        new Chart(document.getElementById('averageScoring'), {
            type: 'line',
            data: {
                labels: ["January", "February"],
                datasets: [{
                    label: 'Dataset 1',
                    borderColor: 'rgb(54, 162, 235)',
                    borderWidth: 2,
                    fill: false,
                    data: [2, 10]
                }]
            },
            options: {
                responsive: true,
                title: {
                    display: true,
                    text: 'Chart.js Draw Line On Chart'
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
                        label: {
                            enabled: false,
                            content: 'Test label'
                        }
                    }]
                }
            }
        });
    };

    $scope.renderAllScoring = function () {
        new Chart(document.getElementById('allScoring'), {
            data: data = {
                datasets: [{
                    data: [10, 20, 30],
                    backgroundColor: ["#cd3131", "#cbba35", "#456cd9"]
                }],
                labels: [
                    'Red',
                    'Yellow',
                    'Blue'
                ]
            },
            type: 'polarArea',
            options: {
                responsive: true,
                title: {
                    display: true,
                    text: 'Puntuaciones de los usuarios'
                }
            }
        });
    };

    $scope.removeExercise = function (exerciseId, typeOperation) {
        if (typeOperation == 'edit') {
            $scope.routineModelEdit.routineTemplate.exercises = $scope.routineModelEdit.routineTemplate.exercises.filter(function (ex) {
                return ex.id != exerciseId
            });
        } else if (typeOperation == 'create') {
            $scope.routineModelAdd.routineTemplate.exercises = $scope.routineModelAdd.routineTemplate.exercises.filter(function (ex) {
                return ex.id != exerciseId;
            })
        }
    }
    $scope.removeNutrition = function (nutritionId, typeOperation) {
        if (typeOperation == 'edit') {
            $scope.routineModelEdit.routineTemplate.nutritions = $scope.routineModelEdit.routineTemplate.nutritions.filter(function (nut) {
                return nut.id != nutritionId
            });
        } else if (typeOperation == 'create') {
            $scope.routineModelAdd.routineTemplate.nutritions = $scope.routineModelAdd.routineTemplate.nutritions.filter(function (nut) {
                return nut.id != nutritionId;
            })
        }
    }

    $scope.addNutrition = function (typeOperation) {
        if (typeOperation == 'edit') {
            var nutritionItem = JSON.parse($scope.routineEdit.nutritionItem);
            var existsInArray = false;

            $scope.routineModelEdit.routineTemplate.nutritions.map(function (nut) {
                if (nut.id == nutritionItem.id) {
                    existsInArray = true;
                }
            });

            if (!existsInArray) {
                $scope.routineModelEdit.routineTemplate.nutritions.push(nutritionItem);
            }
        } else if (typeOperation == 'create') {

            var nutritionItem = JSON.parse($scope.routineAdd.nutritionItem);
            var existsInArray = false;

            $scope.routineModelAdd.routineTemplate.nutritions.map(function (nut) {
                if (nut.id == nutritionItem.id) {
                    existsInArray = true;
                }
            });

            if (!existsInArray) {
                $scope.routineModelAdd.routineTemplate.nutritions.push(nutritionItem);
            }
        }
    }
    $scope.addExercise = function (typeOperation) {
        if (typeOperation == 'edit') {

            var exerciseItem = JSON.parse($scope.routineEdit.exerciseItem);
            var existsInArray = false;

            $scope.routineModelEdit.routineTemplate.exercises.map(function (ex) {
                if (ex.id == exerciseItem.id) {
                    existsInArray = true;
                }
            });

            if (!existsInArray) {
                $scope.routineModelEdit.routineTemplate.exercises.push(exerciseItem);
            }

        } else if (typeOperation == 'create') {

            var exerciseItem = JSON.parse($scope.routineAdd.exerciseItem);
            var existsInArray = false;

            $scope.routineModelAdd.routineTemplate.exercises.map(function (ex) {
                if (ex.id == exerciseItem.id) {
                    existsInArray = true;
                }
            });

            if (!existsInArray) {
                $scope.routineModelAdd.routineTemplate.exercises.push(exerciseItem);
            }
        }
    }

    $scope.createRoutine = function () {
        if ($scope.routineAdd.routineTemplate == 'new') {
            var dataRoutineTemplate = {
                nutritions: $scope.routineModelAdd.routineTemplate.nutritions.map(function (x) {
                    return x.id
                }),
                exercises: $scope.routineModelAdd.routineTemplate.exercises.map(function (x) {
                    return x.id
                })
            };

            RoutineTemplatesService.createRoutineTemplate(dataRoutineTemplate).then(function (response) {
                var dataRoutine = {
                    name: $scope.routineModelAdd.name,
                    description: $scope.routineModelAdd.description,
                    routineTemplate: response.data.id
                };

                RoutinesService.createRoutine(dataRoutine).then(function (response) {
                    MessageNotification.showMessage($filter('translate')('responses.create-routine'));
                    MessageNotification.showMessage("hola");

                    RoutinesService.getRoutines().then(function (response) {
                        $scope.routineList = response.data;
                    });
                })
            });


        } else {
            var dataRoutine = {
                name: $scope.routineModelAdd.name,
                description: $scope.routineModelAdd.description,
                routineTemplate: $scope.routineAdd.routineTemplateSelected.id
            };
            RoutinesService.createRoutine(dataRoutine).then(function (response) {
                MessageNotification.showMessage($filter('translate')('responses.create-routine'));

                RoutinesService.getRoutines().then(function (response) {
                    $scope.routineList = response.data;
                });
            })
        }
    }

    $scope.updateRoutine = function () {
        if ($scope.routineEdit.routineTemplate == 'new') {
            var dataRoutineTemplate = {
                nutritions: $scope.routineModelEdit.routineTemplate.nutritions.map(function (x) {
                    return x.id
                }),
                exercises: $scope.routineModelEdit.routineTemplate.exercises.map(function (x) {
                    return x.id
                })
            };

            RoutineTemplatesService.createRoutineTemplate(dataRoutineTemplate).then(function (response) {
                var dataRoutine = {
                    name: $scope.routineModelEdit.name,
                    description: $scope.routineModelEdit.description,
                    routineTemplate: response.data.id
                };

                RoutinesService.updateRoutine(dataRoutine, $scope.routineModelEdit.id).then(function (response) {
                    MessageNotification.showMessage($filter('translate')('responses.create-routine'));
                    MessageNotification.showMessage("hola");

                    RoutinesService.getRoutines().then(function (response) {
                        $scope.routineList = response.data;
                    });
                })
            });


        } else {
            var dataRoutine = {
                name: $scope.routineModelEdit.name,
                description: $scope.routineModelEdit.description,
                routineTemplate: $scope.routineEdit.routineTemplateSelected.id
            };
            RoutinesService.updateRoutine(dataRoutine, $scope.routineModelEdit.id).then(function (response) {
                MessageNotification.showMessage($filter('translate')('responses.create-routine'));

                RoutinesService.getRoutines().then(function (response) {
                    $scope.routineList = response.data;
                });
            })
        }
    }

    $scope.deleteRoutine = function () {
        RoutinesService.deleteRoutine($scope.routineSelected.id).then(function (response) {
            // TODO Poner un response correcto

            RoutinesService.getRoutines().then(function (response) {
                $scope.routineList = response.data;
            });
        })
    }

})

