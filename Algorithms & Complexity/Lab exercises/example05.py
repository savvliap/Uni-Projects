import networkx as nx
import matplotlib.pyplot as plt

import hellas_cities

#enum_cities = list(enumerate(hellas_cities.Cities))
#dict_cities = {key: city for (key, city) in enum_cities}
# print(dict_cities)

G = hellas_cities.get_cities_distances_120_graph()

p = nx.single_source_dijkstra_path(G, 'Xanthi')
path = p['Patra']
print(len(p.keys()))
print(path)

def path_length(G, path):
    dist = 0
    length = len(path)
    for hop in range(length - 1):
        dist = dist + G[path[hop]][path[hop + 1]]['weight']
    return dist

print(path_length(G, path))

#for city in list(hellas_cities.Cities):
for city in p.keys():
    path = p[city]
    print(city, path_length(G, path))