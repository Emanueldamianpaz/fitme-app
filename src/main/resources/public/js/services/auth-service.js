fitme.service('AuthService', AuthService);

function AuthService($cookies) {
    var COOKIE_NAME = "fitme_session";

    var readCookie = function () {
        return $cookies.get(COOKIE_NAME);
    };

    var readJwt = function () {
        var token = readCookie();
        if (token) {
            var base64Url = token.split('.')[1];
            var base64 = base64Url.replace('-', '+').replace('_', '/');
            return JSON.parse(window.atob(base64));
        }
    }

    var removeCookie = function () {
        $cookies.remove(COOKIE_NAME, {path: '/fitme'});
    }

    return {
        token: readCookie,
        jwt: readJwt,
        invalidateSession: removeCookie
    };
}