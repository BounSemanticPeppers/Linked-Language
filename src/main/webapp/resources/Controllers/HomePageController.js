/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

app.controller('HomePageController', ['$scope','$http','BaseAPI',function($scope,$http,BaseAPI){
    
    $scope.totalScore = "";
    function initialize(){
        BaseAPI.callServlet('getUserScore',{}).then(function(response){
            $scope.totalScore = response;
        });
    }
    
    initialize();
}]);
