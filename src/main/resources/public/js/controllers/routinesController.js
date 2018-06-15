fitme.controller('routinesController', function ($rootScope, $scope, RoutinesService, ExercisesService, NutritionsService) {

    $rootScope.stateCurrent = "routines";

    $scope.routine = {
        add: {
            routineTemplate: 'new',
            exercises: [],
            nutritions: []
        },
        routineTemplate: 'id',
        list: []
    };
    $scope.routineSelected = {};
    $scope.routineEdit = {exercises: [], nutritions: []};

    $scope.setRoutineSelected = function (routine) {
        $scope.routineSelected = [];
        $scope.routineSelected = routine;
        ExercisesService.getExercises().then(function (response) {
            $scope.exercisesToAdd = response.data;

        });
        NutritionsService.getNutritions().then(function (response) {
            $scope.nutritionsToAdd = response.data;
        });

    }
    RoutinesService.getRoutines().then(function (response) {
        $scope.routine.list = response.data;
    });

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

    $scope.removeExercise = function (exerciseId) {
        $scope.routineSelected.routineTemplate.exercises = $scope.routineEdit.exercises.filter(function (ex) {
            return ex.id != exerciseId
        });
    }
    $scope.addExercise = function () {
        $scope.routineSelected.routineTemplate.exercises.push(JSON.parse($scope.routineEdit.exercise));
    }

    $scope.removeNutrition = function (nutritionId) {
        $scope.routineSelected.routineTemplate.nutritions = $scope.routineEdit.nutritions.filter(function (nut) {
            return nut.id != nutritionId
        });
    }
    $scope.addNutrition = function () {
        $scope.routineSelected.routineTemplate.nutritions.push(JSON.parse($scope.routineEdit.nutrition));
    }


})

