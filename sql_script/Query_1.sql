select * from blogging_demo.categories;

select * from blogging_demo.comments;

select * from blogging_demo.post;

select * from blogging_demo.`role`;

insert into blogging_demo.`role` values ("2", "USER");

select * from blogging_demo.users;

update blogging_demo.users set `password` = "$2a$10$4gZ6Mqa9LVnYbBOhlnNgLunYRY7Pavb4zZkHsLFQXR/Nl7hYwikny" where `user_id` = "1";

select * from blogging_demo.user_role;

insert into blogging_demo.user_role values ("1", "2");