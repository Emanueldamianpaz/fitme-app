fitme.service('MealNutritionService', function ($http) {

    var path = api + '/meal-nutrition';

    this.getMealNutritions = function () {
        return $http({
            method: 'get',
            url: path
        });
    }


    this.createMealNutrition = function (nutrition) {
        return $http({
            method: 'post',
            url: path,
            data: nutrition
        });
    }

    this.updateMealNutrition = function (nutrition, id) {
        return $http({
            method: 'patch',
            url: path + '/' + id,
            data: nutrition
        });
    }

    this.deleteMealNutrition = function (id) {
        return $http({
            method: 'delete',
            url: path + '/' + id
        });
    }
})
