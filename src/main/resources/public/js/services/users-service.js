fitme.service('UsersService', function ($http) {

    var path = '/fitme/user';

    this.getListUsers = function () {
        return $http({
            method: 'get',
            url: path
        });
    }

    this.getUser = function (idUser) {
        return $http({
            method: 'get',
            url: `${path}/${idUser}`
        });
    }

    this.getUserLight = function (idUser) {
        return $http({
            method: 'get',
            url: `${path}/${idUser}/light`
        });
    }

    this.getUserInfo = function (idUser) {
        return $http({
            method: 'get',
            url: `${path}/${idUser}/info`
        });
    }

    this.updateUserInfo = function (idUser, userInfo) {
        return $http({
            method: 'patch',
            url: `${path}/${idUser}/info`,
            body: userInfo
        });
    }


})
