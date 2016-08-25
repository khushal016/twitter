myApp.controller('mainCtrl', function () {
    var mymap = L.map('mapid').setView([28.533699, 77.266830], 8);
    L.tileLayer('https://api.mapbox.com/styles/v1/khushal016/cirrke39p0010g5nfdtqztzrz/tiles/256/{z}/{x}/{y}?access_token=pk.eyJ1Ijoia2h1c2hhbDAxNiIsImEiOiJjaXJyazZwNnowaWd3ZmdtODJuMDd3c2NkIn0.T9SyMHVTUH0mP6onKB_Xvg', {
        attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, <a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="http://mapbox.com">Mapbox</a>',
        maxZoom: 18,
        id: 'your.mapbox.project.id',
        accessToken: 'pk.eyJ1Ijoia2h1c2hhbDAxNiIsImEiOiJjaXJyazZwNnowaWd3ZmdtODJuMDd3c2NkIn0.T9SyMHVTUH0mP6onKB_Xvg'
    }).addTo(mymap);
    var marker = L.marker([28.5554247, 77.2652743]).addTo(mymap);
    marker.bindPopup("<b>Work!!</b><br>FarEye").openPopup();
    var popup = L.popup();
    var markers = new Array();
    var latlngs = new Array();


    function onMapClick(e) {

        //if (latlngs.length > 2) {
        //
        //    latlngs.splice(i, 1);
        //
        //}
        //popup
        //    .setLatLng(e.latlng)
        //    .setContent("You clicked the map at " + e.latlng.toString())
        //    .openOn(mymap);


        //i++;
        latlngs.push(e.latlng);
        if (latlngs.length > 1)
            var polyline = new L.polyline(latlngs, {color: 'red'}).addTo(mymap);

         marker2 = new L.marker(e.latlng).addTo(mymap);
         popup = L.popup().setContent("you clicked at:" + e.latlng.toString())
        //var popup.marker = marker2
        marker2.bindPopup(popup)


        markers.push(marker2)

    }

    mymap.on('click', onMapClick);


    mymap.on('dblclick', function (event) {
        console.log(event.latlng);
        //var marker = event.popup.marker;
        //mymap.removeLayer(marker2)
        console.log(markers[0].getLatLng())
        console.log(markers[1].getLatLng())
        for (var i = 0; i < markers.length; i++) {

            if (markers[i].getLatLng()===event.latlng) {
                mymap.removeLayer(markers[i])
            }
        }
    });
    var geojsonFeature = {
        "type": "Feature",
        "properties": {
            "name": "Coors Field",
            "amenity": "Baseball Stadium",
            "popupContent": "This is where the Rockies play!"
        },
        "geometry": {
            "type": "Point",
            "coordinates": [77.25809, 28.55657]
        }
    };
    L.geoJson(geojsonFeature).addTo(mymap);

    L.Routing.control({
        waypoints: [
            L.latLng(28.64754, 77.12879),
            L.latLng(28.5554247, 77.2652743)
        ]
    }).addTo(mymap);


});


myApp.controller("signup", function ($scope, $http, $state) {


    $scope.crt = function () {
        console.log("test");
        var email = $scope.email;
        var name = $scope.name;
        var pass = $scope.password;
        var pass2 = $scope.p2;
        var bio = $scope.bio;
        if (pass != pass2) {
            $scope.error = "Please enter same password";
        }
        else {
            var data = {"email": email, "name": name, "password": pass, "bio": bio};

            $http({
                url: "http://localhost:8080/createUser",
                dataType: 'json',
                method: 'POST',
                data: data,
                headers: {
                    "Content-Type": "application/json"
                }
            }).success(function (response) {
                console.log(response);
                $scope.error = "";
                $scope.name1 = response.msg;
                $scope.reset();


            }).error(function (response) {
                $scope.name1 = "";
                $scope.error = response.msg;
            })
        }
    };
    $scope.reset = function () {
        $scope.name = "";
        $scope.email = "";
        $scope.password = "";
        $scope.p2 = "";
        $scope.bio = "";
    };

});
myApp.controller("tweet", function ($scope, $http, dataService) {
    $scope.display = function () {
        dataService.getTweets()
            .then(function (response) {
                $scope.table = response;
            }, function (error) {
                console.log(error)
                alert("unable to load customer data: " + error.data.msg);
            });

    }
    $scope.send = function () {
        var msg = $scope.msg;
        var date = new Date();
        var data = {"msg": msg, "date": date};

        dataService.sendTweets(data)
            .then(function (response) {
                console.log(response.data)
                $scope.name1 = response.data.msg
                $scope.msg = "";
                $scope.display()
            }, function (error) {
                console.log(error.data)
                $scope.name1 = error.data.msg
            });

    }

})

myApp.controller("follow", function ($scope, $http) {
    var url = "http://localhost:8080/displayUsers";
    $http.get(url).success(function (response) {
        $scope.values = response;

    });

    $scope.foll = function () {
        var name = $scope.follow;
        //var url = "http://localhost:8080/follow";
        var data = {"fname": name};
        $http({
            url: "http://localhost:8080/follow",
            dataType: 'json',
            method: 'POST',
            data: data,
            headers: {
                "Content-Type": "application/json"
            }
        }).success(function (response) {
            console.log(response);
            $scope.error = "";

            $scope.name1 = response.msg;
        }).error(function (response) {
            console.log(response);
            $scope.name1 = "";

            $scope.error = response.msg;
        });

    }
})

myApp.controller("profile", function ($scope, $http) {
    $scope.create = function () {
        var bio = $scope.bio;
        var url = "http://localhost:8080/bio?msg=" + bio;
        console.log(bio)
        $http.get(url).success(function (response) {
            $scope.namebio = response.msg;
            $scope.biotext = bio;
            $scope.bio = "";

        }).error(function (response) {
            $scope.name1 = response.msg;

        })
    }
    $scope.displayProfile = function () {
        var url = "http://localhost:8080/displayProfile";
        $http.get(url).success(function (response) {
            $scope.fcount1 = response.following;
            $scope.fcount2 = response.followers;
            $scope.biotext = response.bio;
            $scope.tcount = response.count;

        });

    }

})
myApp.controller("following", function ($scope, $http, $state) {
    $scope.display = function () {
        var url = "http://localhost:8080/displayFollowing";
        $http.get(url).success(function (response) {
            console.log(response);
            $scope.table = response;
        }).error(function (response) {
            console.log(response);
            $scope.error = response.msg;
        });
    }
    $scope.displayf = function () {
        var url = "http://localhost:8080/displayFollowers";
        $http.get(url).success(function (response) {
            console.log(response);
            $scope.table = response;
        }).error(function (response) {
            console.log(response);
            $scope.error = response.msg;

        });
    }
    $scope.unfollow = function (name) {
        var url = "http://localhost:8080/unfollow?name=" + name;
        $http.get(url).success(function (response) {
            console.log(response);
            $scope.displayProfile();
            //$state.go("profile.following")
            //window.location.reload()
            $state.reload("profile.following")
            $scope.name1 = response.msg;


        })

    }
    $scope.count = function () {
        var url = "http://localhost:8080/count";
        $http.get(url).success(function (response) {
            console.log(response);
            $scope.table = response;
        }).error(function (response) {
            console.log(response);
            $scope.error = response.msg;
        });
    }

})

