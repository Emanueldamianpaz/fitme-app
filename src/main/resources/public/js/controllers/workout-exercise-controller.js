fitme.controller('exercisesController', function ($rootScope, $scope, $filter, WorkoutExerciseService, MessageNotification) {

    $rootScope.stateCurrent = "exercises";

    $scope.exerciseSelected = {};
    $scope.exerciseModelAdd = {
        name: '',
        type: '',
        difficulty: '',
        description: ''
    };
    $scope.exerciseModelEdit = {
        name: '',
        type: '',
        difficulty: '',
        description: ''
    };


    $scope.refreshWorkoutExercises = function () {
        WorkoutExerciseService.getWorkoutExercises().then(function (response) {
            $scope.exerciseList = response.data;
        });
    };

    $scope.refreshWorkoutExercises();

    $scope.setExerciseSelected = function (exercise) {
        $scope.exerciseSelected = Object.create(exercise);
        $scope.exerciseModelEdit = Object.create(exercise);
    };

    $scope.createExercise = function () {
        var dataExercise = {
            name: $scope.exerciseModelAdd.name,
            type: $scope.exerciseModelAdd.type,
            difficulty: $scope.exerciseModelAdd.difficulty,
            description: $scope.exerciseModelAdd.description
        };

        WorkoutExerciseService.createWorkoutExercise(dataExercise).then(function () {
            MessageNotification.showMessage($filter('translate')('responses.create-exercise'));
            $scope.refreshWorkoutExercises();
        })
    }

    $scope.updateExercise = function () {
        var dataExercise = {
            name: $scope.exerciseModelEdit.name,
            type: $scope.exerciseModelEdit.type,
            difficulty: $scope.exerciseModelEdit.difficulty,
            description: $scope.exerciseModelEdit.description
        };

        WorkoutExerciseService.updateWorkoutExercise(dataExercise, $scope.exerciseModelEdit.id).then(function () {
            MessageNotification.showMessage($filter('translate')('responses.create-exercise'));

            $scope.refreshWorkoutExercises();
        })
    };

    $scope.deleteExercise = function () {
        WorkoutExerciseService.deleteWorkoutExercise($scope.exerciseSelected.id).then(function () {
            MessageNotification.showMessage($filter('translate')('responses.create-exercise'));

            $scope.refreshWorkoutExercises();
        })
    }

})

