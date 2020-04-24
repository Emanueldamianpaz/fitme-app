fitme.service('UserRoutinesService', function ($http) {

    var path = '/fitme/user';

    this.getListUserRoutine = function (idUser) {
        return $http({
            method: 'get',
            url: `${path}/${idUser}/user-routine`
        });
    }

    this.getUserRoutine = function (idUser, idUserRoutine) {
        return $http({
            method: 'get',
            url: `${path}/${idUser}/user-routine/${idUserRoutine}`
        });
    }

    this.addUserRoutine = function (idUser, idUserRoutine, userRoutine) {
        return $http({
            method: 'patch',
            url: `${path}/${idUser}/user-routine/${idUserRoutine}`,
            body: userRoutine
        });
    }

    this.getListUserExperiences = function (idUser, idUserRoutine) {
        return $http({
            method: 'get',
            url: `${path}/${idUser}/user-routine/${idUserRoutine}/user-experience`
        });
    }

    this.getUserExperience = function (idUser, idUserRoutine, idUserExperience) {
        return $http({
            method: 'get',
            url: `${path}/${idUser}/user-routine/${idUserRoutine}/user-experience/${idUserExperience}`
        });
    }

    this.setCoachTip = function (idUser, idUserRoutine, idUserExperience, coachTip) {
        return $http({
            method: 'post',
            url: `${path}/${idUser}/user-routine/${idUserRoutine}/user-experience/${idUserExperience}`,
            body: coachTip
        });
    }


})
