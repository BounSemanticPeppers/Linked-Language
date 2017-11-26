/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

app.controller('HomePageController', ['$scope','$http','BaseAPI',function($scope,$http,BaseAPI){
    
    $scope.userName = "";
    function initialize(){
        BaseAPI.callServlet('getUserName',{userId : 5}).then(function(response){
            $scope.userName = response;
        });
    }
    
    initialize();
}]);
