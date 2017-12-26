/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

app.controller('SearchPageController', ['$scope','$http','BaseAPI',function($scope,$http,BaseAPI){

    function getSearchText(){
        var url_string = window.location.href;
        var url = new URL(url_string);
        var searchText = url.searchParams.get("search");
        return searchText;
    }

    function initialize(){
        //alert(getSearchText());
        $scope.searchText = getSearchText();
        $scope.showProgressBar = true;
        $scope.showWikidataResults = false;
        $scope.chosenWordDesc = "";
        $scope.chosenWord = "";
        $scope.imageUrl = "";
        BaseAPI.callServlet('getRelatedWords', {searchWord : $scope.searchText}).then(function (response) {
            if(typeof response == "string") {
                bootbox.alert("Word Not Found", function () {
                    window.location.href = "/LinkedLanguage/welcome";
                });

            }else{
                var nodes = response.nodes;
                var edges = response.edges;
                console.log(response);
                $scope.drawGraph(nodes,edges);
                $scope.showProgressBar = false;
                $("#mynetwork").css("border","1px solid lightgray");
            }

        });

    }

    function getWikiDataInfo(currentWordId){
        $scope.showWikidataResults = true;
        $scope.showWikiDataProgressBar = true;
        BaseAPI.callServlet('getWikiDataOfWord', {wordId : currentWordId}).then(function (response) {
            if(typeof response == "string") {
                bootbox.alert("Search Not Found");
                $scope.showWikidataResults = false;
            }else{
                $scope.showWikidataResults = true;
                if(response.descriptions.en){
                    $scope.chosenWordDesc = response.descriptions.en.text;
                }else{
                    $scope.chosenWordDesc = "";
                }

                if(response.labels.en){
                    $scope.chosenWord = response.labels.en.text;
                }else{
                    $scope.chosenWord = "";
                }
                $scope.imageUrl = "";
                if(response.claims.P18){
                    $scope.imageUrl ="https://commons.wikimedia.org/w/thumb.php?width=300&f="+response.claims.P18[0].mainsnak.datavalue.value;
                }else{
                    $scope.imageUrl = "";
                }
                console.log(response);
            }
            $scope.showWikiDataProgressBar = false;
        });
    }

    $scope.drawGraph = function(nodes, edges){

        var nodesDataset  = new vis.DataSet(nodes);

        // create an array with edges
        var edgesDataset = new vis.DataSet(edges);
        // create a network
        var container = document.getElementById('mynetwork');


        function redrawAll() {
            var container = document.getElementById('mynetwork');
            var options = {
                nodes: {
                    shape: 'dot',
                    scaling: {
                        min: 10,
                        max: 30,
                        label: {
                            min: 8,
                            max: 30,
                            drawThreshold: 12,
                            maxVisible: 20
                        }
                    },
                    font: {
                        size: 12,
                        face: 'Tahoma'
                    }
                },
                edges: {
                    width: 0.15,
                    color: {inherit: 'from'},
                    smooth: {
                        type: 'continuous'
                    }
                },
                physics: false,
                interaction: {
                    tooltipDelay: 200,
                    hideEdgesOnDrag: true,
                    hover: true
                }
            };
            var data = {nodes: nodesDataset, edges: edgesDataset} // Note: data is coming from ./datasources/WorldCup2014.js


            network = new vis.Network(container, data, options);

            // get a JSON object
            allNodes = nodesDataset.get({returnType: "Object"});

            network.on("click", neighbourhoodHighlight);
        }

        function neighbourhoodHighlight(params) {

            if(params.nodes.length > 0){
                var currentWordId = params.nodes[0];
                getWikiDataInfo(currentWordId);
            }

            // if something is selected:
            if (params.nodes.length > 0) {
                highlightActive = true;
                var i,j;
                var selectedNode = params.nodes[0];
                var degrees = 2;

                // mark all nodes as hard to read.
                for (var nodeId in allNodes) {
                    //allNodes[nodeId].color = 'rgba(200,200,200,0.5)';
                    if (allNodes[nodeId].hiddenLabel === undefined) {
                        allNodes[nodeId].hiddenLabel = allNodes[nodeId].label;
                        //allNodes[nodeId].label = undefined;
                    }
                }
                var connectedNodes = network.getConnectedNodes(selectedNode);
                var allConnectedNodes = [];

                // get the second degree nodes
                for (i = 1; i < degrees; i++) {
                    for (j = 0; j < connectedNodes.length; j++) {
                        allConnectedNodes = allConnectedNodes.concat(network.getConnectedNodes(connectedNodes[j]));
                    }
                }

                // all second degree nodes get a different color and their label back
                for (i = 0; i < allConnectedNodes.length; i++) {
                    //allNodes[allConnectedNodes[i]].color = 'rgba(150,150,150,0.75)';
                    if (allNodes[allConnectedNodes[i]].hiddenLabel !== undefined) {
                        allNodes[allConnectedNodes[i]].label = allNodes[allConnectedNodes[i]].hiddenLabel;
                        allNodes[allConnectedNodes[i]].hiddenLabel = undefined;
                    }
                }

                // all first degree nodes get their own color and their label back
                for (i = 0; i < connectedNodes.length; i++) {
                    //allNodes[connectedNodes[i]].color = undefined;
                    if (allNodes[connectedNodes[i]].hiddenLabel !== undefined) {
                        allNodes[connectedNodes[i]].label = allNodes[connectedNodes[i]].hiddenLabel;
                        allNodes[connectedNodes[i]].hiddenLabel = undefined;
                    }
                }

                // the main node gets its own color and its label back.
                //allNodes[selectedNode].color = undefined;
                if (allNodes[selectedNode].hiddenLabel !== undefined) {
                    allNodes[selectedNode].label = allNodes[selectedNode].hiddenLabel;
                    allNodes[selectedNode].hiddenLabel = undefined;
                }
            }
            else if (highlightActive === true) {
                // reset all nodes
                for (var nodeId in allNodes) {
                    //allNodes[nodeId].color = undefined;
                    if (allNodes[nodeId].hiddenLabel !== undefined) {
                        allNodes[nodeId].label = allNodes[nodeId].hiddenLabel;
                        allNodes[nodeId].hiddenLabel = undefined;
                    }
                }
                highlightActive = false
            }

            // transform the object into an array
            var updateArray = [];
            for (nodeId in allNodes) {
                if (allNodes.hasOwnProperty(nodeId)) {
                    updateArray.push(allNodes[nodeId]);
                }
            }
            nodesDataset.update(updateArray);
        }

        redrawAll();


        /*
        var data = {
            nodes: nodes,
            edges: edges
        };
        var options = {};
        var network = new vis.Network(container, data, options); */
    }

    initialize();
}]);
