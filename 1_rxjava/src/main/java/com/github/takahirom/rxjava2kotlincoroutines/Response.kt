package com.github.takahirom.rxjava2kotlincoroutines

class Response(val results: List<Person>)
class Person(val name: Name, val email: String)
class Name(val title: String, val first: String, val last: String)
