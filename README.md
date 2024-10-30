## Little Documentation

### MaterialBag from File (MaterialBag.readFromFile(String path))
File information:
amount is a double, name is a string, costPerTon is a double, CO2 is a double, Waste is a double

Representation (order and structure):
amount, name, costPerTon, CO2, Waste

Good To Know:
These values must be seperated by a comma, followed by a space.
An EXAMPLE-FILE can be found in src/ressources/TestBag.csv

Additional Information:
If you only want to read in Materials, set amount >= 0 and extract the list of Materials from MaterialBag as following
Material[] materials = MaterialBag.readFromFile(path).materials();

## Questions
### Building
1. When demolishing the building, can I recycle both renovation
renovation construct or only the shell construct?
2. Ageing only affects the renovation construct right?
3. We need a method/attribute/indicator that tells us which apartment to renovate
TODO: Fix age() need 3) for that

### Material and MaterialBag
1. perhaps implement two Cost-Objects: [Buying and Building costs] [Demolishing costs]
