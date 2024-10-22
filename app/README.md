# Watchlist-App
LOCAL CLASS A MIDDLE LAYER

we need a separration of layers
PRESENTATION LAYER DOES NOT ACCESS OUR DATA LAYER

MIDDLE LAYER IS DOMAIN LAYER
*MODEL
*CompanyListings
*It has the relevant DATA (THIS DATA DOES NOT NEED AN ID)
*This has nothing to do with the data, it only shows the listings.

CompanyEntity
*we take a company listing entity and transform it into company listing model

Data layer can access the domain Layer

*StockDao //Function to interact with the database, insert company listings

*On CSV file we create an abstraction






ComapnyListingsState
*//everything is relevant for the UI

CompanyListingsViewModel
*//helps to call our data layer functions and taking this data from the repository and map the data to the UI

Company Item
*//UI ITEM


Dependency injection (GIVING AN OBJECT its instance variables)
*constructor injection we need (THIS IS A CONCEPT)
STOCK APLICATION a class, also added on the manifest. 


THE DETAILS SCREEENS START HERE, PLUS FETCHING THE JSON DATA WITH THE API 



