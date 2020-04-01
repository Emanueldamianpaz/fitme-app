fitme.controller('navbarController', function ($rootScope, $scope, AuthService) {

    $scope.userJWT = AuthService.jwt();

    $scope.logout = function () {
        AuthService.invalidateSession();
        location.reload();
    }
})

