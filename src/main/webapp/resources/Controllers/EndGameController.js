/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
app.controller('EndGameController', ['$scope','$http','BaseAPI',function($scope,$http,BaseAPI){

    function getGameId(){
        var url_string = window.location.href;
        var url = new URL(url_string);
        var gameId = url.searchParams.get("gameId");
        return gameId;
    }

    function initialize(){
        $scope.matchWordList = [];
        $scope.firstUserUnmatchedList = [];
        $scope.secondUserUnmatchedList = [];
        $scope.waitingWordList = [];
        $scope.game = {};

        var gameId = getGameId();

        BaseAPI.callServlet('getGameDetails',{gameId : gameId}).then(function(response) {
            $scope.game = response;
        });


        BaseAPI.callServlet('getWaitingWordsOfGame',{gameId : gameId}).then(function(response){
            if(response.length > 0 ){ // if there are waiting words
                $scope.waitingWordList = response;
            }else{
                BaseAPI.callServlet('getMatchedWordsOfGame', {gameId : gameId}).then(function (response) {
                    $scope.matchWordList = response;
                    console.log(response);
                });
                BaseAPI.callServlet('getUnMatchedWordsOfSessionUser', {gameId : gameId}).then(function (response) {
                    $scope.firstUserUnmatchedList = response;
                    console.log(response);
                });
                BaseAPI.callServlet('getUnMatchedWordsOfCompetitor', {gameId : gameId}).then(function (response) {
                    $scope.secondUserUnmatchedList = response;
                    console.log(response);
                });
            }
        });

    }

    initialize();


}]);

