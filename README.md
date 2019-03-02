# Your-Mart API
Your Mart Api is built using Jersey(v 2.25.1), Hibernate(v 5.3.3) and MySQL(v 5.1).The build tool automation used is Apache Maven.

This api handles request for CRUD operations of products and sellers.This api also uses Javax Mail library to send mails if products get rejected by the admin.It also takes care of the session management of the user, if user is authenticated as admin,then user can perform admin related tasks else not.

This api is connected to an Admin Panel that is built using JSP, which handles admin related tasks. It is also connected to Seller Panel built using Angular 6, for seller related tasks.
