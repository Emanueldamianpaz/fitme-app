fitme.service('RoutineTemplatesService', function ($http) {

    var path = `${api}/routine-template`;

    this.getRoutinesTemplates = function () {
        return $http({
            method: 'get',
            url: path
        });
    }

    this.createRoutineTemplate = function (routineTemplate) {
        return $http({
            method: 'post',
            url: path,
            data: routineTemplate
        });
    }

    this.updateRoutineTemplate = function (routineTemplate, id) {
        return $http({
            method: 'patch',
            url: `${path}/${id}`,
            data: routineTemplate
        });
    }

    this.deleteRoutineTemplate = function (id) {
        return $http({
            method: 'delete',
            url: `${path}/${id}`
        });
    }
})
