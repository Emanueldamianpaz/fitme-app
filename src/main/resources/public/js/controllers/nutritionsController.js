fitme.controller('nutritionsController', function ($rootScope, $scope, NutritionsService, MessageNotification, $filter) {

    $rootScope.stateCurrent = "nutritions";

    $scope.nutritionSelected = {};
    $scope.nutritionModelAdd = {
        name: '',
        type: '',
        calories: 0
    };
    $scope.nutritionModelEdit = {
        name: '',
        type: '',
        calories: 0
    };

    NutritionsService.getNutritions().then(function (response) {
        $scope.nutritionList = response.data;
    });

    $scope.setNutritionSelected = function (nutrition) {
        $scope.nutritionSelected = Object.create(nutrition);
        $scope.nutritionModelEdit = Object.create(nutrition);
    };

    $scope.createNutrition = function () {
        var dataNutrition = {
            name: $scope.nutritionModelAdd.name,
            type: $scope.nutritionModelAdd.type,
            calories: $scope.nutritionModelAdd.calories
        };

        NutritionsService.createNutrition(dataNutrition).then(function (response) {
            MessageNotification.showMessage($filter('translate')('responses.create-nutrition'));

            NutritionsService.getNutritions().then(function (response) {
                $scope.nutritionList = response.data;
            });
        })
    }

    $scope.updateNutrition = function () {
        var dataNutrition = {
            name: $scope.nutritionModelEdit.name,
            type: $scope.nutritionModelEdit.type,
            calories: $scope.nutritionModelEdit.calories
        };

        NutritionsService.updateNutrition(dataNutrition, $scope.nutritionModelEdit.id).then(function (response) {
            MessageNotification.showMessage($filter('translate')('responses.create-nutrition'));

            NutritionsService.getNutritions().then(function (response) {
                $scope.nutritionList = response.data;
            });
        })
    }

    $scope.deleteNutrition = function () {
        NutritionsService.deleteNutrition($scope.nutritionSelected.id).then(function (response) {
            MessageNotification.showMessage($filter('translate')('responses.create-nutrition'));

            NutritionsService.getNutritions().then(function (response) {
                $scope.nutritionList = response.data;
            });
        })
    }
})

