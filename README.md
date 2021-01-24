# IKEA_Assignment

This project has been designed as 3 modules which can independently run as separate services.
1. Inventory - this handles all the inventory related operations. Here you can add articles
2. ProductManagement - to handle all the product related operations
3. Sales - here we place a sales order which will inturn connect with product management and inventory modules to update the product status and update the inventory stock

All these modules are developed as springboot applications which will run using embedded tomcat server
