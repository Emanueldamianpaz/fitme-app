fitme.controller('routinesController', function ($rootScope, $scope) {

    $rootScope.stateCurrent = "routines";

    $scope.showRoutine = function (routine) {
        $uibModal.open({
            templateUrl: 'views/components/modals/modal-show-routines.html',
            controller: 'modalShowRoutines',
            size: 'lg',
            resolve: {
                dataRoutine: function () {
                    return routine
                }
            }
        })
    };
})

