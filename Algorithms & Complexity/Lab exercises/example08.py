import networkx as nx
import matplotlib.pyplot as plt

import random

G = nx.gnp_random_graph(20, 0.20, 123)

for (u, v) in G.edges():
    G.edges[u,v]['weight'] = random.randint(1,10)

nx.draw_networkx(G, with_labels=True)
plt.show()

T = nx.tree.minimum_spanning_tree(G, algorithm='kruskal')
nx.draw_networkx(T, with_labels=True)
plt.show()

# Draw the edges of the minimum spanning tree with red color
pos = nx.spring_layout(G)
nx.draw_networkx_nodes(G, pos, with_labels=True)
nx.draw_networkx_edges(G, pos, G.edges, edge_color='b')
edge_list = list(T.edges())
nx.draw_networkx_edges(G, pos, T.edges, edge_color='r')
nx.draw_networkx_labels(G, pos)
plt.show()


