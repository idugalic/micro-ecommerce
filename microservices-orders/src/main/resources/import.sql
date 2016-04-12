insert  into worder(id,ordered_date,status,version) values (1,'2014-07-31 11:38:34',0,1);
insert  into item(id,name,currency,value,quantity,version) values (1,'Test','EUR','12.00',1,1);
insert  into worder_items(worder_id,items_id) values (1,1);
insert  into credit_card(id,card_holder_name,expiry_month,expiry_year,number,version) values (1,'Ivan Dugalic','��\0sr\0org.joda.time.Months6��H�A�\0\0xr\0(org.joda.time.base.BaseSingleFieldPeriod\0\0��NF\0I\0iPeriodxp\0\0\0','��\0sr\0org.joda.time.Years6��H�A�\0\0xr\0(org.joda.time.base.BaseSingleFieldPeriod\0\0��NF\0I\0iPeriodxp\0\0�','1234123412341234',0);

