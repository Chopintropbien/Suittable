
function displayCategory(idCategory){

  var ul = document.getElementById("ulMoreCategories" + idCategory);

  document.getElementById("displayMoreCategories" + idCategory).onclick = function(){
    if(ul.style.display == "block"){
      this.innerHTML = "+ more";
      ul.style.display = "none";
    }
    else{
      this.innerHTML = "- less";
      ul.style.display = "block";
    }

  };
}