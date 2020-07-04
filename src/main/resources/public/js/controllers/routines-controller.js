fitme.controller('routinesController', function ($rootScope, $scope,
                                                 RoutineTemplatesService,
                                                 WorkoutExerciseService,
                                                 MealNutritionService,
                                                 UserRoutinesService,
                                                 MessageNotification,
                                                 $filter) {

    $rootScope.stateCurrent = "routines";
    $scope.routineSelected = {};
    $scope.userExperiences = [];

    $scope.goalType = goalTypeEnum;
    $scope.scoringType = scoringTypeEnum;

    $scope.routineEdit = {workoutExercises: [], mealNutritions: []};
    $scope.routineAdd = {workoutExercises: [], mealNutritions: []};

    $scope.routineModelAdd = {
        name: '',
        description: '',
        workoutExercises: [],
        mealNutritions: [],
        goalType: '',
        scoringSystem: 'UNKNOWN'
    };
    $scope.routineModelEdit = {
        name: '',
        description: '',
        workoutExercises: [],
        mealNutritions: [],
        goalType: '',
        scoringSystem: 'UNKNOWN'
    };

    $scope.$watch('routineModelAdd.name', function () {
        console.log($scope.routineModelAdd.name);
    })
    $scope.refreshData = function () {
        WorkoutExerciseService.getWorkoutExercises().then(function (response) {
            $scope.exerciseList = response.data;
        });
        MealNutritionService.getMealNutritions().then(function (response) {
            $scope.nutritionList = response.data;
        });
        RoutineTemplatesService.getRoutinesTemplates().then(function (response) {
            $scope.routineList = response.data;
        });

    }

    $scope.cleanModal = function () {
        $scope.routineEdit = {workoutExercises: [], mealNutritions: []};
        $scope.routineAdd = {workoutExercises: [], mealNutritions: []};

        $scope.routineModelAdd = {
            name: '',
            description: '',
            workoutExercises: [],
            mealNutritions: [],
            goalType: '',
            scoringSystem: 'UNKNOWN'
        };

        $scope.routineModelEdit = {
            name: '',
            description: '',
            workoutExercises: [],
            mealNutritions: [],
            goalType: '',
            scoringSystem: 'UNKNOWN'
        };
    }

    $scope.createRoutine = function () {
        var dataRoutine = {
            name: $scope.routineModelAdd.name,
            description: $scope.routineModelAdd.description,
            workoutExercises: $scope.routineModelAdd.workoutExercises.map(x => ({
                id: x.id
            })),
            mealNutritions: $scope.routineModelAdd.mealNutritions.map(x => ({
                id: x.id
            })),
            goalType: $scope.routineModelAdd.goalType,
            scoringSystem: 'UNKNOWN'
        };

        RoutineTemplatesService.createRoutineTemplate(dataRoutine)
            .then(x => {
                MessageNotification.showMessage($filter('translate')('responses.create-routine'));
                $scope.refreshData();
            })
            .catch(error => console.error(error));

    };

    $scope.updateRoutine = function () {
        var dataRoutine = {
            name: $scope.routineModelEdit.name,
            description: $scope.routineModelEdit.description,
            workoutExercises: $scope.routineModelEdit.workoutExercises.map(x => ({
                id: x.id
            })),
            mealNutritions: $scope.routineModelEdit.mealNutritions.map(x => ({
                id: x.id
            })),
            goalType: $scope.routineModelEdit.goalType,
            scoringSystem: 'UNKNOWN'
        };

        RoutineTemplatesService.updateRoutineTemplate(dataRoutine, $scope.routineModelEdit.id)
            .then(x => {
                MessageNotification.showMessage($filter('translate')('responses.create-routine'));
                $scope.refreshData();
            })
            .catch(error => console.error(error));


    };

    $scope.deleteRoutine = function () {
        RoutineTemplatesService.deleteRoutineTemplate($scope.routineSelected.id)
            .then($scope.refreshData())
            .catch(error => toastr.error("La rutina está siendo utilizada por usuarios. Desasignela para poder borrar"))
    };


    // ---------------------------------------------------------------------------- Interno para manejo de la UI

    $scope.showDetail = function (routine) {
        UserRoutinesService.getUserRoutines(routine.id).then(x => {
            $scope.userExperiences = x.data;
        });
        $scope.setRoutineSelected(routine);
    }

    $scope.setRoutineSelected = function (routine) {
        $scope.cleanModal();
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

    $scope.removeExercise = function (exerciseId, typeOperation) {
        if (typeOperation == 'edit') {
            $scope.routineModelEdit.workoutExercises = $scope.routineModelEdit
                .workoutExercises.filter(ex => ex.id != exerciseId);
        } else if (typeOperation == 'create') {
            $scope.routineModelAdd.workoutExercises = $scope.routineModelAdd
                .workoutExercises.filter(ex => ex.id != exerciseId)
        }
    }

    $scope.removeNutrition = function (nutritionId, typeOperation) {
        if (typeOperation == 'edit') {
            $scope.routineModelEdit.mealNutritions = $scope.routineModelEdit
                .mealNutritions.filter(nut => nut.id != nutritionId);
        } else if (typeOperation == 'create') {
            $scope.routineModelAdd.mealNutritions = $scope.routineModelAdd
                .mealNutritions.filter(nut => nut.id != nutritionId)
        }
    }

    $scope.addNutrition = function (typeOperation) {
        if (typeOperation == 'edit') {
            var nutritionItem = JSON.parse($scope.routineEdit.mealNutritions);
            var existsInArray = false;

            $scope.routineModelEdit.mealNutritions.map(function (nut) {
                if (nut.id == nutritionItem.id) {
                    existsInArray = true;
                }
            });

            if (!existsInArray) {
                $scope.routineModelEdit.mealNutritions.push(nutritionItem);
            }
        } else if (typeOperation == 'create') {

            var nutritionItem = JSON.parse($scope.routineAdd.mealNutritions);
            var existsInArray = false;

            $scope.routineModelAdd.mealNutritions.map(function (nut) {
                if (nut.id == nutritionItem.id) {
                    existsInArray = true;
                }
            });

            if (!existsInArray) {
                $scope.routineModelAdd.mealNutritions.push(nutritionItem);
            }
        }
    }

    $scope.addExercise = function (typeOperation) {
        if (typeOperation == 'edit') {

            var exerciseItem = JSON.parse($scope.routineEdit.workoutExercises);
            var existsInArray = false;

            $scope.routineModelEdit.workoutExercises.map(function (ex) {
                if (ex.id == exerciseItem.id) {
                    existsInArray = true;
                }
            });

            if (!existsInArray) {
                $scope.routineModelEdit.workoutExercises.push(exerciseItem);
            }

        } else if (typeOperation == 'create') {

            var exerciseItem = JSON.parse($scope.routineAdd.workoutExercises);
            var existsInArray = false;

            $scope.routineModelAdd.workoutExercises.map(function (ex) {
                if (ex.id == exerciseItem.id) {
                    existsInArray = true;
                }
            });

            if (!existsInArray) {
                $scope.routineModelAdd.workoutExercises.push(exerciseItem);
            }
        }
    }

    $scope.getBadgeExperience = function (scoring) {
        switch (scoring) {
            case 'GOOD':
                return 'badge-success'
                break;
            case 'REGULAR':
                return 'badge-warning'
                break;
            case 'BAD':
                return 'badge-danger'
                break;
            case 'UNKNOWN':
                return 'badge-dark'
                break;
        }
    }
    $scope.refreshData();
})

