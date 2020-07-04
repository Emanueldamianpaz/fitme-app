fitme.service('UserRoutinesService', function ($http) {

    var path = '/fitme/user';

    this.getListUserRoutine = function (idUser) {
        return $http({
            method: 'get',
            url: `${path}/${idUser}/user-routine`
        });
    }

    this.getUserRoutines = function (routineTemplateId) {
        return $http({
            method: 'get',
            url: `/fitme/api/user-routine?routine_template_id=${routineTemplateId}`
        });
    }

    this.addUserRoutine = function (idUser, userRoutine) {
        return $http({
            method: 'patch',
            url: `${path}/${idUser}/user-routine`,
            data: userRoutine
        });
    }

    this.deleteUserRoutine = function (idUser, userRoutineId) {
        return $http({
            method: 'delete',
            url: `${path}/${idUser}/user-routine/${userRoutineId}`
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
            url: `${path}/${idUser}/user-routine/${idUserRoutine}/user-experience/${idUserExperience}/coach-tip`,
            data: coachTip
        });
    }


})
