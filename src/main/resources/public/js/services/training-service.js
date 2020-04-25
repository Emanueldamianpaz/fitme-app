fitme.service('TrainingService', function ($http) {

    var path = `${api}/training-session`;

    this.getTrainingSession = function (idUser) {
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
