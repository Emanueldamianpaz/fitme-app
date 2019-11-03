fitme.service('UsersService', function ($http) {

    var pathUsers = '/fitme/user';
    var pathUsersInfo = api + '/user_info';

    this.getListUsersInfo = function () {
        return $http(getListUsersInfo());
    }

    function getListUsersInfo() {
        return req = {
            method: 'get',
            url: pathUsers
        }
    };

    this.getDetailUser = function (id) {
        return $http(getDetailUser(id));
    }

    function getDetailUser(id) {
        return req = {
            method: 'get',
            url: pathUsers + '/' + id + '/info',
        }
    };

    this.sendMessage = function (message, id) {
        return $http(sendMessage(message, id));
    }

    function sendMessage(message, id) {
        return req = {
            method: 'post',
            url: pathUsers + '/' + id + '/message',
            data: message
        }
    };
})