fitme.service('RoutinesService', function ($http) {

    var path = api + '/routine';

    this.getRoutines = function () {
        return $http(getRoutines());
    }

    function getRoutines() {
        return req = {
            method: 'get',
            url: path
        }
    };

    this.getRoutine = function (id) {
        return $http(getRoutine(id));
    }

    function getRoutine(id) {
        return req = {
            method: 'get',
            url: path + '/' + id
        }
    };

    this.createRoutine = function (routine) {
        return $http(createRoutine(routine));
    }

    function createRoutine(routine) {
        return req = {
            method: 'post',
            url: path,
            data: routine
        }
    };

    this.updateRoutine = function (routine, id) {
        return $http(updateRoutine(routine, id));
    }

    function updateRoutine(routine, id) {
        return req = {
            method: 'patch',
            url: path + '/' + id,
            data: routine
        }
    };

    this.deleteRoutine = function (routine, id) {
        return $http(deleteRoutine(routine, id));
    }

    function deleteRoutine(routine, id) {
        return req = {
            method: 'delete',
            url: path + '/' + id,
            data: routine
        }
    };
})