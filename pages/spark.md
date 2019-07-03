# Map, Filter & Reduce

## Map: apply a function to all items on a list

~~~
def add_one(this_item):
    return this_item + 1

numbers_list = [1, 2, 3, 4, 5]

map(add_one, numbers_list)
~~~

output: [2, 3, 4, 5, 6]

## Filter: create new list for items that meet certain criteria

~~~
def is_even(this_item):
    return this_item % 2 == 0

numbers_list = [1, 2, 3, 4, 5]

filter(is_even, numbers_list)
~~~

output: [2, 4]

## Reduce: perform a computation on a list

~~~
def add_items(first, second):
    return first + second

numbers_list = [1, 2, 3, 4, 5]

reduce(add_items, numbers_list)
~~~

output: 15




