# animal-reservation-system

## Usage

To run tests
```
$ lein test
```

To start the development server (listens on [localhost:3000](http://localhost:3000))
```
$ lein ring server-headless
```

There are some [sample requests](docs/requests.txt) that should work with [restclient](https://github.com/pashky/restclient.el).

## curl examples
```
$ curl http://localhost:3000/reservations
{}

$ curl -XPOST -H "Content-Type: application/json" -d'{"number":3,"date":"2016-06-01","periods":["morning"]}' localhost:3000/reservations
["Brooke","All Star","Misty"]

$ curl http://localhost:3000/reservations
{"2016-06-01":{"morning":[{"name":"Misty","gender":"mare"},{"name":"Brooke","gender":"mare"},{"name":"All Star","gender":"stallion"}]}}
```
