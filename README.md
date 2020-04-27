# Trendyol shopping cart challenge

This repository has the proposed solution for the second question of trendyol recruitment process.

## Architecture

The project is divided into 3 main maven submodules:
- `shopping-cart-interface`: includes the abstract classes and interfaces that is used by implementation module.
- `shopping-cart-implementation`: includes all the logic behind running the application.
- `shopping-cart-app`: an shell interface used to run the application running using spring shell.

The project is following `DDD` structure.

`google-java-format` was used to format the code
[Google Java Style][].

[Google Java Style]: https://google.github.io/styleguide/javaguide.html

## Prerequisite

- Maven 3.5+.
- Java 8.

## Build

In order to get the application running you can go with one of the available options:
*** Note: 
Make sure that you are under the project directory

- ### Maven:
    - First you should run:
     
      ```
      mvn -B -T 4C clean install
      ```
      
    - Then:
    
      ```
      java -jar ./shopping-cart-app/target/shopping-cart-app-1.0-SNAPSHOT.jar
      ```
      
- ### run.sh Script:
     Using the script requires that you have java and maven already installed
     then based on your operating system you either run
     - `run.sh` for linux.
     - `run_win.sh` for windows.
     
- ### Docker:
     To use docker we need first to build the image:      
     
     - Using the following command:
     
       ```
       docker build -t demo .
       ```
       
     - Then run the image:
          
       ```
       docker run -it --name test demo:latest
       ```
       
## Usage

If you've followed the build phase you should have up and running shell application,
In order yo use it you have the following command options:

- `product <options>(--list)`: Lists the available products.
- `campaign <options>(--list)`: Lists the available campaigns.
- `coupon <options>(--list)`: Lists the available coupons.
- `additem <options>(-a|-add)(-q|--quantity)`: Adds a product to cart based on its id. usage example: 

     ```
     additem -a 1 -q 3
     ```
       
- `addcampaign <options>(-a|-add)`: Adds a campaign to cart based on its id.
- `addcoupon <options>(-a|-add)`: Adds a coupon to cart based on its id.
- `show`: prints all selected item, campaigns and coupons.
- `checkout`: completes the purchase by applying the discounts.

## Notes

***Github actions pipeline*** was used as well

Project have unit test coverage of ***100%*** for both line and branch.

***Dependency enforcer and checker*** were both used.

***Checkstyle*** dependency is being used as well.