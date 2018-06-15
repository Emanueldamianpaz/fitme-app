// Default colors
var brandPrimary = '#20a8d8';
var brandSuccess = '#4dbd74';
var brandInfo = '#63c2de';
var brandWarning = '#f8cb00';
var brandDanger = '#f86c6b';

var grayDark = '#2a2c36';
var gray = '#55595c';
var grayLight = '#818a91';
var grayLighter = '#d1d4d7';
var grayLightest = '#f8f9fa';

var required = ['ui.router',
    'angular-loading-bar',
    'pascalprecht.translate',
    'ngCookies',
];

var fitme = angular.module('fitme', required);
var api = '/fitme/api';

fitme.config(function ($translateProvider) {

    $translateProvider.useStaticFilesLoader({
        files: [{
            prefix: 'i18n/html-',
            suffix: '.json'
        }]

    });
    $translateProvider.preferredLanguage('es');
    $translateProvider.useLocalStorage();
    $translateProvider.useSanitizeValueStrategy('escape');
})


fitme.factory('MessageNotification', function () {
    var showMessage = function (containerId, htmlContent) {
        var container = $(containerId);
        container.html(htmlContent);
        container.show();
        setTimeout(function () {
            container.hide();
        }, 10000);
    }

    var showErrorMessage = function (message) {
        showMessage("#dangerMsg", "<strong>Ooops!</strong> " + message);
    }
    var showSuccessMessage = function (message) {
        showMessage("#successMsg", "<strong>Great!</strong> " + message);
    }

    return {
        showMessage: showSuccessMessage,
        showError: showErrorMessage
    };
});