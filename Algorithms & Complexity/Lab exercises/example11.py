# Example
# max, min

import operator

print()
print(("python", "java", "scala"))
# max, min:  lexicographic
print('Max (lexigographic): ', max(("python", "java", "scala")))
print('Min (lexigographic): ', min(("python", "java", "scala")))
# max, min: string length
print('Max (length): ', max(("python", "java", "scala"), key=len))
print('Min (length): ', min(("python", "java", "scala"), key=len))

# dict: max key
print()
data = {'a':1000, 'b':3000, 'c': 100}
print('Dictionary: ',  data)
print('Max key value: ', max(data))
print('Entry with max key valye: ', max(data.items(), key=operator.itemgetter(1)))
print('Key of item with with max value: ', max(data.items(), key=operator.itemgetter(1))[0])
print('Value of item with max key value: ', max(data.items(), key=operator.itemgetter(1))[1])