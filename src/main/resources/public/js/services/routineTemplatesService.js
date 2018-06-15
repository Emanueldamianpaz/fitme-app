fitme.service('RoutineTemplatesService', function ($http) {

    var path = api + '/routine_template';

    this.getRoutinesTemplates = function () {
        return $http(getRoutinesTemplates());
    }

    function getRoutinesTemplates() {
        return req = {
            method: 'get',
            url: path
        }
    };

    this.getRoutineTemplate = function (id) {
        return $http(getRoutineTemplate(id));
    }

    function getRoutineTemplate(id) {
        return req = {
            method: 'get',
            url: path + '/' + id
        }
    };

    this.createRoutineTemplate = function (routineTemplate) {
        return $http(createRoutineTemplate(routineTemplate));
    }

    function createRoutineTemplate(routineTemplate) {
        return req = {
            method: 'post',
            url: path,
            data: routineTemplate
        }
    };

    this.updateRoutineTemplate = function (routineTemplate, id) {
        return $http(updateRoutineTemplate(routineTemplate, id));
    }

    function updateRoutineTemplate(routineTemplate, id) {
        return req = {
            method: 'patch',
            url: path + '/' + id,
            data: routineTemplate
        }
    };

    this.deleteRoutineTemplate = function (routineTemplate, id) {
        return $http(deleteRoutineTemplate(routineTemplate, id));
    }

    function deleteRoutineTemplate(routineTemplate, id) {
        return req = {
            method: 'delete',
            url: path + '/' + id,
            data: routineTemplate
        }
    };
})