function addMarker(map, position){
  // Create a marker and set its position.
  var marker = new google.maps.Marker({
    map: map,
    position: position,
    title: 'Hello World!'
  });
}