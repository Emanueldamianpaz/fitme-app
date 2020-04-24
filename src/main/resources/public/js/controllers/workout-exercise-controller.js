fitme.controller('exercisesController', function ($rootScope, $scope, $filter, WorkoutExerciseService, MessageNotification) {

    $rootScope.stateCurrent = "exercises";

    $scope.workoutExercisesType = workoutExerciseTypeEnum;
    $scope.difficultyType = difficultyTypeEnum;

    $scope.exerciseSelected = {};
    $scope.exerciseModelAdd = null;
    $scope.exerciseModelEdit = null;

    $scope.refreshData = function () {
        WorkoutExerciseService.getWorkoutExercises()
            .then(response => $scope.exerciseList = response.data)
            .catch(error => console.error(error))

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
        $scope.exerciseSelected = {};

    };

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
            $scope.refreshData();
        })
    }

    $scope.updateExercise = function () {
        var dataExercise = {
            name: $scope.exerciseModelEdit.name,
            type: $scope.exerciseModelEdit.type,
            difficulty: $scope.exerciseModelEdit.difficulty,
            description: $scope.exerciseModelEdit.description
        };

        WorkoutExerciseService.updateWorkoutExercise(dataExercise, $scope.exerciseModelEdit.id)
            .then(x => {
                MessageNotification.showMessage($filter('translate')('responses.create-nutrition'));
                $scope.refreshData();
            })
            .catch(error => console.error(error))

    };

    $scope.deleteExercise = function () {
        WorkoutExerciseService.deleteWorkoutExercise($scope.exerciseSelected.id)
            .then(x => {
                MessageNotification.showMessage($filter('translate')('responses.create-exercise'));
                $scope.refreshData();
            })
            .catch(error => console.error(error))
    };

    $scope.refreshData();


})

