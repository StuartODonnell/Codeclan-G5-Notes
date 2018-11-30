const app = function(){

  let coords = [55.857236, -3.166804]
  let coords2 = [55.8554, -3.1602]
  let zoom = 13;
  let containerID = 'main-map'

  const mainMap = new MapWrapper(containerID, coords, zoom);
  mainMap.addMarker(coords);
  mainMap.addMarker(coords2);

}

window.addEventListener('DOMContentLoaded', app);
