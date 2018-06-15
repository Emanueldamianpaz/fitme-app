fitme.service('ExercisesService', function ($http) {

    var path = api + '/exercise';

    this.getExercises = function () {
        return $http(getExercises());
    }

    function getExercises() {
        return req = {
            method: 'get',
            url: path
        }
    };

    this.getExercise = function (id) {
        return $http(getExercise(id));
    }

    function getExercise(id) {
        return req = {
            method: 'get',
            url: path + '/' + id
        }
    };

    this.createExercise = function (exercise) {
        return $http(createExercise(exercise));
    }

    function createExercise(exercise) {
        return req = {
            method: 'post',
            url: path,
            data: exercise
        }
    };

    this.updateExercise = function (exercise, id) {
        return $http(updateExercise(exercise, id));
    }

    function updateExercise(exercise, id) {
        return req = {
            method: 'patch',
            url: path + '/' + id,
            data: exercise
        }
    };

    this.deleteExercise = function (exercise, id) {
        return $http(deleteExercise(exercise, id));
    }

    function deleteExercise(exercise, id) {
        return req = {
            method: 'delete',
            url: path + '/' + id,
            data: exercise
        }
    };
})