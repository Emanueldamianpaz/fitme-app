fitme.controller('modalShowRoutines', function ($scope, $uibModalInstance, dataRoutine){

    $scope.ok = function () {
      $uibModalInstance.close();
    };

    console.log(dataRoutine)
    $scope.cancel = function () {
      $uibModalInstance.dismiss('cancel');
    };

})

