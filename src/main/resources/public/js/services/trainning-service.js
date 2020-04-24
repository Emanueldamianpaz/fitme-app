fitme.service('TrainningService', function ($http) {

    var path = `${api}/trainning-session`;

    this.getTrainningSession = function (idUser) {
        return $http({
            method: 'get',
            url: `${path}/${idUser}/info`
        });
    }

    this.createRunningSession = function (idUser, exercise) {
        return $http({
            method: 'post',
            url: `${path}/${idUser}/exercise`,
            data: exercise
        });
    }

    this.createNutritionSession = function (idUser, nutrition) {
        return $http({
            method: 'post',
            url: `${path}/${idUser}/nutrition`,
            data: nutrition
        });
    }
})
