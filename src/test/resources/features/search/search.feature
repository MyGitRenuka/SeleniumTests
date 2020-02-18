@search

Feature: Search

Scenario Outline: Search for a product and get the details of a specific product from search results
Given I am in home page
When I search for the product "<product>"
And I click on the product "<product_name>"
Then I can see the price "<price>" and review "<reviews>" of the selected product

Examples:
	| product 		  	| product_name 																																						 																								 | price  		| reviews |
	| pendrive sony 	| Sony 64GB MicroVault Q-Series USB Flash Drive (USM64GQX/B) 	| $50			| average	|
	| headphones bose | Bose SoundLink Around Ear Wireless Headphones II - Black    | $229.00 |	good		|
