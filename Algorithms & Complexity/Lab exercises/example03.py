# Draw a graph using different layouts
import networkx as nx
from matplotlib import pyplot as plt

G = nx.florentine_families_graph()

#Draw the graph G with spring_layout (default)
plt.title('Default (spring) layout')
nx.draw(G, with_labels=True)
plt.show()

#Draw the graph G with a circular layout.
plt.title('Circular layout')
nx.draw_circular(G, with_labels=True)
plt.show()

#Draw the graph G with a Kamada-Kawai force-directed layout.
plt.title('Kamada-Kawai layout')
nx.draw_kamada_kawai(G, with_labels=True)
plt.show()

#Draw the graph G with a random layout.
plt.title('Random layout')
nx.draw_random(G, with_labels=True)
plt.show()

#Draw the graph G with a spectral layout.
plt.title('Spectral layout')
nx.draw_spectral(G, with_labels=True)
plt.show()

#Draw the graph G with a spring layout.
plt.title('Spring layout')
nx.draw_spring(G, with_labels=True)
plt.show()

#Draw networkx graph with shell layout.
plt.title('Shell layout')
nx.draw_shell(G, with_labels=True)
plt.show()

