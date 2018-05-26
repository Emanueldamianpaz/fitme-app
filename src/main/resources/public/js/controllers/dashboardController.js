fitme.controller('dashboardController', function ($rootScope){

    $rootScope.stateCurrent = "dashboard";

    new Chart(document.getElementById("registeredUsers"), {
        type: 'bar',
        data: {
            labels: ["18-20 años", "20-22 años", "22-24 años", "24-26 años", ">24 años"],
            datasets: [{
                label: 'Usuarios',
                data: [12, 19, 3, 5, 2],
                backgroundColor: [
                    'rgba(255, 99, 132, 0.2)',
                    'rgba(54, 162, 235, 0.2)',
                    'rgba(255, 206, 86, 0.2)',
                    'rgba(75, 192, 192, 0.2)',
                    'rgba(153, 102, 255, 0.2)'
                ],
                borderColor: [
                    'rgba(255,99,132,1)',
                    'rgba(54, 162, 235, 1)',
                    'rgba(255, 206, 86, 1)',
                    'rgba(75, 192, 192, 1)',
                    'rgba(153, 102, 255, 1)'
                ],
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero:true
                    }
                }]
            }
        }
    });

    new Chart(document.getElementById("genreUsers"), {
        type: 'pie',
        data: {
          labels: ["Hombres", "Mujeres"],
          datasets: [
            {
              backgroundColor: ["#3e95cd", "#8e5ea2","#3cba9f","#e8c3b9","#c45850"],
              data: [2478,5267]
            }
          ]
        },
    });
})

