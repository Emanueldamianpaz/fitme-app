fitme.controller('routinesController', function ($rootScope, $scope) {

    $rootScope.stateCurrent = "routines";

    $scope.renderAverageScoring = function () {
        new Chart(document.getElementById('averageScoring'), {
            type: 'line',
            data: {
                labels: ["January", "February"],
                datasets: [{
                    label: 'Dataset 1',
                    borderColor: 'rgb(54, 162, 235)',
                    borderWidth: 2,
                    fill: false,
                    data: [2, 10]
                }]
            },
            options: {
                responsive: true,
                title: {
                    display: true,
                    text: 'Chart.js Draw Line On Chart'
                },
                tooltips: {
                    mode: 'index',
                    intersect: true
                },
                annotation: {
                    annotations: [{
                        type: 'line',
                        mode: 'horizontal',
                        scaleID: 'y-axis-0',
                        value: 5,
                        borderColor: 'rgb(75, 192, 192)',
                        borderWidth: 4,
                        label: {
                            enabled: false,
                            content: 'Test label'
                        }
                    }]
                }
            }
        });
    };

    $scope.renderAllScoring = function () {
        new Chart(document.getElementById('allScoring'), {
            data: data = {
                datasets: [{
                    data: [10, 20, 30],
                    backgroundColor: ["#cd3131", "#cbba35", "#456cd9"]
                }],
                labels: [
                    'Red',
                    'Yellow',
                    'Blue'
                ]
            },
            type: 'polarArea',
            options: {
                responsive: true,
                title: {
                    display: true,
                    text: 'Puntuaciones de los usuarios'
                }
            }
        });
    };


})

