gamedation-api
--------------
The API service for gamedation.

To start in dev mode, just run `sbt ~reStart`. You can change files as its running and the service will automatically recompile.
To build a jar, run `sbt assembly`.

For dev and production mode, you need to create a directory `conf/` and stick a `api.json` in there with the following contents:

```
{
  "db.driver": "com.mysql.jdbc.Driver",
  "db.url": "jdbc:mysql://localhost/gamedation",
  "db.user": "MYSQL_USERNAME",
  "db.password": "MYSQL_PASSWORD"
}
```
