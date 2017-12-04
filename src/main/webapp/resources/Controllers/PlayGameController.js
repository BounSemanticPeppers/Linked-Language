/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
app.controller('GameController', ['$scope','$http','BaseAPI',function($scope,$http,BaseAPI){
    

    function initialize(){
        $("#txtFeedWord").attr("disabled", "disabled");
        $("#btnSubmit").attr("disabled", "disabled");

        BaseAPI.callServlet('getRandomWord',{}).then(function(response){
            $scope.askedWord = response;
            $scope.remainingTime = 20;
            initializeClock();
            $("#txtFeedWord").removeAttr("disabled");
            $("#btnSubmit").removeAttr("disabled");

        });
        
        $scope.askedWord = "";
        $scope.wordList = [];
        $scope.feedWord = "";
        $("#txtFeedWord").focus();
    }
    
    initialize();

    function initializeClock(){

        var timeinterval = setInterval(function(){
            $scope.remainingTime = $scope.remainingTime -1;
            $("#spnRemaining").text( $scope.remainingTime );
            if($scope.remainingTime <= 0){
                clearInterval(timeinterval);
                $("#txtFeedWord").attr("disabled", "disabled");
                $("#btnSubmit").attr("disabled", "disabled");
                BaseAPI.callServlet('finishGameForUser',{}).then(function(response){
                    console.log(response);
                    if(response.finished == true){
                        window.location.href = "/LinkedLanguage/gameEnd";
                    }
                });

            }
        },1000);
    }


    $scope.submitNewWord = function(){

        if($scope.feedWord == ""){return};
        $scope.wordList.push($scope.feedWord);

        // control existence of word and save it to db
        BaseAPI.callServlet('saveFeedWord',{feedWord: $scope.feedWord}).then(function(response){
            console.log(response);
        });

        // make feedWord as empty
        $scope.feedWord = "";

    }
    
}]);

