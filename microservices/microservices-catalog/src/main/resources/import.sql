insert  into products(id,name,currency,value,quantity,version) values (1,'Black Shirt','EUR','12.00',1,1);
insert  into products(id,name,currency,value,quantity,version) values (2,'Red Shirt','EUR','132.00',11,1);
insert  into products(id,name,currency,value,quantity,version) values (3,'Green Shirt','EUR','152.00',10,1);
insert  into products(id,name,currency,value,quantity,version) values (4,'Black Hat','EUR','32.00',12,1);
insert  into products(id,name,currency,value,quantity,version) values (5,'Brown Hat','EUR','19.00',17,1);
insert  into products(id,name,currency,value,quantity,version) values (6,'White Hat','EUR','132.00',1,1);
insert  into products(id,name,currency,value,quantity,version) values (7,'Yellow Shorts','EUR','332.00',12,1);
insert  into products(id,name,currency,value,quantity,version) values (8,'Black Shorts','EUR','198.00',17,1);
insert  into products(id,name,currency,value,quantity,version) values (9,'Pink Shorts','EUR','162.00',1,1);

insert into categories (name, id) values ('Mens',1);
insert into categories (name, id) values ('Womens',2);
insert into categories (name, id) values ('Shirts',10);

insert into products_categories(product_id,category_id) values (1,10);
insert into products_categories(product_id,category_id) values (1,1);
insert into products_categories(product_id,category_id) values (2,10);
insert into products_categories(product_id,category_id) values (2,1);
insert into products_categories(product_id,categoyr_id) values (3,10);
insert into products_categories(product_id,categoyr_id) values (3,11);
insert into products_categories(product_id,category_id) values (4,1);
insert into products_categories(product_id,category_id) values (5,1);
insert into products_categories(product_id,category_id) values (6,1);
insert into products_categories(product_id,category_id) values (7,2);
insert into products_categories(product_id,category_id) values (8,2);
insert into products_categories(product_id,category_id) values (9,2);
