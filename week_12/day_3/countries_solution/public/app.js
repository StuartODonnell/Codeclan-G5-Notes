const app = function(){
  const url = 'https://restcountries.eu/rest/v2'
  makeRequest(url, requestComplete)

  let jsonString = localStorage.getItem('currentCountry');
  let savedCountry = JSON.parse(jsonString)
  countryDetails(savedCountry)
}

const makeRequest = function(url, callback) {
  const request = new XMLHttpRequest();
  request.open("GET", url);
  request.addEventListener('load', callback);
  request.send();
}

const requestComplete = function() {
  if(this.status !== 200) return;
  const jsonString = this.responseText;
  countries = JSON.parse(jsonString);
  populateSelect(countries)
  getCountry(countries)
}

const populateSelect = function(countries) {
  const select = document.getElementById('country-select')
  countries.forEach(function(country, index) {
    let option = document.createElement('option')
    option.innerText = country.name
    option.value = index
    select.appendChild(option)
  })
}

const getCountry = function (countries) {
  const selectedCountry = document.querySelector('select')
  selectedCountry.addEventListener('change', function() {
    let country = countries[this.value]
    saveCountry(country)
    countryDetails(country)
  })
}

const countryDetails = function (country) {
  const div = document.getElementById('country-details')
  clearContent(div)
  const countryName = document.createElement('p')
  countryName.innerText = `Country: ${country.name}`
  const countryPop = document.createElement('p')
  countryPop.innerText = `Population: ${country.population}`
  const countryCapital = document.createElement('p')
  countryCapital.innerText = `Captial City: ${country.capital}`
  const countryFlag = document.createElement('img')
  countryFlag.src = country.flag
  div.appendChild(countryName)
  div.appendChild(countryPop)
  div.appendChild(countryCapital)
  div.appendChild(countryFlag)
  addPin(country)
  borderingCountries(country)
  return div
}

const addPin = function(country){
  const container = document.getElementById('main-map')
  const center = {lat: country.latlng[0], lng: country.latlng[1]}
  const map = new MapWrapper(container, center, 5)
  map.addMarker(center)
}

const saveCountry = function(country){
  const jsonString = JSON.stringify(country);
  localStorage.setItem('currentCountry', jsonString);
}

const borderingCountries = function(country){
  const div = document.getElementById('country-details')
  const borderingC = country.borders
  borderingC.forEach(function(country){
    const button = document.createElement('button')
    button.innerText = country
    button.value = country
    div.appendChild(button)

    button.addEventListener('click', changeCountry)
  })
}

const changeCountry = function(){
  countries.forEach(function(country) {
    if(country.alpha3Code === this.value){
      countryDetails(country)
      saveCountry(country)
    }
  }.bind(this))
}


const clearContent = function(node){
  while (node.hasChildNodes()) {
    node.removeChild(node.lastChild);
  }
}







window.addEventListener('load', app);
