import unittest
from sum_of_bits import sum_of_bits


class MyTestCase(unittest.TestCase):
    def test_sum(self):
        a = [1, 0, 1, 0, 1, 0, 1, 1]  # 171
        b = [1, 1, 1, 0, 1, 0, 1, 0]  # 234

        self.assertEqual(sum_of_bits(a, b), 405)


if __name__ == '__main__':
    unittest.main()
