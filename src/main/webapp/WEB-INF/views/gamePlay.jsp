<%-- 
    Document   : gamePlay
    Created on : Dec 3, 2017, 7:08:02 PM
    Author     : onurm
--%>

<%@ include file="header.jsp" %>
<script src="${contextPath}/resources/Controllers/PlayGameController.js"></script>
<div class="row text-center" ng-app="myApp" ng-controller="GameController" >

    <style>
        #clockdiv > div{
            padding: 5px;
            border-radius: 3px;
            background: #00BF96;
            display: inline-block;
        }

        #clockdiv div h1 > span{
            padding: 10px;
            border-radius: 3px;
            background: #00816A;
            display: inline-block;
        }
    </style>

    <div class="progress ng-hide" style="position:fixed; top:30%; left: 25%; width: 50%; position:center; " ng-show="showProgressBar">
        <div class="progress-bar progress-bar-striped active" role="progressbar" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100" style="width: 100%;">
            Please Wait
        </div>
    </div>

    <div class="row" ng-show="!showProgressBar">

        <div class="row" id="clockdiv">
            <div>
                <h1><span id="spnRemaining">20</span></h1>
                <div>Seconds</div>
            </div>
        </div>

        <div>
            <h3><p class="text-warning"> enter words related to: </p></h3>
            <h2><p class="text-info">{{askedWord.text}}</p> </h2>
        </div>

        <div>
            <form class="form-inline" ng-submit="submitNewWord()">
                <div class="form-group">
                  <label for="txtFeedWord">New Word:</label>
                  <input class="form-control" ng-model="feedWord" id="txtFeedWord">
                </div>
                <button type="submit" id="btnSubmit" class="btn btn-default">Submit</button>
            </form>
        </div>
        <div>
            <h4 ng-repeat="curWord in wordList track by $index">
                <p class="text-success"> {{curWord}} </p>
            </h4>
        </div>
    </div>
</div>

<%@ include file="footer.jsp" %>
