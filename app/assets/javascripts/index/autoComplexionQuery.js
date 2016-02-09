




function autoComplete(idInputToAutoComplete, idUlAutocomplete){

    var placeholder;
    var ulAutocomplete = $('#' + idUlAutocomplete);
    var li = ulAutocomplete.children();
    var selectedLiIdx = -1;
    var firstArrow = true;
    var classLiSelected = "selectedAutocompleteLi";

    $('#' + idInputToAutoComplete)
      .click(function(){
        placeholder = $(this).attr("placeholder");
        $(this).attr("placeholder", "");
      })
      .focusout(function(){
        $(this).attr("placeholder", placeholder);
         ulAutocomplete.css('display', "none");
      })
      .bind('keydown', function(e){
        if(e.which == 40){ // down
            li.eq(selectedLiIdx).removeClass(classLiSelected);
            selectedLiIdx++;
            selectedLiIdx = selectedLiIdx % li.length;
            li.eq(selectedLiIdx).addClass(classLiSelected);
        }
        else if(e.which == 38){ // up
            li.eq(selectedLiIdx).removeClass(classLiSelected);
            if(!firstArrow)  selectedLiIdx--;
            else firstArrow = false;
            selectedLiIdx = selectedLiIdx % li.length;
            li.eq(selectedLiIdx).addClass(classLiSelected);
        }
        else if(e.which == 13){ // enter
            $(this).val(li.eq(selectedLiIdx).text());
            ulAutocomplete.css('display', "none");
            return false;
        }
        else{ // TODO
            ulAutocomplete.css('display', "block");
        }
      });

}

autoComplete("queryInput", "queryAutocomplete");
autoComplete("nearInput", "nearAutocomplete");



