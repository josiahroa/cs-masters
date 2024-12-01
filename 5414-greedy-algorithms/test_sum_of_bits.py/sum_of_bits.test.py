import unittest
from sum_of_bits import sum_of_bits


class MyTestCase(unittest.TestCase):
    def test_sum(self):
        a = [1, 1, 0, 1, 0, 1, 0, 1]  #
        b = [0, 1, 0, 1, 0, 1, 1, 1]  #
        c = 0  # initialize carry

        sum_value = sum_of_bits(a, b, c)
        self.assertEqual(sum_value, 9)


if __name__ == '__main__':
    unittest.main()
