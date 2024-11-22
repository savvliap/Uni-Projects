# run shortest paths algorithm
import networkx as nx

G = nx.florentine_families_graph()

# Calculate shortest path for specific node pair
path = nx.shortest_path(G, source='Ginori', target='Pazzi')
print(path)

# Calculate all shortest paths from node 'Ginori' to any other node
paths = nx.shortest_path(G, source='Ginori') # target not specified
print(paths['Pazzi'])

# Calculate all shortest paths from any node to node 'Pazzi'
paths2 = nx.shortest_path(G, target='Pazzi') # source not specified
print(paths2['Ginori'])

# Calculate all shortest paths from any node to any node
paths3 = nx.shortest_path(G) # source, target not specified
print(paths3['Ginori']['Pazzi'])
