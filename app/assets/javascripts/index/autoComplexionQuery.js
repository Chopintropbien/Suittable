
function autoComplete(idInputToAutoComplete, idUlAutocomplete, nbMaxRecommendation){

    var inputToAutoComplete = $('#' + idInputToAutoComplete);
    var placeholder = inputToAutoComplete.attr("placeholder"); // save the placeholder
    var ulAutocomplete = $('#' + idUlAutocomplete);
    var selectedLiIdx = -1;
    var firstArrow = true;
    var classLiSelected = "selectedAutocompleteLi";
    var classLiUnique  = idInputToAutoComplete + "Li";
    var nbLiDisplayed = nbMaxRecommendation;


    // add undisplayed li and event to the li
//    addEmptyLiAndEvent(inputToAutoComplete, ulAutocomplete, nbMaxRecommendation, classLiSelected, classLiUnique);


// add li
    for(var i = 0; i < nbMaxRecommendation; ++i){
        ulAutocomplete.append("<li class=\""+classLiUnique+"\" id=\""+classLiUnique + i +"\" style=\"display: none;\"></li>");
    }

    var li = ulAutocomplete.children();

    $('.' + classLiUnique)
        .bind('click', function() {
            $(inputToAutoComplete).val($(this).text());
            ulAutocomplete.css('display', "none");
            selectedLiIdx = -1;
        })
        .bind('mouseenter', function(){
            li.eq(selectedLiIdx).removeClass(classLiSelected);
            selectedLiIdx = $(this).index();
            li.eq(selectedLiIdx).addClass(classLiSelected);
        })
        .bind('mouseout', function(){
            li.eq(selectedLiIdx).removeClass(classLiSelected);
            selectedLiIdx = -1;
        });

// action on the input
    inputToAutoComplete
      .click(function(){
        $(this).attr("placeholder", "");
      })
      .focusout(function(){
//        $(this).attr("placeholder", placeholder);
//         ulAutocomplete.css('display', "none");
         selectedLiIdx = -1;
      })
      .bind('keyup', function(e){
        var keyCode = e.keyCode || e.which;


        if(keyCode == 40){ // down
            li.eq(selectedLiIdx).removeClass(classLiSelected);
            selectedLiIdx++;
            selectedLiIdx = selectedLiIdx % nbLiDisplayed;
            li.eq(selectedLiIdx).addClass(classLiSelected);
        }
        else if(keyCode == 38){ // up
            li.eq(selectedLiIdx).removeClass(classLiSelected);
            if(!firstArrow)  selectedLiIdx--;
            else firstArrow = false;
            selectedLiIdx = (selectedLiIdx + nbLiDisplayed) % nbLiDisplayed;
            li.eq(selectedLiIdx).addClass(classLiSelected);
        }
        else if(keyCode == 13){ // enter
            li.eq(selectedLiIdx).removeClass(classLiSelected);
            if(selectedLiIdx != -1) $(this).val(li.eq(selectedLiIdx).text());
            ulAutocomplete.css('display', "none");
            selectedLiIdx = -1;
        }
        else{
            li.eq(selectedLiIdx).removeClass(classLiSelected);
            selectedLiIdx = -1;

            // set the url for get the recommendation
            var query = $(this).val().toLowerCase();
            var near = "annecy";
            var url = "/autoCompletion?near="+ near + "&query=" + query;

            if(!query){
                ulAutocomplete.hide();
            }
            else{
                ulAutocomplete.show();

                // get the recommendation
                $.get(url, function(data, status){
                    var jsonData = JSON.parse(data);
                    if(status == "success"){
                        //display the recommendation
                        for(var i = 0; i < nbMaxRecommendation; i++){
                            if(i < jsonData.length){
                                var text = jsonData[i].name.toLowerCase().split(query);
                                $("#" + classLiUnique + i)
                                    .html(text.join("<span>" + query + "</span>"))
                                    .show();
                            }
                            else $("#" + classLiUnique + i).hide();
                        }
                        nbLiDisplayed = jsonData.length;
                    }
                });
                ulAutocomplete.css('display', "block");
            }
        }
      });



}

function addEmptyLiAndEvent(inputToAutoComplete, ulAutocomplete, nbMaxRecommendation, classLiSelected, classLiUnique){


}





autoComplete("queryInput", "queryAutocomplete", 10);
autoComplete("nearInput", "nearAutocomplete", 10);

// disable form enter default event
$('#formSearch').on('keyup keypress', function(e) {
  var keyCode = e.keyCode || e.which;
  if (keyCode === 13) {
    e.preventDefault();
    return false;
  }
});












