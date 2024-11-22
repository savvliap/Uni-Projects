# list comprehensions
import hellas_cities as hc

# cities graph
G = hc.get_cities_distances_120_graph()

# print edges with weight in the range 118 - 120
for u, v, d in G.edges(data=True):
    # u, v are the endpoints of the edge
    # d is a dictionary with the edge attrbutes
    w = d['weight']
    if 118 <= d['weight'] <= 120:
        print(u,v,d)

print('\nList comprehensions')

# create a list of tuples with all edges
S1 = [(u, v, d['weight']) for u, v, d in G.edges(data=True)]
#print(S1)

# create a list of tuples with edges of specific weights
S2 = [(u, v, d['weight']) for u, v, d in G.edges(data=True) if 118 <= d['weight'] <= 120]
print(S2)

# create a list of tuples with edges to or from Xanthi
S3 = [(u, v, d['weight']) for u, v, d in G.edges(data=True) if 'Xanthi' in { u, v} ]
print(S3)

