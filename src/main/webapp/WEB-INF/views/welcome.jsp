<%@ include file="header.jsp" %>
<script src="${contextPath}/resources/Controllers/HomePageController.js"></script>

<div class="row" ng-app="myApp" ng-controller="HomePageController" >


    <div class="row">

        <div class="row">

            <% if(userType == 1){  // gamer %>

            <div class="row">

                <div class="col-sm-6">

                    <table class="table table-hover table-striped text-left">
                        <thead>
                        <tr>
                            <th colspan="3"><h3><p class="text-warning">Total Score:  <b>{{ totalScore }} </b></p></h3></th>
                        </tr>
                        <tr>
                            <th colspan="3"><h3><p class="text-info">Previous Games:</p></h3></th>
                        </tr>
                        <tr class="info">
                            <th>
                                Word
                            </th>
                            <th>
                                Date
                            </th>
                            <th>
                                Score
                            </th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr ng-repeat="game in gameList">

                            <td>
                                <a href="${contextPath}/gameEnd?gameId={{game.gameId}}"> <b> {{game.askedWord}} </b> <a
                            </td>
                            <td>
                                <a href="${contextPath}/gameEnd?gameId={{game.gameId}}"> {{game.createdDate}} </a>
                            </td>
                            <td>
                                <a ng-if="game.complete == 0" href="${contextPath}/gameEnd?gameId={{game.gameId}}"> Waiting </a>

                                <a ng-if="game.complete == 1" href="${contextPath}/gameEnd?gameId={{game.gameId}}"> {{game.score}} </a>
                            </td>

                        </tr>
                        </tbody>
                    </table>

                </div>
                <div class="col-sm-4">
                    <a class="btn btn-lg center btn-success" type="button" href="${contextPath}/playGame" >
                        Start a Game
                    </a>
                </div>
                <div class="col-sm-2" >


                </div>
            </div>
            <% }else{ %>

            <div class="row">
                <div class="col-sm-3"></div>
                <div class="col-sm-6">
                    <form class="form" ng-submit="search()">
                        <div class="input-group">
                            <input class="form-control" placeholder="Search" ng-model="searchText" name="srch-term" id="srch-term" type="text">
                            <div class="input-group-btn">
                                <button class="btn btn-default" type="submit"><i class="glyphicon glyphicon-search"></i></button>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="col-sm-3"></div>
            </div>

            <% } %>
        </div>
    </div>

</div>
<%@ include file="footer.jsp" %>