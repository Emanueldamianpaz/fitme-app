fitme.controller('exercisesController', function ($rootScope, $scope, ExercisesService, MessageNotification, $filter) {

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


    ExercisesService.getExercises().then(function (response) {
        $scope.exerciseList = response.data;
    });

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

        ExercisesService.createExercise(dataExercise).then(function (response) {
            MessageNotification.showMessage($filter('translate')('responses.create-exercise'));

            ExercisesService.getExercises().then(function (response) {
                $scope.exerciseList = response.data;
            });
        })
    }

    $scope.updateExercise = function () {
        var dataExercise = {
            name: $scope.exerciseModelEdit.name,
            type: $scope.exerciseModelEdit.type,
            difficulty: $scope.exerciseModelEdit.difficulty,
            description: $scope.exerciseModelEdit.description
        };

        ExercisesService.updateExercise(dataExercise, $scope.exerciseModelEdit.id).then(function (response) {
            MessageNotification.showMessage($filter('translate')('responses.create-exercise'));

            ExercisesService.getExercises().then(function (response) {
                $scope.exerciseList = response.data;
            });
        })
    }

    $scope.deleteExercise = function () {
        ExercisesService.deleteExercise($scope.exerciseSelected.id).then(function (response) {
            MessageNotification.showMessage($filter('translate')('responses.create-exercise'));

            ExercisesService.getExercises().then(function (response) {
                $scope.exerciseList = response.data;
            });
        })
    }

})

