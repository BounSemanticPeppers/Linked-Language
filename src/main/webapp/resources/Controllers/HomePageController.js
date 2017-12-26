/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

app.controller('HomePageController', ['$scope','$http','BaseAPI',function($scope,$http,BaseAPI){

    function initialize(){
        $scope.totalScore = "";
        $scope.gameList = [];

        BaseAPI.callServlet('getUserScore',{}).then(function(response){
            $scope.totalScore = response;
        });

        BaseAPI.callServlet('getUserGames',{}).then(function(response){
            $scope.gameList = response;
            console.log(response);
        });

        $scope.searchText = "";
    }
    
    initialize();

    $scope.search = function () {
        console.log($scope.searchText);

        if($scope.searchText != ""){
            BaseAPI.callServlet('searchWord',{searchWord: $scope.searchText}).then(function(response){
                if(typeof response == "string") {
                    bootbox.alert("Word Not Found");
                }else{
                    window.location.href = "/LinkedLanguage/searchResult?search="+$scope.searchText;
                }
            });

        }
    }
}]);
