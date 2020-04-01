fitme.service('WorkoutExerciseService', function ($http) {

    var path = api + '/workout-exercise';

    this.getWorkoutExercises = function () {
        return $http({
            method: 'get',
            url: path
        });
    }

    this.createExercise = function (exercise) {
        return $http({
            method: 'post',
            url: path,
            data: exercise
        });
    }

    this.updateExercise = function (exercise, id) {
        return $http({
            method: 'patch',
            url: path + '/' + id,
            data: exercise
        });
    }

    this.deleteExercise = function (id) {
        return $http({
            method: 'delete',
            url: path + '/' + id
        });
    }

})
