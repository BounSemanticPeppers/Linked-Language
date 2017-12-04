<%@ include file="header.jsp" %>
<script src="${contextPath}/resources/Controllers/HomePageController.js"></script>

<div class="row" ng-app="myApp" ng-controller="HomePageController" >


    <div class="row text-center">

        <div class="row">
            <h3><p class="text-warning">Your total score is <b>{{ totalScore }} </b></p></h3>

        </div>
        <div class="row">

            <% if(userType == 1){  // gamer %>
            <a class="btn btn-lg center btn-success" type="button" href="${contextPath}/playGame" >
                Start the Game
            </a>
            <% } %>
        </div>
    </div>

</div>
<%@ include file="footer.jsp" %>