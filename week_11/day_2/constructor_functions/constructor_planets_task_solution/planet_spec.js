var assert = require('assert');
var Planet = require('./planet');
var SolarSystem = require('./solar_system');

describe('Solar System', function() {

  it('should create a planet with the right name', function() {
    var mars = new Planet("Mars");
    assert.equal(mars.getName(), "Mars");
  });

  it('new SolarSystem should have an intial empty array of planets', function() {
    var sol = new SolarSystem("Sol");
    assert.deepEqual(sol.planets, []);
  });

  it('should add some planets', function() {
    var sol = new SolarSystem("sol");
    var earth = new Planet("Earth");
    var mars = new Planet("Mars");
    
    sol.addPlanet(earth);
    sol.addPlanet(mars);
    
    assert.deepEqual(sol.planets, [earth, mars]);
  });
});
