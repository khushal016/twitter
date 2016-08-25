angular.module('myApp')
    .service('dataService', ['$http', function ($http) {

        var urlBase = "http://localhost:8080/displayTweet";

        this.getTweets = function () {
            return $http.get(urlBase);
        };

        this.sendTweets = function (data) {
            return $http({
                url: "http://localhost:8080/createTweet",
                dataType: 'json',
                method: 'POST',
                data: data,
                headers: {
                    "Content-Type": "application/json"
                }
            })
        }

    }]);

