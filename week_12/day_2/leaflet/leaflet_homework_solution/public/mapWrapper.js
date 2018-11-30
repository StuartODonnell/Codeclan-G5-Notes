const MapWrapper = function(container, coords, zoom){
  const osmLayer = new L.TileLayer("http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png");

  this.map = L.map(container).setView(coords, zoom).addLayer(osmLayer);
  this.map.on("click", event => this.addMarker(event.latlng))
}

MapWrapper.prototype.moveMap = function (coords) {
  this.map.setView(coords);

  const jsonString = JSON.stringify(coords);
  localStorage.setItem('lastvisited', jsonString);
};

MapWrapper.prototype.addMarker = function (coords) {
  L.marker(coords).addTo(this.map).bindPopup(coords.toString()).on("click", function(event) {
    this.openPopup();
  });
};

MapWrapper.prototype.goSomewhereTerrible = function () {
  var terriblePlaces = [
      {lat: 55.662772564884335, lng: -93.44558715820312},
      {lat: 39.6267046, lng: -79.325172},
      {lat: 39.5321857, lng: -76.8257135},
      {lat: -50.6067956, lng: 165.968396},
      {lat: -37.3958069, lng: 146.1348347},
      {lat: 32.2686765, lng: -112.7475133},
      {lat: 54.8034801, lng: -1.5996617},
      {lat: 47.6590897, lng: -92.2067013},
      {lat: 56.6201814, lng:-3.945826},
      {lat: 48.9493989, lng: -116.1861762},
      {lat: 34.4800159, lng:-113.3403038}];

  var terribleCoords = terriblePlaces[Math.floor(Math.random() * terriblePlaces.length)];
  this.map.flyTo(terribleCoords);

  const jsonString = JSON.stringify(terribleCoords);
  localStorage.setItem('lastvisited', jsonString);
};
