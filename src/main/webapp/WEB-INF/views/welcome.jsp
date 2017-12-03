<%@ include file="header.jsp" %>
<script src="${contextPath}/resources/Controllers/HomePageController.js"></script>

<div class="row" ng-app="myApp" ng-controller="HomePageController" >
</div>

<div class="row text-center">
    <% if(userType == 1){  // gamer %>
    <a class="btn btn-lg center btn-success" type="button" href="${contextPath}/playGame" > 
        Start the Game
    </a>
    <% } %>
</div>

<%@ include file="footer.jsp" %>