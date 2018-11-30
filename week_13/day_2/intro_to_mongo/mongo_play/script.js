use farm;
db.animals.insert({ 
  name: "Erik",
  type: "Polar Bear"
});
db.animals.insert({ //UPDATED
  name: "Fred",
  type: "Fox"
});
db.animals.update(
   { name: "Fred" },
   {
     $set: { type: "Goose" },
   }
)

db.animals.remove( { name: "Fred" } )
db.animals.find();
db.dropDatabase();