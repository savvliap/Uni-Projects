# Example 06
# Sorting an iterable
# The sorted() method sorts the elements of a given iterable in a specific order - Ascending or Descending.
# https://docs.python.org/3/howto/sorting.html

a = [5, 2, 3, 1, 4];

result01 = sorted(a)
print('01: ', result01)

result02 = sorted(a, reverse = True)
print('02: ', result02)

b = [5, 2, 3, 1, 4]
b.sort()
print(b)

result04 = sorted("This is a test string from Andrew".split(), key=str.lower)
print('04: ', result04)

result05 = sorted("This is a test string from Andrew".split(), key=str)
print('05: ', result05)

# lambda function
student_tuples = [
  ('john', 'A', 15),
  ('jane', 'B', 12),
  ('dave', 'B', 10),
  ('andrea', 'A', 11),
]

result06 = sorted(student_tuples, key=lambda student: student[2])   # sort by age
print('06: ', result06)

result07 = sorted(student_tuples, key=lambda student: student[0])   # sort by name
print('07: ', result07)

from operator import itemgetter, attrgetter

result08 = sorted(student_tuples, key=itemgetter(2))
print('08: ', result08)

class Student:
  def __init__(self, name, grade, age):
     self.name = name
     self.grade = grade
     self.age = age
  def __repr__(self):
     return repr((self.name, self.grade, self.age))

student_objects = [
  Student('john', 'A', 15),
  Student('jane', 'B', 12),
  Student('dave', 'B', 10),
  Student('andrea', 'A', 11),
]

result09 = sorted(student_objects, key=attrgetter('age'))
print('09: ', result09)

# The operator module functions allow multiple levels of sorting. For example, to sort by grade then by age:

result10 = sorted(student_tuples, key=itemgetter(1,2))
print('10: ', result10)


