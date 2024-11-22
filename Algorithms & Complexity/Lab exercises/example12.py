# pseudo-random numbers
import random

# creating a list with 10 random integers
L = list()
for i in range(10):
    L.append(random.randint(0, 1000))

print(L)

# Random shuffle the list
random.shuffle(L)
print(L)

# Sort the list
L.sort()
print(L)

# Random shuffle the list
random.shuffle(L)
print(L)

#
print(random.randint(0, 1000))
print(random.randint(0, 1000))
# Initialize the random number generator with a specific number.
# This generates the same pseudo-random number sequence in each execution
random.seed(123)
print(random.randint(0, 1000))
print(random.randint(0, 1000))