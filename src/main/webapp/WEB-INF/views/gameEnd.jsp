<%--
  Created by IntelliJ IDEA.
  User: onurm
  Date: 12/4/2017
  Time: 10:46 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ include file="header.jsp" %>
<script src="${contextPath}/resources/Controllers/EndGameController.js"></script>
<div class="row text-center" ng-app="myApp" ng-controller="EndGameController" >
    <div class="row" >
        <h4>
            <p class="text-danger" ng-if="waitingWordList.length > 0" >
                Waiting for another user
            </p>
            <p class="text-success" ng-if="waitingWordList.length == 0">
                Total Score is: <b> {{game.score}} </b>
            </p>
            <p class="text-success">
                Asked Word: <b> {{game.askedWord}} </b>
            </p>
        </h4>
    </div>
    <div class="row">
        <div class="col-sm-6">
            <div class="row">
                <h2><p class="text-info"> Your Responses </p></h2>
            </div>
            <div class="row" ng-if="matchWordList.length > 0">
                <p ng-repeat="matchWord in matchWordList" class="text-success">
                    {{matchWord.text}}
                </p>
            </div>
            <div class="row" ng-if="firstUserUnmatchedList.length > 0">
                <p ng-repeat="unmatchWord in firstUserUnmatchedList" class="text-warning">
                    {{unmatchWord.text}}
                </p>
            </div>
            <div class="row" ng-if="waitingWordList.length > 0">
                <p ng-repeat="waitingWord in waitingWordList" class="text-primary">
                    {{waitingWord.text}}
                </p>
            </div>

        </div>
        <div class="col-sm-6">
            <div class="row">
                <h2> <p class="text-info"> Buddy Responses</p> </h2>
            </div>
            <div class="row" ng-if="matchWordList.length > 0">
                <p ng-repeat="matchWord in matchWordList" class="text-success">
                    {{matchWord.text}}
                </p>
            </div>
            <div class="row" ng-if="secondUserUnmatchedList.length > 0">
                <p ng-repeat="unmatchWord in secondUserUnmatchedList" class="text-warning">
                    {{unmatchWord.text}}
                </p>
            </div>
        </div>
    </div>
</div>

<%@ include file="footer.jsp" %>
