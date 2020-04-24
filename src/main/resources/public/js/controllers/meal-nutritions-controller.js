fitme.controller('nutritionsController', function ($rootScope, $scope, MealNutritionService, MessageNotification, $filter) {

    $rootScope.stateCurrent = "nutritions";

    $scope.nutritionType = mealNutritionTypesEnum;

    $scope.nutritionSelected = {};
    $scope.nutritionModelAdd = null;
    $scope.nutritionModelEdit = null;

    $scope.refreshData = function () {
        MealNutritionService.getMealNutritions().then(response => {
            $scope.nutritionList = response.data
        });

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

        $scope.nutritionSelected = {};
    };

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

        MealNutritionService.createMealNutrition(dataNutrition)
            .then(x => {
                MessageNotification.showMessage($filter('translate')('responses.create-nutrition'));
                $scope.refreshData();
            }).catch(error => console.error(error))
    }

    $scope.updateNutrition = function () {
        var dataNutrition = {
            name: $scope.nutritionModelEdit.name,
            type: $scope.nutritionModelEdit.type,
            calories: $scope.nutritionModelEdit.calories
        };

        MealNutritionService.updateMealNutrition(dataNutrition, $scope.nutritionModelEdit.id)
            .then(x => {
                MessageNotification.showMessage($filter('translate')('responses.create-nutrition'));
                $scope.refreshData();
            })
            .catch(error => console.error(error))
    }

    $scope.deleteNutrition = function () {
        MealNutritionService.deleteMealNutrition($scope.nutritionSelected.id)
            .then(x => {
                MessageNotification.showMessage($filter('translate')('responses.create-nutrition'))
                $scope.refreshData()
            })
            .catch(error => console.error(error))
    }

    $scope.refreshData();
})

