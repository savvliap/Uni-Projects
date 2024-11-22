import networkx as nx
import matplotlib.pyplot as plt

G = nx.florentine_families_graph()

T = nx.bfs_tree(G, 'Medici')
nx.draw_networkx(T, with_labels=True)
plt.show()
