function SolarSystem(name) {
  this.name = name;
  this.planets = [];

  this.addPlanet = function(planet) {
    this.planets.push(planet);
  };

}

console.log(SolarSystem);

module.exports = SolarSystem;
