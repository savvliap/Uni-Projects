import hellas_cities as hc

# The complete cities graph: All city pairs are directly connected
G = hc.get_cities_distances_graph()

# https://www.w3schools.com/python/python_lambda.asp
# A lambda function is a small anonymous function.
# A lambda function can take any number of arguments, but can only have one expression.

# A simple lambda function
x = lambda a : a + 10
print(x(5))

# A lambda function with two parameters
x = lambda a, b : a * b
print(x(5, 6))

# max function using lambda function
max_weight_item = max(dict(G.edges).items(), key=lambda x: x[1]['weight'])
print(max_weight_item)
print(max_weight_item[1]['weight'])

# max function using lambda function
max_weight_edge = max(G.edges(data=True), key=lambda x: x[2]['weight'])
print(max_weight_edge)
print(max_weight_edge[2]['weight'])