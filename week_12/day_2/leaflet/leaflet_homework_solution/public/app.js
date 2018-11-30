const terribleButtonHandler = function(map){
  map.goSomewhereTerrible();
  document.getElementsByTagName('audio')[0].play();
  return false;
}

const whereAmIButtonHandler = function(map){
  navigator.geolocation.getCurrentPosition(function(position){
    let coords = [position.coords.latitude, position.coords.longitude];
    map.moveMap(coords);
  })
}

const app = function(){

  let coords = JSON.parse(localStorage.getItem('lastvisited')) || [0,0];
  const zoom = 13;
  const map = new MapWrapper('main-map', coords, zoom);

  const wamiButton = document.getElementById('wami-button');
  wamiButton.addEventListener('click', () => whereAmIButtonHandler(map));

  const terribleButton = document.getElementById('terrible-button');
  terribleButton.addEventListener('click', () => terribleButtonHandler(map))

}

window.addEventListener('DOMContentLoaded', app)
