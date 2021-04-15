# play-framework

Testdrive: REST API using the framework

## application

It can be started with `sbt run` ( dev mode ) or `sbt start` ( prod mode ) and it's available at `http://localhost:9000/playfw`

### resources

| verbs  | url                     |
| ------ | ----------------------- |
| GET    | /                       |
| GET    | /account/{no}           |
| GET    | /setting/rateOfInterest |

## commands

### console

```
// format code
sbt compile
sbt test:compile

// create .jar
sbt assembly

// generate coverage report
sbt clean coverage test coverageReport
```


### docker

```
// postgresql
docker run --name postgres -p 5432:5432 -e POSTGRES_PASSWORD=admin -d postgres:11

// redis
docker run --name redis -p 6379:6379 -d redis

// influxdb
// start the container
docker run --name influxdb -p 8086:8086 -p 8083:8083 -e INFLUXDB_ADMIN_ENABLED=true \
-e INFLUXDB_ADMIN_USER=admin -e INFLUXDB_ADMIN_PASSWORD=secret --network mordor -d influxdb

// check databases
curl -G http://localhost:8086/query --data-urlencode "q=CREATE DATABASE mydb"    
curl -G http://localhost:8086/query --data-urlencode "q=SHOW DATABASES" 

// grafana
docker run --name=grafana -p 3000:3000 \
-e "GF_SERVER_ROOT_URL=http://grafana" \
-e "GF_SECURITY_ADMIN_PASSWORD=secret" \
--network mordor -d grafana/grafana
```

## references

### blogs

* [redis named-caches]( https://github.com/KarelCemus/play-redis-samples/tree/master/named_caches )

### docker

* [grafana-image]( http://docs.grafana.org/installation/docker/ )
* [postgresql-image]( https://hub.docker.com/_/postgres )
* [redis-image]( https://hub.docker.com/_/redis )

### libraries

* [cats]( https://typelevel.org/cats/ )
* [lightbend config]( https://github.com/lightbend/config )
* [play-redis]( https://github.com/KarelCemus/play-redis )
* [scalatest]( http://www.scalatest.org/ )

### plugins

* [assembly]( https://github.com/sbt/sbt-assembly )
* [coverage]( https://github.com/scoverage/sbt-scoverage )
* [play-framework]( https://www.playframework.com/documentation/2.7.x/ScalaHome )
* [scalariform]( https://github.com/sbt/sbt-scalariform )