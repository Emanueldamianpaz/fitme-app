fitme.service('NutritionsService', function ($http) {

    var path = api + '/nutrition';

    this.getNutritions = function () {
        return $http(getNutritions());
    }

    function getNutritions() {
        return req = {
            method: 'get',
            url: path
        }
    };

    this.getNutrition = function (id) {
        return $http(getNutrition(id));
    }

    function getNutrition(id) {
        return req = {
            method: 'get',
            url: path + '/' + id
        }
    };

    this.createNutrition = function (nutrition) {
        return $http(createNutrition(nutrition));
    }

    function createNutrition(nutrition) {
        return req = {
            method: 'post',
            url: path,
            data: nutrition
        }
    };

    this.updateNutrition = function (nutrition, id) {
        return $http(updateNutrition(nutrition, id));
    }

    function updateNutrition(nutrition, id) {
        return req = {
            method: 'patch',
            url: path + '/' + id,
            data: nutrition
        }
    };

    this.deleteNutrition = function (id) {
        return $http(deleteNutrition(id));
    }

    function deleteNutrition(id) {
        return req = {
            method: 'delete',
            url: path + '/' + id
        }
    };
})