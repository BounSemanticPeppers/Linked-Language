<%--
  Created by IntelliJ IDEA.
  User: onurm
  Date: 12/25/2017
  Time: 7:59 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="header.jsp" %>
<script src="https://cdnjs.cloudflare.com/ajax/libs/vis/4.18.1/vis.min.js"></script>
<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/vis/4.18.1/vis.min.css">
<!-- bu script font için script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.3.2/jspdf.debug.js"></script-->
<script src="${contextPath}/resources/Controllers/SearchPageController.js"></script>

<style type="text/css">
    #mynetwork {
        width: 100%;
        height: 400px;
    }
</style>
<div class="row" ng-app="myApp" ng-controller="SearchPageController" >

    <div class="progress ng-hide" style="position:fixed; top:30%; left: 25%; width: 50%; position:center; " ng-show="showProgressBar">
        <div class="progress-bar progress-bar-striped active" role="progressbar" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100" style="width: 100%;">
            Please Wait
        </div>
    </div>


    <div class="row">
        <div class="col-sm-4">
            <div class="col-xs-12" ng-show="showWikidataResults">
                <div class="progress ng-hide" style="position:absolute; top:50%; left: 5%; width: 90%; position:center; " ng-show="showWikiDataProgressBar">
                    <div class="progress-bar progress-bar-striped progress-bar-warning active" role="progressbar" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100" style="width: 100%;">
                        Please Wait
                    </div>
                </div>
                <div ng-show="!showWikiDataProgressBar" class="row">
                    <p class="text-info">
                        <b>Word:</b>
                        {{chosenWord}}
                    </p>
                    <p class="text-success">
                        <b>Description:</b>
                        {{ chosenWordDesc }}
                    </p>
                    <p class="text-info">
                        <b>Image:</b>
                    </p>
                    <div class="col-xs-12" ng-show="showResultImage">
                        <img ng-src="{{imageUrl}}">
                    </div>


                </div>
            </div>
        </div>
        <div class="col-sm-8">
            <div id="mynetwork"></div>
        </div>
    </div>
</div>

<%@ include file="footer.jsp" %>
