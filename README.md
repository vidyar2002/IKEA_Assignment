# IKEA_Assignment

This project has been designed as 3 modules which can independently run as separate services.
1. Inventory - this handles all the inventory related operations. Here you can add articles
2. ProductManagement - to handle all the product related operations
3. Sales - here we place a sales order which will inturn connect with product management and inventory modules to update the product status and update the inventory stock

All these modules are developed as springboot applications which will run using embedded tomcat server

Possible improvements
----------------------
1. Can add more APIs for product search, product hard delete etc
2. While selling a product, right now from the sales module I am making seperate API calls for products and inventory module to update the product status and stock in the inventory. This is currently done using Rest template.  This can be modified as async API calls from sales module or even set up a messaging system using RabbitMQ and push messages and there by update the inventory system.
