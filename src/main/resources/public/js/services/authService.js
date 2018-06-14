fitme.service('AuthService', AuthService);

function AuthService() {

    var roles = [
        {
            id: 0000000001,
            role: 'admin',
            permissions: [
                {
                    id: ''
                }
            ]
        }
    ];

    this.getRoles = function () {
        return roles;
    }
}