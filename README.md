# city_project
Before the start you need to:
1. Move to directory docs/database.md.;
2. Create user root in MySQL (localhost:3306);
3. Create password for root qazxsw123;
4. Create scheme according to the file database.md;
5. Run SpringBootCityApplication;


In order to use all functionality you need to authorize (authorization endpoint).
After authorization you will get token.
There are 2 users in the DB:
1. login: admin, password: admin;
2. login: user, password: user;

User can view pages of all cities, search by name and get page of result;
Admin have user functionality plus ability to upload cities from csv file and edit city.
You need to upload cities from csv with admin (at the beginning db is empty);
