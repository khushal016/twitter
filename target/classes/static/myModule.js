var myApp = angular.module('myApp', ['ui.router']);
myApp.config(function ($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise("/");
    $stateProvider

        .state('register', {
            url: "/register",
            templateUrl: 'register.html',
            controller: 'signup'
        })
        .state('login', {
            url: "/login",
            templateUrl: 'login.html',
        })
        .state('first', {
            url: "/home",
            //templateUrl: 'inventory.html'

            views: {
                "a": {templateUrl: 'first.html'}
            }

        })
        .state('map', {
            url: "/map",
            controller:"mainCtrl",
            //templateUrl: 'inventory.html'

            views: {
                "a": {templateUrl: 'map.html'}
            }

        })
        .state('follow', {
            url: "/follow",
            //templateUrl: 'inventory.html'
            controller:"follow",

            views: {
                "a": {templateUrl: 'foll.html'}
            }

        })
        .state('profile', {
            url: "/profile",
            //templateUrl: 'inventory.html'
            controller:"profile",

            views: {
                "a": {templateUrl: 'profile.html'}
            }

        })
        .state('profile.following', {
            url: "/displayFollowing",
            templateUrl: 'following.html',
            controller: 'following'
        })
        .state('profile.followers', {
            url: "/displayFollowers",
            templateUrl: 'followers.html',
            controller: 'following'
        })
        .state('profile.tweetCount', {
        url: "/tweetCount",
        templateUrl: 'tweetCount.html',
        controller: 'following'
    })

});