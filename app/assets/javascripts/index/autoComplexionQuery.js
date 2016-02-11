




function autoComplete(idInputToAutoComplete, idUlAutocomplete){

    var inputToAutoComplete = $('#' + idInputToAutoComplete);
    var placeholder = inputToAutoComplete.attr("placeholder"); // save the placeholder
    var ulAutocomplete = $('#' + idUlAutocomplete);
    var selectedLiIdx = -1;
    var firstArrow = true;
    var classLiSelected = "selectedAutocompleteLi";


    inputToAutoComplete
      .click(function(){
        $(this).attr("placeholder", "");
      })
      .focusout(function(){
        $(this).attr("placeholder", placeholder);
         ulAutocomplete.css('display', "none");
         selectedLiIdx = -1;
      })
      .bind('keyup', function(e){
        var li = ulAutocomplete.children();
        var keyCode = e.keyCode || e.which;


        if(keyCode == 40){ // down
            li.eq(selectedLiIdx).removeClass(classLiSelected);
            selectedLiIdx++;
            selectedLiIdx = selectedLiIdx % li.length;
            li.eq(selectedLiIdx).addClass(classLiSelected);
        }
        else if(keyCode == 38){ // up
            li.eq(selectedLiIdx).removeClass(classLiSelected);
            if(!firstArrow)  selectedLiIdx--;
            else firstArrow = false;
            selectedLiIdx = selectedLiIdx % li.length;
            li.eq(selectedLiIdx).addClass(classLiSelected);
        }
        else if(keyCode == 13){ // enter
            if(selectedLiIdx != -1) $(this).val(li.eq(selectedLiIdx).text());
            ulAutocomplete.css('display', "none");
        }
        else{
            // set the url for get the recommendation
            var query = $(this).val().toLowerCase();
            var near = "annecy";
            var url = "/autoCompletion?near="+ near + "&query=" + query;

            if(!query){
                ulAutocomplete.empty();
            }
            else{
                // get the recommendation
                $.get(url, function(data, status){
                    var jsonData = JSON.parse(data);
                    if(status == "success"){
                        // set clear the list of recommendation
                        ulAutocomplete.empty();

                        //display the recommendation
                        for(var i = 0; i < data.length; i++){
                            var text = jsonData[i].name.toLowerCase().split(query);
                            ulAutocomplete.append("<li>" + text.join("<span>" + query + "</span>") + "</li>");
                        }
                    }
                });
                ulAutocomplete.css('display', "block");
            }
        }
      });
}

autoComplete("queryInput", "queryAutocomplete");
autoComplete("nearInput", "nearAutocomplete");

// disable form enter default event
$('#formSearch').on('keyup keypress', function(e) {
  var keyCode = e.keyCode || e.which;
  if (keyCode === 13) {
    e.preventDefault();
    return false;
  }
});












